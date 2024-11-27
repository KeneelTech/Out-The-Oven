package workingdaycalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WorkingDayUI {
    private final WorkingDayCalendar manager = new WorkingDayCalendar();
    private final JFrame frame = new JFrame("Working Days Calendar");
    private final JPanel calendarPanel = new JPanel();
    private final JLabel statusLabel = new JLabel("Status: Select a date to add a note.");
    private final JLabel monthYearLabel = new JLabel();
    private JButton selectedButton = null;
    private LocalDate currentDate = LocalDate.now();

    public WorkingDayUI() {
        setupUI();
    }

    // Setup the UI components
    private void setupUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 800);
        frame.setLayout(new BorderLayout());

        // Panel for navigation
        JPanel navPanel = new JPanel();
        JButton prevMonthButton = new JButton("Previous Month");
        prevMonthButton.addActionListener(e -> changeMonth(-1));
        JButton nextMonthButton = new JButton("Next Month");
        nextMonthButton.addActionListener(e -> changeMonth(1));

        navPanel.add(prevMonthButton);
        navPanel.add(nextMonthButton);

        // Month-Year label
        monthYearLabel.setFont(new Font("Arial", Font.BOLD, 16));
        updateMonthYearLabel();

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(navPanel, BorderLayout.WEST);
        titlePanel.add(monthYearLabel, BorderLayout.CENTER);

        // Calendar grid panel
        calendarPanel.setLayout(new GridLayout(6, 7)); // 6 rows, 7 columns (for full month view)
        populateCalendar();

        // Action buttons
        JButton confirmButton = new JButton("Confirm Working Days");
        confirmButton.addActionListener(e -> {
            new ConfirmedDaysUI(manager).displayUI();
            frame.setVisible(false);
        });

        JPanel actionPanel = new JPanel();
        actionPanel.add(confirmButton);

        // Add components to frame
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(calendarPanel, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.LINE_START);
        frame.add(statusLabel, BorderLayout.PAGE_END);

        frame.setVisible(true);
    }

    // Update the month and year label
    private void updateMonthYearLabel() {
        String monthYearText = currentDate.getMonth().name() + " " + currentDate.getYear();
        monthYearLabel.setText(monthYearText);
    }

    // Change month when navigating
    private void changeMonth(int monthOffset) {
        currentDate = currentDate.plusMonths(monthOffset);
        updateMonthYearLabel();
        populateCalendar();
    }

    // Populate the calendar with the correct days for the current month
    private void populateCalendar() {
        calendarPanel.removeAll(); // Clear existing calendar
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd");

        // Fill the calendar with buttons for each day
        for (int i = 1; i <= firstDayOfMonth.getDayOfWeek().getValue() - 1; i++) {
            calendarPanel.add(new JLabel("")); // Empty spaces for previous month days
        }

        for (int day = 1; day <= lastDayOfMonth.getDayOfMonth(); day++) {
            LocalDate date = currentDate.withDayOfMonth(day);
            JButton dayButton = new JButton(date.format(format));
            dayButton.setBackground(Color.LIGHT_GRAY);
            dayButton.addActionListener(new DayButtonListener(date));
            calendarPanel.add(dayButton);
        }

        // Fill the rest of the grid with empty labels if necessary
        for (int i = lastDayOfMonth.getDayOfWeek().getValue(); i < 7; i++) {
            calendarPanel.add(new JLabel("")); // Empty spaces for next month days
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    // Day button listener
    private class DayButtonListener implements ActionListener {
        private final LocalDate date;

        public DayButtonListener(LocalDate date) {
            this.date = date;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            selectedButton = (JButton) e.getSource();
            selectedButton.setBackground(Color.YELLOW);

            String selectedDate = selectedButton.getText();
            statusLabel.setText("Status: Selected Date: " + selectedDate);

            if (selectedButton != null) {
                String note = JOptionPane.showInputDialog(
                        frame,
                        "Enter note for this working day for customers to see:" + "\n" + "Day:" + selectedDate,
                        "Add Note",
                        JOptionPane.PLAIN_MESSAGE
                );

                if (note != null && !note.trim().isEmpty()) {
                    manager.addWorkingDay(selectedDate, note);
                    manager.confirmInput();
                    statusLabel.setText("Status: Note saved for day " + selectedDate);
                } else {
                    statusLabel.setText("Status: No note added.");
                    selectedButton.setBackground(Color.LIGHT_GRAY);
                }
            }

        }
    }

    public void displayUI() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

}


package workingdaycalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WorkingDayCalendarUI {
    private final WorkingDayCalendar manager = new WorkingDayCalendar();
    private final JFrame frame = new JFrame("Working Days Calendar");
    private final JPanel calendarPanel = new JPanel();
    private final JLabel statusLabel = new JLabel("Status: Select a date to add a note.");
    private final JLabel monthYearLabel = new JLabel();
    private JButton selectedButton = null;
    private LocalDate currentDate = LocalDate.now();

    public WorkingDayCalendarUI() {
        setupUI();
    }

    // method use to create the layout and design for the ui
    private void setupUI() {
        frame.setSize(700, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel navPanel = new JPanel();
        JButton prevMonthButton = new JButton("Previous Month");
        prevMonthButton.addActionListener(e -> nextMonth(-1));
        JButton nextMonthButton = new JButton("Next Month");
        nextMonthButton.addActionListener(e -> nextMonth(1));

        navPanel.add(prevMonthButton);
        navPanel.add(nextMonthButton);

        monthYearLabel.setFont(new Font("Arial", Font.BOLD, 16));
        updateMonthYearLabel();

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(navPanel, BorderLayout.WEST);
        titlePanel.add(monthYearLabel, BorderLayout.CENTER);

        calendarPanel.setLayout(new GridLayout(6, 7));

        JButton confirmButton = new JButton("Confirm Working Days");
        confirmButton.addActionListener(e -> {
            new ConfirmedDaysUI(manager).displayUI();
            frame.setVisible(false);
        });

        JPanel actionPanel = new JPanel();
        actionPanel.add(confirmButton);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(actionPanel, BorderLayout.LINE_START);
        frame.add(statusLabel, BorderLayout.PAGE_END);
        frame.add(calendarPanel, BorderLayout.CENTER);

        createTheCalendarView();
        frame.setVisible(true);
    }

    //This method is what  brings up the name of the month and year when
    // uer clicks to go forward or back a month
    private void updateMonthYearLabel() {
        String monthYearText = currentDate.getMonth().name() + " " + currentDate.getYear();
        monthYearLabel.setText(monthYearText);
    }

    //This method changes the value in the current date so that the accurate month is what is shown
    //when user clicks next month button
    private void nextMonth(int monthOffset) {
        currentDate = currentDate.plusMonths(monthOffset);
        updateMonthYearLabel();
        createTheCalendarView();
    }


//method to create the calendar view, uses for loop to decide the days of the month shown on the ui
    private void createTheCalendarView() {
        calendarPanel.removeAll();
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd");

        for (int i = 1; i <= firstDayOfMonth.getDayOfWeek().getValue() - 1; i++) {
            calendarPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= lastDayOfMonth.getDayOfMonth(); day++) {
            LocalDate date = currentDate.withDayOfMonth(day);
            JButton dayButton = new JButton(date.format(format));
            dayButton.setBackground(Color.LIGHT_GRAY);
            dayButton.addActionListener(new DayButtonListener(date));
            calendarPanel.add(dayButton);
        }

        for (int i = lastDayOfMonth.getDayOfWeek().getValue(); i < 7; i++) {
            calendarPanel.add(new JLabel(""));
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }


    //button listen class for when a day on the calendar ui is clicked
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


    //Uses the swing library to bring up the ui
    public void displayUI() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

}


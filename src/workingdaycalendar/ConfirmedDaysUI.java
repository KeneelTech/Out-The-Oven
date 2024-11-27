package workingdaycalendar;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConfirmedDaysUI {
    private final WorkingDayCalendar manager;
    private final JFrame frame = new JFrame("Confirmed Working Days");
    private final JPanel calendarPanel = new JPanel();
    private final JLabel statusLabel = new JLabel("Status: Viewing confirmed working days.");
    private final JButton goBackButton = new JButton("Go Back");


    public ConfirmedDaysUI(WorkingDayCalendar manager) {
        this.manager = manager;
        setupUI();
    }

    // Setup the UI components
    private void setupUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        goBackButton.addActionListener(e -> goBackToWorkingDayUI());

        // Panel for the Go Back button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(goBackButton);


        // Calendar grid panel
        calendarPanel.setLayout(new GridLayout(6, 7)); // 6 rows, 7 columns (for full month view)
        populateCalendar();

        // Add components to frame
        frame.add(calendarPanel, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.PAGE_END);
        frame.add(buttonPanel, BorderLayout.NORTH);


        frame.setVisible(true);
    }

    // Populate the calendar with the confirmed days and notes
    private void populateCalendar() {
        calendarPanel.removeAll();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd");


        LocalDate currentDate = LocalDate.now();
        List<WorkingDay> workingDays = manager.getConfirmedWorkingDays(); // This method should return the list of confirmed working days from the database

        // Fill the calendar with the confirmed working days
        for (int i = 1; i <= currentDate.lengthOfMonth(); i++) {
            String date = currentDate.withDayOfMonth(i).format(format);
            JButton dayButton = new JButton(String.valueOf(i));
            dayButton.setBackground(Color.LIGHT_GRAY);


            // Check if this day has been confirmed
            for (WorkingDay workingDay : workingDays) {
                if (workingDay.getDate().equals(date)) {
                    dayButton.setBackground(Color.GREEN);  // Mark confirmed days in green
                    dayButton.addActionListener(e -> showConfirmedNoteDialog(date, workingDay));
                    break;
                }
            }
            calendarPanel.add(dayButton);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    // Display the note for a confirmed day
    private void showConfirmedNoteDialog(String date, WorkingDay workingDay) {
        JOptionPane.showMessageDialog(
                frame,
                "Confirmed Note for " + date + ":\n" + workingDay.getNote(),
                "Confirmed Day",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void goBackToWorkingDayUI() {
        frame.setVisible(false);
        new WorkingDayUI().displayUI();
    }

    public void displayUI() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}
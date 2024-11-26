package workingdaycalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkingDayUI {
    private final WorkingDayCalendar manager = new WorkingDayCalendar();
    private final JFrame frame = new JFrame("Working Days Calendar");
    private final JPanel calendarPanel = new JPanel();
    private final JTextArea noteArea = new JTextArea(3, 20);
    private final JLabel statusLabel = new JLabel("Status: Select a date and add a note.");
    private String selectedDate;

    public WorkingDayUI() {
        setupUI();
    }

    // Setup the UI components
    private void setupUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Calendar grid panel
        calendarPanel.setLayout(new GridLayout(5, 7)); // 5 rows, 7 columns
        populateCalendar();

        // Note input panel
        JPanel notePanel = new JPanel();
        notePanel.setLayout(new BorderLayout());
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        notePanel.add(new JLabel("Enter Note:"), BorderLayout.NORTH);
        notePanel.add(new JScrollPane(noteArea), BorderLayout.CENTER);

        // Action buttons
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new SaveButtonListener());

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            manager.confirmInput();
            statusLabel.setText("Status: Input Confirmed!");
        });

        JPanel actionPanel = new JPanel();
        actionPanel.add(saveButton);
        actionPanel.add(confirmButton);

        // Add components to frame
        frame.add(calendarPanel, BorderLayout.CENTER);
        frame.add(notePanel, BorderLayout.SOUTH);
        frame.add(actionPanel, BorderLayout.NORTH);
        frame.add(statusLabel, BorderLayout.PAGE_END);

        frame.setVisible(true);
    }

    // Populate calendar with buttons
    private void populateCalendar() {
        for (int i = 1; i <= 30; i++) { // Simplified calendar with 30 days
            JButton dayButton = new JButton(String.valueOf(i));
            dayButton.addActionListener(new DayButtonListener());
            calendarPanel.add(dayButton);
        }
    }

    // Day button listener
    private class DayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedDate = ((JButton) e.getSource()).getText();
            statusLabel.setText("Status: Selected Date: " + selectedDate);
        }
    }

    // Save button listener
    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedDate != null && !noteArea.getText().isEmpty()) {
                manager.addWorkingDay(selectedDate, noteArea.getText());
                noteArea.setText("");
                statusLabel.setText("Status: Day " + selectedDate + " saved with note.");
            } else {
                statusLabel.setText("Status: Please select a date and add a note.");
            }
        }
    }

    public void displayUI() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}

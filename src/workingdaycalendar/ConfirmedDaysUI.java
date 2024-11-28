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

    private final LocalDate currentDate = LocalDate.now();



    public ConfirmedDaysUI(WorkingDayCalendar manager) {
        this.manager = manager;
        setupUI();
    }

    private void setupUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        goBackButton.addActionListener(e -> goBackToWorkingDayUI());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(goBackButton);


        calendarPanel.setLayout(new GridLayout(6, 7));
        populateCalendar();

        frame.add(calendarPanel, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.PAGE_END);
        frame.add(buttonPanel, BorderLayout.NORTH);


        frame.setVisible(true);
    }


    //Puts the calendar information with the jpanels and using method to call data from the db
    // to decide which buttons should turn green to confirm working day
    private void populateCalendar() {
        calendarPanel.removeAll();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd");


        List<WorkingDay> workingDays = manager.getSelectedWorkingDays();

        for (int i = 1; i <= currentDate.lengthOfMonth(); i++) {
            String date = currentDate.withDayOfMonth(i).format(format);
            JButton dayButton = new JButton(String.valueOf(i));
            dayButton.setBackground(Color.LIGHT_GRAY);


            for (WorkingDay workingDay : workingDays) {
                if (workingDay.getDate().equals(date)) {
                    dayButton.setBackground(Color.GREEN);
                    dayButton.addActionListener(e -> showConfirmedNoteDialog(date, workingDay));
                    break;
                }
            }
            calendarPanel.add(dayButton);
        }

    }

    // when user clicks on a day that has a working day, then a note should come up for that day
    private void showConfirmedNoteDialog(String date, WorkingDay workingDay) {
      int option = JOptionPane.showConfirmDialog(
              frame,
              "Confirmed Note for " + date + ":\n" + workingDay.getNote(),
              "Confirmed Day",
              JOptionPane.OK_CANCEL_OPTION
      );

      if(option == JOptionPane.CANCEL_OPTION){
          try {
              manager.removeWorkingDay(date);
          }catch (Exception e){
              System.out.println("The stacktrace error: " + e);
          }
        System.out.println("Data deleted from db");
      }
    }

    //this method goes back to the working day calendar ui
    private void goBackToWorkingDayUI() {
        frame.setVisible(false);
        new WorkingDayCalendarUI().displayUI();
    }

    public void displayUI() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }
}
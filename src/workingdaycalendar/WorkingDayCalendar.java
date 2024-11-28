package workingdaycalendar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkingDayCalendar {
    private final List<WorkingDay> workingDays = new ArrayList<>();
    private final List<WorkingDay> selectedDays = new ArrayList<>();

    private static final String url = "jdbc:mysql://127.0.0.1:3306/out_the_oven?useSSL=false&serverTimezone=UTC";
    private static final String user = "root";
    private static final String pass = "";


    //method to add the date and note selected by user and added to a list which will be use
    // later to add that info into the db
    public void addWorkingDay(String date, String note) {
        workingDays.add(new WorkingDay(date,note));
    }

    
    //This method will save the calendar data to the db once the user clicks a day
    // and enter a note for the day.
    //it first makes a connection
    public void saveToDatabase() {
        try (Connection dbConnection = DriverManager.getConnection(url, user, pass)) {
            String insertStatement = "INSERT INTO calendar_info (date, note) VALUES (?, ?)";
            try (PreparedStatement command = dbConnection.prepareStatement(insertStatement)) {
                for (WorkingDay workingDay : workingDays) {
                    command.setString(1, workingDay.getDate());
                    command.setString(2, workingDay.getNote());
                    command.executeUpdate();
                }
                System.out.println("The day selected has been saved.");
            }
        } catch (SQLException e) {
            System.out.println("The stacktrace error: " + e);
            System.out.println("An error has occurred when trying to save the day selected, try again.");
        }
    }

    //Call this method to saved the info when user selects ok after selecting a working day.
    //and in the terminal check if the correct date and note was saved
    public void confirmInput() {
        for (WorkingDay workingDay : workingDays) {
            System.out.println("Date: " + workingDay.getDate() + ", Note: " + workingDay.getNote());
        }
        saveToDatabase(); 
    }

    
    //This method is for getting the data from the database and showing it to the customers when they 
    // look at the working day calendar the owner set
    public List<WorkingDay> getSelectedWorkingDays() {
        try (Connection dbConnection = DriverManager.getConnection(url, user, pass)) {
            String sql = "SELECT date, note FROM calendar_info";
            try (PreparedStatement pstmt = dbConnection.prepareStatement(sql)) {
                ResultSet result = pstmt.executeQuery();
                while (result.next()) {
                    String date = result.getString("date");
                    String note = result.getString("note");
                    selectedDays.add(new WorkingDay(date, note));
                }
            }
        } catch (SQLException e) {
            System.out.println("The stacktrace error: " + e);
            System.out.println("Error retrieving data from the database.");
        }
        return selectedDays;
    }

    //this method removes a working day from the calendar db
    public void removeWorkingDay(String date){
        try (Connection dbConnection = DriverManager.getConnection(url, user, pass)) {
            String sql = "DELETE FROM calendar_info WHERE date = ?";
            PreparedStatement sqlStatement = dbConnection.prepareStatement(sql);
            sqlStatement.setString(1,date);
            sqlStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting data from the database.");
        }
    }

}



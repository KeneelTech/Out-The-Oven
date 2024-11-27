package workingdaycalendar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkingDayCalendar {
    private final List<WorkingDay> workingDays = new ArrayList<>();


    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/out_the_oven?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public void addWorkingDay(String date, String note) {
        workingDays.add(new WorkingDay(date,note));
    }

    public List<WorkingDay> getWorkingDays() {
        return workingDays;
    }

    public void saveToDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO calendar_info (date, note) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (WorkingDay workingDay : workingDays) {
                    pstmt.setString(1, workingDay.getDate().toString());
                    pstmt.setString(2, workingDay.getNote());
                    pstmt.executeUpdate();
                }
                System.out.println("Working days successfully saved to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error saving data to the database.");
        }
    }

    // Confirm and display input
    public void confirmInput() {
        System.out.println("Input Confirmed!");
        for (WorkingDay workingDay : workingDays) {
            System.out.println("Date: " + workingDay.getDate() + ", Note: " + workingDay.getNote());
        }
        saveToDatabase(); // Save to the database on confirmation
    }

    public List<WorkingDay> getConfirmedWorkingDays() {
        List<WorkingDay> confirmedDays = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT date, note FROM calendar_info";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String date = rs.getString("date");
                    String note = rs.getString("note");
                    confirmedDays.add(new WorkingDay(date, note));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving data from the database.");
        }
        return confirmedDays;
    }

}



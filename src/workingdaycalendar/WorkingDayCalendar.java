package workingdaycalendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class WorkingDayCalendar {
    private final Map<String, String> workingDays = new HashMap<>();

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/out_the_oven?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    // Add a working day with a note to in-memory storage
    public void addWorkingDay(String date, String note) {
        workingDays.put(date, note);
    }

    // Get all working days and notes
    public Map<String, String> getWorkingDays() {
        return workingDays;
    }

    // Save data to MySQL database
    public void saveToDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO calendar_info (date, note) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (Map.Entry<String, String> entry : workingDays.entrySet()) {
                    pstmt.setString(1, entry.getKey());
                    pstmt.setString(2, entry.getValue());
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
        for (Map.Entry<String, String> entry : workingDays.entrySet()) {
            System.out.println("Date: " + entry.getKey() + ", Note: " + entry.getValue());
        }
        saveToDatabase(); // Save to the database on confirmation
    }
}

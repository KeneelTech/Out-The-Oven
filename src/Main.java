import workingdaycalendar.WorkingDayCalendarUI;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        WorkingDayCalendarUI calendar = new WorkingDayCalendarUI();
        calendar.displayUI();

    }
}
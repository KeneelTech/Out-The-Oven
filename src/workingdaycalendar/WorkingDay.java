package workingdaycalendar;

public class WorkingDay {
    private final String date;
    private final String note;

    // Constructor
    public WorkingDay(String date, String note) {
        this.date = date;
        this.note = note;
    }

    // Getters
    public String getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return "WorkingDay{" +
                "date=" + date +
                ", note='" + note + '\'' +
                '}';
    }
}

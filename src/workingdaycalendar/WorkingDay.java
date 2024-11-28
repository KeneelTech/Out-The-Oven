package workingdaycalendar;


//This is just the working day class to make it easier to retrieve data from db
public class WorkingDay {
    private final String date;
    private final String note;

    public WorkingDay(String date, String note) {
        this.date = date;
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

}

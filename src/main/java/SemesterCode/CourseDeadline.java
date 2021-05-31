package SemesterCode;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CourseDeadline {
    private String name;
    private String notes;
    private ZonedDateTime dueDate;
    private String taskId;

    public CourseDeadline(String name, String notes, String dueDate, String timeZone){
        this.name = name;
        this.notes = notes;
        LocalDate dueDT = LocalDate.parse(dueDate);//"year-month-day"
        ZoneId thisTimezone = ZoneId.of(timeZone);
        //set 23:59 as time because google tasks api cannot set day of time for due dates
        ZonedDateTime thisDueDate = ZonedDateTime.of(dueDT, LocalTime.of(23,59), thisTimezone);
        this.dueDate = thisDueDate;

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(thisTimezone);

        String givenID = null;
        try {
            givenID = GoogleServices.createDeadline(name, thisDueDate.format(formatter), notes);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.taskId = givenID;
    }

    public void setDueDate(String newDueDate) {
        LocalDate dueDT = LocalDate.parse(newDueDate);//"year-month-day"
        ZoneId thisTimezone = this.dueDate.getZone();
        //set 23:59 as time because google tasks api cannot set day of time for due dates
        ZonedDateTime thisNewDueDate = ZonedDateTime.of(dueDT, LocalTime.of(23,59), thisTimezone);

        this.dueDate = thisNewDueDate;
    }

    public String toString(){
        return this.name;
    }

}

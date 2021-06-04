package SemesterCode;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CourseDeadline implements java.io.Serializable {
    private String name;
    private Course thisCourse;
    private String notes;
    private ZonedDateTime dueDate;
    private String taskId;
    private String semName;

    public CourseDeadline(String semName, String deadlineName, Course thisCourse, String notes, ZonedDateTime dueDate,
                          String taskId){
        this.name = deadlineName;
        this.notes = notes;
        this.thisCourse = thisCourse;
        this.semName = semName;
        this.dueDate = dueDate;
        this.taskId = taskId;
    }
    //getters
    public String getName(){
        return this.name;
    }

    public String getTaskId(){
        return this.taskId;
    }

    public String getNotes(){
        if(this.notes == null) {
            return null;
        }
        else {
            String[] result = this.notes.split("\n", 2);
            return result[1];
        }
    }

    public String getDueDate(){
        if(this.dueDate == null) {
            return null;
        }
        else {
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd");
            return this.dueDate.format(formatter);
        }
    }


    //setters
    public void setDueDate(String newDueDate) {
        LocalDate dueDT = LocalDate.parse(newDueDate);//"year-month-day"
        ZoneId thisTimezone = this.dueDate.getZone();
        //set 23:59 as time because google tasks api cannot set day of time for due dates
        ZonedDateTime thisNewDueDate = ZonedDateTime.of(dueDT, LocalTime.of(23,59), thisTimezone);

        this.dueDate = thisNewDueDate;

        //format new due date
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(thisTimezone);

        //update in google api
        try {
            GoogleServices.updateDeadline(semName, taskId, null, thisNewDueDate.format(formatter), null);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setName(String newName) {
        this.name = newName;

        //update in google api
        try {
            GoogleServices.updateDeadline(semName, taskId, newName, null, null);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNotes(String newNotes) {
        String fullNewNotes = thisCourse.getCourseCode() + "\n" + newNotes;
        this.notes = fullNewNotes;

        //update in google api
        try {
            GoogleServices.updateDeadline(semName, taskId, null, null, fullNewNotes);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("MM/dd/yyyy");
        return this.name + " due on " + this.dueDate.format(formatter);
    }

}

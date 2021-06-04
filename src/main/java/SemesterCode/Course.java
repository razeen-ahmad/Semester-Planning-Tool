package SemesterCode;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Course implements java.io.Serializable {
    private String courseTitle;
    private String profName;
    private int[] dayInts;//.getDayOfWeek().getValue()
    private String courseDesc;
    private ZonedDateTime courseStartDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private String semEnd;
    private Semester thisSemester;
    private String eventID;
    private ArrayList<CourseDeadline> deadlines;

    public Course(String courseCode, String profName, int[] daysOfWeek, LocalTime start, LocalTime end,
                  String eventID, ZonedDateTime courseStartDay, Semester thisSemester){
        this.courseTitle = courseCode;
        this.profName = profName;
        this.dayInts = daysOfWeek;
        this.courseDesc = "Instructor: " + profName + "\n";
        this.startTime = start;
        this.endTime = end;
        DateTimeFormatter semEndFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        this.semEnd = thisSemester.getEnd().format(semEndFormatter);
        this.eventID = eventID;
        this.courseStartDay = courseStartDay;
        this.thisSemester = thisSemester;
        this.deadlines = new ArrayList<CourseDeadline>();
    }

    //getters
    public String getCourseCode(){
        return this.courseTitle;
    }

    public String getProfName(){
        return this.profName;
    }

    public int[] getDayInts(){
        return this.dayInts;
    }

    public String getCourseDesc(){
        String[] result = this.courseDesc.split("\n", 2);
        return result[1];
    }

    public String getEventID(){
        return this.eventID;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public ArrayList<CourseDeadline> getDeadlines() {
        return this.deadlines;
    }

    //setters
    public void setCourseTitle(String newCourseTitle){
        this.courseTitle = newCourseTitle;

        try {
            GoogleServices.updateCourse(this.eventID, newCourseTitle,null, null,
                    null, null, this.semEnd, this.thisSemester.getTimezone());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public void setProfName(String newProfName){
        this.profName = newProfName;
        setCourseDesc("");
    }

    private void setCourseStartDay(ZonedDateTime newStartDay){
        this.courseStartDay = newStartDay;
    }

    public void setDayInts(int[] newDaysOfWeek){
        this.dayInts = newDaysOfWeek;

        //create days of week string for recurrence rules
        String newCourseDays = Semester.getDaysOfWeek(newDaysOfWeek);

        //get new start day for course, with correct start and end times
        ZonedDateTime newStartDate = Semester.getCourseStart(this.thisSemester.getStart(), newDaysOfWeek);
        this.courseStartDay = newStartDate;

        //get formatted date-time strings for new start day, based on semester start and new days of week
        String newStartDT = Semester.getFormattedDT(courseStartDay, this.startTime);
        String newEndDT = Semester.getFormattedDT(courseStartDay, this.endTime);

        try {
            GoogleServices.updateCourse(this.eventID, null, newStartDT, newEndDT,
                    newCourseDays, null, this.semEnd, this.thisSemester.getTimezone());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public void setCourseDesc(String newDesc){
        String fullNewDesc = "Instructor: " + this.profName + "\n" + newDesc;
        this.courseDesc = fullNewDesc;

        try {
            GoogleServices.updateCourse(this.eventID, null,null, null,
                    null, fullNewDesc, this.semEnd, this.thisSemester.getTimezone());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public void setStartTime(String newBegTime){
        //create new start time
        LocalTime newStartTime = LocalTime.parse(newBegTime);
        this.startTime = newStartTime;

        //get formatted DT string
        String newStartDT = Semester.getFormattedDT(this.courseStartDay, newStartTime);

        try {
            GoogleServices.updateCourse(this.eventID, null, newStartDT, null,
                    null, null, this.semEnd, this.thisSemester.getTimezone());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public void setEndTime(String newStopTime){
        //create new end time
        LocalTime newEndTime = LocalTime.parse(newStopTime);
        this.endTime = newEndTime;

        //get formatted DT string
        String newEndDT = Semester.getFormattedDT(this.courseStartDay, newEndTime);

        //update with google api
        try {
            GoogleServices.updateCourse(this.eventID, null, null, newEndDT,
                    null, null, this.semEnd, this.thisSemester.getTimezone());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    //course deadlines
    public void addDeadline(String name, String dueDate, String notes) {
        LocalDate dueDT = LocalDate.parse(dueDate);//"year-month-day"
        ZoneId thisTimezone = ZoneId.of(this.thisSemester.getTimezone());
        //set 23:59 as time because google tasks api cannot set day of time for due dates
        ZonedDateTime thisDueDate = ZonedDateTime.of(dueDT, LocalTime.of(23,59), thisTimezone);

        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .withZone(thisTimezone);
        String fullNotes = "For: " + this.getCourseCode()+ "\n" + notes;

        String givenID = null;
        try {
            givenID = GoogleServices.createDeadline(this.thisSemester.getName(), name, thisDueDate.format(formatter),
                    fullNotes);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CourseDeadline newDeadline = new CourseDeadline(this.thisSemester.getName(), name, this, fullNotes,
                thisDueDate, givenID);
        this.deadlines.add(newDeadline);
    }

    public void deleteDeadline(int deadlineIndex) {
        String thisDeadlineId = this.deadlines.get(deadlineIndex).getTaskId();
        this.deadlines.remove(deadlineIndex);

        //remove with google api
        try {
            GoogleServices.deleteDeadline(thisDeadlineId, thisSemester.getName());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString(){
        String returnString = "" + courseTitle + " taught by: " + profName;
        return returnString;
    }

}

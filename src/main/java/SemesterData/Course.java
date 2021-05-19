package SemesterData;

import jdk.vm.ci.meta.Local;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Course {
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
        return this.courseDesc;
    }

    public String getEventID(){
        return this.eventID;
    }

    //setters
    public void setCourseTitle(String newCourseTitle){
        this.courseTitle = newCourseTitle;

        try {
            GCalServices.updateCourse(this.eventID, newCourseTitle,null, null,
                    null, null, this.semEnd, this.thisSemester.getTimezone());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public void setProfName(String newProfName){
        this.profName = newProfName;
        setCourseDesc("");
    }

    public void setDayInts(int[] newDaysOfWeek){
        this.dayInts = newDaysOfWeek;

        //create days of week string for recurrence rules
        String newCourseDays = Semester.getDaysOfWeek(newDaysOfWeek);

        //get new start day for course, with correct start and end times
         ZonedDateTime newStartDate = Semester.getCourseStart(this.thisSemester.getStart().getDayOfWeek().getValue(),
                this.thisSemester.getStart(), newDaysOfWeek);
        this.courseStartDay = newStartDate;

        //get formatted date-time strings for new start day, based on semester start and new days of week
        String newStartDT = Semester.getFormattedDT(courseStartDay, this.startTime);
        String newEndDT = Semester.getFormattedDT(courseStartDay, this.endTime);

        try {
            GCalServices.updateCourse(this.eventID, null, newStartDT, newEndDT,
                    newCourseDays, null, this.semEnd, this.thisSemester.getTimezone());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public void setCourseDesc(String newDesc){
        this.courseDesc = "Instructor: " + this.profName + "\n" + newDesc;

        try {
            GCalServices.updateCourse(this.eventID, null,null, null,
                    null, newDesc, this.semEnd, this.thisSemester.getTimezone());
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
            GCalServices.updateCourse(this.eventID, null, newStartDT, null,
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

        try {
            GCalServices.updateCourse(this.eventID, null, null, newEndDT,
                    null, null, this.semEnd, this.thisSemester.getTimezone());
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public String toString(){
        String returnString = "" + courseTitle + " taught by: " + profName + " taught on ";
        for(int i = 0; i < dayInts.length; i++){
            returnString += dayInts[i] + " ";
        }
        return returnString;
    }

}

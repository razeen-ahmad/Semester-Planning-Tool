package SemesterData;

import jdk.vm.ci.meta.Local;

import java.time.LocalTime;

public class Course {
    private String courseTitle;
    private String profName;
    private int[] dayInts;//.getDayOfWeek().getValue()
    private String courseNotes;
    private LocalTime startTime;
    private LocalTime endTime;
    private Semester thisSemester;
    private String eventID;

    public Course(String courseCode, String profName, int[] daysOfWeek, LocalTime start, LocalTime end, String eventID, Semester thisSemester){
        this.courseTitle = courseCode;
        this.profName = profName;
        this.dayInts = daysOfWeek;
        this.courseNotes = "No notes for this course yet";
        this.startTime = start;
        this.endTime = end;
        this.eventID = eventID;
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

    public String getCourseNotes(){
        return this.courseNotes;
    }

    public String getEventID(){
        return this.eventID;
    }
    //setters
    public void setCourseCode(String newCourseCode){
        this.courseTitle = newCourseCode;
    }

    public void setProfName(String newProfName){
        this.profName = newProfName;
    }

    public void setDayInts(int[] newDaysOfWeek){
        this.dayInts = newDaysOfWeek;
    }

    public void setCourseNotes(String newCourseNotes){
        this.courseNotes = newCourseNotes;
    }

    public String toString(){
        String returnString = "" + courseTitle + " taught by: " + profName + " taught on ";
        for(int i = 0; i < dayInts.length; i++){
            returnString += dayInts[i] + " ";
        }
        return returnString;
    }
}

package SemesterData;

import jdk.vm.ci.meta.Local;

import java.time.LocalTime;

public class Course {
    private String courseCode;
    private String profName;
    private int[] dayInts;//.getDayOfWeek().getValue()
    private String courseNotes;
    private LocalTime startTime;
    private LocalTime endTime;
    private Semester thisSemester;

    public Course(String courseCode, String profName, int[] daysOfWeek, LocalTime start, LocalTime end, Semester thisSemester){
        this.courseCode = courseCode;
        this.profName = profName;
        this.dayInts = daysOfWeek;
        this.courseNotes = "No notes for this course yet";
        this.startTime = start;
        this.endTime = end;
        this.thisSemester = thisSemester;
    }

    //getters
    public String getCourseCode(){
        return this.courseCode;
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

    //setters
    public void setCourseCode(String newCourseCode){
        this.courseCode = newCourseCode;
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
        String returnString = "" + courseCode + " taught by: " + profName + " taught on ";
        for(int i = 0; i < dayInts.length; i++){
            returnString += dayInts[i] + " ";
        }
        return returnString;
    }
}

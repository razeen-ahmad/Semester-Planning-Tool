package SemesterData;
import jdk.vm.ci.meta.Local;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public class Semester {
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    protected ZoneId timezone;
    private ArrayList<Course> courses;
    private static final LocalTime BEG_DAY = LocalTime.of(0,0);
    private static final LocalTime END_DAY = LocalTime.of(11,59);
    private static final String[] DAYS_OF_WEEK = new String[]{"SU", "MO", "TU", "WE", "TH", "FR", "SA"};

    public Semester(String startDate, String endDate, String zoneName){
        LocalDate start = LocalDate.parse(startDate);//"year-month-day"
        LocalDate end = LocalDate.parse(endDate);
        ZoneId thisTimezone = ZoneId.of(zoneName);
        this.timezone = thisTimezone;
        this.startDate = ZonedDateTime.of(start, BEG_DAY, thisTimezone);
        this.endDate = ZonedDateTime.of(end, END_DAY, thisTimezone);
        this.courses = new ArrayList<Course>();
    }

    //getters
    public String getStart(){
        return this.startDate.toString();
    }

    public String getEnd(){
        return this.endDate.toString();
    }

    public String getTimezone(){
        return this.timezone.toString();
    }

    public Course getCourse(int courseIndex){
        return this.courses.get(courseIndex);
    }

    //setters
    public void setStartDate(String newStart){
        LocalDate newDate = LocalDate.parse(newStart);
        this.startDate = ZonedDateTime.of(newDate, BEG_DAY, this.timezone);
    }

    public void setEndDate(String newEnd){
        LocalDate newDate = LocalDate.parse(newEnd);
        this.endDate = ZonedDateTime.of(newDate, BEG_DAY, this.timezone);
    }

    public void setTimezone(String newZoneID){
        this.timezone = ZoneId.of(newZoneID);
    }

    private ZonedDateTime getCourseStart(int[] daysOfWeek){
        int semesterStartDay = this.startDate.getDayOfWeek().getValue();
        int courseStartDay = daysOfWeek[0];
        if(semesterStartDay <= daysOfWeek[daysOfWeek.length - 1]){
            for(int i = 0; i < daysOfWeek.length; i++){
                if(daysOfWeek[i] < semesterStartDay){
                    continue;
                }
                else{
                    courseStartDay = daysOfWeek[i];
                    break;
                }
            }
        }
        ZonedDateTime courseStartDate = this.startDate;
        for(int i = 0; i < 7; i++){
            if(courseStartDate.getDayOfWeek().getValue() != courseStartDay){
                courseStartDate = courseStartDate.plusDays(1);
            }
            else{
                break;
            }
        }
        return courseStartDate;
    }

    public void addCourse(String courseCode, String profName, int[] daysOfWeek, String begTime, String stopTime){
        LocalTime startTime = LocalTime.parse(begTime);
        LocalTime endTime = LocalTime.parse(stopTime);
        Course newCourse = new Course(courseCode, profName, daysOfWeek, startTime, endTime, this);
        this.courses.add(newCourse);
        ZonedDateTime courseFirstDay = getCourseStart(daysOfWeek);
        ZonedDateTime firstDayStart = courseFirstDay.with(startTime);
        ZonedDateTime firstDayEnd = courseFirstDay.with(endTime);
        String firstDayStartDT = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(firstDayStart);
        String firstDayEndDT = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(firstDayEnd);
        DateTimeFormatter semEndFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String semEnd = this.endDate.format(semEndFormatter);
        String courseDays = "";
        int i = 0;
        for(; i < daysOfWeek.length - 1; i++){
            int dayIndex = daysOfWeek[i];
            courseDays += DAYS_OF_WEEK[dayIndex] + ",";
        }
        courseDays += DAYS_OF_WEEK[daysOfWeek[i]];
        try {
            GCalServices.createCourse(courseCode, firstDayStartDT, firstDayEndDT, semEnd, this.timezone.getId(), courseDays);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        };
        System.out.println("Added course to calendar");
    }
}

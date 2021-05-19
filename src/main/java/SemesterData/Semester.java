package SemesterData;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


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
    public ZonedDateTime getStart(){
        return this.startDate;
    }

    public ZonedDateTime getEnd(){
        return this.endDate;
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
        this.endDate = ZonedDateTime.of(newDate, END_DAY, this.timezone);
    }

    public void setTimezone(String newZoneID){
        this.timezone = ZoneId.of(newZoneID);
    }

    protected static ZonedDateTime getCourseStart(int semesterStartDay,
                                                ZonedDateTime courseStartDate, int[] daysOfWeek){
        //initialize courseStartDay (day of week index) to the first value in daysOfWeek
        int courseStartDay = daysOfWeek[0];
        //check if semester starts in the middle of the course's weekly occurrences
        if(semesterStartDay <= daysOfWeek[daysOfWeek.length - 1]){
            //get first day of week course will start after semester start day
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

        //iterate for at most a week until we get date for first day of course that matches courseStartDay
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

    protected static String getDaysOfWeek(int[] daysOfWeek){
        //create days of week string for recurrence rules
        String courseDays = "";
        int i = 0;
        //loop through every day of week except the last one
        for(; i < daysOfWeek.length - 1; i++){
            int dayIndex = daysOfWeek[i];
            courseDays += DAYS_OF_WEEK[dayIndex] + ",";
        }
        //add last day of week
        courseDays += DAYS_OF_WEEK[daysOfWeek[i]];

        return courseDays;
    }

    protected static String getFormattedDT(ZonedDateTime day, LocalTime time){
        ZonedDateTime thisDT = day.with(time);
        String formattedStr = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(thisDT);
        return formattedStr;
    }

    public void addCourse(String courseCode, String profName, int[] daysOfWeek, String begTime, String stopTime){
        //get first day of course given start of semester
        ZonedDateTime courseFirstDay = getCourseStart(this.startDate.getDayOfWeek().getValue(),
                this.startDate, daysOfWeek);

        //parse times given in "hh:mm" 24hour time format
        LocalTime startTime = LocalTime.parse(begTime);
        LocalTime endTime = LocalTime.parse(stopTime);

        //create correct date-time format for start/end times for Google API
        String firstDayStartDT = getFormattedDT(courseFirstDay, startTime);
        String firstDayEndDT = getFormattedDT(courseFirstDay, endTime);

        //format semester end date (end of event recurrences)
        DateTimeFormatter semEndFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String semEnd = this.endDate.format(semEndFormatter);

        //create days of week string for recurrence rules
        String courseDays = getDaysOfWeek(daysOfWeek);

        try {
            //use GCalServices to create recurring event in user's primary calendar.
            String eventID = GCalServices.createCourse(courseCode, firstDayStartDT, firstDayEndDT,
                    semEnd, this.timezone.getId(), courseDays, "Instructor: " + profName + "\n");
            //create corresponding course object.
            Course newCourse = new Course(courseCode, profName, daysOfWeek, startTime, endTime,
                    eventID, courseFirstDay, this);
            this.courses.add(newCourse);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        };
        System.out.println("Added course to calendar");
    }
}

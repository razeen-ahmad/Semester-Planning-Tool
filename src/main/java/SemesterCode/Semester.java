package SemesterCode;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Semester implements java.io.Serializable {
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private ArrayList<Course> courses;

    protected final ZoneId TIMEZONE;

    private static final long serialVersionUID = 2825191020787845619L;
    private static final LocalTime BEG_DAY = LocalTime.of(0,0);
    private static final LocalTime END_DAY = LocalTime.of(11,59);
    private static final String[] DAYS_OF_WEEK = new String[]{"SU", "MO", "TU", "WE", "TH", "FR", "SA"};

    public Semester(String semName, String startDate, String endDate, String zoneName){
        LocalDate start = LocalDate.parse(startDate);//"year-month-day"
        LocalDate end = LocalDate.parse(endDate);
        ZoneId thisTimezone = ZoneId.of(zoneName);
        this.name = semName;
        this.TIMEZONE = thisTimezone;
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
        return this.TIMEZONE.toString();
    }

    public String getName(){
        return this.name;
    }

    public Course getCourse(int courseIndex){
        return this.courses.get(courseIndex);
    }

    public ArrayList<Course> getCourseList() {
        return this.courses;
    }

    public String getCourses(){
        String returnString = "Courses in this Semester:\n";
        for(int i = 0; i < this.courses.size(); i++){
            Course thisCourse = this.courses.get(i);
            returnString += thisCourse.toString() + "\n";
        }
        return returnString;
    }

    //setters
    public void setStartDate(String newStart){
        LocalDate newDateStr = LocalDate.parse(newStart);
        ZonedDateTime newStartDate = ZonedDateTime.of(newDateStr, BEG_DAY, this.TIMEZONE);
        this.startDate = newStartDate;
        for(int i = 0; i < this.courses.size(); i++){
            Course thisCourse = this.courses.get(i);
            thisCourse.setDayInts(thisCourse.getDayInts());
        }
    }

    public void setEndDate(String newEnd){
        //get updated zoneddatetime object from inputted new end date
        LocalDate newDateStr = LocalDate.parse(newEnd);
        ZonedDateTime newEndDate = ZonedDateTime.of(newDateStr, END_DAY, this.TIMEZONE);
        this.endDate = newEndDate;

        //get correct semester end date format for updating recurrence rule
        DateTimeFormatter semEndFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String newSemEnd = newEndDate.format(semEndFormatter);

        //update each course event's recurrence rule in google calendar
        for(int i = 0; i < this.courses.size(); i++){
            Course thisCourse = this.courses.get(i);
            String thisCourseID = thisCourse.getEventID();
            String courseDays = getDaysOfWeek(thisCourse.getDayInts());
            try {
                GCalServices.updateCourse(thisCourseID, null, null, null,
                        courseDays, null, newSemEnd, null);
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
    }

    protected static ZonedDateTime getCourseStart(ZonedDateTime semesterStartDate, int[] daysOfWeek){
        //semester start date day of week
        int semesterStartDay = semesterStartDate.getDayOfWeek().getValue();
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
        ZonedDateTime courseStartDate = semesterStartDate;
        for(int i = 0; i < 7; i++){
            int thisStartDay = courseStartDate.getDayOfWeek().getValue();
            if(thisStartDay != courseStartDay){
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
        ZonedDateTime courseFirstDay = getCourseStart(this.startDate, daysOfWeek);

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
                    semEnd, this.TIMEZONE.getId(), courseDays, "Instructor: " + profName + "\n");
            //create corresponding course object.
            Course newCourse = new Course(courseCode, profName, daysOfWeek, startTime, endTime,
                    eventID, courseFirstDay, this);
            this.courses.add(newCourse);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        };
        System.out.println("Added course to calendar");
    }

    public void deleteCourse(int courseIndex){
        if(courseIndex > this.courses.size() - 1 || courseIndex < 0){
            System.out.println("invalid course");
        }
        else{
            Course thisCourse = this.courses.get(courseIndex);
            this.courses.remove(courseIndex);
            try {
                GCalServices.deleteCourse(thisCourse.getEventID());
            } catch (IOException | GeneralSecurityException e) {
                e.printStackTrace();
            }
        }
    }

    //serialize and de-serialize
    public void serializeSem(){
        try{
            String basePath = System.getProperty("user.dir");
            Path filePath = Paths.get(basePath + "/src/main/java/data/" + this.name + ".ser");
            Boolean fileExists = Files.exists(filePath);
            if(!fileExists){
                Files.createFile(filePath);
            }
            FileOutputStream fileOut = new FileOutputStream(filePath.toString());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Semester deserializeSem(String semName){
        Semester loadedInSem = null;
        try{
            String basePath = System.getProperty("user.dir");
            Path newTestPath = Paths.get(basePath + "/src/main/java/data/"+ semName +".ser");
            FileInputStream fileIn = new FileInputStream(newTestPath.toString());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            loadedInSem = (Semester) in.readObject();
            in.close();
            fileIn.close();
            return loadedInSem;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedInSem;
    }
}

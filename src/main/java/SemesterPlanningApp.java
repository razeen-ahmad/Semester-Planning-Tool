import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import SemesterData.*;

public class SemesterPlanningApp {
    public static void main(String[] args){
        System.out.println("running semester planning app");
        Semester thisSem = new Semester("2021-05-20", "2021-06-20", "America/Chicago");
        System.out.println("created semester");
        thisSem.addCourse("TEST COURSE", "Miguel Rdor", new int[]{1,3,5}, "09:45", "11:00");
    }
}

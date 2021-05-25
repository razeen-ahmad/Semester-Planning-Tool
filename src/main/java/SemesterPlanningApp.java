import java.time.DateTimeException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import SemesterData.*;
import java.util.Scanner;

public class SemesterPlanningApp {
    public static void main(String[] args){
        System.out.println("running semester planning app");
        Semester thisSem = new Semester("2021-05-20", "2021-06-20", "America/Chicago");

        thisSem.addCourse("TEST COURSE", "Miguel Rdor", new int[]{1,3,5}, "09:45", "11:00");
        thisSem.addCourse("TEST COURSE2", "Billy", new int[]{2,4}, "13:15", "14:30");

        while(true){//loop to wait for user input before updating event
            System.out.println("Created test events. Type in '1' to delete TEST COURSE " +
                    "(the event on mondays, wednesdays, and fridays");
            Scanner s = new Scanner(System.in);
            if(s.hasNextInt()){
                int chosenMode = s.nextInt();
                if(chosenMode == 1){
                    break;
                }
                else{
                    System.out.println("Invalid entry");
                }
            }
            else{
                System.out.println("Invalid entry");
            }
        }

        thisSem.deleteCourse(0);
        System.out.println("Done");
    }
}

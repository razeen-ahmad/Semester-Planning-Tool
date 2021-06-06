import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import SemesterCode.*;
import GUI.*;

import javax.swing.*;

public class SemesterPlanningApp {
    public static void main(String[] args){
        System.out.println("running semester planning app");

//        Semester thisSem = Semester.deserializeSem("testSem");
//        Course thisCourse = thisSem.getCourse(0);
//        thisSem.serializeSem();

//        Semester newSem = new Semester("testSem", "2021-06-05", "2021-06-20",
//                "America/Chicago");
//        newSem.addCourse("test course 1!", "prof mouse", new int[] {1,3,5}, "12:00", "13:15");
//        newSem.addCourse("test course 2!", "prof duck", new int[] {2,4}, "11:00", "12:15");
//        newSem.getCourse(0).addDeadline("test task 1", "2021-06-08", "task 1 notes");
//        newSem.getCourse(1).addDeadline("test task 2", "2021-06-05", "task 2 notes");
//        newSem.serialize();



//        CourseDeadline thisDeadline = new CourseDeadline("test Sem", "test task",
//                "my notes", "2021-06-07", "America/Chicago");



//        try {
//            String basePath = System.getProperty("user.dir");
//            Path datadir = Paths.get(basePath + "/src/main/java/SavedSemesters");
//
//            Stream<Path> files = Files.list(datadir);
//            System.out.println("done running files.list");
//            Object[] filePaths = files.toArray();
//            String[] fileNames = new String[filePaths.length];
//            for(int i = 0; i < filePaths.length; i++) {
//                Path thisFile = (Path) filePaths[i];
//                String fileName = thisFile.getFileName().toString();
//                String cutFileName = fileName.substring(0, fileName.length() - 4);
//                fileNames[i] = cutFileName;
//            }
//
//            JFrame testSelect = new SemesterSelect(fileNames);
//            testSelect.setVisible(true);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        while(true){//loop to wait for user input before updating event
//            System.out.println("Created test events. Type in '1' to delete TEST COURSE " +
//                    "(the event on mondays, wednesdays, and fridays");
//            Scanner s = new Scanner(System.in);
//            if(s.hasNextInt()){
//                int chosenMode = s.nextInt();
//                if(chosenMode == 1){
//                    break;
//                }
//                else{
//                    System.out.println("Invalid entry");
//                }
//            }
//            else{
//                System.out.println("Invalid entry");
//            }
//        }
//
//        thisSem.deleteCourse(0);
        System.out.println("Done");
    }
}

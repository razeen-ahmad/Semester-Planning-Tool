import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import SemesterCode.*;
import GUI.*;

import javax.swing.*;

public class SemesterPlanningApp {
    public static void main(String[] args){
        System.out.println("running semester planning app");

        CourseDeadline thisDeadline = new CourseDeadline("test task",
                "my notes", "2021-06-07", "America/Chicago");


//        Semester thisSem = Semester.deserializeSem("mySem");
//        Course thisCourse = thisSem.getCourse(0);
//        thisCourse.setCourseTitle("course #1");
//        thisSem.serializeSem();


//        try {
//            String basePath = System.getProperty("user.dir");
//            Path datadir = Paths.get(basePath + "/src/main/java/data");
//
//            Stream<Path> files = Files.list(datadir);
//            System.out.println("done running files.list");
//            Object[] filePaths = files.toArray();
//            String[] fileNames = new String[filePaths.length];
//            for(int i = 0; i < filePaths.length; i++) {
//                Path thisFile = (Path) filePaths[i];
//                String fileName = thisFile.getFileName().toString();
//                String cutFileName = fileName.substring(0, fileName.length() - 4);
//                System.out.println(cutFileName);
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

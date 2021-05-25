import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import SemesterCode.*;

public class SemesterPlanningApp {
    public static void main(String[] args){
        System.out.println("running semester planning app");
        try{
            Semester loadedInSem = null;
            System.out.println("De-Serializing test semester");
            String basePath = System.getProperty("user.dir");
            Path newTestPath = Paths.get(basePath + "/src/main/java/data/testSem.ser");

            FileInputStream fileIn = new FileInputStream(newTestPath.toString());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            loadedInSem = (Semester) in.readObject();
            in.close();
            fileIn.close();

            System.out.println(loadedInSem.getCourses());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//        //create new semester object
//        Semester thisSem = new Semester("2021-05-27", "2021-06-20", "America/Chicago");
//
//        thisSem.addCourse("TEST COURSE1", "Miguel Rdor", new int[]{1,3,5}, "09:45", "11:00");
//        thisSem.addCourse("TEST COURSE2", "Billy", new int[]{2,4}, "13:15", "14:30");


//        try{//try catch block to serialize semester object
//            System.out.println("Serializing test semester");
//            String basePath = System.getProperty("user.dir");
//            Path newTestPath = Paths.get(basePath + "/src/main/java/data/testSem.ser");
//            Files.createFile(newTestPath);
//            FileOutputStream fileOut = new FileOutputStream(newTestPath.toString());
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(thisSem);
//            out.close();
//            fileOut.close();
//            System.out.println("done serializing");
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
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

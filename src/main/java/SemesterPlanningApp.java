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
        SemesterSelect.main(null);
    }
}

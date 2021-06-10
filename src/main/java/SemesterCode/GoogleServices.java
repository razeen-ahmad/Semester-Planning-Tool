package SemesterCode;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.tasks.model.Task;
import com.google.api.services.oauth2.model.Userinfo;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.Oauth2Scopes;
import com.google.api.services.tasks.Tasks;
import com.google.api.services.tasks.TasksScopes;
import com.google.api.services.tasks.model.TaskList;
import com.google.api.services.tasks.model.TaskLists;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class GoogleServices {
    private static final String APPLICATION_NAME = "Semester Planning App";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR_EVENTS, TasksScopes.TASKS,
            Oauth2Scopes.USERINFO_EMAIL);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleServices.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    // Build a new authorized API client service for GCal.
    private static Calendar getCalAPIClientService() throws IOException, GeneralSecurityException{
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }

    // Build a new authorized API client service for Google Tasks.
    private static Tasks getTasksAPIClientService() throws IOException, GeneralSecurityException{
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Tasks service = new Tasks.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }

    // Build a new authorized API client service for Google Tasks.
    private static Oauth2 getOauthAPIClientService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Oauth2 service = new Oauth2.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        return service;
    };

    // function to create a course object and add it to user's primary GCal
    public static String createCourse(String courseCode, String startTime, String endTime, String semEnd,
                                      String timezone, String daysOfWeek, String desc)
            throws IOException, GeneralSecurityException{
        //get api service
        Calendar service = getCalAPIClientService();
        //create new gcal event
        Event thisCourse = new Event();

        //set event details (title, description, start time, end time, timezone, recurrence)
        thisCourse.setSummary(courseCode);
        thisCourse.setDescription(desc);
        DateTime start = DateTime.parseRfc3339(startTime);
        DateTime end = DateTime.parseRfc3339(endTime);
        thisCourse.setStart(new EventDateTime().setDateTime(start).setTimeZone(timezone));
        thisCourse.setEnd(new EventDateTime().setDateTime(end).setTimeZone(timezone));
        thisCourse.setRecurrence(Arrays.asList("RRULE:FREQ=WEEKLY;UNTIL="+ semEnd + ";BYDAY=" + daysOfWeek));

        //add event to user's primary calendar
        thisCourse = service.events().insert("primary", thisCourse).execute();
        return thisCourse.getId();
    }

    // function to update a course object and corresponding event in GCal
    public static void updateCourse(String eventID, String newCourseTitle, String newStartTime, String newEndTime,
                                    String newDaysOfWeek, String newDesc, String semEnd, String timezone)
            throws IOException, GeneralSecurityException{
        //get api service
        Calendar service = getCalAPIClientService();

        //get corresponding gcal event
        Event thisCourse = service.events().get("primary", eventID).execute();

        if(newCourseTitle != null){
            thisCourse.setSummary(newCourseTitle);
        }
        if(newStartTime != null){
            DateTime newStart = DateTime.parseRfc3339(newStartTime);
            thisCourse.setStart(new EventDateTime().setDateTime(newStart).setTimeZone(timezone));
        }
        if(newEndTime != null){
            DateTime newEnd = DateTime.parseRfc3339(newEndTime);
            thisCourse.setEnd(new EventDateTime().setDateTime(newEnd).setTimeZone(timezone));
        }
        if(newDaysOfWeek != null){
            thisCourse.setRecurrence(Arrays.asList("RRULE:FREQ=WEEKLY;UNTIL="+ semEnd + ";BYDAY=" + newDaysOfWeek));
        }
        if(newDesc != null){
            thisCourse.setDescription(newDesc);
        }
        service.events().update("primary", thisCourse.getId(), thisCourse).execute();
    }

    public static void deleteCourse(String eventID) throws IOException, GeneralSecurityException{
        //get api service
        Calendar service = getCalAPIClientService();

        //get corresponding gcal event
        Event thisCourse = service.events().get("primary", eventID).execute();

        //delete course from gcal
        thisCourse.setStatus("cancelled");

        service.events().update("primary", thisCourse.getId(), thisCourse).execute();

    }

    //get tasklist id for semester by finding or creating it
    private static String getTasklistID(Tasks service, String semName)
            throws IOException {
        //get all tasklists from user's google account
        TaskLists result = service.tasklists().list().execute();
        List<TaskList> taskLists = result.getItems();

        //check if tasklist already exists for this semester
        boolean foundTasklist = false;
        String taskListID = null;
        for (TaskList tasklist : taskLists) {
            if(tasklist.getTitle().equals(semName)){
                foundTasklist = true;
                //get tasklist id
                taskListID = tasklist.getId();
            }
        }
        if (!foundTasklist) {
            //create new tasklist for all tasks created through semester planning tool
            TaskList newList = new TaskList();
            newList.setTitle(semName);
            //add to google tasklists
            newList = service.tasklists().insert(newList).execute();
            //get tasklist id
            taskListID = newList.getId();
        }

        return taskListID;
    }

    public static String createDeadline(String semName, String deadlineName, String dueDate, String notes)
            throws GeneralSecurityException, IOException {
        Tasks service = getTasksAPIClientService();
        //create new task
        Task thisTask = new Task();

        //set task details
        thisTask.setTitle(deadlineName);
        thisTask.setNotes(notes);
        thisTask.setDue(dueDate);

        //get tasklist id
        String taskListID = getTasklistID(service, semName);

        thisTask = service.tasks().insert(taskListID, thisTask).execute();
        return thisTask.getId();
    }

    public static void updateDeadline(String semName, String taskId, String newName, String newDueDate, String newNotes)
            throws GeneralSecurityException, IOException {
        Tasks service = getTasksAPIClientService();
        String taskListID = getTasklistID(service, semName);

        //get task from google api
        Task thisTask = service.tasks().get(taskListID, taskId).execute();

        //check which fields being updated
        if(newName != null) {
            thisTask.setTitle(newName);
        }
        if(newDueDate != null) {
            thisTask.setDue(newDueDate);
        }
        if(newNotes != null){
            thisTask.setNotes(newNotes);
        }
        //push changes to google tasks api
        service.tasks().update(taskListID, thisTask.getId(), thisTask).execute();
    }

    public static void deleteDeadline(String taskId, String semName)
            throws GeneralSecurityException, IOException {
        Tasks service = getTasksAPIClientService();
        String taskListID = getTasklistID(service, semName);

        //get task from google api
        Task thisTask = service.tasks().get(taskListID, taskId).execute();

        thisTask.setDeleted(true);

        //push changes to google tasks api
        service.tasks().update(taskListID, thisTask.getId(), thisTask).execute();
    }

    public static void deleteTaskList(String semName)
            throws GeneralSecurityException, IOException {
        Tasks service = getTasksAPIClientService();
        String taskListID = getTasklistID(service, semName);

        service.tasklists().delete(taskListID).execute();
    }

    public static void signIn() throws GeneralSecurityException, IOException {
        getOauthAPIClientService();
    }

    public static void signOut(String userName) throws IOException {
        //delete saved credential file
        String basePath = System.getProperty("user.dir");
        Path filePath = Paths.get(basePath + "/tokens/StoredCredential");
        Files.delete(filePath);

        //check if can delete user's SavedSemester directory
        Path savedUserPath = Paths.get(basePath + "/src/main/java/SavedSemesters/" + userName);
        if(!Files.list(savedUserPath).iterator().hasNext()) {
            //if user's savedsemester directory is empty, then can safely delete it.
            Files.delete(savedUserPath);
        }
    };

    public static String getUserName(boolean withDomainName) throws GeneralSecurityException, IOException {
        Oauth2 service = getOauthAPIClientService();
        Userinfo userInfo = service.userinfo().get().execute();
        String fullEmail = userInfo.getEmail();
        if(withDomainName) {
            return fullEmail;
        }
        else {
            String[] emailSplit = fullEmail.split("@");
            return emailSplit[0];
        }
    }
}
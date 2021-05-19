package SemesterData;

import com.google.api.client.auth.oauth2.Credential;
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
import com.google.api.services.calendar.model.Events;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class GCalServices {
    private static final String APPLICATION_NAME = "Semester Planning App";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR_EVENTS, CalendarScopes.CALENDAR_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GCalServices.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
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

    // Build a new authorized API client service.
    private static Calendar getAPIClientService() throws IOException, GeneralSecurityException{
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }

    // function to create a course object and add it to user's primary GCal
    public static String createCourse(String courseCode, String startTime, String endTime, String semEnd,
                                      String timezone, String daysOfWeek, String desc)
            throws IOException, GeneralSecurityException{
        //get api service
        Calendar service = getAPIClientService();
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
        Calendar service = getAPIClientService();

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
        thisCourse = service.events().update("primary", thisCourse.getId(), thisCourse).execute();
    }

    //this main method from Google- Java Google Calendar Quickstart code
    public static void main(String... args) throws IOException, GeneralSecurityException {
        Calendar service = getAPIClientService();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
    }
}
# Semester-Planning-Tool 

This is a java desktop application that helps users plan out their semesters in Google Calendar and Google Tasks. The application was built using Gradle and the intellij IDE. 
The application uses Google's Oauth client to sign the user into their Google account and push changes via the Calendar and Tasks API. Each semester is locally saved
in a serialized file within the "Saved Semesters" directory, allowing for easy access between sessions. All code pertaining to the objects themselves are found within the 
"Semester Code" Package. All code pertaining to the Java Swing GUI is found within the "GUI" Package. 

The API key and client secret are not included in this repository for security reasons. This information was used to create an executable JAR file locally. You can see a demo
and explanation of the project here: https://youtu.be/Skric_3_g-c

If you have any questions, please feel free to reach out: rahmad875@gmail.com

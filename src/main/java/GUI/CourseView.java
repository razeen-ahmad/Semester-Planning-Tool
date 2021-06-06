package GUI;

import SemesterCode.Course;
import SemesterCode.CourseDeadline;
import SemesterCode.Semester;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Arrays;


public class CourseView {
    protected JPanel CourseView;
    private JButton updateCourse;
    private JButton goBack;
    private JFormattedTextField courseTitleValue;
    private JFormattedTextField profNameValue;
    private JFormattedTextField courseDescValue;
    private JFormattedTextField startTimeValue;
    private JFormattedTextField endTimeValue;
    private JLabel courseTitleLabel;
    private JLabel profNameLabel;
    private JLabel daysOfWeekLabel;
    private JList daysOfWeekList;
    private JLabel descLabel;
    private JLabel startTimeLabel;
    private JLabel endTimeLabel;
    private JLabel courseDeadlinesLabel;
    private JList courseDeadlineList;
    private JButton addDeadline;
    private JButton selectDeadline;
    private JRadioButton startTimeAM;
    private JRadioButton startTimePM;
    private JRadioButton endTimeAM;
    private JRadioButton endTimePM;
    private JLabel CourseViewLabel;
    private JLabel daysOfWeekHelpLabel;
    private JButton deleteDeadline;
    private JLabel timeZoneLabel;

    private static final String[] DAYS_OF_WEEK = new String[]
            {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public CourseView(Course thisCourse, boolean newCourse, Semester thisSem) {
        //get field values from thisCourse
        String courseTitle = thisCourse.getCourseCode();
        String courseProf = thisCourse.getProfName();
        int[] courseDays = thisCourse.getDayInts();
        String courseDesc = thisCourse.getCourseDesc();

        //get text field formatters
        DateFormat timeFormat = new SimpleDateFormat("hh:mm");
        DateFormatter timeFormatter = new DateFormatter(timeFormat);


        //get formatted start/end time, booleans for pm/am
        String courseStartTime;
        String courseEndTime;
        boolean startIsAM;
        boolean endIsAM;
        //format start time to 12 hour
        if (thisCourse.getStartTime().getHour() > 12) {
            courseStartTime = thisCourse.getStartTime().minusHours(12L).toString();
            startIsAM = false;

        } else if (thisCourse.getStartTime().getHour() == 12) {
            courseStartTime = thisCourse.getStartTime().toString();
            startIsAM = false;
        } else if (thisCourse.getStartTime().getHour() == 0) {
            courseStartTime = thisCourse.getStartTime().plusHours(12L).toString();
            startIsAM = true;
        } else {
            courseStartTime = thisCourse.getStartTime().toString();
            startIsAM = true;
        }
        //format end time to 12 hour
        if (newCourse || thisCourse.getEndTime().getHour() > 12) {
            courseEndTime = thisCourse.getEndTime().minusHours(12L).toString();
            endIsAM = false;
        } else if (thisCourse.getEndTime().getHour() == 12) {
            courseEndTime = thisCourse.getEndTime().toString();
            endIsAM = false;
        } else if (thisCourse.getEndTime().getHour() == 0) {
            courseEndTime = thisCourse.getEndTime().plusHours(12L).toString();
            endIsAM = true;
        } else {
            courseEndTime = thisCourse.getEndTime().toString();
            endIsAM = true;
        }

        //intellij form generated java code for swing layout
        CourseView = new JPanel();
        CourseView.setLayout(new GridLayoutManager(15, 9, new Insets(0, 0, 0, 0), -1, -1));
        CourseViewLabel = new JLabel();
        CourseViewLabel.setText("Edit Course Details");
        CourseView.add(CourseViewLabel, new GridConstraints(0, 2, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        courseTitleLabel = new JLabel();
        courseTitleLabel.setText("Course Title:*");
        CourseView.add(courseTitleLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        profNameLabel = new JLabel();
        profNameLabel.setText("Instructor:*");
        CourseView.add(profNameLabel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        daysOfWeekLabel = new JLabel();
        daysOfWeekLabel.setText("Taught On:*");
        CourseView.add(daysOfWeekLabel, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descLabel = new JLabel();
        descLabel.setText("Course Description:");
        CourseView.add(descLabel, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startTimeLabel = new JLabel();
        startTimeLabel.setText("Start Time:*");
        CourseView.add(startTimeLabel, new GridConstraints(6, 1, 2, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endTimeLabel = new JLabel();
        endTimeLabel.setText("End Time:*");
        CourseView.add(endTimeLabel, new GridConstraints(9, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        courseTitleValue = new JFormattedTextField(courseTitle);
        CourseView.add(courseTitleValue, new GridConstraints(1, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        profNameValue = new JFormattedTextField(courseProf);
        CourseView.add(profNameValue, new GridConstraints(2, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        courseDescValue = new JFormattedTextField(courseDesc);
        CourseView.add(courseDescValue, new GridConstraints(5, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        startTimeValue = new JFormattedTextField(timeFormatter);
        CourseView.add(startTimeValue, new GridConstraints(6, 2, 2, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        endTimeValue = new JFormattedTextField(timeFormatter);
        CourseView.add(endTimeValue, new GridConstraints(9, 2, 2, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        daysOfWeekList = new JList(DAYS_OF_WEEK);
        CourseView.add(daysOfWeekList, new GridConstraints(3, 2, 2, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer1 = new Spacer();
        CourseView.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        CourseView.add(spacer2, new GridConstraints(14, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        CourseView.add(spacer3, new GridConstraints(12, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        CourseView.add(spacer4, new GridConstraints(1, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        updateCourse = new JButton();
        updateCourse.setText("Save Course Changes");
        CourseView.add(updateCourse, new GridConstraints(13, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        goBack = new JButton();
        goBack.setText("Go Back");
        CourseView.add(goBack, new GridConstraints(13, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        CourseView.add(spacer5, new GridConstraints(5, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        CourseView.add(spacer6, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        courseDeadlinesLabel = new JLabel();
        courseDeadlinesLabel.setText("Course Deadlines");
        CourseView.add(courseDeadlinesLabel, new GridConstraints(2, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        courseDeadlineList = new JList(thisCourse.getDeadlines().toArray());
        CourseView.add(courseDeadlineList, new GridConstraints(3, 6, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        addDeadline = new JButton();
        addDeadline.setText("Add New Deadline");
        CourseView.add(addDeadline, new GridConstraints(2, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startTimeAM = new JRadioButton();
        startTimeAM.setText("AM");
        CourseView.add(startTimeAM, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startTimePM = new JRadioButton();
        startTimePM.setText("PM");
        CourseView.add(startTimePM, new GridConstraints(8, 3, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endTimeAM = new JRadioButton();
        endTimeAM.setText("AM");
        CourseView.add(endTimeAM, new GridConstraints(11, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endTimePM = new JRadioButton();
        endTimePM.setText("PM");
        CourseView.add(endTimePM, new GridConstraints(11, 3, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        daysOfWeekHelpLabel = new JLabel();
        daysOfWeekHelpLabel.setText("(hold CTRL to select multiple)");
        CourseView.add(daysOfWeekHelpLabel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteDeadline = new JButton();
        deleteDeadline.setText("Delete Selected Deadline");
        CourseView.add(deleteDeadline, new GridConstraints(6, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        selectDeadline = new JButton();
        selectDeadline.setText("Go to Selected Deadline");
        CourseView.add(selectDeadline, new GridConstraints(4, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        timeZoneLabel = new JLabel();
        timeZoneLabel.setText("NOTE: All times are in: " + thisSem.getTimezone());
        CourseView.add(timeZoneLabel, new GridConstraints(12, 2, 1, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        //initialize time fields
        startTimeValue.setText(courseStartTime);
        endTimeValue.setText(courseEndTime);

        //set radio button groups
        ButtonGroup startTimeGroup = new ButtonGroup();
        startTimeGroup.add(startTimeAM);
        startTimeGroup.add(startTimePM);
        ButtonGroup endTimeGroup = new ButtonGroup();
        endTimeGroup.add(endTimeAM);
        endTimeGroup.add(endTimePM);

        //set default radio button selection
        startTimeAM.setSelected(startIsAM);
        startTimePM.setSelected(!startIsAM);
        endTimeAM.setSelected(endIsAM);
        endTimePM.setSelected(!endIsAM);

        //set days of week list selections
        daysOfWeekList.setSelectedIndices(courseDays);

        //check if creating course for first time- do not want to add deadlines null course
        if (newCourse) {
            addDeadline.setEnabled(false);
        }


        //update semester button press, conditional action listeners
        //based on whether creating new or editing existing course
        if (newCourse) {
            updateCourse.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    LocalTime givenStartTime = getStartTime();
                    LocalTime givenEndTime = getEndTime();
                    boolean isNotFilled = checkIfNotFilled(givenStartTime, givenEndTime);
                    if (isNotFilled) {
                        JOptionPane.showMessageDialog(null,
                                "Fill in all * fields" +
                                        "\nCheck that start time and end time are compatible" +
                                        "\n(e.g. end time comes after start time)"
                        );
                    } else {
                        JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(updateCourse);
                        Loading.getLoadingScreen(mainFrame);
                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                //add through Google API
                                thisSem.addCourse(
                                        courseTitleValue.getText(), profNameValue.getText(),
                                        daysOfWeekList.getSelectedIndices(), givenStartTime.toString(),
                                        givenEndTime.toString()
                                );
                                JOptionPane.showMessageDialog(null, "Course Added!");
                                SemesterView.getUpdatedSemView(mainFrame, thisSem);
                            }
                        });
                        t.start();
                    }

                }
            });
        } else {
            updateCourse.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(deleteDeadline);
                    Loading.getLoadingScreen(mainFrame);

                    Thread t = new Thread(new Runnable() {
                        public void run() {
                            LocalTime startTime = getStartTime();
                            LocalTime endTime = getEndTime();
                            boolean isValidDates = checkDateValidity(startTime, endTime);
                            boolean changed = checkAndUpdateChanges(thisCourse, courseStartTime, courseEndTime,
                                    startIsAM, endIsAM, startTime, endTime, isValidDates);
                            if (!isValidDates) {
                                JOptionPane.showMessageDialog(null,
                                        "\nCheck that start time and end time are compatible" +
                                                "\n(e.g. end time comes after start time)."
                                );
                                SemesterView.getSelectedCourseView(mainFrame, thisCourse);
                            } else if (changed) {
                                JOptionPane.showMessageDialog(null, "Course Updated!");
                                getUpdatedCourseView(mainFrame, thisCourse);
                            } else {
                                JOptionPane.showMessageDialog(null, "No updates to be made");
                                SemesterView.getSelectedCourseView(mainFrame, thisCourse);
                            }
                        }

                    });
                    t.start();
                }
            });
        }

        //new deadline button listener
        addDeadline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(addDeadline);
                getCreateDeadlineView(mainFrame, thisCourse);
            }
        });

        //delete button listener and initialization
        deleteDeadline.setEnabled(false);
        deleteDeadline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CourseDeadline selectedDeadline = (CourseDeadline) courseDeadlineList.getSelectedValue();
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete " + selectedDeadline + "?",
                        "Warning", JOptionPane.YES_NO_OPTION);

                if (dialogResult == JOptionPane.YES_OPTION) {
                    JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(deleteDeadline);
                    Loading.getLoadingScreen(mainFrame);

                    Thread t = new Thread(new Runnable() {
                        public void run() {
                            int selectedIndex = courseDeadlineList.getSelectedIndex();
                            thisCourse.deleteDeadline(selectedIndex);
                            JOptionPane.showMessageDialog(null,
                                    "Deadline Deleted and Saved!");
                            getUpdatedCourseView(mainFrame, thisCourse);
                        }

                    });
                    t.start();
                }

            }
        });

        //select deadline button listener and initialization
        selectDeadline.setEnabled(false); //initially, no deadline selected
        selectDeadline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(selectDeadline);
                CourseDeadline selectedDeadline = (CourseDeadline) courseDeadlineList.getSelectedValue();

                getSelectedDeadlineView(mainFrame, selectedDeadline, thisCourse);
            }
        });

        //course deadline list listener
        courseDeadlineList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selectDeadline.setEnabled(true); //enable select button
                deleteDeadline.setEnabled(true); //enable delete button
            }
        });

        //go back button listener
        goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "Any unsaved changes will be lost. Do you still want to go back?",
                        "Warning", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(goBack);
                    SemesterSelect.getSelectedSemView(mainFrame, thisSem);
                }
            }
        });

    }

    protected static void getUpdatedCourseView(JFrame mainFrame, Course thisCourse) {
        Course updatedCourse = thisCourse.serialize();
        JPanel updatedCoursePanel = new CourseView(updatedCourse, false,
                thisCourse.getThisSemester()).CourseView;

        mainFrame.getContentPane().removeAll();
        mainFrame.setContentPane(updatedCoursePanel);
        mainFrame.setSize(900, 450);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private static void getCreateDeadlineView(JFrame mainFrame, Course thisCourse) {
        CourseDeadline nullDeadline = new CourseDeadline(null, null, thisCourse,
                null, null, null);
        JPanel thisPanel = new DeadlineView(nullDeadline, true, thisCourse,
                thisCourse.getThisSemester()).DeadlineView;

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setContentPane(thisPanel);
        mainFrame.setSize(750, 400);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    protected static void getSelectedDeadlineView(JFrame mainFrame, CourseDeadline selectedDeadline,
                                                  Course thisCourse) {
        JPanel courseDeadlinePanel = new DeadlineView(selectedDeadline, false,
                thisCourse, thisCourse.getThisSemester()).DeadlineView;

        mainFrame.getContentPane().removeAll();
        mainFrame.setContentPane(courseDeadlinePanel);
        mainFrame.setSize(750, 400);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private boolean checkIfNotFilled(LocalTime givenStartTime, LocalTime givenEndTime) {
        boolean titleNotFilled = courseTitleValue.getText() == null || courseTitleValue.getText().equals("");
        boolean profNameNotFilled = profNameValue.getText() == null || profNameValue.getText().equals("");
        boolean daysOfWeekNotFilled = Arrays.equals(daysOfWeekList.getSelectedIndices(), new int[]{});
        boolean descNotFilled = courseDescValue.getText() == null;
        boolean startTimeNotFilled = startTimeValue.getText() == null;
        boolean endTimeNotFilled = endTimeValue.getText() == null;
        //check that given start/end times are compatible
        boolean isValidDates = checkDateValidity(givenStartTime, givenEndTime);

        return titleNotFilled || profNameNotFilled || daysOfWeekNotFilled ||
                descNotFilled || startTimeNotFilled || endTimeNotFilled || !isValidDates;
    }

    //function that checks which fields changed compared to serialized course object values
    private boolean checkAndUpdateChanges(Course thisCourse, String courseStartTime, String courseEndTime,
                                          boolean startIsAM, boolean endIsAM, LocalTime startTime,
                                          LocalTime endTime, boolean isValidDates) {
        boolean changed = false;
        if (!courseTitleValue.getText().equals(thisCourse.getCourseCode())) {
            changed = true;
            thisCourse.setCourseTitle(courseTitleValue.getText());
        }
        if (!profNameValue.getText().equals(thisCourse.getProfName())) {
            changed = true;
            thisCourse.setProfName(profNameValue.getText());
        }
        if (!Arrays.equals(daysOfWeekList.getSelectedIndices(), thisCourse.getDayInts())) {
            changed = true;
            thisCourse.setDayInts(daysOfWeekList.getSelectedIndices());
        }
        if (!courseDescValue.getText().equals(thisCourse.getCourseDesc())) {
            changed = true;
            thisCourse.setCourseDesc(courseDescValue.getText());
        }

        //determine if start/end times have been changed
        boolean timeChange = false;
        if (!startTimeValue.getText().equals(courseStartTime) || startTimeAM.isSelected() != startIsAM) {
            changed = true;
            timeChange = true;
        }
        if (!endTimeValue.getText().equals(courseEndTime) || endTimeAM.isSelected() != endIsAM) {
            changed = true;
            timeChange = true;
        }

        //check if valid changes and then make
        if (timeChange && isValidDates) {
            thisCourse.setTimes(startTime.toString(), endTime.toString());
        }

        //boolean whether any fields have been changed and updated
        return changed;
    }

    private LocalTime getStartTime() {
        //get local time for start
        LocalTime startTime = LocalTime.parse(startTimeValue.getText());
        if (!startTimeAM.isSelected() && startTime.getHour() != 12) {
            startTime = startTime.plusHours(12);
        } else if (startTimeAM.isSelected() && startTime.getHour() == 12) {
            startTime = startTime.minusHours(12);
        }

        return startTime;
    }

    private LocalTime getEndTime() {
        //get local time for end
        LocalTime endTime = LocalTime.parse(endTimeValue.getText());
        if (!endTimeAM.isSelected() && endTime.getHour() != 12) {
            endTime = endTime.plusHours(12);
        } else if (endTimeAM.isSelected() && endTime.getHour() == 12) {
            endTime = endTime.minusHours(12);
        }
        return endTime;
    }

    private boolean checkDateValidity(LocalTime startTime, LocalTime endTime) {

        boolean isValid = true;
        if (startTime.isAfter(endTime)) {
            isValid = false;
        } else if (endTime.isBefore(startTime)) {
            isValid = false;
        }

        return isValid;

    }

    public static void main(String[] args) {
        Semester thisSem = Semester.deserialize("testSem");

        Course thisCourse = thisSem.getCourse(0);
        JPanel thisPanel = new CourseView(thisCourse, false, thisSem).CourseView;

//        Course nullCourse = new Course(null, null, new int[]{},
//                LocalTime.parse("12:00"), LocalTime.parse("12:00"),
//                null, null, thisSem);
//        JPanel thisPanel = new CourseView(nullCourse, true, thisSem).CourseView;

        JFrame thisFrame = new JFrame("Semester Planning Tool");
        thisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        thisFrame.setContentPane(thisPanel);
        thisFrame.setSize(900, 450);
        thisFrame.setLocationRelativeTo(null);
        thisFrame.setVisible(true);
    }
}

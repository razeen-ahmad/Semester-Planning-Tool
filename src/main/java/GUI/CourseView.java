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
        courseTitleLabel.setText("Course Title:");
        CourseView.add(courseTitleLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        profNameLabel = new JLabel();
        profNameLabel.setText("Instructor:");
        CourseView.add(profNameLabel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        daysOfWeekLabel = new JLabel();
        daysOfWeekLabel.setText("Taught On:");
        CourseView.add(daysOfWeekLabel, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descLabel = new JLabel();
        descLabel.setText("Course Description:");
        CourseView.add(descLabel, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startTimeLabel = new JLabel();
        startTimeLabel.setText("Start Time:");
        CourseView.add(startTimeLabel, new GridConstraints(6, 1, 2, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endTimeLabel = new JLabel();
        endTimeLabel.setText("End Time:");
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
        selectDeadline.setText("Select Deadline");
        CourseView.add(selectDeadline, new GridConstraints(4, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

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


        //update semester button press, conditional action listeners
        //based on whether creating new or editing existing course
        if (newCourse) {
            updateCourse.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean isNotFilled = checkIfNotFilled();
                    if (isNotFilled) {
                        JOptionPane.showMessageDialog(null, "Fill in all fields");
                    } else {
                        //get localtime of start time value
                        LocalTime realStartTime = LocalTime.parse(startTimeValue.getText());
                        if (!startTimeAM.isSelected()) {
                            realStartTime = realStartTime.plusHours(12);
                        }
                        //get localtime of end time value
                        LocalTime realEndTime = LocalTime.parse(endTimeValue.getText());
                        if (!endTimeAM.isSelected()) {
                            realEndTime = realEndTime.plusHours(12);
                        }

                        //add through Google API
                        thisSem.addCourse(
                                courseTitleValue.getText(), profNameValue.getText(),
                                daysOfWeekList.getSelectedIndices(), realStartTime.toString(),
                                realEndTime.toString()
                        );
                        JOptionPane.showMessageDialog(null, "Successfully created new course");
                    }
                }
            });
        } else {
            updateCourse.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean changed = checkAndUpdateChanges(thisCourse, courseStartTime, courseEndTime,
                            startIsAM, endIsAM);
                    if (changed) {
                        JOptionPane.showMessageDialog(null, "Course Updated!");
                    } else {
                        JOptionPane.showMessageDialog(null, "No updates to be made");
                    }
                }
            });
        }

        //new deadline button listener
        addDeadline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "new deadline to be added!");
            }
        });

        //delete button listener and initialization
        deleteDeadline.setEnabled(false);
        deleteDeadline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CourseDeadline selectedDeadline = (CourseDeadline) courseDeadlineList.getSelectedValue();
                JOptionPane.showMessageDialog(null,
                        "You will delete: " + selectedDeadline.toString());
            }
        });

        //select deadline button listener and initialization
        selectDeadline.setEnabled(false); //initially, no deadline selected
        selectDeadline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CourseDeadline selectedDeadline = (CourseDeadline) courseDeadlineList.getSelectedValue();
                JOptionPane.showMessageDialog(null,
                        "You have selected: " + selectedDeadline.toString());

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
                CourseDeadline selectedDeadline = (CourseDeadline) courseDeadlineList.getSelectedValue();
                JOptionPane.showMessageDialog(null, "Go Back");

            }
        });

    }

    private boolean checkIfNotFilled() {
        boolean titleNotFilled = courseTitleValue.getText() == null || courseTitleValue.getText().equals("");
        boolean profNameNotFilled = profNameValue.getText() == null || profNameValue.getText().equals("");
        boolean daysOfWeekNotFilled = Arrays.equals(daysOfWeekList.getSelectedIndices(), new int[]{});
        boolean descNotFilled = courseDescValue.getText() == null;
        boolean startTimeNotFilled = startTimeValue.getText() == null;
        boolean endTimeNotFilled = endTimeValue.getText() == null;

        return titleNotFilled || profNameNotFilled || daysOfWeekNotFilled ||
                descNotFilled || startTimeNotFilled || endTimeNotFilled;
    }

    //function that checks which fields changed compared to serialized course object values
    private boolean checkAndUpdateChanges(Course thisCourse, String courseStartTime, String courseEndTime,
                                          boolean startIsAM, boolean endIsAM) {
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
        if (!startTimeValue.getText().equals(courseStartTime) || startTimeAM.isSelected() != startIsAM) {
            changed = true;
            //get local time
            LocalTime newStartTime = LocalTime.parse(startTimeValue.getText());
            if (!startTimeAM.isSelected()) {
                newStartTime = newStartTime.plusHours(12);
            }
            thisCourse.setStartTime(newStartTime.toString());
        }
        if (!endTimeValue.getText().equals(courseEndTime) || endTimeAM.isSelected() != endIsAM) {
            changed = true;
            //get local time
            LocalTime newEndTime = LocalTime.parse(endTimeValue.getText());
            if (!startTimeAM.isSelected()) {
                newEndTime = newEndTime.plusHours(12);
            }
            thisCourse.setEndTime(newEndTime.toString());
        }
        return changed;
    }

    public static void main(String[] args) {
        Semester thisSem = Semester.deserialize("testSem");

        Course thisCourse = thisSem.getCourse(0);
        JPanel thisPanel = new CourseView(thisCourse, false, null).CourseView;

//        Course nullCourse = new Course(null, null, new int[]{},
//                LocalTime.parse("12:00"), LocalTime.parse("12:00"),
//                null, null, thisSem);
//        JPanel thisPanel = new CourseView(nullCourse, true, thisSem).CourseView;

        JFrame thisFrame = new JFrame("Semester Planning Tool");
        thisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        thisFrame.setContentPane(thisPanel);
        thisFrame.setSize(750, 450);
        thisFrame.setLocationRelativeTo(null);
        thisFrame.setVisible(true);
    }
}

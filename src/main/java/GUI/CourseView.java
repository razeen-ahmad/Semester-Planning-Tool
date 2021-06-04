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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


public class CourseView extends JFrame {
    private JPanel CourseView;
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

    private static final String[] DAYS_OF_WEEK = new String[]
            {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public CourseView(Course thisCourse, boolean newCourse, Semester thisSem) {
        //JFrame initialization
        super("Semester Planning Tool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(700, 400);

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
        CourseView.setLayout(new GridLayoutManager(16, 9, new Insets(0, 0, 0, 0), -1, -1));
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
        CourseView.add(startTimeLabel, new GridConstraints(6, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endTimeLabel = new JLabel();
        endTimeLabel.setText("End Time:");
        CourseView.add(endTimeLabel, new GridConstraints(10, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        courseTitleValue = new JFormattedTextField(courseTitle);
        CourseView.add(courseTitleValue, new GridConstraints(1, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        profNameValue = new JFormattedTextField(courseProf);
        CourseView.add(profNameValue, new GridConstraints(2, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        courseDescValue = new JFormattedTextField(courseDesc);
        CourseView.add(courseDescValue, new GridConstraints(5, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        startTimeValue = new JFormattedTextField(timeFormatter);
        CourseView.add(startTimeValue, new GridConstraints(6, 2, 2, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        endTimeValue = new JFormattedTextField(timeFormatter);
        CourseView.add(endTimeValue, new GridConstraints(10, 2, 2, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        daysOfWeekList = new JList(DAYS_OF_WEEK);
        CourseView.add(daysOfWeekList, new GridConstraints(3, 2, 2, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer1 = new Spacer();
        CourseView.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        CourseView.add(spacer2, new GridConstraints(15, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        CourseView.add(spacer3, new GridConstraints(13, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        CourseView.add(spacer4, new GridConstraints(1, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        updateCourse = new JButton();
        updateCourse.setText("Save Course Changes");
        CourseView.add(updateCourse, new GridConstraints(14, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        goBack = new JButton();
        goBack.setText("Go Back");
        CourseView.add(goBack, new GridConstraints(14, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        CourseView.add(spacer5, new GridConstraints(5, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        CourseView.add(spacer6, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        courseDeadlinesLabel = new JLabel();
        courseDeadlinesLabel.setText("Course Deadlines");
        CourseView.add(courseDeadlinesLabel, new GridConstraints(2, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        courseDeadlineList = new JList(thisCourse.getDeadlines().toArray());
        CourseView.add(courseDeadlineList, new GridConstraints(3, 6, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        selectDeadline = new JButton();
        selectDeadline.setText("Select Deadline");
        CourseView.add(selectDeadline, new GridConstraints(3, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addDeadline = new JButton();
        addDeadline.setText("Add New Deadline");
        CourseView.add(addDeadline, new GridConstraints(2, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        CourseView.add(spacer7, new GridConstraints(9, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        startTimeAM = new JRadioButton();
        startTimeAM.setText("AM");
        CourseView.add(startTimeAM, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startTimePM = new JRadioButton();
        startTimePM.setText("PM");
        CourseView.add(startTimePM, new GridConstraints(8, 3, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endTimeAM = new JRadioButton();
        endTimeAM.setText("AM");
        CourseView.add(endTimeAM, new GridConstraints(12, 2, 1, 1, GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endTimePM = new JRadioButton();
        endTimePM.setText("PM");
        CourseView.add(endTimePM, new GridConstraints(12, 3, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        daysOfWeekHelpLabel = new JLabel();
        daysOfWeekHelpLabel.setText("(hold CTRL to select multiple)");
        CourseView.add(daysOfWeekHelpLabel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        //set jframe contents
        this.setContentPane(CourseView);

        //initialize time fields
        startTimeValue.setText(courseStartTime);
        endTimeValue.setText(courseEndTime);
//        startTimeValue.addPropertyChangeListener("value", new PropertyChangeListener() {
//            public void propertyChange(PropertyChangeEvent evt) {
//                startTimeValue.getText();
//            }
//        });
//        endTimeValue.addPropertyChangeListener("value", new PropertyChangeListener() {
//            public void propertyChange(PropertyChangeEvent evt) {
//                System.out.println(endTimeValue.getText());
//            }
//        });

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
                    if (courseTitleValue.getText() == null || courseTitleValue.getText().equals("") ||
                            profNameValue.getText() == null || profNameValue.getText().equals("") ||
                            Arrays.equals(daysOfWeekList.getSelectedIndices(), new int[]{}) ||
                            courseDescValue.getText() == null || startTimeValue.getText() == null ||
                            endTimeValue.getText() == null) {
                        JOptionPane.showMessageDialog(null, "Fill in all fields");
                    } else {
                        LocalTime realStartTime = LocalTime.parse(startTimeValue.getText());
                        if (!startTimeAM.isSelected()) {
                            realStartTime = realStartTime.plusHours(12);
                        }
                        LocalTime realEndTime = LocalTime.parse(endTimeValue.getText());
                        if (!startTimeAM.isSelected()) {
                            realEndTime = realEndTime.plusHours(12);
                        }
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
                    boolean changed = false;
                    //check which fields changed compared to serialized course object values
                    if (!courseTitleValue.getText().equals(courseTitle)) {
                        changed = true;
                        thisCourse.setCourseTitle(courseTitleValue.getText());
                    }
                    if (!profNameValue.getText().equals(courseProf)) {
                        changed = true;
                        thisCourse.setProfName(profNameValue.getText());
                    }
                    if (!Arrays.equals(daysOfWeekList.getSelectedIndices(), thisCourse.getDayInts())) {
                        changed = true;
                        thisCourse.setDayInts(daysOfWeekList.getSelectedIndices());
                    }
                    if (!courseDescValue.getText().equals(courseDesc)) {
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

                    if (changed) {
                        JOptionPane.showMessageDialog(null, "Course Updated!");
                    } else {
                        JOptionPane.showMessageDialog(null, "No updates to be made");
                    }
                }
            });
        }

        //add new deadline button press
        addDeadline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "new deadline to be added!");
            }
        });

        //select deadline button
        selectDeadline.setEnabled(false); //initially, no deadline selected
        courseDeadlineList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selectDeadline.setEnabled(true); //enable select button
            }
        });
        selectDeadline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CourseDeadline selectedDeadline = (CourseDeadline) courseDeadlineList.getSelectedValue();
                JOptionPane.showMessageDialog(null,
                        "You have selected: " + selectedDeadline.toString());

            }
        });


    }

    public static void main(String[] args) {
        Semester thisSem = Semester.deserializeSem("testSem");

//        Course thisCourse = thisSem.getCourse(0);
//
//        JFrame thisFrame = new CourseView(thisCourse, false, null);

        Course nullCourse = new Course(null, null, new int[]{},
                LocalTime.parse("12:00"), LocalTime.parse("12:00"),
                null, null, thisSem);

        JFrame thisFrame = new CourseView(nullCourse, true, thisSem);

        thisFrame.setVisible(true);
    }
}

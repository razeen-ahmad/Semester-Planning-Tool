package GUI;

import SemesterCode.Course;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SemesterView extends JFrame {
    private JList courseList;
    private JButton goBack;
    private JButton selectCourse;
    private JLabel semName;
    private JLabel semEndLabel;
    private JLabel semZoneLabel;
    private JLabel semEndValue;
    private JLabel semZoneValue;
    private JLabel courseListLabel;
    private JLabel semStartValue;
    private JLabel semStartLabel;
    private JPanel semView;
    private JLabel semDetails;
    private JButton addCourse;

    private Course selectedCourse;

    public SemesterView(Semester sem) {
        super("Semester Planning Tool");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(300, 300);

        semView = new JPanel();
        semView.setLayout(new GridLayoutManager(6, 12, new Insets(0, 0, 0, 0), -1, -1));
        semName = new JLabel();
        semName.setText(sem.getName());
        semView.add(semName, new GridConstraints(0, 1, 1, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        courseList = new JList(sem.getCourseList().toArray());
        courseList.setSelectionMode(0);
        semView.add(courseList, new GridConstraints(2, 5, 3, 5, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        semEndValue = new JLabel();
        semEndValue.setText(sem.getEnd().format(dateFormatter));
        semView.add(semEndValue, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semZoneValue = new JLabel();
        semZoneValue.setText(sem.getTimezone());
        semView.add(semZoneValue, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        courseListLabel = new JLabel();
        courseListLabel.setText("Courses in " + sem.getName() + ":");
        semView.add(courseListLabel, new GridConstraints(1, 5, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semStartValue = new JLabel();
        semStartValue.setText(sem.getStart().format(dateFormatter));
        semView.add(semStartValue, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semZoneLabel = new JLabel();
        semZoneLabel.setText("Semester Time Zone:");
        semView.add(semZoneLabel, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semEndLabel = new JLabel();
        semEndLabel.setText("Semester End Date:");
        semView.add(semEndLabel, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semStartLabel = new JLabel();
        semStartLabel.setText("Semester Start Date:");
        semView.add(semStartLabel, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        semView.add(spacer1, new GridConstraints(5, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        semView.add(spacer2, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        semView.add(spacer3, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        semView.add(spacer4, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        semView.add(spacer5, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        semView.add(spacer6, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        semView.add(spacer7, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        semView.add(spacer8, new GridConstraints(3, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        semDetails = new JLabel();
        semDetails.setText(sem.getName() + " Details:");
        semView.add(semDetails, new GridConstraints(1, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        semView.add(spacer9, new GridConstraints(0, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        semView.add(spacer10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        selectCourse = new JButton();
        selectCourse.setText("Go to Selected Course");
        semView.add(selectCourse, new GridConstraints(5, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        goBack = new JButton();
        goBack.setText("Go Back");
        semView.add(goBack, new GridConstraints(5, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addCourse = new JButton();
        addCourse.setText("Add New Course");
        semView.add(addCourse, new GridConstraints(2, 10, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));


        selectedCourse = null;
        selectCourse.setEnabled(false);
        selectCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You have selected " + selectedCourse.toString());
            }
        });

        courseList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selectCourse.setEnabled(true);
                selectedCourse = (Course) courseList.getSelectedValue();
            }
        });

        this.setContentPane(semView);
    }

    public static void main(String[] args) {
        Semester thisSem = Semester.deserializeSem("mySem");

        JFrame semViewFrame = new SemesterView(thisSem);
        semViewFrame.setVisible(true);
    }
}

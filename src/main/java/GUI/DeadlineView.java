package GUI;

import SemesterCode.Course;
import SemesterCode.CourseDeadline;
import SemesterCode.Semester;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DeadlineView {
    private JFormattedTextField deadlineNameValue;
    private JFormattedTextField deadlineNotesValue;
    private JFormattedTextField deadlineDueDateValue;
    private JButton updateDeadlineButton;
    private JButton goBackButton;
    private JLabel deadlineNameLabel;
    private JLabel deadlineNotesLabel;
    private JLabel deadlineDueDateLabel;
    private JLabel deadlineViewTitle;
    protected JPanel DeadlineView;
    private JLabel deadlineDueDateHelperLabel;
    private JLabel deadlineNotesHelperLabel;


    public DeadlineView(CourseDeadline thisDeadline, boolean newDeadline, Course thisCourse, Semester thisSem) {

        //create due date formatter
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dateFormat = new DateFormatter(simpleDateFormat);

        //intellij generated java swing layout for JPanel
        DeadlineView = new JPanel();
        DeadlineView.setLayout(new GridLayoutManager(7, 7, new Insets(0, 0, 0, 0), -1, -1));
        deadlineViewTitle = new JLabel();
        deadlineViewTitle.setText("Deadline Details");
        DeadlineView.add(deadlineViewTitle, new GridConstraints(0, 0, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        DeadlineView.add(spacer1, new GridConstraints(1, 6, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        deadlineNameLabel = new JLabel();
        deadlineNameLabel.setText("Deadline Name:*");
        DeadlineView.add(deadlineNameLabel, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deadlineNotesLabel = new JLabel();
        deadlineNotesLabel.setText("Deadline Notes:");
        DeadlineView.add(deadlineNotesLabel, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deadlineDueDateLabel = new JLabel();
        deadlineDueDateLabel.setText("Due Date:*");
        DeadlineView.add(deadlineDueDateLabel, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        DeadlineView.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        DeadlineView.add(spacer3, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        deadlineNameValue = new JFormattedTextField();
        DeadlineView.add(deadlineNameValue, new GridConstraints(1, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        deadlineNotesValue = new JFormattedTextField();
        DeadlineView.add(deadlineNotesValue, new GridConstraints(2, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        deadlineDueDateValue = new JFormattedTextField(dateFormat);
        DeadlineView.add(deadlineDueDateValue, new GridConstraints(4, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        deadlineDueDateHelperLabel = new JLabel();
        deadlineDueDateHelperLabel.setText("Enter date as: 'YYYY-MM-DD' (e.g Jan. 1, 2010 = '2010-01-01')");
        DeadlineView.add(deadlineDueDateHelperLabel, new GridConstraints(5, 3, 1, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        updateDeadlineButton = new JButton();
        updateDeadlineButton.setText("Save Deadline Changes");
        DeadlineView.add(updateDeadlineButton, new GridConstraints(6, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        goBackButton = new JButton();
        goBackButton.setText("Go Back");
        DeadlineView.add(goBackButton, new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deadlineNotesHelperLabel = new JLabel();
        deadlineNotesHelperLabel.setText("Notes will automatically include corresponding course in Google Tasks");
        DeadlineView.add(deadlineNotesHelperLabel, new GridConstraints(3, 3, 1, 2, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        //initialize deadline values
        String origDeadlineName = thisDeadline.getName();
        String origDeadlineNotes = thisDeadline.getNotes();
        String origDeadlineDueDate = thisDeadline.getDueDate();
        deadlineNameValue.setText(origDeadlineName);
        deadlineNotesValue.setText(origDeadlineNotes);
        deadlineDueDateValue.setText(origDeadlineDueDate);

        //go back button listener
        goBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "Any unsaved changes will be lost. Do you still want to go back?",
                        "Warning", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(goBackButton);
                    SemesterView.getSelectedCourseView(mainFrame, thisCourse);
                }
            }
        });

        //update deadline button listener
        if (!newDeadline) { //if updating existing task
            updateDeadlineButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(updateDeadlineButton);
                    Loading.getLoadingScreen(mainFrame);

                    Thread t = new Thread(new Runnable() {
                        public void run() {
                            boolean changed = checkAndUpdateChanges(thisDeadline, origDeadlineName,
                                    origDeadlineNotes, origDeadlineDueDate);

                            if (changed) {
                                JOptionPane.showMessageDialog(null,
                                        "Deadline Updated and Saved!");
                                CourseView.getUpdatedCourseView(mainFrame, thisCourse);
                            } else {
                                JOptionPane.showMessageDialog(null, "No changes to update");
                                CourseView.getSelectedDeadlineView(mainFrame, thisDeadline, thisCourse);
                            }
                        }
                    });
                    t.start();
                }
            });
        } else { //if creating new task
            updateDeadlineButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean isNotFilled = checkIfNotFilled();
                    if (isNotFilled) {
                        JOptionPane.showMessageDialog(null, "Fill in all * fields");
                    } else {
                        JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(updateDeadlineButton);
                        Loading.getLoadingScreen(mainFrame);

                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                //update in Google API
                                thisCourse.addDeadline(deadlineNameValue.getText(), deadlineDueDateValue.getText(),
                                        deadlineNotesValue.getText());
                                JOptionPane.showMessageDialog(null,
                                        "New Deadline Created and Saved!");
                                CourseView.getUpdatedCourseView(mainFrame, thisCourse);
                            }
                        });
                        t.start();
                    }
                }
            });
        }

    }

    private boolean checkIfNotFilled() {
        boolean nameNotFilled = deadlineNameValue.getText().equals("") || deadlineNameValue.getText() == null;
        boolean notesNotFilled = deadlineNotesValue.getText() == null;
        boolean dueDateNotFiled = deadlineDueDateValue.getText().equals("") || deadlineDueDateValue.getText() == null;

        return nameNotFilled || notesNotFilled || dueDateNotFiled;
    }

    private boolean checkAndUpdateChanges(CourseDeadline thisDeadline, String origDeadlineName,
                                          String origDeadlineNotes, String origDeadlineDueDate) {
        boolean changed = false;
        if (!deadlineNameValue.getText().equals(origDeadlineName)) {
            changed = true;
            thisDeadline.setName(deadlineNameValue.getText());
        }
        if (!deadlineNotesValue.getText().equals(origDeadlineNotes)) {
            changed = true;
            thisDeadline.setNotes(deadlineNotesValue.getText());
        }
        if (!deadlineDueDateValue.getText().equals(origDeadlineDueDate)) {
            changed = true;
            thisDeadline.setDueDate(deadlineDueDateValue.getText());
        }

        return changed;
    }

    public static void main(String[] args) {
        Semester thisSem = Semester.deserialize("testSem");
        Course thisCourse = thisSem.getCourse(0);
//        CourseDeadline thisDeadline = thisCourse.getDeadlines().get(0);
//        JPanel thisPanel = new DeadlineView(thisDeadline, false, null).DeadlineView;


        CourseDeadline nullDeadline = new CourseDeadline(null, null, thisCourse,
                null, null, null);
        JPanel thisPanel = new DeadlineView(nullDeadline, true, thisCourse, thisSem).DeadlineView;

        JFrame thisFrame = new JFrame("Semester Planning Tool");
        thisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        thisFrame.setContentPane(thisPanel);
        thisFrame.setSize(750, 400);
        thisFrame.setLocationRelativeTo(null);
        thisFrame.setVisible(true);
    }
}

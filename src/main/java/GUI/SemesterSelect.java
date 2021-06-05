package GUI;

import SemesterCode.Semester;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class SemesterSelect {
    protected JPanel selectPanel;
    private JComboBox semSelect;
    private JButton semSelectButton;
    private JButton addSem;
    private JLabel semSelectHelpLabel;
    private JTextArea introText;
    private JLabel SemesterSelectTitle;
    private String semName;

    public SemesterSelect() {
        //get existing serialized semesters
        String[] semNames = getSavedSems();

        //string for intro body text
        String introPara =
                "   This is a java application to help students plan their semesters with" +
                        "\nGoogle Calendar and Google Tasks. Semesters mark the start and" +
                        "\nend dates for courses, where courses are implemented as recurring" +
                        "\nevents in Google Calendar. Each course has its own list of deadlines," +
                        "\nwhich are implemented as tasks in Google Tasks. To start, select or" +
                        "\ncreate a semester to add courses and deadlines!";

        //intellij generated swing layout code for this pane
        selectPanel = new JPanel();
        selectPanel.setLayout(new GridLayoutManager(5, 16, new Insets(0, 0, 0, 0), -1, -1));
        final Spacer spacer1 = new Spacer();
        selectPanel.add(spacer1, new GridConstraints(1, 15, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        selectPanel.add(spacer2, new GridConstraints(4, 1, 1, 10, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        selectPanel.add(spacer3, new GridConstraints(4, 14, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        semSelect = new JComboBox(semNames);
        selectPanel.add(semSelect, new GridConstraints(3, 1, 1, 10, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semSelectHelpLabel = new JLabel();
        semSelectHelpLabel.setText("Select Semester");
        selectPanel.add(semSelectHelpLabel, new GridConstraints(2, 1, 1, 10, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        introText = new JTextArea(introPara);
        introText.putClientProperty("html.disable", Boolean.FALSE);
        selectPanel.add(introText, new GridConstraints(1, 1, 1, 10, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer4 = new Spacer();
        selectPanel.add(spacer4, new GridConstraints(1, 14, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel SemesterSelectTitle = new JLabel();
        SemesterSelectTitle.setText("Welcome to the Semester Planning Tool!");
        selectPanel.add(SemesterSelectTitle, new GridConstraints(0, 1, 1, 10, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addSem = new JButton();
        addSem.setText("Create New Semester");
        selectPanel.add(addSem, new GridConstraints(2, 11, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        selectPanel.add(spacer5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        semSelectButton = new JButton();
        semSelectButton.setText("Select Semester");
        selectPanel.add(semSelectButton, new GridConstraints(4, 11, 1, 3, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        //check if there are any semesters to select from
        if (semNames.length > 0) {
            semName = semNames[0];
        }
        if (semNames.length == 0) {
            semSelect.setEnabled(false);
            semSelectButton.setEnabled(false);
        }

        //add listener to semester select dropdown menu
        semSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String selectedName = (String) cb.getSelectedItem();
                semName = selectedName;
            }
        });

        //add listener to select semester button
        semSelectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You have chosen semester: " + semName);
                Semester selectedSem = Semester.deserialize(semName);
                JPanel semViewPanel = new SemesterView(selectedSem).semView;
                JFrame f1 = (JFrame) SwingUtilities.windowForComponent(semSelectButton);
                f1.getContentPane().removeAll();
                f1.setContentPane(semViewPanel);
                f1.pack();
                f1.setVisible(true);
            }
        });

        //add listener to add semester button
        addSem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Create new semester");
            }
        });

        //make head text area non-editable
        introText.setEditable(false);
        introText.setBackground(new Color(0, 0, 0, 0));
    }

    protected static String[] getSavedSems() {
        String[] fileNames = null;

        try {
            //get all serialized semester objects
            String basePath = System.getProperty("user.dir");
            Path datadir = Paths.get(basePath + "/src/main/java/data");

            Stream<Path> files = Files.list(datadir);
            Object[] filePaths = files.toArray();
            fileNames = new String[filePaths.length];
            for (int i = 0; i < filePaths.length; i++) {
                Path thisFile = (Path) filePaths[i];
                String fileName = thisFile.getFileName().toString();
                String cutFileName = fileName.substring(0, fileName.length() - 4);
                fileNames[i] = cutFileName;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileNames;
    }

    public static void main(String[] args) {

        JFrame thisFrame = new JFrame("Semester Planning Tool");
        SemesterSelect thisPanel = new SemesterSelect();

        thisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        thisFrame.setContentPane(thisPanel.selectPanel);
        thisFrame.setSize(650, 350);
        thisFrame.setLocationRelativeTo(null);
        thisFrame.setVisible(true);
    }
}

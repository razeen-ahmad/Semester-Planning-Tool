package GUI;

import SemesterCode.GoogleServices;
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
import java.security.GeneralSecurityException;
import java.util.stream.Stream;

public class SemesterSelect {
    protected JPanel selectPanel;
    private JComboBox semSelect;
    private JButton semSelectButton;
    private JButton addSem;
    private JLabel semSelectHelpLabel;
    private JTextArea introText;
    private JLabel SemesterSelectTitle;
    private JButton deleteSem;
    private JButton signOut;

    private String semName;
    private String username;

    public SemesterSelect() {
        //get username
        try {
            username = GoogleServices.getUserName();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //get existing serialized semesters
        String[] semNames = getSavedSems(username);

        //string for intro body text
        String introPara =
                "   This is a java application to help students plan their semesters in" +
                        "\nGoogle Calendar and Google Tasks. Semesters mark the first and" +
                        "\nlast dates for courses. Courses are implemented as recurring" +
                        "\nevents in Google Calendar. Each course has its own list of deadlines." +
                        "\nThese deadlines are implemented as tasks in Google Tasks. All tasks" +
                        "\nare added to the tasklist corresponding to the course's semester." +
                        "\nTo start, select or create a semester to add courses and deadlines!" +
                        "\n\nYou are currently signed into: " + username + "@gmail.com. If you are" +
                        "\nnot seeing the semesters you expected, try signing in to a different " +
                        "\nGoogle account.";

        //intellij generated swing layout code for this pane
        selectPanel = new JPanel();
        selectPanel.setLayout(new GridLayoutManager(5, 19, new Insets(0, 0, 0, 0), -1, -1));
        final Spacer spacer1 = new Spacer();
        selectPanel.add(spacer1, new GridConstraints(1, 18, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        selectPanel.add(spacer2, new GridConstraints(4, 1, 1, 10, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        selectPanel.add(spacer3, new GridConstraints(4, 17, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        semSelect = new JComboBox(semNames);
        selectPanel.add(semSelect, new GridConstraints(3, 1, 1, 11, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semSelectHelpLabel = new JLabel();
        semSelectHelpLabel.setText("Select Semester");
        selectPanel.add(semSelectHelpLabel, new GridConstraints(2, 1, 1, 11, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        introText = new JTextArea(introPara);
        introText.putClientProperty("html.disable", Boolean.FALSE);
        selectPanel.add(introText, new GridConstraints(1, 1, 1, 11, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer4 = new Spacer();
        selectPanel.add(spacer4, new GridConstraints(1, 17, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        SemesterSelectTitle = new JLabel();
        SemesterSelectTitle.setText("Welcome to the Semester Planning Tool!");
        selectPanel.add(SemesterSelectTitle, new GridConstraints(0, 1, 1, 11, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addSem = new JButton();
        addSem.setText("Create New Semester");
        selectPanel.add(addSem, new GridConstraints(2, 12, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        selectPanel.add(spacer5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        semSelectButton = new JButton();
        semSelectButton.setText("Select Semester");
        selectPanel.add(semSelectButton, new GridConstraints(4, 14, 1, 3, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteSem = new JButton();
        deleteSem.setText("Delete Semester");
        selectPanel.add(deleteSem, new GridConstraints(4, 12, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        signOut = new JButton();
        signOut.setText("Sign Out");
        selectPanel.add(signOut, new GridConstraints(1, 13, 1, 4, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        //check if there are any semesters to select from
        if (semNames.length > 0) {
            semName = semNames[0];
        }
        if (semNames.length == 0) {
            semSelect.setEnabled(false);
            semSelectButton.setEnabled(false);
            deleteSem.setEnabled(false);
        }

        //add listener to semester select dropdown menu
        semSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String selectedName = (String) cb.getSelectedItem();
                semName = selectedName;
            }
        });

        //add listener to sign out button
        signOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    GoogleServices.signOut();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(signOut);
                getSignIn(mainFrame);
            }
        });

        //add listener to select semester button
        semSelectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Semester selectedSem = Semester.deserialize(semName, username);
                JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(semSelectButton);
                getSelectedSemView(mainFrame, selectedSem);
            }
        });

        //add listener to delete semester button
        deleteSem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Semester selectedSem = Semester.deserialize(semName, username);
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete " + selectedSem.getName() + "?",
                        "Warning", JOptionPane.YES_NO_OPTION);

                if (dialogResult == JOptionPane.YES_OPTION) {
                    JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(deleteSem);
                    Loading.getLoadingScreen(mainFrame);

                    Thread t = new Thread(new Runnable() {
                        public void run() {
                            selectedSem.deleteSem();
                            JOptionPane.showMessageDialog(null, "Semester Deleted!");
                            SemesterView.getSemSelectView(mainFrame);
                        }
                    });
                    t.start();
                }
            }
        });

        //add listener to add semester button
        addSem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(addSem);
                getCreateSemView(mainFrame);
            }
        });

        //make head text area non-editable
        introText.setEditable(false);
        introText.setBackground(new Color(0, 0, 0, 0));
    }

    protected static String[] getSavedSems(String userName) {
        String[] fileNames = null;

        try {
            //get all serialized semester objects
            String basePath = System.getProperty("user.dir");
            Path datadir = Paths.get(basePath + "/src/main/java/SavedSemesters/" + userName);

             if(!Files.isDirectory(datadir)) {
                 //if this user does not have directory for saved semesters, create one
                 Files.createDirectories(datadir);
             }

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

    protected static void getSelectedSemView(JFrame mainFrame, Semester selectedSem) {
        JPanel semViewPanel = new SemesterView(selectedSem).semView;

        mainFrame.getContentPane().removeAll();
        mainFrame.setContentPane(semViewPanel);
        mainFrame.setSize(700, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private static void getCreateSemView(JFrame mainFrame) {
        JPanel createSemPanel = new CreateSemester().CreateSemPanel;

        mainFrame.getContentPane().removeAll();
        mainFrame.setContentPane(createSemPanel);
        mainFrame.setSize(1000, 350);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private static void getSignIn(JFrame mainFrame) {
        JPanel signInPanel = new SignIn().SignIn;

        mainFrame.getContentPane().removeAll();
        mainFrame.setContentPane(signInPanel);
        mainFrame.setSize(500, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}

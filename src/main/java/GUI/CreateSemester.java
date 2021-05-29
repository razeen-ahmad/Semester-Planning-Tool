package GUI;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CreateSemester extends JFrame {
    private JPanel CreateSemPanel;
    private JButton createButton;
    private JFormattedTextField semNameInput;
    private JFormattedTextField semStartInput;
    private JFormattedTextField semEndInput;
    private JTextArea SemNameText;
    private JLabel ScreenTitle;
    private JLabel SemNameLabel;
    private JLabel SemStartLabel;
    private JLabel SemEndLabel;
    private JLabel SemZoneLabel;
    private JComboBox semZoneSelect;
    private JLabel semNameLabel;
    private JLabel semStartLabel;
    private JLabel semEndLabel;
    private JLabel semZoneLabel;

    private String givenSemName;
    private String givenStartDate;
    private String givenEndDate;
    private String givenZone;

    public CreateSemester(String[] zoneNames) {
        super("Semester Planning Tool");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(900, 300);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dateFormatter = new DateFormatter(dateFormat);

        CreateSemPanel = new JPanel();
        CreateSemPanel.setLayout(new GridLayoutManager(6, 7, new Insets(0, 0, 0, 0), -1, -1));
        ScreenTitle = new JLabel();
        ScreenTitle.setText("Create new Semester");
        CreateSemPanel.add(ScreenTitle, new GridConstraints(0, 1, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SemNameLabel = new JLabel();
        SemNameLabel.setText("Semester Name");
        CreateSemPanel.add(SemNameLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SemStartLabel = new JLabel();
        SemStartLabel.setText("Semester Start Date");
        CreateSemPanel.add(SemStartLabel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        SemEndLabel = new JLabel();
        SemEndLabel.setText("Semester End Date");
        CreateSemPanel.add(SemEndLabel, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        final Spacer spacer1 = new Spacer();
        CreateSemPanel.add(spacer1, new GridConstraints(5, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));

        createButton = new JButton();
        createButton.setText("Create");
        CreateSemPanel.add(createButton, new GridConstraints(5, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(givenSemName == null || givenStartDate == null || givenEndDate == null){
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                }
                else {
                    JOptionPane.showMessageDialog(null,
                            "You have created a new semester: " + givenSemName +
                            "\nstarts on: " + givenStartDate + " and ends on: " + givenEndDate +
                            "\n in: " + givenZone
                    );
                }
            }
        });

        SemZoneLabel = new JLabel();
        SemZoneLabel.setText("Time Zone");
        CreateSemPanel.add(SemZoneLabel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        semNameInput = new JFormattedTextField();
        CreateSemPanel.add(semNameInput, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        semNameInput.addPropertyChangeListener("value", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                givenSemName = semNameInput.getText();
            }
        });
        semStartInput = new JFormattedTextField(dateFormatter);
        CreateSemPanel.add(semStartInput, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        semStartInput.addPropertyChangeListener("value", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                givenStartDate = semStartInput.getText();
            }
        });
        semEndInput = new JFormattedTextField(dateFormatter);
        CreateSemPanel.add(semEndInput, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        semEndInput.addPropertyChangeListener("value", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                givenEndDate = semEndInput.getText();
            }
        });

        semZoneSelect = new JComboBox(zoneNames);
        semZoneSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String selectedName = (String) cb.getSelectedItem();
                givenZone = selectedName;
            }
        });
        CreateSemPanel.add(semZoneSelect, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        final Spacer spacer2 = new Spacer();
        CreateSemPanel.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        CreateSemPanel.add(spacer3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        CreateSemPanel.add(spacer4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        CreateSemPanel.add(spacer5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        CreateSemPanel.add(spacer6, new GridConstraints(5, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        CreateSemPanel.add(spacer7, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        CreateSemPanel.add(spacer8, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        CreateSemPanel.add(spacer9, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        CreateSemPanel.add(spacer10, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        CreateSemPanel.add(spacer11, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        CreateSemPanel.add(spacer12, new GridConstraints(1, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        CreateSemPanel.add(spacer13, new GridConstraints(2, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        CreateSemPanel.add(spacer14, new GridConstraints(3, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer15 = new Spacer();
        CreateSemPanel.add(spacer15, new GridConstraints(4, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        semNameLabel = new JLabel();
        semNameLabel.setText("only alphanumeric characters");
        CreateSemPanel.add(semNameLabel, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semStartLabel = new JLabel();
        semStartLabel.setText("MM/DD/YYYY");
        CreateSemPanel.add(semStartLabel, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semEndLabel = new JLabel();
        semEndLabel.setText("MM/DD/YYYY");
        CreateSemPanel.add(semEndLabel, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semZoneLabel = new JLabel();
        semZoneLabel.setText("Choose one from the dropdown menu");
        CreateSemPanel.add(semZoneLabel, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        this.setContentPane(CreateSemPanel);
        givenSemName = null;
        givenStartDate = null;
        givenEndDate = null;
        givenZone = zoneNames[0];
    }

    public static void main(String[] args) {
        String[] zoneNames = new String[]{"America/Chicago", "America/New York", "America/California"};

        JFrame thisFrame = new CreateSemester(zoneNames);
        thisFrame.setVisible(true);
    }
}

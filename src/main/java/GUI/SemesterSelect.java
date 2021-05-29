package GUI;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SemesterSelect extends JFrame {
    private JPanel selectPanel;
    private JComboBox semSelect;
    private JLabel appName;
    private JButton button1;
    private String semName;

    public SemesterSelect(String[] semNames) {
        super("Semester Planning Tool");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(300, 300);

        selectPanel = new JPanel();
        selectPanel.setLayout(new GridLayoutManager(3, 18, new Insets(0, 0, 0, 0), -1, -1));
        appName = new JLabel();
        appName.setText("Semester Planning Tool");
        selectPanel.add(appName, new GridConstraints(0, 0, 1, 16, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        selectPanel.add(spacer1, new GridConstraints(1, 15, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        selectPanel.add(spacer2, new GridConstraints(0, 17, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        selectPanel.add(spacer3, new GridConstraints(2, 11, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        button1 = new JButton();
        button1.setText("Continue");
        selectPanel.add(button1, new GridConstraints(2, 12, 1, 3, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        selectPanel.add(spacer4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        semSelect = new JComboBox(semNames);
        selectPanel.add(semSelect, new GridConstraints(1, 5, 1, 3, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        selectPanel.add(spacer5, new GridConstraints(2, 16, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));

        if (semNames.length > 0) {
            semName = semNames[0];
        }

        semSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String selectedName = (String) cb.getSelectedItem();
                semName = selectedName;
            }
        });

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You have chosen semester: " + semName);
            }
        });

        this.setContentPane(selectPanel);
    }

    public static void main(String[] args) {
        String[] mySemNames = new String[]{"sem1", "sem2", "semester 3"};
        JFrame myFrame = new SemesterSelect(mySemNames);
        myFrame.setVisible(true);
    }
}

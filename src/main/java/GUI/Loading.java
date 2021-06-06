package GUI;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;

public class Loading {
    private JProgressBar loadingBar;
    protected JPanel loadingPanel;
    private JLabel loadingLabel;
    private JLabel warningLabel;

    public Loading() {
        //intellij generated swing layout code
        loadingPanel = new JPanel();
        loadingPanel.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        final Spacer spacer1 = new Spacer();
        loadingPanel.add(spacer1, new GridConstraints(0, 0, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        loadingPanel.add(spacer2, new GridConstraints(0, 2, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        loadingLabel = new JLabel();
        loadingLabel.setText("Making Changes...");
        loadingPanel.add(loadingLabel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadingBar = new JProgressBar();
        loadingPanel.add(loadingBar, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        warningLabel = new JLabel();
        warningLabel.setText("DO NOT close out of this window");
        loadingPanel.add(warningLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        //set indeterminate loading bar
        loadingBar.setIndeterminate(true);
    }

    protected static void getLoadingScreen(JFrame mainFrame) {
        JPanel loadingPanel = new Loading().loadingPanel;

        mainFrame.getContentPane().removeAll();
        mainFrame.setContentPane(loadingPanel);
        mainFrame.setSize(400, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        JPanel thisPanel = new Loading().loadingPanel;

        JFrame thisFrame = new JFrame("Semester Planning Tool");
        thisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        thisFrame.setContentPane(thisPanel);
        thisFrame.setSize(400, 300);
        thisFrame.setLocationRelativeTo(null);
        thisFrame.setVisible(true);
    }
}

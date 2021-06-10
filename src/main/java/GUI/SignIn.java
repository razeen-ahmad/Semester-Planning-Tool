package GUI;

import SemesterCode.GoogleServices;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class SignIn {
    private JButton signInButton;
    protected JPanel SignIn;

    public SignIn() {
        SignIn = new JPanel();
        SignIn.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        signInButton = new JButton();
        signInButton.setText("Sign In");
        SignIn.add(signInButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        SignIn.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        SignIn.add(spacer2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));

        //add listener to add semester button
        signInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(signInButton);
                try {
                    GoogleServices.signIn();
                } catch (GeneralSecurityException generalSecurityException) {
                    generalSecurityException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                SemesterView.getSemSelectView(mainFrame);
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (Exception ignored) {
        }


        JFrame thisFrame = new JFrame("Semester Planning Tool");
        SignIn thisPanel = new SignIn();


        thisFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        thisFrame.setContentPane(thisPanel.SignIn);
        thisFrame.setSize(500, 300);
        thisFrame.setLocationRelativeTo(null);
        thisFrame.setVisible(true);
    }
}

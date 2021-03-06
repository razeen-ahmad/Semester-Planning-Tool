package GUI;

import SemesterCode.Semester;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateSemester {
    protected JPanel CreateSemPanel;
    private JButton createButton;
    private JFormattedTextField semNameInput;
    private JFormattedTextField semStartInput;
    private JFormattedTextField semEndInput;
    private JLabel ScreenTitle;
    private JLabel SemNameLabel;
    private JLabel SemStartLabel;
    private JLabel SemEndLabel;
    private JLabel SemZoneLabel;
    private JComboBox semZoneSelect;
    private JLabel semNameHelperLabel;
    private JLabel semStartHelperLabel;
    private JLabel semEndHelperLabel;
    private JLabel semZoneHelperLabel;
    private JButton goBack;
    private JLabel fieldsNotesLabel;

    private String givenZone;

    private static final String[] ZONE_NAMES = new String[]{
            "America/New_York",
            "America/Chicago",
            "America/Denver",
            "America/Los_Angeles",
            "Asia/Aden",
            "Asia/Almaty",
            "Asia/Amman",
            "Asia/Anadyr",
            "Asia/Aqtau",
            "Asia/Aqtobe",
            "Asia/Ashgabat",
            "Asia/Ashkhabad",
            "Asia/Atyrau",
            "Asia/Baghdad",
            "Asia/Bahrain",
            "Asia/Baku",
            "Asia/Bangkok",
            "Asia/Barnaul",
            "Asia/Beirut",
            "Asia/Bishkek",
            "Asia/Brunei",
            "Asia/Calcutta",
            "Asia/Chita",
            "Asia/Choibalsan",
            "Asia/Chongqing",
            "Asia/Chungking",
            "Asia/Colombo",
            "Asia/Dacca",
            "Asia/Damascus",
            "Asia/Dhaka",
            "Asia/Dili",
            "Asia/Dubai",
            "Asia/Dushanbe",
            "Asia/Famagusta",
            "Asia/Gaza",
            "Asia/Harbin",
            "Asia/Hebron",
            "Asia/Ho_Chi_Minh",
            "Asia/Hong_Kong",
            "Asia/Hovd",
            "Asia/Irkutsk",
            "Asia/Istanbul",
            "Asia/Jakarta",
            "Asia/Jayapura",
            "Asia/Jerusalem",
            "Asia/Kabul",
            "Asia/Kamchatka",
            "Asia/Karachi",
            "Asia/Kashgar",
            "Asia/Kathmandu",
            "Asia/Katmandu",
            "Asia/Khandyga",
            "Asia/Kolkata",
            "Asia/Krasnoyarsk",
            "Asia/Kuala_Lumpur",
            "Asia/Kuching",
            "Asia/Kuwait",
            "Asia/Macao",
            "Asia/Macau",
            "Asia/Magadan",
            "Asia/Makassar",
            "Asia/Manila",
            "Asia/Muscat",
            "Asia/Nicosia",
            "Asia/Novokuznetsk",
            "Asia/Novosibirsk",
            "Asia/Omsk",
            "Asia/Oral",
            "Asia/Phnom_Penh",
            "Asia/Pontianak",
            "Asia/Pyongyang",
            "Asia/Qatar",
            "Asia/Qostanay",
            "Asia/Qyzylorda",
            "Asia/Rangoon",
            "Asia/Riyadh",
            "Asia/Saigon",
            "Asia/Sakhalin",
            "Asia/Samarkand",
            "Asia/Seoul",
            "Asia/Shanghai",
            "Asia/Singapore",
            "Asia/Srednekolymsk",
            "Asia/Taipei",
            "Asia/Tashkent",
            "Asia/Tbilisi",
            "Asia/Tehran",
            "Asia/Tel_Aviv",
            "Asia/Thimbu",
            "Asia/Thimphu",
            "Asia/Tokyo",
            "Asia/Tomsk",
            "Asia/Ujung_Pandang",
            "Asia/Ulaanbaatar",
            "Asia/Ulan_Bator",
            "Asia/Urumqi",
            "Asia/Ust-Nera",
            "Asia/Vientiane",
            "Asia/Vladivostok",
            "Asia/Yakutsk",
            "Asia/Yangon",
            "Asia/Yekaterinburg",
            "Asia/Yerevan",
            "Canada/Atlantic",
            "Canada/Central",
            "Canada/Eastern",
            "Canada/Mountain",
            "Canada/Newfoundland",
            "Canada/Pacific",
            "Canada/Saskatchewan",
            "Canada/Yukon",
            "Europe/Amsterdam",
            "Europe/Andorra",
            "Europe/Astrakhan",
            "Europe/Athens",
            "Europe/Belfast",
            "Europe/Belgrade",
            "Europe/Berlin",
            "Europe/Bratislava",
            "Europe/Brussels",
            "Europe/Bucharest",
            "Europe/Budapest",
            "Europe/Busingen",
            "Europe/Chisinau",
            "Europe/Copenhagen",
            "Europe/Dublin",
            "Europe/Gibraltar",
            "Europe/Guernsey",
            "Europe/Helsinki",
            "Europe/Isle_of_Man",
            "Europe/Istanbul",
            "Europe/Jersey",
            "Europe/Kaliningrad",
            "Europe/Kiev",
            "Europe/Kirov",
            "Europe/Lisbon",
            "Europe/Ljubljana",
            "Europe/London",
            "Europe/Luxembourg",
            "Europe/Madrid",
            "Europe/Malta",
            "Europe/Mariehamn",
            "Europe/Minsk",
            "Europe/Monaco",
            "Europe/Moscow",
            "Europe/Nicosia",
            "Europe/Oslo",
            "Europe/Paris",
            "Europe/Podgorica",
            "Europe/Prague",
            "Europe/Riga",
            "Europe/Rome",
            "Europe/Samara",
            "Europe/San_Marino",
            "Europe/Sarajevo",
            "Europe/Saratov",
            "Europe/Simferopol",
            "Europe/Skopje",
            "Europe/Sofia",
            "Europe/Stockholm",
            "Europe/Tallinn",
            "Europe/Tirane",
            "Europe/Tiraspol",
            "Europe/Ulyanovsk",
            "Europe/Uzhgorod",
            "Europe/Vaduz",
            "Europe/Vatican",
            "Europe/Vienna",
            "Europe/Vilnius",
            "Europe/Volgograd",
            "Europe/Warsaw",
            "Europe/Zagreb",
            "Europe/Zaporozhye",
            "Europe/Zurich",
    };

    public CreateSemester() {

        //create date and name formatters
        DateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormatter inputDateFormatter = new DateFormatter(inputDateFormat);
        MaskFormatter nameFormatter = null;
        try {
            nameFormatter = new MaskFormatter("********************");
            nameFormatter.setValidCharacters(
                    "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ "
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //intellij generated code for swing layout in JPanel
        CreateSemPanel = new JPanel();
        CreateSemPanel.setLayout(new GridLayoutManager(7, 8, new Insets(0, 0, 0, 0), -1, -1));
        ScreenTitle = new JLabel();
        ScreenTitle.setText("Create New Semester");
        CreateSemPanel.add(ScreenTitle, new GridConstraints(0, 1, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SemNameLabel = new JLabel();
        SemNameLabel.setText("Semester Name:");
        CreateSemPanel.add(SemNameLabel, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SemStartLabel = new JLabel();
        SemStartLabel.setText("Semester Start Date:");
        CreateSemPanel.add(SemStartLabel, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SemEndLabel = new JLabel();
        SemEndLabel.setText("Semester End Date:");
        CreateSemPanel.add(SemEndLabel, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        CreateSemPanel.add(spacer1, new GridConstraints(6, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        createButton = new JButton();
        createButton.setText("Create");
        CreateSemPanel.add(createButton, new GridConstraints(6, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SemZoneLabel = new JLabel();
        SemZoneLabel.setText("Semester Time Zone:");
        CreateSemPanel.add(SemZoneLabel, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semNameInput = new JFormattedTextField(nameFormatter);
        CreateSemPanel.add(semNameInput, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        semStartInput = new JFormattedTextField(inputDateFormatter);
        CreateSemPanel.add(semStartInput, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        semEndInput = new JFormattedTextField(inputDateFormatter);
        CreateSemPanel.add(semEndInput, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        semZoneSelect = new JComboBox(ZONE_NAMES);
        CreateSemPanel.add(semZoneSelect, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        CreateSemPanel.add(spacer2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        CreateSemPanel.add(spacer3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        CreateSemPanel.add(spacer4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        CreateSemPanel.add(spacer5, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        CreateSemPanel.add(spacer6, new GridConstraints(6, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        CreateSemPanel.add(spacer7, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        CreateSemPanel.add(spacer8, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        CreateSemPanel.add(spacer9, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        CreateSemPanel.add(spacer10, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        CreateSemPanel.add(spacer11, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        CreateSemPanel.add(spacer12, new GridConstraints(2, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        CreateSemPanel.add(spacer13, new GridConstraints(3, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        CreateSemPanel.add(spacer14, new GridConstraints(4, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer15 = new Spacer();
        CreateSemPanel.add(spacer15, new GridConstraints(5, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        semNameHelperLabel = new JLabel();
        semNameHelperLabel.setText("Only Alphanumeric Characters");
        CreateSemPanel.add(semNameHelperLabel, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semStartHelperLabel = new JLabel();
        semStartHelperLabel.setText("Date Format: 'MM/DD/YYYY'. (e.g Jan. 1, 2010 = '01/01/2010').");
        CreateSemPanel.add(semStartHelperLabel, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semEndHelperLabel = new JLabel();
        semEndHelperLabel.setText("Date Format: 'MM/DD/YYYY'. (e.g Jan. 1, 2010 = '01/01/2010').");
        CreateSemPanel.add(semEndHelperLabel, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        semZoneHelperLabel = new JLabel();
        semZoneHelperLabel.setText("Choose the timezone of this semester.");
        CreateSemPanel.add(semZoneHelperLabel, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        goBack = new JButton();
        goBack.setText("Go Back");
        CreateSemPanel.add(goBack, new GridConstraints(6, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fieldsNotesLabel = new JLabel();
        fieldsNotesLabel.setText("NOTE: These Values Cannot Be Changed Later!");
        CreateSemPanel.add(fieldsNotesLabel, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        //initialize time zone field variable
        givenZone = ZONE_NAMES[0];

        //semName field initialization
        semNameInput.setValue(null);

        //time zone drop down menu listener
        semZoneSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                String selectedName = (String) cb.getSelectedItem();
                givenZone = selectedName;
            }
        });

        //create semester button listener
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String givenSemName = semNameInput.getText().trim();
                String givenEndDate = semEndInput.getText();
                String givenStartDate = semStartInput.getText();

                //get input date into correct format for semester object and google API
                DateFormat correctDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = inputDateFormat.parse(givenStartDate);
                    endDate = inputDateFormat.parse(givenEndDate);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                boolean isValidDates = checkDateValidity(startDate, endDate);
                if (givenSemName.equals("") || givenStartDate.equals("") || givenEndDate.equals("")) {
                    JOptionPane.showMessageDialog(null, "Fill in all fields");
                } else if (!isValidDates) {
                    JOptionPane.showMessageDialog(null,
                            "\nCheck that start date and end date are compatible" +
                                    "\n(e.g. end date comes after start date)."
                    );
                } else {
                    JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(createButton);
                    Loading.getLoadingScreen(mainFrame);
                    String correctStartDate = correctDateFormat.format(startDate);
                    String correctEndDate = correctDateFormat.format(endDate);

                    Thread t = new Thread(new Runnable() {
                        public void run() {
                            Semester newSem = new Semester(givenSemName, correctStartDate, correctEndDate, givenZone);
                            JOptionPane.showMessageDialog(null, "New Semester Created!");
                            SemesterView.getUpdatedSemView(mainFrame, newSem);
                        }
                    });
                    t.start();
                }
            }
        });

        //go back button listener
        goBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame mainFrame = (JFrame) SwingUtilities.windowForComponent(goBack);
                SemesterView.getSemSelectView(mainFrame);
            }
        });
    }

    private static boolean checkDateValidity(Date startDate, Date endDate) {
        if (startDate.after(endDate)) {
            return false;
        }
        return true;
    }
}

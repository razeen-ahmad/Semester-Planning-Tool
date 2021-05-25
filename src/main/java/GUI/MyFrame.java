package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {

    private JComboBox petList;
    private static String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };

   public MyFrame(){
        //JFrame frame = new JFrame("My First GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,300);
//        JButton button = new JButton("Press");
//        this.getContentPane().add(button); // Adds Button to content pane of frame

        //Create the combo box, select item at index 4.
        //Indices start at 0, so 4 specifies the pig.
//        this.petList = new JComboBox(petStrings);
//        this.petList.setSelectedIndex(4);
//        this.petList.addActionListener(this);
//        this.add(petList);
    }


    public void actionPerformed(ActionEvent e){
        if(e.getSource() == petList){
            System.out.println("action on combobox");
        }
    }

}

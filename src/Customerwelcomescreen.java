import workingdaycalendar.ConfirmedDaysUI;
import workingdaycalendar.WorkingDayCalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Customerwelcomescreen extends JFrame{
    public Customerwelcomescreen(){
        setTitle("Customer Welcome Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JLabel titleLabel = new JLabel("Customer Welcome Screen");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        panel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        JButton addcustomer = new JButton("New Customer Record");
        JButton editcustomer = new JButton("Edit Customer Record");
        JButton dayscalendar= new JButton("Working Days Calendar");
        addcustomer.setBackground(Color.LIGHT_GRAY);
        editcustomer.setBackground(Color.LIGHT_GRAY);
        dayscalendar.setBackground(Color.LIGHT_GRAY);

        addcustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame listItemsFrame = new JFrame("New Customer Record");
                listItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                User.main(null);
                listItemsFrame.pack();
                listItemsFrame.setVisible(true);
            }
        });
        editcustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame listItemsFrame = new JFrame("Edit Customer Record");
                listItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                listItemsFrame.pack();
                listItemsFrame.setVisible(true);
            }
        });
        dayscalendar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame listItemsFrame = new JFrame("Working Days Calendar");
                listItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                WorkingDayCalendar manager=new WorkingDayCalendar();
                ConfirmedDaysUI confirmeddays = new ConfirmedDaysUI(manager);
                confirmeddays.displayUI();
                listItemsFrame.pack();
                listItemsFrame.setVisible(true);
            }
        });

        buttonPanel.add(addcustomer);
        buttonPanel.add(editcustomer);
        buttonPanel.add(dayscalendar);
        panel.add(buttonPanel);

        add(panel);
        setVisible(true);
    } 
}
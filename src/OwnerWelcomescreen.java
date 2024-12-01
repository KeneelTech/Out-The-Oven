import workingdaycalendar.WorkingDayCalendarUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OwnerWelcomescreen extends JFrame {
    public OwnerWelcomescreen() {
        setTitle("Owner Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel("Owner Screen");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        panel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        JButton editcustomer = new JButton("Edit Customer Record");
        JButton todo= new JButton("To-Do List");
        JButton updateinv= new JButton("Update Inventory");
        JButton workingdays= new JButton ("Select Working Days");
        JButton invoice=new JButton ("Generate Invoice");
        editcustomer.setBackground(Color.LIGHT_GRAY);
        todo.setBackground(Color.LIGHT_GRAY);
        updateinv.setBackground(Color.LIGHT_GRAY);
        workingdays.setBackground(Color.LIGHT_GRAY);

        editcustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame listItemsFrame = new JFrame("Edit Customer Record");
                listItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                listItemsFrame.pack();
                listItemsFrame.setVisible(true);
            }
        });
        todo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame listItemsFrame = new JFrame("Generate To-Do List");
                listItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                listItemsFrame.getContentPane().add(new GenerateTodolist());
                listItemsFrame.pack();
                listItemsFrame.setVisible(true);
  
            }
        });
        updateinv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame listItemsFrame = new JFrame("Update Inventory");
                listItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                //Inventorylist.main(null);
                OptionSelect.main(null);
                listItemsFrame.pack();
                listItemsFrame.setVisible(true);
            }
        });
        workingdays.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame listItemsFrame = new JFrame("Select Working Days");
                listItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                WorkingDayCalendarUI workingDayScreen = new WorkingDayCalendarUI();
                workingDayScreen.displayUI(); 
                listItemsFrame.pack();
                listItemsFrame.setVisible(true);
            }
        });
        invoice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame listItemsFrame = new JFrame("Generate Invoice");
                OrderUI.main(null);
                listItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                listItemsFrame.pack();
                listItemsFrame.setVisible(true);
            }
        });
        buttonPanel.add(editcustomer);
        buttonPanel.add(todo);
        buttonPanel.add(updateinv);
        buttonPanel.add(workingdays);
        buttonPanel.add(invoice);

        panel.add(buttonPanel);
        add(panel);
        setVisible(true);
    }
}
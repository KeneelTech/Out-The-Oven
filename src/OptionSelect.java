/**
 * Creates gui welcome screen 
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OptionSelect extends JFrame {
    public OptionSelect() {
        setTitle("Select an Option");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        JLabel titleLabel = new JLabel("Select an Option");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.blue); 
        panel.add(titleLabel);
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        JButton manageStockButton = new JButton("Manage Stock");
        JButton manageShoppingListButton = new JButton("Item Entry");
        manageStockButton.setBackground(Color.LIGHT_GRAY);
        manageShoppingListButton.setBackground(Color.LIGHT_GRAY);


        manageStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to open/manage the stock management GUI
                String pword = JOptionPane.showInputDialog( null, "Admin only screen Enter password");//accepts password from user 
                String password="Admin";
                if (pword.equals(password)){//checks if the password is correct 
                    JFrame listItemsFrame = new JFrame("Stock Management");//creates new jframe Stock management if password is correct
                    listItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to return to WelcomeScreen
                    listItemsFrame.getContentPane().add(new Inventorylist());//opens Inventorylist window 
                    listItemsFrame.pack();
                    listItemsFrame.setVisible(true);
            }else{ 
                JOptionPane.showMessageDialog(null, "Wrong Password");//if the password is wrong the message Wrong password is shown

            }
        }
        });

        manageShoppingListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame listItemsFrame = new JFrame("Shopping List");
                listItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to return to WelcomeScreen
                listItemsFrame.getContentPane().add(new listItems());
                listItemsFrame.pack();
                listItemsFrame.setVisible(true);
            
        }
        });


        buttonPanel.add(manageStockButton);
        buttonPanel.add(manageShoppingListButton);

        // Add button panel to main panel
        panel.add(buttonPanel);

        // Add panel to frame
        add(panel);
        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new OptionSelect();
            }
        });
    }
}


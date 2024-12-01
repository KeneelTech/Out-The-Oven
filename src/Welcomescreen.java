import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Welcomescreen extends JFrame {
    public Welcomescreen() {
        setTitle("Welcome To Out The Oven");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
                // Load the image
        String imagePath = "C:/Users/palme/OneDrive/Pictures/oto.png";
        ImageIcon originalIcon = new ImageIcon(imagePath);

        // Resize the image
        int newWidth = 200;  // Desired width
        int newHeight = 200; // Desired height
        Image resizedImage = originalIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        // Add the resized image to a JLabel
        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(imageLabel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Welcome To Out The Oven");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLUE);
        panel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        JButton owner = new JButton("Owner");
        JButton customer = new JButton("Customer");
        owner.setBackground(Color.LIGHT_GRAY);
        customer.setBackground(Color.LIGHT_GRAY);

        owner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pword = JOptionPane.showInputDialog( null, "Owner page only. Enter Password");//accepts password from user 
                String password="Admin";
                if (pword.equals(password)){//checks if the password is correct 
                    JFrame listItemsFrame = new JFrame("Owner");
                    listItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    listItemsFrame.getContentPane().add(new OwnerWelcomescreen());//opens Inventorylist window 
                    listItemsFrame.pack();
                    listItemsFrame.setVisible(true);
                }else{
                JOptionPane.showMessageDialog(null, "Wrong Password");
            }
        }
        });

        customer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame listItemsFrame = new JFrame("Edit Customer Record");
                listItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                listItemsFrame.getContentPane().add(new Customerwelcomescreen());
                listItemsFrame.pack();
                listItemsFrame.setVisible(true);
            }
        });

        buttonPanel.add(owner);
        buttonPanel.add(customer);

        panel.add(buttonPanel);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Welcomescreen();
            }
        });
    }
}
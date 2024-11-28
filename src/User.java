import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.regex.*;

class User {
    private String name;
    private String address;
    private String contactNumber;
    private String email;
    private String productDescription;
    private String quantity;
    private String inspirationalPhoto;
    private String deliveryDate;
    private boolean isDelivery;
    private String allergies;

    // Constructor
    public User(String name, String address, String contactNumber, String email, String productDescription, String quantity,
                String inspirationalPhoto, String deliveryDate, boolean isDelivery, String allergies) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
        this.productDescription = productDescription;
        this.quantity = quantity;
        this.inspirationalPhoto = inspirationalPhoto;
        this.deliveryDate = deliveryDate;
        this.isDelivery = isDelivery;
        this.allergies = allergies;
    }

    // Method to save user data to a file
    public void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("customers.txt", true))) {
            writer.write(this.toString());
            writer.newLine();
            JOptionPane.showMessageDialog(null, "Customer data saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving customer data: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return name + "|" + address + "|" + contactNumber + "|" + email + "|" + productDescription + "|" + quantity + "|" +
               inspirationalPhoto + "|" + deliveryDate + "|" + isDelivery + "|" + allergies;
    }

    // Method to validate email format
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Method to clear input fields and uncheck checkboxes
    public static void clearFields(JTextField[] fields, JCheckBox[] checkboxes) {
        for (JTextField field : fields) {
            field.setText("");
        }
        for (JCheckBox checkbox : checkboxes) {
            checkbox.setSelected(false);
        }
    }

    // Method to show profile screen with customer details
    public static void showProfileScreen(User user) {
        JFrame profileFrame = new JFrame("Customer Profile");
        profileFrame.setSize(800, 800);
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileFrame.setLayout(new BorderLayout());

        // Create a JPanel for customer information
        JPanel infoPanel = new JPanel(new GridLayout(0, 2)); // Dynamic rows

        // Create close button
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(Color.RED);
        closeButton.setPreferredSize(new Dimension(100, 30));

        // Display customer information
        infoPanel.add(new JLabel("Name:"));
        infoPanel.add(new JLabel(user.name));
        infoPanel.add(new JLabel("Address:"));
        infoPanel.add(new JLabel(user.address));
        infoPanel.add(new JLabel("Contact Number:"));
        infoPanel.add(new JLabel(user.contactNumber));
        infoPanel.add(new JLabel("Email:"));
        infoPanel.add(new JLabel(user.email));
        infoPanel.add(new JLabel("Product Description:"));
        infoPanel.add(new JLabel(user.productDescription));
        infoPanel.add(new JLabel("Quantity:"));
        infoPanel.add(new JLabel(String.valueOf(user.quantity))); // Ensure quantity is shown correctly as string
        infoPanel.add(new JLabel("Delivery/Pickup Date:"));
        infoPanel.add(new JLabel(user.deliveryDate));
        infoPanel.add(new JLabel("Delivery:"));
        infoPanel.add(new JLabel(user.isDelivery ? "Yes" : "No"));
        infoPanel.add(new JLabel("Allergies/Additional Info:"));
        infoPanel.add(new JLabel(user.allergies));

        // Create a JPanel for the inspirational photo
        JPanel photoPanel = new JPanel();
        photoPanel.setLayout(new BorderLayout());

        // Create the label for Inspirational Photo
        JLabel photoLabel = new JLabel("Inspirational Photo:");
        photoPanel.add(photoLabel, BorderLayout.NORTH);

        // Show the photo if it exists
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
    
        if (user.inspirationalPhoto != null && !user.inspirationalPhoto.isEmpty()) {
            try {
                ImageIcon imageIcon = new ImageIcon(user.inspirationalPhoto);
                Image scaledImage = imageIcon.getImage().getScaledInstance(400, 350, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                imageLabel.setText("Error loading image.");
            }   
        } else {
            imageLabel.setText("No Image Available");
        }

        // Add the image label to the photo panel
        photoPanel.add(imageLabel, BorderLayout.CENTER);

        // Add the photo panel to the top of the frame
        profileFrame.add(photoPanel, BorderLayout.NORTH);

        // Add the info panel to the center
        profileFrame.add(infoPanel, BorderLayout.CENTER);

        // Add the close button to the bottom
        profileFrame.add(closeButton, BorderLayout.SOUTH);

        // Display the profile frame
        profileFrame.setVisible(true);

        // Add action listener to close button
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                profileFrame.dispose();
            }
        });
    }

    // Custom Document to limit JTextField input length
    static class JTextFieldLimit extends javax.swing.text.PlainDocument {
        private final int limit;

        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
            if (str == null) return;
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }
}

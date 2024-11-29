import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;

class User {
    private String name;
    private String address;
    private String contactNumber;
    private String email;
    private String productDescription;
    private int quantity;
    private String inspirationalPhoto;
    private int deliveryDate;
    private boolean isDelivery;
    private String allergies;

    // Constructor
    public User(String name, String address, String contactNumber, String email, String productDescription, int quantity,
                String inspirationalPhoto, int deliveryDate, boolean isDelivery, String allergies) {
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
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getProduct() {
        return productDescription;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPhoto() {
        return inspirationalPhoto;
    }

    public int getDate() {
        return deliveryDate;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public String getAllergies() {
        return allergies;
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

    // Main method to test User class
    public static void main(String[] args) {
        JFrame frame = new JFrame("Add New Customer");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(11, 2));

        // Create text fields and checkboxes
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField("+1 ");
        JTextField emailField = new JTextField();
        JTextField productField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField photoField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField allergiesField = new JTextField();
        JCheckBox deliveryCheckBox = new JCheckBox("Delivery");
        
        // Create buttons
        JButton saveButton = new JButton("Save Customer");
        JButton cancelButton = new JButton("Cancel");
        JButton addPhotoButton = new JButton("Add Photo");

        // Set button colors
        saveButton.setBackground(Color.GREEN);
        cancelButton.setBackground(Color.RED);
        addPhotoButton.setBackground(Color.BLUE);


        // Add action listener to save button
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String contact = contactField.getText();
                String email = emailField.getText();
                String product = productField.getText();
                var quantity = quantityField.getText();
                String photo = photoField.getText();
                String date = dateField.getText();
                String address = addressField.getText();
                boolean isDelivery = deliveryCheckBox.isSelected();
                String allergies = allergiesField.getText();
                
                // Initialize a flag to check for errors
                boolean hasErrors = false;

                // Check for empty fields and display appropriate error messages
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter your name");
                    hasErrors = true;
                }

                if (product.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter your product description");
                    hasErrors = true;
                }

                if (quantity.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter product quantity");
                    hasErrors = true;
                }

                if (date.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter your pickup/delivery date");
                    hasErrors = true;
                }

                if (contact.length() < 4) {
                    JOptionPane.showMessageDialog(frame, "Please enter your contact");
                    hasErrors = true;
                }
                
                if (isDelivery && address.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter your address");
                    hasErrors = true;
                } else {
                    address = "N/A";
                }

                // If allergies are empty, set it to "N/A"
                if (allergies.trim().isEmpty()) {
                    allergies = "N/A";
                }

                // Validate email format
                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(frame, "Invalid email format.");
                    return;
                }

                // If there are any errors, prevent saving and exit the method
                if (hasErrors) {
                    return; // Exit the action listener
                }

                // Create the User object and save data if no errors were found
                User user = new User(name, address, contact, email, product, quantity, photo, date, isDelivery, allergies);
                user.saveUserData();

                // Show profile screen with customer data
                showProfileScreen(user);

                // Clear fields after saving
                JTextField[] textFields = { nameField, contactField, emailField, productField, quantityField, photoField, dateField, addressField, allergiesField };
                JCheckBox[] checkboxes = { deliveryCheckBox };
                
                clearFields(textFields, checkboxes);
            }
        });

        // Add action listener to cancel button
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        // Add action listener to add photo button
        addPhotoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "gif"));
                int returnValue = fileChooser.showOpenDialog(null);
        
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    photoField.setText(selectedFile.getAbsolutePath());

                    // Show success message
                    JOptionPane.showMessageDialog(null, "Photo added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Show error message if the user cancels or an error occurs
                    JOptionPane.showMessageDialog(null, "Error: No photo selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        // Adding components to the frame
        frame.add(new JLabel("Name:"));
        frame.add(nameField);
        frame.add(new JLabel("Address:"));
        frame.add(addressField);
        frame.add(new JLabel("Contact Number:"));
        frame.add(contactField);
        frame.add(new JLabel("Email:"));
        frame.add(emailField);
        frame.add(new JLabel("Product Description:"));
        frame.add(productField);
        frame.add(new JLabel("Quantity:"));
        frame.add(quantityField);
        frame.add(new JLabel("Inspirational Photo:"));
        frame.add(addPhotoButton);
        frame.add(new JLabel("Delivery/Pickup Date:"));
        frame.add(dateField);
        frame.add(new JLabel("Allergies/Additional Info:"));
        frame.add(allergiesField);
        frame.add(new JLabel("Tick the box for delivery"));
        frame.add(deliveryCheckBox);
        frame.add(saveButton);
        frame.add(cancelButton);

        // Make the frame visible
        frame.setVisible(true);
        
    
        // Set maximum lengths for fields
        nameField.setDocument(new JTextFieldLimit(35));
        addressField.setDocument(new JTextFieldLimit(70));
        productField.setDocument(new JTextFieldLimit(250));
        
        } 

        // Method to validate email format
        private static boolean isValidEmail(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            return pattern.matcher(email).matches();
        }

        // Method to clear input fields and uncheck checkboxes
        private static void clearFields(JTextField[] fields, JCheckBox[] checkboxes) {
            for (JTextField field : fields) {
                field.setText("");
            }
            for (JCheckBox checkbox : checkboxes) {
                checkbox.setSelected(false);
            }
        
        }

        // Method to show profile screen with customer details
        private static void showProfileScreen(User user) {
            JFrame profileFrame = new JFrame("Customer Profile");
            profileFrame.setSize(800, 800);
            profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            profileFrame.setLayout(new BorderLayout());

        // Create a JPanel for customer information
        JPanel infoPanel = new JPanel(new GridLayout(0, 2)); // Dynamic rows

        // Create close button
        JButton closeButton = new JButton("Close");

        // Set button color
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
        infoPanel.add(new JLabel(String.valueOf(user.deliveryDate)));
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
                // Scale image to a reasonable size, adjust as necessary
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

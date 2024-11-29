import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class CustomerDB {
    private String name;
    private String address;
    private String contact;
    private String email;
    private String product;
    private int quantity;
    private String photo;
    private String date;
    private boolean isDelivery;
    private String allergies;

    // Constructor
    public CustomersDB(String name, String address, String contact, String email, String product,
                int quantity, String photo, String date, boolean isDelivery, String allergies) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.product = product;
        this.quantity = quantity;
        this.photo = photo;
        this.date = date;
        this.isDelivery = isDelivery;
        this.allergies = allergies;
    }

    // Getters for user data (optional, depending on what you need to do with the user data)
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getContact() { return contact; }
    public String getEmail() { return email; }
    public String getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public String getPhoto() { return photo; }
    public String getDate() { return date; }
    public boolean isDelivery() { return isDelivery; }
    public String getAllergies() { return allergies; }
}

  // Method to fetch all customer data from the file
public static List<User> fetchAllCustomerData() {
    List<User> customers = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader("customers.txt"))) {
        String line;

        while ((line = reader.readLine()) != null) {
            // Split the line using the escaped pipe character
            String[] data = line.split("\\|");

            // Ensure that we have the correct number of fields to create a User object
            if (data.length == 10) { // Adjust the number based on the fields in User constructor
                String name = data[0];
                String address = data[1];
                String contact = data[2];
                String email = data[3];
                String product = data[4];
                int quantity = Integer.parseInt(data[5]); // Ensure quantity is an integer
                String photo = data[6];
                String date = data[7];
                boolean isDelivery = Boolean.parseBoolean(data[8]); // Convert to boolean
                String allergies = data[9];

                // Create a new User object and add it to the list
                User user = new User(name, address, contact, email, product, quantity, photo, date, isDelivery, allergies);
                customers.add(user);
            }
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error reading customer data: " + e.getMessage());
    }

// Method to read customer data from file for order invoice
    public static void loadCustomerData(String name, JTextField addressField, JTextField itemField, JTextField quantityField) {
        try (BufferedReader reader = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line using the escaped pipe character
                String[] data = line.split("\\|");
                // Check if the first element in data matches the name (case-insensitive)
                if (data[0].equalsIgnoreCase(name)) {
                    // Populate the corresponding fields
                    addressField.setText(data[1]);
                    itemField.setText(data[4]);
                    quantityField.setText(data[5]);
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Customer not found.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading customer data: " + e.getMessage());
        }
    }

    return customers; // Return the list of customers
}

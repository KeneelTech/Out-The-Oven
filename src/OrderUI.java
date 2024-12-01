import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class OrderUI {
    private String customerName;
    private String customerAddress;
    private String itemDescription;
    private int quantity;
    private double pricePerItem;
    private String invoiceDate;
    private String bankName;
    private String accountNumber;
    private String total;

    // Constructor
    public OrderUI(String customerName, String customerAddress, String itemDescription,
                   int quantity, double pricePerItem, 
                   String invoiceDate, String bankName, String accountNumber, String total) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
        this.invoiceDate = invoiceDate;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.total = total;
    }

    // Method to calculate total amount
    public double calculateTotal() {
        return quantity * pricePerItem;
    }

    // Method to save invoice data to a file
    public void saveInvoiceData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("invoices.txt", true))) {
            writer.write(this.toString());
            writer.newLine();
            JOptionPane.showMessageDialog(null, "Invoice data saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving invoice data: " + e.getMessage());
        }
    }

    // Method to read customer data from file
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
    
    @Override
    public String toString() {
        // Ensure price is formatted to two decimal places
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedPrice = currencyFormat.format(pricePerItem);
        return customerName + "," + customerAddress + "," + itemDescription + "," +
                quantity + "," + formattedPrice + "," + invoiceDate + "," +
                bankName + "," + accountNumber + "," + total;
    }
   

    // Main method to test OrderUI class
    public static void main(String[] args) {
        JFrame frame = new JFrame("Create Order Invoice");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(10, 2));
        
        // Create text fields
        JTextField nameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField itemField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField priceField = new JTextField("0.00"); // default value
        JTextField dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        JTextField bankField = new JTextField();
        JTextField accountField = new JTextField();
        JTextField totalField = new JTextField();
        totalField.setEditable(false); // make total field non-editable

        // Create Save and Cancel button
        JButton saveButton = new JButton("Save Invoice");
        JButton cancelButton = new JButton("Cancel");

        // Set button colors
        saveButton.setBackground(Color.GREEN);
        cancelButton.setBackground(Color.RED);

        // Add action listener to save button
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String address = addressField.getText();
                String item = itemField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                String date = dateField.getText();
                String bank = bankField.getText();
                String account = accountField.getText();
                String total = totalField.getText();

                OrderUI order = new OrderUI(name, address, item, quantity, price, date, bank, account, total);
                order.saveInvoiceData();

                // Clear fields after saving
                JTextField[] textFields = { nameField, addressField, itemField, quantityField, priceField, dateField, bankField, accountField, totalField };
                clearFields(textFields);
            }
        });

        // Add action listener to cancel button
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        // Add action listener to name field to load customer data
        nameField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                OrderUI.loadCustomerData(name, addressField, itemField, quantityField);
            }
        });

        // Add action listener to quantity and price fields to calculate total
        quantityField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTotal(totalField, quantityField, priceField);
            }
        });

        priceField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTotal(totalField, quantityField, priceField);
            }
        });

        // Adding components to the frame
        frame.add(new JLabel("Customer Name:"));
        frame.add(nameField);
        frame.add(new JLabel("Customer Address:"));
        frame.add(addressField);
        frame.add(new JLabel("Item Description:"));
        frame.add(itemField);
        frame.add(new JLabel("Quantity:"));
        frame.add(quantityField);
        frame.add(new JLabel("Price per Item:"));
        frame.add(priceField);
        frame.add(new JLabel("Invoice Date:"));
        frame.add(dateField);
        frame.add(new JLabel("Bank Name:"));
        frame.add(bankField);
        frame.add(new JLabel("Account Number:"));
        frame.add(accountField);
        frame.add(new JLabel("Total:"));
        frame.add(totalField);
        frame.add(saveButton);
        frame.add(cancelButton);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to update total field
    private static void updateTotal(JTextField totalField, JTextField quantityField, JTextField priceField) {
        try {
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());
            double total = quantity * price;
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
            totalField.setText(currencyFormat.format(total)); // Format total as currency
        } catch (NumberFormatException e) {
            totalField.setText("Invalid input");
        }
    }

    // Method to clear input fields and uncheck checkboxes
    private static void clearFields(JTextField[] fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}

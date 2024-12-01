/**
 * Creates Inventory list gui where admin users can manage stock  
 */
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import java.nio.file.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class Inventorylist extends JPanel {
    private JButton     cmdAdjustinventory;
    private JButton     cmdClose;
    private JButton     cmdSortItemQT;
    private JButton     cmdSortItemQuantity;
    private JPanel      pnlCommand; 
    private JPanel      pnlDisplay;
    private ArrayList<Inventory> ilist;
    private  JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;
    

    public Inventorylist(){
        super(new GridLayout(2,1));
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        ilist= loadItems("item_data.txt");
        
    String[] categories= {"Item","Quantity Threshold","Stock"};
    model=new DefaultTableModel(categories,0);
    table = new JTable(model);
    showTable(ilist);
    table.setPreferredScrollableViewportSize(new Dimension(500, ilist.size()*15 +50));
    table.setFillsViewportHeight(true);
    scrollPane = new JScrollPane(table);
   
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus,int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    
            // Get the quantity and threshold from the model
            int quantity = Integer.parseInt(table.getValueAt(row, 2).toString());
            int threshold = Integer.parseInt(table.getValueAt(row, 1).toString());
    
            if (quantity < threshold) {
                cell.setBackground(Color.RED); // Turn row red if quantity < threshold
            } else {
                cell.setBackground(Color.WHITE); // Default color
            }
    
            if (isSelected) {
                cell.setBackground(Color.LIGHT_GRAY); // Highlight selected rows
            }
    
            return cell;
        }
    });

    add(scrollPane);
    cmdAdjustinventory  = new JButton("Adjust Inventory");
    cmdClose = new JButton("Close");
    cmdSortItemQT  = new JButton("Sort by Threshold");
    cmdSortItemQuantity = new JButton("Sort by Quantity");

    cmdSortItemQuantity.addActionListener(new SortQuantityButtonListener());
    cmdClose.addActionListener(new CloseButtonListener());
    cmdAdjustinventory.addActionListener(new AdjustinventoryListener()); 
    cmdSortItemQT.addActionListener(new SortQuantityThresholdButtonListener());
   
    
    pnlCommand.add(cmdAdjustinventory);
    pnlCommand.add(cmdSortItemQT);
    pnlCommand.add(cmdSortItemQuantity);
    pnlCommand.add(cmdClose);
    add(pnlCommand);


}
private void showTable(ArrayList<Inventory> ilist) { //shows the table 
    for (Inventory i : ilist) {
        addt(i);
    }
}
private void addt(Inventory i)//adds name and stock to the table 
{ 
     String name = i.getName();
    String[] item = { name, "" + i.getQT(), "" + i.getStock() }; // Corrected order: Name, Threshold, Stock
    model.addRow(item);   

}

private ArrayList<Inventory> loadItems(String ifile){//goes through the file and gets name, Quantity Threshold ,quantity and number of stock 
     Scanner iscan = null;
        ArrayList<Inventory> ilist = new ArrayList<Inventory>();
        try {
            iscan = new Scanner(new File(ifile));
            while (iscan.hasNextLine()) {
                String line = iscan.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 3) { // Ensure we have at least 3 parts
                    String name = parts[0];
                    int txtQt = Integer.parseInt(parts[1].trim()); // Parse the second part as an integer
                    int quantity =Integer.parseInt(parts[2].trim());
                    int numstock=0;
                    Inventory i = new Inventory(name, txtQt,quantity,numstock);//creates new inventory item 
                    ilist.add(i);//adds inventory item to ilist
            }
        }
            iscan.close();
        }
        catch(IOException e)
        {}
        return ilist;//returns ilist 
}
private class CloseButtonListener implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {
        System.exit(0);
    }

}
private class SortQuantityThresholdButtonListener implements ActionListener {
    public void actionPerformed (ActionEvent e){
        ArrayList <Inventory> ItemList = ilist;
        Collections.sort(ItemList, Comparator.comparingInt(Items::getQT)); // compares and sorts the list by age 
        model.setRowCount(0);// clear the table
        showTable(ilist);// adds to the table
    }
}
private class SortQuantityButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        // Sort the list by stock quantity
        Collections.sort(ilist, Comparator.comparingInt(Inventory::getStock)); // Use getStock for quantity
        model.setRowCount(0); // Clear the table
        showTable(ilist); // Reload the table
    }
}

private class AdjustinventoryListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        String searchstr = JOptionPane.showInputDialog(null, "Enter the name of the item to be updated:"); // Item name
        if (searchstr == null || searchstr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Item name cannot be empty!");
            return;
        }

        try {
            String thresholdStr = JOptionPane.showInputDialog(null, "Enter the new quantity threshold:");
            String quantityStr = JOptionPane.showInputDialog(null, "Enter the new stock quantity:");
            
            if (thresholdStr == null || quantityStr == null || thresholdStr.isEmpty() || quantityStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Threshold and quantity values cannot be empty!");
                return;
            }

            int threshold = Integer.parseInt(thresholdStr.trim());
            int quantity = Integer.parseInt(quantityStr.trim());

            updatethreshold(threshold, searchstr); // Update threshold
            updatestock(quantity, searchstr); // Update stock

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter numeric values for threshold and quantity.");
        }
    }
}
private void updatestock(int n, String searchstr) {
    for (Inventory i : ilist) {
        if (i.getName().equals(searchstr)) { // Search for item
            i.addStock(n); // Add stock to item
        }
    }
    saveAllItemsToFile(); // Save updated inventory to file
    model.setRowCount(0); // Clear the table
    showTable(ilist); // Reload the table
}
private void updatethreshold(int n, String searchstr) {
    for (Inventory i : ilist) {
        if (i.getName().equals(searchstr)) { // Search for item
            i.addQT(n); // Update threshold
        }
    }
    saveAllItemsToFile(); // Save updated inventory to file
    model.setRowCount(0); // Clear the table
    showTable(ilist); // Reload the table
}

public void saveAllItemsToFile() {
    try (PrintWriter writer = new PrintWriter(new FileWriter("item_data.txt", false))) { // Overwrite the file
        for (Inventory i : ilist) {
            writer.println(i.getName() + "," + i.getQT() + "," + i.getStock());
        }
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(null, "Error saving items to file: " + ex.getMessage());
    }
}
public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Inventory Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new Inventorylist());
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            frame.setVisible(true);
        });
}
}
    
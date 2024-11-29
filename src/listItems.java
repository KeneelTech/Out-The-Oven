import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.Collections;
import java.awt.Color;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.*;

public class listItems extends JPanel{
    private JButton     cmdAddNewItem;
    private JButton     cmdClose;
    private JButton     cmdSortItemName;
    private JButton     cmdDeletep;
    private JPanel      pnlCommand; 
    private JPanel      pnlDisplay;
    private ArrayList<Items> ilist;
    private listItems current;
    private  JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;
    public listItems(){
        super(new GridLayout(2,1));
        current = this;
        
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        ilist= loadItems("item_data.txt");
        String[] categories= {"Item Name",
        "Quantity Threshold", };
        model=new DefaultTableModel(categories,0);
        table = new JTable(model);
        showTable(ilist);

        table.setPreferredScrollableViewportSize(new Dimension(500, ilist.size()*15 +50));
        table.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(table);
       
        add(scrollPane);

       
        cmdAddNewItem  = new JButton("Add New Item");
        cmdSortItemName= new JButton("Sort by Name");
        cmdClose   = new JButton("Close");
        cmdDeletep= new JButton ("Del item");

        cmdAddNewItem.setBackground(Color.GRAY);
        cmdSortItemName.setBackground(Color.ORANGE);
        cmdClose.setBackground(Color.GREEN); 


        cmdClose.addActionListener(new CloseButtonListener());
        cmdAddNewItem.addActionListener(new AddNewItemButtonListener()); 
        cmdSortItemName.addActionListener(new SortItemNameButtonListener());
        cmdDeletep.addActionListener(new DeletepButtonListener ());
        
        pnlCommand.add(cmdAddNewItem);
        pnlCommand.add(cmdClose);
        pnlCommand.add(cmdSortItemName);
        pnlCommand.add(cmdDeletep);
        add(pnlCommand);
    }
    private void showTable(ArrayList<Items> ilist) { 
        for (Items i : ilist) {
            addt(i);
        }
    }
    private void addt(Items i)
    { 
        String name= i.getName();
        String[] item={name,""+ i.getQT(),""/*+i.getinStock(),""*/+i.getQuantity()};
        model.addRow(item);        

    }
    public void additem(Items i) 
    {
        ilist.add(i);
        addt(i);

    }
     private ArrayList<Items> loadItems(String ifile)
    {
        Scanner iscan = null;
        ArrayList<Items> ilist = new ArrayList<Items>();
        try {
            iscan = new Scanner(new File(ifile));
            while (iscan.hasNextLine()) {
                String line = iscan.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 3) { // Ensure we have at least 3 parts
                    String name = parts[0];
                    int QT = Integer.parseInt(parts[1].trim()); // Parse the second part as an integer
                    int quantity = Integer.parseInt(parts[2].trim());
                    
                    Items i = new Items(name, QT,quantity);
                    ilist.add(i);
            }
        }

            iscan.close();
        }
        catch(IOException e)
        {}
        return ilist;
    }
     
    private class CloseButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
            
        }

    }
    private class AddNewItemButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ItemEntry newentry= new ItemEntry(current);
        }

    }

    private class SortItemNameButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            ArrayList <Items> ItemList = ilist;//sorts items        
            Collections.sort(ItemList, Comparator.comparing(Items::getName));
            model.setRowCount(0);//clears table
            showTable(ilist);//repopulates table 
        }
    }
  

private class DeletepButtonListener implements ActionListener {//class for delete button
    public void actionPerformed (ActionEvent e){
        String itemNameToDelete = JOptionPane.showInputDialog(null, "Enter the name of the item to delete:");// message displayed 

        // Search for the item in the list of items
        for (Items item : ilist) {
            if (item.getName().equals(itemNameToDelete)) {
                // Remove the item from the list
                ilist.remove(item);
                saveItemsToFile(ilist);
                model.setRowCount(0);// clear the table
                showTable(ilist);// adds to the table
                JOptionPane.showMessageDialog(null, "Item '" + itemNameToDelete + "' has been deleted.");
                return; // Exit the loop after deleting the item
            }
        }
        // If the item is not found
        JOptionPane.showMessageDialog(null, "Item '" + itemNameToDelete + "' not found.");
    }
}
public void saveItemsToFile(ArrayList<Items> itemList) {//saves items to item_data file 
    try (PrintWriter writer = new PrintWriter(new FileWriter("item_data.txt"))) {
        for (Items item : itemList) { //iterates through items in the itemlist 
            writer.println(item.getName() + "," + item.getQT() + "," + item.getQuantity());//gets name, quantity threshold and quantity of the item and saves it 
        }
    } catch (IOException ex) {//catches io exception 
        ex.printStackTrace();
    }
}

}



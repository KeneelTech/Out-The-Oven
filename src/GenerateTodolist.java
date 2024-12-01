import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateTodolist extends JPanel{
    private JButton     cmdClose;
    private JButton     cmdDel;
    private JPanel      pnlCommand; 
    private JPanel      pnlDisplay;
    private List<User> ilist;
    private GenerateTodolist current;
    private  JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel model;
    public GenerateTodolist(){
        super(new GridLayout(2,1));
        current = this;
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        ilist= loadItems("customer.txt");
        String[] categories= {"Name",
        "Product Description",
         "Delivery Date" };
        model=new DefaultTableModel(categories,0);
        table = new JTable(model);
        JLabel titleLabel = new JLabel("Upcoming Orders");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        showTable(ilist);
        table.setPreferredScrollableViewportSize(new Dimension(500, ilist.size()*15 +50));
        table.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(table);
        add(scrollPane);
        add(titleLabel);
       
        cmdClose   = new JButton("Close");
        cmdDel= new JButton("Completed Task");
        cmdClose.addActionListener(new CloseButtonListener());
        cmdDel.addActionListener(new DelButtonListener());

        pnlCommand.add(cmdClose);
        pnlCommand.add(cmdDel);
        add(pnlCommand);
    }
    private void showTable(List<User> ilist) { 
        for (User i : ilist) {
            addt(i);
        }
    }
    private void addt(User i)
    { 
        String name= i.getname();
        String[] item={name,""+ i.getProductDescription(),""/*+i.getinStock(),""*/+i.getdeliverydate()};
        model.addRow(item);        

    }
    public void additem(User i) 
    {
        ilist.add(i);
        addt(i);

    }
     private List<User> loadItems(String ifile)
    {
       String filePath = "customers.txt"; 
        List<User> ilist = new ArrayList<>();

        try {
            List<User> users = Files.lines(Paths.get(filePath))
                .map(line -> {
                    String[] parts = line.split("\\|"); 
                    String name = parts[0];
                    String productDescription = parts[4];
                    String deliveryDate = parts[7];
                    return new User(name, "", "", "", productDescription, "", "", deliveryDate, true, "");
                })
                .collect(Collectors.toList());

            users.sort(Comparator.comparing(User::getdeliverydate));

            // Add each user to the sortedUsers list instead of printing
            for (User user : users) {
                ilist.add(user);  // Add user to the sorted list
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        // Return the sorted list
        return ilist;
    }
    private class CloseButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
            
        }

    }
    private class DelButtonListener implements ActionListener {//class for delete button
        public void actionPerformed (ActionEvent e){
            String itemNameToDelete = JOptionPane.showInputDialog(null, "Enter the name of completed order:");// message displayed 
    
            // Search for the item in the list of items
            for (User item : ilist) {
                if (item.getname().equals(itemNameToDelete)) {
                    // Remove the item from the list
                    ilist.remove(item);
                    //saveItemsToFile(ilist);
                    model.setRowCount(0);// clear the table
                    showTable(ilist);// adds to the table
                    JOptionPane.showMessageDialog(null, "Item '" + itemNameToDelete + "' has been completed and removed.");
                    return; // Exit the loop after deleting the item
                }
            }
            // If the item is not found
            JOptionPane.showMessageDialog(null, "Item '" + itemNameToDelete + "' not found.");
        }
    }
}

     


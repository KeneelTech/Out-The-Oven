
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ItemEntry extends JFrame
{
    private JTextField  txtName;       //name
    private JTextField  txtQt;        //Quantity Threshold
    private JTextField  txtQuantity; //Quantity
    //private JCheckBox check;
    private JButton     cmdSave;
    private JButton     cmdClose;

    private JPanel      pnlCommand;
    private JPanel      pnlDisplay; 
    private listItems li;
  
    public ItemEntry(listItems listitems)
    {
        this.li = listitems;
        setTitle("Add New Item");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        pnlDisplay.add(new JLabel("Name:")); 
        txtName = new JTextField(20);
        pnlDisplay.add(txtName);
        pnlDisplay.add(new JLabel("Quantity Threshold:"));
        txtQt= new JTextField(3);
        pnlDisplay.add(txtQt);
        
        pnlDisplay.add(new JLabel("Quantity:")); 
        txtQuantity = new JTextField(4);
        pnlDisplay.add(txtQuantity);

        pnlDisplay.setLayout(new GridLayout(4,5));
       
        cmdSave      = new JButton("Save");
        cmdClose   = new JButton("Close");


        pnlCommand.add(cmdSave);
        pnlCommand.add(cmdClose);
        add(pnlDisplay, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        cmdClose.addActionListener(new CloseButtonListener());
        // in order to allow for functionality

        cmdSave.addActionListener(new SaveButtonListener());

        

    }
    
    private class CloseButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent e)
        {
            setVisible(false);
        }

    }

    private class SaveButtonListener implements ActionListener{

        public void actionPerformed (ActionEvent e){
            try{
            String name = txtName.getText().trim();
            String intPrice = txtQt.getText().trim();
            Integer Price = Integer.parseInt(intPrice);
            String intQuantity = txtQuantity.getText().trim();
            Integer Quantity = Integer.parseInt(intQuantity);
           // }catch(InvalidInputException e) {{
           if (name.length() > 0 && Price instanceof Integer && Quantity instanceof Integer)
            {
                Items newitem=new Items(name, /*inStock, */Price,Quantity);
                li.additem(newitem);
                saveItemtoFile(newitem);
                setVisible(false);
            } 
        } catch (NumberFormatException num) {
            // Handle the NumberFormatException, indicating that the input cannot be parsed as an integer
            //System.err.println("Invalid input: Price or Quantity is not a valid integer value.");
            JOptionPane.showMessageDialog(null, "Quantity or Price not an integer");
        }
        }
         public void saveItemtoFile(Items item){
            try (PrintWriter writer = new PrintWriter(new FileWriter("item_data.txt", true))) {
                // Append person data to the file
                writer.println(item.getName() + "," + item.getQT() + ","  + item.getQuantity());

            } catch (IOException ex) {
                // Handle file writing error
               // System.err.println("Error saving person d");
            }
        }   
    }
}




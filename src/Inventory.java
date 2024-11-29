/**
 * Constructs an instance of an Inventory item
 * @param name The name of the item
 * @param price The price of the item
 * @param quantity the quantity of the item
 * @param numstock number of stock available for the item
 */
public class Inventory extends Items{
    private int numStock;
    private int qt;
    
    public Inventory( String name, int QT,int quantity,int numStock){
        super(name, QT,quantity);
        this.numStock=numStock;
        
    }
    public int getStock()
    {
        return numStock;
    }
    public void addStock(int newstock)
    {
       numStock= newstock;
    }

    public void addQT(int newQT)
    {
        qt = newQT; 
    }
    
}

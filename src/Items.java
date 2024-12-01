/**
 * Constructs an instance of an Inventory item
 * @param name The name of the item
 * @param price The price of the item
 * @param quantity the quantity of the item
 */
public class Items {
    protected String name;
	private int QT;
    private int quantity;

public Items (String name, int QT,int quantity){
    this.name=name;
    this.QT=QT;
    this.quantity=quantity;
}
public String getName()
{
    return name;
}
public int getQT()
{
return QT;
}

public int getQuantity(){
    return quantity;
}
}

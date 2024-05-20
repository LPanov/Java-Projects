import java.util.*;

class ShoppingCart {
  private ArrayList<Item> items;
  private double total;
  private int numItems;

  public ShoppingCart(){
    items = new ArrayList<Item>();
  }

  public void addItem(Item i){
    items.add(i);
    calculateTotal(i);
    calculateNumItems(i);
  }

  private void calculateTotal(Item i){
    total += i.getSubtotal();
  }

  public double getTotal(){
    return total;
  }

  public int getNumItems(){
    return numItems;
  }

  private void calculateNumItems(Item i){
    numItems += i.getQuantity();
  }

  public String toString(){
    return getClass().getName()+"[items="+items+", total="+total+"]";
  }
}
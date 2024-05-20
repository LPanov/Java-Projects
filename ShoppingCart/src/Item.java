class Item {
  private String name;
  private double price;
  private int quantity;
  private double subtotal;

  public Item(String n, double p, int q){
    name = n;
    price =p;
    quantity = q;
    subtotal = quantity*price;
  }

  public void calculateSubtotal(){
    subtotal += quantity*price;
  }

  public double getSubtotal(){
    return subtotal;
  }

  public int getQuantity(){
    return quantity;
  }
  public String toString(){
    return getClass().getName()+"[name="+name+", price="+price+", quantity="+ quantity+", subtotal="+subtotal+"]";
  }
}
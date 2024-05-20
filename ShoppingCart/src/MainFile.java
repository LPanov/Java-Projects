public class MainFile {  
  public static void main(String[] args) {
    
    Item item1 = new Item("milk", 1.5, 1);
    Item item2 = new Item("apple", 0.75, 5);
    Item item3 = new Item("bread", 2.25, 2);
    ShoppingCart cart = new ShoppingCart();

    cart.addItem(item1);
    cart.addItem(item2);
    cart.addItem(item3);

    System.out.println(cart.getTotal());
    System.out.println(cart.getNumItems());
    System.out.println(cart);

  }
}
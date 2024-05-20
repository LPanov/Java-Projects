public class Main {  
    public static void main(String[] args) {
      
      Library library = new Library();
      Book book1 = new Book("Three Musketeers", "Alexandre Dumas", "fiction");
      Book book2 = new Book("The Count of Monte Cristo", "Alexandre Dumas", "fiction");
      Book book3 = new Book("Educated", "Tara Westover", "nonfiction");
  
      library.addBook(book1);
      library.addBook(book2);
      library.addBook(book3);
      library.sortBooks();
      System.out.println(library.getBooks());
      System.out.println(library.getFiction());
      System.out.println(library.getNonfiction());
      System.out.println(library.searchAuthor("Alexandre Dumas"));
      System.out.println(library.searchAuthor("Herman Melville"));
      System.out.println(library.searchTitle("Educated"));
      System.out.println(library.searchTitle("Moby Dick"));
      
    }
  }
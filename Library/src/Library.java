import java.util.*;

class Library {
  private ArrayList<Book> books;
  private ArrayList<Book> fiction;
  private ArrayList<Book> nonfiction;
  
  /**
  * Default contstructor 
  */
  public Library() {
    books = new ArrayList<Book>();
    fiction = new ArrayList<Book>();
    nonfiction = new ArrayList<Book>();
  }
  
  /**
  * Getter for books 
  */
  public ArrayList<Book> getBooks() {
    return books;
  }
  
  /**
  * Getter for fiction 
  */
  public ArrayList<Book> getFiction() {
    return fiction;
  }
  
  /**
  * Getter for nonfiction 
  */
  public ArrayList<Book> getNonfiction() {
    return nonfiction;
  }
  
  /**
  * Takes a Book object and adds it to books 
  */
  public void addBook(Book b) {
    books.add(b);
  }
  
  /**
  * Takes a string and returns a boolean 
  */
  public boolean searchTitle(String title) {
    boolean hasBook = false;
    for (Book b : books) {
      if (title.toLowerCase().equals(b.getTitle().toLowerCase())) {
        hasBook = true;
      }
    }
    return hasBook;
  }
  
  /**
  * Takes a string and returns an ArrayList of Book objects 
  */
  public ArrayList<Book> searchAuthor(String author) {
    ArrayList<Book> authorBooks = new ArrayList<Book>();
    for (Book b : books) {
      if (author.toLowerCase().equals(b.getAuthor().toLowerCase())) {
        authorBooks.add(b);
      }
    }
    return authorBooks;
  }
  
  /**
  * Helper method that calls sortFiction and sortNonfiction 
  */
  public void sortBooks() {
    fiction = sortFiction();
    nonfiction = sortNonfiction();
  }
  
  /**
  * Returns an ArrayList of Book objects where the gengre is fiction
  */
  private ArrayList<Book> sortFiction() {
    ArrayList<Book> fictionBooks = new ArrayList<Book>();
    for (Book b : books) {
      if (b.getGenre().toLowerCase().equals("fiction")) {
        fictionBooks.add(b);
      }
    }
    return fictionBooks;
  }
  
  /**
  * Returns an ArrayList of Book objects where the gengre is nonfiction
  */
  private ArrayList<Book> sortNonfiction() {
    ArrayList<Book> nonfictionBooks = new ArrayList<Book>();
    for (Book b : books) {
      if (b.getGenre().toLowerCase().equals("nonfiction")) {
        nonfictionBooks.add(b);
      }
    }
    return nonfictionBooks;
  }
}
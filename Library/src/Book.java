class Book{
  private String title;
  private String author;
  private String genre;

  public Book(String t, String a, String g){
    title = t;
    author = a;
    genre = g;
  }

  public String getTitle(){
    return title;
  }
  public String getAuthor(){
    return author;
  }
  public String getGenre(){
    return genre;
  }

  public String toString() {
    return getClass().getName() + "[title="+ title +", author="+author+", genre="+genre+"]";
    }
}

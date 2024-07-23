package Logic;

import java.time.Year;
import java.util.ArrayList;

public class Book implements Prototype{
    private ArrayList<Observer> observers = new ArrayList<>(); // library, members, ui
    private String title;
    private String author;
    private int publishYear;
    private BookState state;
    private int bookId;
    private static int id = 0;

    public Book(String title, String author, int publishYear){
        this.title = title.toLowerCase();
        this.author = author.toLowerCase();
        this.publishYear = publishYear;
        this.state = new AvailableState(); // by default available - consider using state pattern
        this.bookId = Book.id++;
    }


    // getters - title, author, publish year, isAvailable + bookId (for searches and loans)

    /**
     * gets the title of the book
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * gets the author name
     * @return String
     */
    public String getAuthor() {
        return author;
    }

    /**
     * gets the publishing year of the book
     * @return int
     */
    public int getPublishYear() {
        return publishYear;
    }

    /**
     * gets the book id
     * @return int
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * gets the current state of the book as string
     * @return String represents the current state
     */
    public String getState(){
        return this.state.toString();
    }


    // setters - title, author, publish year, isAvailable (used by loans)

    /**
     * sets the title for the book if not empty/ null
     * @param title String
     */
    public void setTitle(String title) {
        if(title == null){
            System.out.println("can't receive null as book title");
            return;
        }
        if(title.isEmpty()){
            System.out.println("can't receive empty book title");
            return;
        }
        this.title = title;
    }

    /**
     * changes the name of the author (if not empty / null)
     * @param author String (name of the author)
     */
    public void setAuthor(String author) {
        if(author == null){
            System.out.println("can't receive null as author name");
            return;
        }
        if(author.isEmpty()){
            System.out.println("can't receive empty author name");
            return;
        }
        this.author = author;
    }

    /**
     * changes the publishing year of the book to the new year object
     * checks if the year received is valid
     * (can add only books that where created until the current year)
     * @param publishYear int
     */
    public void setPublishYear(int publishYear) {
        int currentYear = Year.now().getValue();
        if(publishYear > currentYear){
            this.publishYear = publishYear;
        }
        else{
            System.out.println("invalid year");
        }
    }

    /**
     * used to change the state of the book object
     * @param newState of type BookState
     */
    public void setState(BookState newState) {
        this.state = newState;
    }

    /**
     * creates a clone of the book object using Prototype pattern
     * implementation of method from Prototype interface
     * @return Prototype of Book
     */
    @Override
    public Prototype clone() {
        return new Book(this.title, this.author, this.publishYear);
    }

    /**
     * changes the state after borrowing/ returning a book
     */
    public void doAction(){
        if(this.state instanceof AvailableState){
            this.state.borrowBook(this);
        }else{
            this.state.returnBook(this);
        }
    }

    /**
     * override for toString function, returns the book object as string
     * @return String
     */
    @Override
    public String toString() {
        return String.format("title: %s\nauthor: %s\npublish year: %d\navailable: %s\nid: %d", this.title, this.author, this.publishYear,
             this.state.toString().equals("available") ? "true" : "borrowed" , this.bookId);
    }


}

import java.util.ArrayList;

public class Library {
    private static Library libraryInstance = null;
    private Librarian librarian; // could have more than 1 librarian?
    private ArrayList<Book> books; // using arraylist to have a dynamic list and an easy access
//    private ArrayList<Loan> loans;
    private ArrayList<Member> members;


    private Library(){
        this.librarian = new Librarian("1");
        this.books = new ArrayList<Book>();
        this.members = new ArrayList<Member>();
//        loans = new ArrayList<Loan>(); // NOT SURE IF LOAN SHOULD BE RELATED TO THE BOOK ITSELF
    }

    public static Library getInstance() {
        if(Library.libraryInstance == null) {
            Library.libraryInstance = new Library();
        }
        return libraryInstance;
    }


    // members
    // method of adding a new member
    // method of removing an existing member
    // method of getting a member by name/id

    // books
    // method of adding a new book
    // method of removing an existing book
    // method of adding a copy to existing book
    // method of searching a book by id/title/author

    // loans
    // method of getting all books that are borrowed by members
    // method of getting all books that their return time had passed
    // method of returning a book (removing from list of borrowed books of member, changing state of book to available in all books list)

    // library summery method - includes all private methods:
    // getting number of books borrowed (with data if return date has passed, and by who)
    // getting number of books available
    // getting number of books in the library
    // getting number of members
    // getting number of loans(? including past loans?)



}

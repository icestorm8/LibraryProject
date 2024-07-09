import java.util.ArrayList;
import java.util.Date;

public class Library {
    private static Library libraryInstance = null;
    private Librarian librarian; // could have more than 1 librarian?
    private ArrayList<Book> books; // using arraylist to have a dynamic list and an easy access
//    private ArrayList<Loan> loans;
    private ArrayList<Member> members;
    private MemberFactory memberfactory;
    private BookFactory bookfactory;
    private Library(){
        this.librarian = new Librarian("1");
        this.books = new ArrayList<Book>();
        this.members = new ArrayList<Member>();
        this.memberfactory = new MemberFactory(this.members);
        this.bookfactory = new BookFactory(this.books);
//        loans = new ArrayList<Loan>(); // NOT SURE IF LOAN SHOULD BE RELATED TO THE BOOK ITSELF
    }

    // create a single instance of Library using singleton design pattern
    public static Library getInstance() {
        if(Library.libraryInstance == null) {
            Library.libraryInstance = new Library();
        }
        return libraryInstance;
    }


    // members
    // method of checking if user already exists
    public boolean hasMembership(String name, String phoneNumber){
        for(Member member: this.members){
            if(member.getName().equals(name) && member.getPhoneNumber().equals(phoneNumber)){
                return true;
            }
        }
        return false;
    }

    // method of adding a new member - return the memeber id
    public int addMember(String name, String phoneNumber){
        // if doesn't have membership = create new member, else - return id of existing one
        Member newMember = this.memberfactory.createMember(name, phoneNumber);
        return newMember.getMemberId();
    }

    // method of removing an existing member
    // by id
    public boolean removeMember(int id){
        return this.members.removeIf(member -> member.getMemberId() == id);
    }
    // by name and phone number
    public boolean removeMember(String name, String phoneNumber){
        return this.members.removeIf(member -> member.getName().equals(name) && member.getPhoneNumber().equals(phoneNumber));
    }
    // method of getting a member by name/id

    // books
    // method of adding a new book
    public int addBook(String title, String author, int publishYear){
        // if already exists - return the first book that answers these properties
        // else - returns the new book's id with those properties
        Book newBook = this.bookfactory.createBook(title, author, publishYear);
        return newBook.getBookId();
    }
    // method of removing an existing book
    // by id - remove specific copy of a book
    public boolean removeBook(int id){
        return this.books.removeIf(book -> book.getBookId() == id);
    }
    // remove all copies by properties
    public boolean removeAllCopies(String title, String author, int publishYear){
        return this.books.removeIf(book -> book.getTitle().equals(title) && book.getAuthor().equals(author) && book.getPublishYear() == publishYear);
    }

    // method of adding a copy to existing book
    // maybe use cloneable / prototype


    // method of searching a book by id/title/author
    // by id:
    public Book searchById(int id){
       for(Book book: this.books){
           if(book.getBookId() == id){
               return book;
           }
       }
       return null; // book with that id wasn't found
    }
    // by title
    public ArrayList<Book> searchByTitle(String title){
        ArrayList<Book> booksWithTitle = new ArrayList<>();
        for(Book book: this.books){
            if(book.getTitle().equals(title) || book.getTitle().contains(title)){
                booksWithTitle.add(book);
            }
        }
        return booksWithTitle;
    }
    // by author
    public ArrayList<Book> searchByAuthor(String author){
        ArrayList<Book> booksWithAuthor = new ArrayList<>();
        for(Book book: this.books){
            if(book.getAuthor().equals(author) || book.getAuthor().contains(author)){
                booksWithAuthor.add(book);
            }
        }
        return booksWithAuthor;
    }



    // loans
    // method of getting all books that are borrowed by members
    // method of getting all books that their return time had passed
    public ArrayList<Loan> getAllBorrowedPastTime(){
        ArrayList<Loan> late = new ArrayList<>();
        Date today = new Date(System.currentTimeMillis());
        for(Member member : this.members){
            for(Loan loan : member.getBorrowedBooks()){
                if(loan.getReturnDate().after(today)){
                    late.add(loan);
                }
            }
        }
        return late; // maybe should return here a list of users that are late
    }
    // method of returning a book (removing from list of borrowed books of member, changing state of book to available in all books list)

    // library summery method - includes all private methods:

    public String getSummery(){
        return "ACTIVE MEMBERS: " + this.members.size() + "\nTOTAL BOOKS: " + this.books.size() + "\nBORROWED BOOKS: " + this.getAmountOfBorrowedBooks() + "\nAVAILABLE BOOKS: "+( this.books.size()- this.getAmountOfBorrowedBooks());
    }
    // getting number of books borrowed (with data if return date has passed, and by who)
    private int getAmountOfBorrowedBooks(){
        int count = 0;
        for(Book book : this.books){
            if(!book.isAvailable()){
                count++;
            }
        }
        return this.books.size() - count;
    }
    // getting number of books available
    // getting number of loans(? including past loans?)
    // ^ this is the number of books borrowed, unless it includes the past loans, but i think having a statistic of who is late is better here



}

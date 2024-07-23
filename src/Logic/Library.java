package Logic;

import java.util.ArrayList;
import java.util.Date;

public class Library {
    private static Library libraryInstance = null;
    private Librarian librarian; // could have more than 1 librarian? will be used if had any need for login
    private ArrayList<Book> books; // using arraylist to have a dynamic list and an easy access
    private ArrayList<Member> members;
    private MemberFactory memberfactory;
    private BookFactory bookfactory;

    /**
     * private constructor, used for creating only one instance of the book
     */
    private Library(){
        this.librarian = new Librarian("1");
        this.books = new ArrayList<Book>();
        this.members = new ArrayList<Member>();
        this.memberfactory = new MemberFactory(this.members);
        this.bookfactory = new BookFactory(this.books);
    }

    /**
     * create a single instance of Library using singleton design pattern
     * if the instance already created - returns that instance
     */
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

    // get all memebers
    public ArrayList<Member> getAllMembers(){
        return this.members;
    }
    // method of getting a member
    // by id
    public Member getMemberById(int id){
        for(Member member: this.members){
            if(member.getMemberId() == id){
                return member;
            }
        }
        return null; // book with that id wasn't found
    }
    // by name - could be a list of members with the same name
    public ArrayList<Member> getMembersByName(String name){
        ArrayList<Member> members = new ArrayList<>();
        for(Member member: this.members){
            if(member.getName().equalsIgnoreCase(name)){
                members.add(0, member);
            }
            else if (member.getName().toLowerCase().contains(name.toLowerCase())){
                members.add(member);
            }
        }
        return members;
    }

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
    public void addCopyofBook(Book book){
        System.out.println(book);
        Book copy = (Book) book.clone();
        System.out.println(copy);
        this.books.add(copy);
    }
    public void addCopyofBookById(int id){
        Book book = this.getBookById(id);
        if(book != null){
            System.out.println(book);
            Book copy = (Book) book.clone();
            System.out.println(copy);
            this.books.add(copy);
        }

    }
    // get all books
    public ArrayList<Book> getAllBooks(){
        return this.books;
    }


    // method of searching a book by id/title/author
    // by id:
    public Book getBookById(int id){
       for(Book book: this.books){
           if(book.getBookId() == id){
               return book;
           }
       }
       return null; // book with that id wasn't found
    }
    // by title
    public ArrayList<Book> getBookByTitle(String title){
        ArrayList<Book> booksWithTitle = new ArrayList<>();
        for(Book book: this.books){
            if(book.getTitle().equals(title) || book.getTitle().contains(title)){
                booksWithTitle.add(book);
            }
        }
        return booksWithTitle;
    }
    // by author
    public ArrayList<Book> getBookByAuthor(String author){
        ArrayList<Book> booksWithAuthor = new ArrayList<>();
        for(Book book: this.books){
            if(book.getAuthor().equals(author) || book.getAuthor().contains(author)){
                booksWithAuthor.add(book);
            }
        }
        return booksWithAuthor;
    }



    // loans
    // loan a book by id
    public boolean loanBook(Book book, int memberId){
        Member member = this.getMemberById(memberId);
        if(member!= null){
            book.doAction();
            member.addBorrowedBook(book.getBookId());
            return true;
        }
        return false;
    }
    // return a book by id
    public void returnBook(int bookId){
        // this will return the book if the book is borrowed
        Book book = this.getBookById(bookId);
        // remove loan from member
        this.removeLoanByBookId(bookId);
        book.doAction();

    }

    // view all loans of member
    // method of getting all books that are borrowed by members
    public boolean removeLoanByBookId(int bookId){
        for(Member member: this.members){
            ArrayList<Loan> memberActiveLoans = member.getAllActiveLoans();
            if(!memberActiveLoans.isEmpty()){
                for(Loan loan: memberActiveLoans){
                    if(loan.getBookId() == bookId){
                        member.returnBorrowedBook(bookId);
                        return true;
                    }

                }
            }
        }
        return false;
    }
    // method of getting all books that their return time had passed
    public ArrayList<Loan> getAllBorrowedPastTime(){
        ArrayList<Loan> late = new ArrayList<>();
        Date today = new Date(System.currentTimeMillis());
        for(Member member : this.members){
            for(Loan loan : member.getAllActiveLoans()){
                if(loan.getReturnDate().after(today)){
                    late.add(loan);
                }
            }
        }
        return late; // maybe should return here a list of users that are late
    }
    // method of returning a book (removing from list of borrowed books of member, changing state of book to available in all books list)
//    public boolean returnBook(){
//
//    }
    // library summery method - includes all private methods:

    public String getSummery(){
        return String.format("SUMMERY: \nACTIVE MEMBERS: %d\nTOTAL BOOKS: %d\nBORROWED BOOKS: %d\nAVAILABLE BOOKS: %d\n" ,this.members.size(), this.books.size(), this.getAmountOfBorrowedBooks(), ( this.books.size()- this.getAmountOfBorrowedBooks()));
    }
    // getting number of books borrowed (with data if return date has passed, and by who)
    private int getAmountOfBorrowedBooks(){
        int count = 0;
        for(Book book : this.books){
            if(book.getState().equals("available")){
                count++;
            }
        }
        return this.books.size() - count;
    }

    // ^ this is the number of books borrowed, unless it includes the past loans, but i think having a statistic of who is late is better here



}

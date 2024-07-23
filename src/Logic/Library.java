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

    /**
     * checks if member with the same name and phone number already exists
     * @param name person name
     * @param phoneNumber person phone number
     * @return does member have membership (boolean)
     */
    public boolean hasMembership(String name, String phoneNumber){
        for(Member member: this.members){
            if(member.getName().equals(name) && member.getPhoneNumber().equals(phoneNumber)){
                return true;
            }
        }
        return false;
    }

    /**
     * adds a new member using name and phone number and returns the new id of it
     * @param name name of the member
     * @param phoneNumber phone number of the member
     * @return id int
     */
    public int addMember(String name, String phoneNumber){
        if(name == null || phoneNumber == null){
            System.out.println("invalid arguments - null");
            return -1;
        }
        if(name.isEmpty() || phoneNumber.isEmpty()){
            System.out.println("invalid arguments - empty");
            return -1;
        }
        // if doesn't have membership = create new member, else - return id of existing one
        Member newMember = this.memberfactory.createMember(name, phoneNumber);
        return newMember.getMemberId();
    }

    /**
     * remove an existing member by its id
     * if member doesn't exist - doesn't do anything
     * @param id member id
     * @return boolean - did member with the id was found and removed
     */
    public boolean removeMember(int id){
        return this.members.removeIf(member -> member.getMemberId() == id);
    }

    /**
     * remove member by name and phone number
     * @param name name of the member
     * @param phoneNumber phone number of the member
     * @return boolean did member was found and removed
      */
    public boolean removeMember(String name, String phoneNumber){
        return this.members.removeIf(member -> member.getName().equals(name) && member.getPhoneNumber().equals(phoneNumber));
    }

    /**
     * get all members of the library
     * @return arraylist of members
     */
    public ArrayList<Member> getAllMembers(){
        return this.members;
    }

    /**
     * gets a member by id.
     * if member with the id wasn't found - returns null
     * @param id member id
     * @return Member found
     */
    public Member getMemberById(int id){
        for(Member member: this.members){
            if(member.getMemberId() == id){
                return member;
            }
        }
        return null; // book with that id wasn't found
    }



    /**
     * returns a list with all members that has the same name (equals or contains)
     * the members who are an exact match are inserted at the top of the list
     * the members whose names only contain the part of the name will be at the end of the list
     * @param name String
     * @return list of members with the name entered
     */
    public ArrayList<Member> getMembersByName(String name){
        ArrayList<Member> members = new ArrayList<>();
        if(name == null){return members;} // return the empty list if null
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

    /**
     * adds a new book to the library
     * @param title
     * @param author
     * @param publishYear
     * @return id of the added book
     */
    public int addBook(String title, String author, int publishYear){
        // if already exists - return the first book that answers these properties
        // else - returns the new book's id with those properties
        Book newBook = this.bookfactory.createBook(title, author, publishYear);
        return newBook.getBookId();
    }


    /**
     * removes a book by its id and returns
     * true if the book was found and removed
     * false if wasn't found
     * @param id - of book to remove
     * @return boolean
     */
    public boolean removeBook(int id){
        return this.books.removeIf(book -> book.getBookId() == id);
    }

    /**
     * removes all copies of a book properties( name, author and publishing year_
     * @param title
     * @param author
     * @param publishYear
     * @return true (books found and removed), false (book/s weren't found)
     */
    public boolean removeAllCopies(String title, String author, int publishYear){
        return this.books.removeIf(book -> book.getTitle().equals(title) && book.getAuthor().equals(author) && book.getPublishYear() == publishYear);
    }

    // method of adding a copy to existing book
    // maybe use cloneable / prototype

    /**
     * clones a book by receiving the book object itself
     * @param book
     */
    public void addCopyofBook(Book book){
        System.out.println(book);
        Book copy = (Book) book.clone();
        System.out.println(copy);
        this.books.add(copy);
    }

    /**
     * clones a book by its id
     * the id is than searched and if the book object with that id is found - the book is cloned
     * @param id
     */
    public void addCopyofBookById(int id){
        Book book = this.getBookById(id);
        if(book != null){
            System.out.println(book);
            Book copy = (Book) book.clone();
            System.out.println(copy);
            this.books.add(copy);
        }

    }

    /**
     * gets the list of books in the library
     * @return arraylist of books
     */
    public ArrayList<Book> getAllBooks(){
        return this.books;
    }


    /**
     * returns a book by its id
     * if book wasn't found returns null
     * @param id
     * @return book or null
     */
    public Book getBookById(int id){
       for(Book book: this.books){
           if(book.getBookId() == id){
               return book;
           }
       }
       return null; // book with that id wasn't found
    }


    /**
     * returns a list of books that their titles are equal or contain the query received
     * @param query - the title to be searched
     * @return list of books containing/equal to the query in title, or null - if query was null
     */
    public ArrayList<Book> getBookByTitle(String query){
        if(query == null){
            return null;
        }
        ArrayList<Book> booksWithTitle = new ArrayList<>();
        for(Book book: this.books){
            if(book.getTitle().equals(query) || book.getTitle().contains(query)){
                booksWithTitle.add(book);
            }
        }
        return booksWithTitle;
    }

    /**
     * returns a list of books that their author names are equal or contain the query received
     * @param query - the author name to be searched
     * @return list of books containing/equal to the query in author, or null - if query was null
     */
    public ArrayList<Book> getBookByAuthor(String query){
        if(query == null){
            return null;
        }
        ArrayList<Book> booksWithAuthor = new ArrayList<>();
        for(Book book: this.books){
            if(book.getAuthor().equals(query) || book.getAuthor().contains(query)){
                booksWithAuthor.add(book);
            }
        }
        return booksWithAuthor;
    }



    // loans

    /**
     * loans a book by receiving the book object to be loaned and the member id
     * if member wasn't found returns false
     * if book was null - returns false
     * otherwise loans the book and change the member borrowed books list and returns true
     * @param book to be loand
     * @param memberId of member who loans the book
     * @return boolean
     */
    public boolean loanBook(Book book, int memberId){
        if(book == null) return false;
        Member member = this.getMemberById(memberId);
        if(member!= null){
            book.doAction();
            member.addBorrowedBook(book.getBookId());
            return true;
        }
        return false;
    }

    /**
     * returns a book by id
     * updates the book state and removes it from the loan list of the member that loaned it
     * @param bookId
     */
    public void returnBook(int bookId){
        // this will return the book if the book is borrowed
        Book book = this.getBookById(bookId);
        if(book == null){return;}
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

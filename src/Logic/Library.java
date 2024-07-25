package Logic;

import java.util.ArrayList;
import java.util.Date;

public class Library implements Subject {
    private ArrayList<Observer> observers = new ArrayList<>(); // library, members, ui
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
        this.books = new ArrayList<Book>();
        this.members = new ArrayList<Member>();
        this.memberfactory = new MemberFactory(this.members);
        this.bookfactory = new BookFactory(this.books);
        this.librarian = (Librarian) this.getMemberById(this.addLibrarian("librarian 1", "0504616333"));
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

    /**
     * used for testing
     */
    public void resetLibrary(){
        this.books.clear();
        this.members.clear();
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
        Member newMember = this.memberfactory.createMember(name, phoneNumber, "member");
        this.notifyObservers();
        return newMember.getMemberId();
    }

    private int addLibrarian(String name, String phoneNumber){
        if(name == null || phoneNumber == null){
            System.out.println("invalid arguments - null");
            return -1;
        }
        if(name.isEmpty() || phoneNumber.isEmpty()){
            System.out.println("invalid arguments - empty");
            return -1;
        }
        // if doesn't have membership = create new member, else - return id of existing one
        Member newMember = this.memberfactory.createMember(name, phoneNumber, "librarian");
        this.notifyObservers();
        return newMember.getMemberId();
    }


    /**
     * remove an existing member by its id
     * if member doesn't exist - doesn't do anything
     * @param id member id
     * @return boolean - did member with the id was found and removed
     */
    public boolean removeMember(int id){
        boolean isSuccessful = this.members.removeIf(member -> member.getMemberId() == id);
        this.notifyObservers();
        return isSuccessful;
    }

    /**
     * remove member by name and phone number
     * @param name name of the member
     * @param phoneNumber phone number of the member
     * @return boolean did member was found and removed
      */
    public boolean removeMember(String name, String phoneNumber){
        boolean isSuccessful =  this.members.removeIf(member -> member.getName().equals(name) && member.getPhoneNumber().equals(phoneNumber));
        this.notifyObservers();
        return isSuccessful;
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
     * returns a list of members with the phone number requested (or if librarian entered half of the phone)
     * a list because a mom can have a membership for her kid and for herself lets say
     * @param phoneNumber of member
     * @return arraylist of members with the same phone (typically a family)
     */
    public ArrayList<Member> getMembersByPhone(String phoneNumber){
        ArrayList<Member> result = new ArrayList<>();
        if(phoneNumber == null){
            return null;
        }
        if(phoneNumber.isEmpty()){
            return null;
        }
        if(!Library.isValidGlobalNumber(phoneNumber)){
            String regex = "[0-9]+";
            if(phoneNumber.matches(regex)){
                for(Member member: this.members){
                    if(member.getPhoneNumber().contains(phoneNumber)){
                        result.add(member);
                    }
                }
                return result;
            }else{
                return null;
            }
        }
        else{
            for(Member member: this.members){
                if(member.getPhoneNumber().equals(phoneNumber) || member.getPhoneNumber().contains(phoneNumber)){
                    result.add(member);
                }
            }
            return result;
        }

    }

    /**
     * checks if string is a valid global phone number
     * @param phoneNumber to check
     * @return true - is valid, false - not valid
     */
    private static boolean isValidGlobalNumber(String phoneNumber) {
        String allCountryRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";
        return phoneNumber.matches(allCountryRegex);
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
        this.notifyObservers();
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
        boolean isSuccessful = false;
        Book foundBook = this.getBookById(id);
        if(foundBook != null ){
           if(foundBook.getState().equals("borrowed")){
               this.removeLoanByBookId(id);
           }
            isSuccessful = this.books.removeIf(book -> book.getBookId() == id);
            this.notifyObservers();
        }
        else{
            return false;
        }

        return isSuccessful;
    }

    /**
     * removes all copies of a book (same properties - name, author and publishing year)
     * @param toRemove book
     * @return true (books found and removed), false (book/s weren't found)
     */
    public boolean removeAllCopies(Book toRemove){
        boolean isSuccessful = this.books.removeIf(book -> book.equals(toRemove));
        this.notifyObservers();
        return isSuccessful;
    }

    // method of adding a copy to existing book
    // maybe use cloneable / prototype

    /**
     * clones a book by receiving the book object itself
     * @param book
     */
    public Book addCopyofBook(Book book){
        Book copy = (Book) book.clone();
        this.books.add(copy);
        this.notifyObservers();
        return copy;
    }

    /**
     * clones a book by its id
     * the id is than searched and if the book object with that id is found - the book is cloned
     * @param id
     */
    public Book addCopyofBookById(int id){
        Book book = this.getBookById(id);
        if(book != null){
            Book copy = (Book) book.clone();
            this.books.add(copy);
            this.notifyObservers();
            return copy;
        }
        return null;
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

    public ArrayList<Book> getBooksByYear(int year){
        ArrayList<Book> result = new ArrayList<>();
        for(Book book : this.books){
            if(book.getPublishYear() == year){
                result.add(book);
            }
        }
        return result;
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
        if(book.getState().equals("borrowed")) return false;
        Member member = this.getMemberById(memberId);
        if(member!= null){
            book.doAction();
            member.addBorrowedBook(book.getBookId());
            this.notifyObservers(); // loan added
            return true;
        }
        return false;
    }

    /**
     * returns a book by id
     * updates the book state and removes it from the loan list of the member that loaned it
     * @param bookId
     */
    public boolean returnBook(int bookId){
        // this will return the book if the book is borrowed
        Book book = this.getBookById(bookId);
        if(book == null){return false;}
        if(book.getState().equals("available")) return false;
        // remove loan from member
        this.removeLoanByBookId(bookId);
        book.doAction();
        this.notifyObservers();
        return true;
    }

    /**
     * removes a loan by the id of the book
     * finds the member that loaned the book with the id received
     * if loan for that id wasn't found, returns false
     * otherwise returns true and removes the loan from the member
     * @param bookId id of book to return
     * @return boolean
     */
    private boolean removeLoanByBookId(int bookId){
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

    /**
     * gets all loans that their return time had passed
     * @return list of loans
     */
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

    /**
     * gets the member by its loan so that we can display easier view for librarian using the system
     * of the late loans
     * @param loaned
     * @return
     */
    public Member getMemberByLoan(Loan loaned){
        for(Member member: this.members){
            ArrayList<Loan> loans = member.getAllActiveLoans();
            for(Loan loan: loans){
                if(loan.getBookId() == loaned.getBookId()){
                    return member;
                }
            }
        }
        return null;
    }

    /**
     * returns a string representing the library summery
     * @return String summery
     */
    public String getSummery(){
        return String.format("SUMMERY: \nACTIVE MEMBERS: %d\nTOTAL BOOKS: %d\nBORROWED BOOKS: %d\nAVAILABLE BOOKS: %d\n" ,this.members.size(), this.books.size(), this.getAmountOfBorrowedBooks(), ( this.books.size()- this.getAmountOfBorrowedBooks()));
    }

    /**
     * gets all books that are borrowed
     * @return number of borrowed books
     */
    private int getAmountOfBorrowedBooks(){
        int count = 0;
        for(Book book : this.books){
            if(book.getState().equals("available")){
                count++;
            }
        }
        return this.books.size() - count;
    }


    /**
     * @param observer
     */
    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * @param observer
     */
    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * when book list changed in size
     * when member list changed in size
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            // change in size of books
            observer.update();
        }
    }

}

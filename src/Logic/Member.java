package Logic;

import java.util.ArrayList;

public class Member {
    private String name;
    private String phoneNumber; // adding another identifier, to help librarian decide which member to remove(names are not unique)
    private ArrayList<Loan> borrowedBooks; // maybe shouldn't be dynamic so theres a limit on amount of books a memmber can loan
    private final int memberId;
    private static int id = 1;

    /**
     * using Exception to prevent creation of the object since data wasn't checked before arriving here
     * @param name string
     * @param phoneNumber phone number
     */
    public Member(String name, String phoneNumber){
        if(name == null || phoneNumber == null){
            System.out.println("can't create member with null fields");
            throw new NullPointerException("arguments are null -> Must provide name and phone number");

        }
        if(name.isEmpty() || phoneNumber.isEmpty()){
            System.out.println("can't create member, fields must be filled");
            throw new IllegalArgumentException(
                    "arguments are empty -> must provide name and phone number");

        }
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.borrowedBooks = new ArrayList<Loan>();
        this.memberId = Member.id++;
    }

    // getters
    /**
     * gets the member name
     * @return member name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the member phone number
     * @return phone number as String
     */
    public String getPhoneNumber(){return this.phoneNumber;}

    /**
     * returns the list of the active loans the member has
     * @return ArrayList of Loan type
     */
    public ArrayList<Loan> getAllActiveLoans() {
        return borrowedBooks;
    }

    /**
     * returns the id of the member
     * @return id
     */
    public int getMemberId() {
        return memberId;
    }

    // setters

    /**
     * sets the name of the member to a new name
     * if name is empty/ null -> doesn't change the name
     * @param name new name for the member
     */
    public void setName(String name) {
        if(name == null){
            System.out.println("name can't be null");
            return;
        }
        if(name.isEmpty()){
            System.out.println("name can't be empty");
            return;
        }
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    /**
     * creates a new loan for the book with the id received
     * adds it to the list of borrows of the member
     * @param bookId that the member wants to borrow
     */
    public void addBorrowedBook(int bookId){
        this.borrowedBooks.add(new Loan(bookId));
    }

    /**
     * removes loan in which the book has been borrowed from the member loan list
     * @param bookId the id of the book the member wants to return
     */
    public void returnBorrowedBook(int bookId){
        this.borrowedBooks.removeIf(loan -> loan.getBookId() == bookId);
    }

    /**
     * returns the member as string
     * @return String
     */
    @Override
    public String toString() {
        return String.format("name: %s\nphone number: %s\nid: %d\nloans: %s\n", this.name,this.phoneNumber, this.memberId, this.borrowedBooks.isEmpty() ? "no loans found": this.borrowedBooks.size() + "loans were found");
    }
}

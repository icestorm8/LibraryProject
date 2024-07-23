package Logic;

import java.util.ArrayList;

public class Member {
    private String name;
    private String phoneNumber; // adding another identifier, to help librarian decide which member to remove(names are not unique)
    private ArrayList<Loan> borrowedBooks; // maybe shouldn't be dynamic so theres a limit on amount of books a memmber can loan
    private final int memberId;
    private static int id = 1;

    public Member(String name, String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.borrowedBooks = new ArrayList<Loan>();
        this.memberId = Member.id++;
    }

    public void addborrowedBook(int bookId){
        // each book id could be loaned one at a time, so the book id is sort of the id of the loan
        this.borrowedBooks.add(new Loan(bookId));
    }

    // getters
    public String getName() {
        return name;
    }
    public String getPhoneNumber(){return this.phoneNumber;}

    public ArrayList<Loan> getAllActiveLoans() {
        return borrowedBooks;
    }

    public int getMemberId() {
        return memberId;
    }

    // setters

    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    // maybe this methods should  be in an interface which each one of the classes involved in returning and borrowing a book are
    // updating
    public void addBorrowedBook(int bookId){
        this.borrowedBooks.add(new Loan(bookId));
    }
    public void returnBorrowedBook(int bookId){
        this.borrowedBooks.removeIf(loan -> loan.getBookId() == bookId);
    }
    @Override
    public String toString() {
        return String.format("name: %s\nphone number: %s\nid: %d\nloans: %s\n", this.name,this.phoneNumber, this.memberId, this.borrowedBooks.isEmpty() ? "no loans found": this.borrowedBooks.size() + "loans were found");
    }
}

import java.util.ArrayList;

public class Member {
    private String name;
    private ArrayList<Loan> borrowedBooks; // maybe shouldn't be dynamic so theres a limit on amount of books a memmber can loan
    private final int memberId;
    private static int id = 1;

    public Member(String name){
        this.name = name;
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

    public ArrayList<Loan> getBorrowedBooks() {
        return borrowedBooks;
    }

    public int getMemberId() {
        return memberId;
    }

    // setters

    public void setName(String name) {
        this.name = name;
    }

    public void setBorrowedBooks(ArrayList<Loan> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    @Override
    public String toString() {
        return String.format("name: %s\nid: %d\nloans: %s", this.name, this.memberId, this.borrowedBooks.toString());
    }
}

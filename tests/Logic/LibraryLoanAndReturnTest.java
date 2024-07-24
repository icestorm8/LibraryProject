package Logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LibraryLoanAndReturnTest {
    Library library;
    ArrayList<Book> books;
    ArrayList<Member> members;

    @BeforeEach
    void setUp() {
        library = Library.getInstance();

        // set up books
        this.books = new ArrayList<>();
        this.books.add(library.getBookById(library.addBook("book2", "shaked", 2024)));
        this.books.add(library.getBookById(library.addBook("book3", "stav", 2015)));

        // set up members
        this.members = new ArrayList<>();
        this.members.add(library.getMemberById(library.addMember("member3", "0504616222")));
        this.members.add(library.getMemberById(library.addMember("member1", "0504616223")));
    }

    @AfterEach
    void tearDown() {
        this.books.clear();
        this.members.clear();
        library.resetLibrary();
    }

    @Test
    void loanBook() {
        Member borrower = this.members.get(0);
        Book toBorrow = this.books.get(0);
        assertEquals(0, borrower.getAllActiveLoans().size(), "member shouldn't have any active loans");
        assertEquals("available", toBorrow.getState());
        library.loanBook(toBorrow, borrower.getMemberId());
        assertEquals(1, borrower.getAllActiveLoans().size(), "member shouldn't have any active loans");
        assertEquals("borrowed", toBorrow.getState());
        assertEquals(toBorrow, library.getBookById(borrower.getAllActiveLoans().get(0).getBookId()));
    }

    @Test
    void returnAvailableBook() {
        assertFalse(library.returnBook(this.books.get(0).getBookId()), "book isn't borrowed so it can't be returned");
    }
    @Test
    void returnBorrowedBook() {
        loanBook();
        assertEquals(1, this.members.get(0).getAllActiveLoans().size(), "after first method, member should have one loan");
        assertTrue(library.returnBook(this.members.get(0).getAllActiveLoans().get(0).getBookId()), "book is loaned, so it can be returned");
        assertEquals(0, this.members.get(0).getAllActiveLoans().size(), "after return, member should have 0 loans");
    }

    @Test
    void getMemberByLoan() {
        loanBook();
        Loan loan = this.members.get(0).getAllActiveLoans().get(0);
        Member byLoan = this.library.getMemberByLoan(loan);
        assertEquals(this.members.get(0), byLoan, "member who borrowed the book should be found by its loan");
    }
}
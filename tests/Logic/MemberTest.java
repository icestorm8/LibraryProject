package Logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    Member member;
    @BeforeEach
    void setUp() {
        this.member = new Member("original name", "0581616223");
    }

    @Test
    void getName() {
        assertEquals("original name", this.member.getName());
    }

    @Test
    void getPhoneNumber() {
        assertEquals("0581616223", this.member.getPhoneNumber());
    }

    @Test
    void getAllActiveLoans() {
        assertEquals(0, this.member.getAllActiveLoans().size(), "new member shouldn't have loans");
    }

    @Test
    void setName() {
        this.member.setName("new name");
        assertEquals("new name", this.member.getName());
        assertThrows(IllegalArgumentException.class, () -> {this.member.setName("0");});
        assertThrows(NullPointerException.class , () -> {this.member.setName(null);});
    }

    @Test
    void setPhoneNumber() {
        this.member.setPhoneNumber("0504978616");
        assertEquals("0504978616", this.member.getPhoneNumber());
        assertThrows(IllegalArgumentException.class, () -> {this.member.setPhoneNumber("0");});
        assertThrows(NullPointerException.class , () -> {this.member.setPhoneNumber(null);});
    }

    @Test
    void addBorrowedBook() {
        Book book = new Book("title", "author", 2024);
        this.member.addBorrowedBook(book.getBookId());
        assertEquals(1, this.member.getAllActiveLoans().size());
    }

    @Test
    void returnBorrowedBook() {
        this.addBorrowedBook();
        assertEquals(1, this.member.getAllActiveLoans().size());
        this.member.returnBorrowedBook(this.member.getAllActiveLoans().get(0).getBookId());
        assertTrue(this.member.getAllActiveLoans().isEmpty());
    }
}
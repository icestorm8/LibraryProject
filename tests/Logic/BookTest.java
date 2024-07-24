package Logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    Book book;

    @BeforeEach
    void setUp(){
        this.book = new Book("original title" , "original author", 2002);
    }
    @Test
    void getTitle() {
        assertEquals("original title", book.getTitle());
    }

    @Test
    void getAuthor() {
        assertEquals("original author", book.getAuthor());
    }

    @Test
    void getPublishYear() {
        assertEquals(2002, book.getPublishYear());
    }

    @Test
    void getState() {
        assertEquals("available", book.getState(), "state should be available");
        book.doAction(); // sets the state to a different state (depending on the state object)
        assertEquals("borrowed", book.getState(), "state should be borrowed");
    }

    @Test
    void setTitle() {
        book.setTitle("new title");
        assertEquals("new title", book.getTitle());
        book.setTitle("");
        assertEquals("new title", book.getTitle(), "should not change for empty title");
        book.setTitle(null);
        assertEquals("new title", book.getTitle(), "should not change for empty title");
    }

    @Test
    void setAuthor() {
        book.setAuthor("new author");
        assertEquals("new author", book.getAuthor());
        book.setAuthor("");
        assertEquals("new author", book.getAuthor(), "should not change for empty author");
        book.setAuthor(null);
        assertEquals("new author", book.getAuthor(), "should not change for null");
    }

    @Test
    void setPublishYear() {
        book.setPublishYear(2019);
        assertEquals(2019, book.getPublishYear());
        book.setPublishYear(-2019);
        assertEquals(2019, book.getPublishYear());
        book.setPublishYear(2025);
        assertEquals(2019, book.getPublishYear());
    }

    @Test
    void doAction() {
        assertEquals("available", book.getState(), "state should be available");
        book.doAction(); // sets the state to a different state (depending on the state object)
        assertEquals("borrowed", book.getState(), "state should be borrowed");
    }
}
package Logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryAddBooksTest {
    Library library;

    @BeforeEach
    void setUp() {
        library = Library.getInstance();
    }

    @AfterEach
    void tearDown() {
        library.resetLibrary();
    }

    @Test
    void addBook() {
        int bookId = library.addBook("new book", "test", 2022);
        Book addedBook = library.getBookById(bookId);
        assertNotNull( addedBook, "Book should not be null");
        assertEquals("new book", addedBook.getTitle());
    }

    @Test
    void addCopyOfBook() {
        int bookId = library.addBook("new book", "test", 2022);
        Book addedBook = library.getBookById(bookId);
        Book copy = null;
        if (addedBook != null && addedBook.getTitle().equals("new book")){
            copy = library.addCopyofBook(addedBook);
        }
        assertNotNull(copy,"copy of book should not be null");
        assertEquals("new book", copy.getTitle());
        assertEquals("test", copy.getAuthor());
        assertEquals(2022, copy.getPublishYear());
        assertNotEquals(copy.getBookId(), addedBook.getBookId());
    }

    @Test
    void addCopyOfBookById() {
        int bookId = library.addBook("new book", "test", 2022);
        Book addedBook = library.getBookById(bookId);
        Book copy = null;
        if (addedBook != null && addedBook.getTitle().equals("new book")){
            copy = library.addCopyofBookById(bookId);
        }
        assertNotNull(copy,"copy of book should not be null");
        assertEquals("new book", copy.getTitle());
        assertEquals("test", copy.getAuthor());
        assertEquals(2022, copy.getPublishYear());
        assertNotEquals(copy.getBookId(), addedBook.getBookId());
    }
    @Test
    void addCopyofBookByIdNonExistingId() {
        Book copy = library.addCopyofBookById(1000000);
        assertNull(copy,"copy of book should be null");
    }

}
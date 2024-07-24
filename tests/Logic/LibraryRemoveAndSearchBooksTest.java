package Logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LibraryRemoveAndSearchBooksTest {
    Library library;
    int toRemoveID;
    int bookIdToCopy;
    ArrayList<Book> books;
    @BeforeEach
    void setUp() {
        library = Library.getInstance();
        this.books = new ArrayList<>();
        toRemoveID = library.addBook("book1", "ron", 2022);
        this.books.add(library.getBookById(toRemoveID));
        this.books.add(library.getBookById(library.addBook("book2", "shaked", 2024)));
        bookIdToCopy = library.addBook("book3", "stav", 2015);
        this.books.add(library.getBookById(bookIdToCopy));
        this.books.add(library.addCopyofBookById(bookIdToCopy));
        this.books.add(library.addCopyofBookById(bookIdToCopy));
    }

    @AfterEach
    void tearDown() {
        library.resetLibrary();
        this.bookIdToCopy = -1;
        this.toRemoveID = -1;
        this.books.clear();
    }
    @Test
    void removeBook() {
        Book book = library.getBookById(toRemoveID);
        assertTrue(library.getAllBooks().contains(book));
        library.removeBook(toRemoveID);
        assertFalse(library.getAllBooks().contains(book));

    }

    @Test
    void removeAllCopies() {
        Book book = this.library.getBookById(bookIdToCopy);
        this.library.removeAllCopies(book);
        assertFalse(this.library.getAllBooks().stream().anyMatch(current -> current.equals(book)), "shouldn't find books that are equal to this copy");
    }

    @Test
    void getAllBooks() {
        assertTrue(this.library.getAllBooks().containsAll(this.books), "All my books should be in the library");
        assertEquals(this.library.getAllBooks().size(), this.books.size(), "Library should only contain my books");
    }

    @Test
    void getBookById() {
        String title = "title";
        String author = "test";
        int year = 2022;
        int bookId = library.addBook(title, author, year);
        Book book = library.getBookById(bookId);
        assertTrue(title.equals(book.getTitle()) && author.equals(book.getAuthor()) && year == book.getPublishYear());
        assertEquals(book.getBookId(), bookId);
    }

    @Test
    void getBooksByYear() {
        int year = 2022;
        assertEquals(1, library.getBooksByYear(year).size(), "Expected 1 books from 2022");
        year = 2015;
        assertEquals(3, library.getBooksByYear(year).size(), "Expected 3 books from 2015");
    }

    @Test
    void getBookByTitle() {
        String title = "book1";
        assertEquals(1, library.getBookByTitle(title).size(), "Expected 1 books with title 'book1'");
        title = "book";
        assertEquals(5, library.getBookByTitle(title).size(), "Expected 5 books with titles containing 'book'");
    }

    @Test
    void getBookByAuthor() {
        String author = "shaked";
        assertEquals(1, library.getBookByAuthor(author).size(), "Expected 1 books with author 'shaked'");
        author = "s";
        assertEquals(4, library.getBookByAuthor(author).size(), "Expected 4 books with author containing 's'");
    }
}
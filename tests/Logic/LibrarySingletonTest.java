package Logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibrarySingletonTest {
    private Library library;
    @BeforeEach
    void setUp() {
        library = Library.getInstance();
    }

    @AfterEach
    void tearDown() {
        library = null;
    }

    @Test
    void testSingelton() {
        Library anotherLibrary = Library.getInstance();
        assertSame(library, anotherLibrary, "Singleton instances should be the same");
    }
}
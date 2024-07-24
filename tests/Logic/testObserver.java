package Logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class testObserver implements Observer{
    Library library;
    private boolean updateReceived = false;

    @BeforeEach
    void setUp() {
        this.updateReceived = false;
        this.library = Library.getInstance();
        this.library.addObserver(this);
    }

    @AfterEach
    void tearDown() {
        this.updateReceived = false;
        this.library.removeObserver(this);
    }

    @Test
    void testObserverAfterChange() {
        this.library.addMember("test", "0501616222");
        assertTrue(this.updateReceived, "should be true since library updated its members");
    }

    @Test
    void testObserverBeforeChage() {
        assertFalse(this.updateReceived, "should be true since library updated its members");
    }

    /**
     *
     */
    @Override
    public void update() {
        this.updateReceived = true;
        System.out.println("there was an update in the library");
    }
}

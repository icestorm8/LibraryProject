package Logic;

import java.util.ArrayList;

public interface Observer {
    // this will be implements by the classes that listen for changes
    // like the ui to library/ library to members

    // this will be implements by the classes that listen for changes
    // like the ui to library/ library to members
    void update(); // BOOK REMOVED - UPDATE LIST IN MEMBERS
    // MEMBER REMOVED - UPDATE LIST IN BOOKS (REMOVE BOOKS THAT AREN'T YET RETURNED)
}

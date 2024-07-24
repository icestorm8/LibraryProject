package Logic;

public class BorrowedState implements BookState{

    /**
     * implementation of return book method for Borrowed state
     * changes the state of the book to be available
     * @param book Book to return
     */
    @Override
    public void doAction(Book book) {
//        System.out.println("book is borrowed, return the book");
        book.setState(new AvailableState());
    }

    /**
     * toString override, returns the state as a readable string
     * @return String
     */
    @Override
    public String toString() {
        return "borrowed";
    }
}

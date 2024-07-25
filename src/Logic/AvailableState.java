package Logic;

public class AvailableState implements BookState{

    /**
     * implementation of return book method for Available state
     * changes the state of the book to be borrowed
     * @param book Book to borrow
     */
    @Override
    public void doAction(Book book) {
        book.setState(new BorrowedState());
    }


    /**
     * toString override, returns the state as a readable string
     * @return String
     */
    @Override
    public String toString() {
        return "available";
    }
}

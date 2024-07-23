package Logic;

public class AvailableState implements BookState{

    /**
     * implementation of return book method for Available state
     * changes the state of the book to be borrowed
     * @param book Book to borrow
     */
    @Override
    public void borrowBook(Book book) {
        System.out.println("book can be borrowed");
        book.setState(new BorrowedState());
    }


    @Override
    public void returnBook(Book book) {
        System.out.println("book already available, no returns - do nothing");
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

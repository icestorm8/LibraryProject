package Logic;

public class AvailableState implements BookState{

    @Override
    public void borrowBook(Book book) {
        System.out.println("book can be borrowed");
        book.setState(new BorrowedState());
    }

    @Override
    public void returnBook(Book book) {
        System.out.println("book already available, no returns - do nothing");
    }

    @Override
    public String toString(){
        return "available";
    }
}

package Logic;

public class BorrowedState implements BookState{
    @Override
    public void borrowBook(Book book) {
        System.out.println("book is already borrowed - do nothing");
    }

    @Override
    public void returnBook(Book book) {
        System.out.println("book is borrowed, return the book");
        book.setState(new AvailableState());
    }

    @Override
    public String toString(){
        return "borrowed";
    }
}

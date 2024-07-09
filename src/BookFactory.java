import java.util.ArrayList;

public class BookFactory {
    private ArrayList<Book> books;

    public BookFactory( ArrayList<Book> libraryBooks) {
        this.books = libraryBooks;
    }

    public Book createBook(String title, String author, int publishYear) {
        // Check if a book with the same title, author and publish year exists and return it if so
        for (Book existingBook : this.books) {
            if (existingBook.getTitle().equals(title) && existingBook.getAuthor().equals(author) && existingBook.getPublishYear() == publishYear) {
                return existingBook;
            }
        }

        // Create a new book if book not already exist - if it does exist (have a couple of copies, this is not the way for it to be created)
        Book newBook = new Book(title, author, publishYear);
        this.books.add(newBook);
        return newBook;
    }
}

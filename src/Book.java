public class Book {
    private String title;
    private String author;
    private int publishYear;
    private boolean isAvailable;
    private int bookId;
    private static int id = 0;

    public Book(String title, String author, int publishYear){
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.isAvailable = true; // by default available - consider using state pattern
        this.bookId = Book.id++;
    }


    // getters - title, author, publish year, isAvailable + bookId (for searches and loans)
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getBookId() {
        return bookId;
    }

    // setters - title, author, publish year, isAvailable (used by loans)

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


    //5. Prototype Pattern - consider using for copying the book and creating another instance of it
    //The prototype pattern is used when the Object creation is costly and requires a lot of time and resources, and you have a similar Object already existing. So this pattern provides a mechanism to copy the original Object to a new Object and then modify it according to our needs. This pattern uses Java cloning to copy the Object. The prototype design pattern mandates that the Object which you are copying should provide the copying feature. It should not be done by any other class. However, whether to use the shallow or deep copy of the object properties depends on the requirements and is a design decision.
}

package Logic;

import java.lang.reflect.Member;
import java.util.Calendar;
import java.util.Date;

public class Loan {
    private int bookId; // not sure if book should contain a loan object
    private Date loanDate;
    private Date returnDate;

    private String loanStatus;

//    private Logic.Member borrower;

    public Loan(int bookId){
        this.loanDate = new Date(System.currentTimeMillis()); // set the current date, the date of creating the new loan
        this.bookId = bookId;
        this.returnDate = getReturnDate(this.loanDate); // this should be today's date + 2 weeks or so
//        this.borrower = borrower; // the person who borrowed the book, should get it to update its loan list
        this.loanStatus = "loaned";
    }

    public Date getReturnDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 14); // + 2 weeks
        return calendar.getTime();
    }
    // getters

    public int getBookId() {
        return bookId;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public String getLoanStatus(){ return this.loanStatus;}


    // setters
    public void setReturnDate(Date returnDate) {
        // this should get a plus of how much time to add until return date and change it accordingly
        this.returnDate = returnDate;
    }

    public void returnBook(){
        this.loanStatus = "returned";
        // search for the book id in the book list/ get the book object instead, set it to available
        // remove from members borrowed books list
    }
    @Override
    public String toString() {
        return String.format("book id: %d\nloan date: %s\nreturn date: %s\n",this.bookId, this.loanDate.toString(), this.returnDate.toString());
    }
}

import java.util.Date;

public class Main {
    public static void main(String[] args) {
//         test - is loan dates working
//        Loan loan = new Loan(1);
//        System.out.println(loan);

        Member b = new Member("yossi ibrahim");
//        System.out.println(b);
        b.addborrowedBook(466);
        b.addborrowedBook(56);
        System.out.println(b);


//        check if library indeed creates only one instance
//        Library a = Library.getInstance();
//        Library c = Library.getInstance();
//        System.out.println(a.equals(c));
    }
}
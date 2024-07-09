import java.util.Date;

public class Main {
    public static void main(String[] args) {
//         test - is loan dates working
//        Loan loan = new Loan(1);
//        System.out.println(loan);

//        Member b = new Member("yossi ibrahim");
////        System.out.println(b);
//        b.addborrowedBook(466);
//        b.addborrowedBook(56);
//        System.out.println(b);
//

//        check if library indeed creates only one instance
        Library a = Library.getInstance();
        a.addMember("shaked", "0584616222");
        a.addMember("shaked", "0584616222");
        a.addMember("rom", "0584616222");
        a.addMember("rom", "0591612564");
        System.out.println(a.getSummery());
        System.out.println("----------------------------------------");
        a.removeMember(3); // should make it remove by name and phone number to remove dealing with searching id
        System.out.println(a.getSummery());
        System.out.println("----------------------------------------");
        a.removeMember("shaked", "0584616222");
        System.out.println(a.getSummery());
//        Library c = Library.getInstance();
//        System.out.println(a.equals(c));
    }
}
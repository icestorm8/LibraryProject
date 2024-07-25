package Logic;

public class Librarian extends Member {

    public Librarian(String name, String phoneNumber){
        // librarian is a member because he can borrow books as well.
        // maybe later could have username and password cause librarian is the user of the ui,
        // but currently I don't have a db or file management, so I don't implement login in the system
        super(name, phoneNumber);
    }

}

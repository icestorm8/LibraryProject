package LibrerianUI;

import Logic.Book;
import Logic.Library;
import Logic.Loan;
import Logic.Member;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoanBookFrame extends JFrame {
    private ArrayList<JTextField> inputs;
    private Book book;
    private final JLabel error1 = new JLabel("member id is empty!");
    private final JLabel error2 = new JLabel( "member doesn't exist");
    private final JLabel error3 = new JLabel( "bad id - must be numeric");
    private JPanel memberData;
    private JButton searchMember;
    private JButton submit;
    private ArrayList<JLabel> memberDataLabels;
    private LoanBookFrame frame;
    public LoanBookFrame(Book book){
        this.frame = this;
        // librarian enters the id of the book the member chose (like scanning it)
        // than clicks on it
        // than if chose the option of borrowing the book - this frame is opened
        // in this frame the librarian asks for the id of the member (like taking a member card from a member)
        // he than enters the id of the member and searches for the member details (to verify)
        // than, he clicks on create loan, which creates a new loan in the library system (which gets updated with the member, loan and such)
        // if he clicked cancel the dialog will be closed, living the details as is to allow for edit

        this.book = book;
        this.inputs = new ArrayList<>();
        this.setLayout(new GridLayout(3, 1));


        this.add(this.errorPanel());
        JPanel loanForm = getInputs();
        this.add(loanForm);
        this.add(this.createMemberPanel());
        // set size - center the frame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int xSize = ((int) dim.getWidth());
        int ySize = ((int) dim.getHeight());
        this.setSize(xSize-400, ySize/2);
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }
    private JPanel errorPanel(){
        JPanel errors = new JPanel();
        errors.add(this.error1);
        errors.add(this.error2);
        errors.add(this.error3);
        this.error1.setVisible(false);
        this.error2.setVisible(false);
        this.error3.setVisible(false);
        return errors;
    }
    private void displayErrors(int errorCode){
        switch (errorCode){
            case(1):
                this.error1.setVisible(true);
                break;
            case(2):
                this.error2.setVisible(true);
                break;
            case(3):
                this.error3.setVisible(true);
                break;
            default:
                this.error1.setVisible(false);
                this.error2.setVisible(false);
                this.error3.setVisible(false);
        }
    }

    private JPanel createMemberPanel(){
        this.memberData = new JPanel();
        this.memberData.setLayout(new BoxLayout(this.memberData, BoxLayout.Y_AXIS));
        this.memberDataLabels = new ArrayList<>();
        this.memberDataLabels.add(new JLabel("Member Found:"));
        this.memberDataLabels.add(new JLabel("name: "));
        this.memberDataLabels.add(new JLabel("phone number: "));
        for(JLabel label: this.memberDataLabels){
            this.memberData.add(label);
        }
        this.memberData.setVisible(false);
        return this.memberData;
    }
    private void updateMemberPanel(Member member){
//        this.memberData.repaint();
        this.memberDataLabels.get(1).setText("name: " + member.getName());
        this.memberDataLabels.get(2).setText("phone number: " + member.getPhoneNumber());
        this.memberData.setVisible(true);
    }
    private void hideMemberPanel(){
        this.memberData.setVisible(false);
    }
    private JPanel getInputs(){
        JPanel form = new JPanel();
        JLabel l = new JLabel("member id: ", JLabel.TRAILING);
        form.add(l);
        JTextField textField = new JTextField(10);
        this.inputs.add(textField);
        l.setLabelFor(textField);
        form.add(textField);
        // define inputs and button
        Loan loan = new Loan(this.book.getBookId()); // create a loan to preview

        final String[][] data = {{"book id: ", loan.getBookId() +""}, {"loan date: ", loan.getLoanDate() + ""}, {"return date: ", loan.getReturnDate() + ""}};
        for (String[] field : data) {
            l = new JLabel(field[0], JLabel.TRAILING);
            form.add(l);
            textField = new JTextField(10);
            textField.setText(field[1]);
            textField.setEditable(false);
            this.inputs.add(textField);
            l.setLabelFor(textField);
            form.add(textField);
        }
        this.searchMember = new JButton("confirm member");
        this.submit = new JButton("add loan");
        form.add(searchMember);
        form.add(submit);
        this.submit.setEnabled(false);
        this.inputs.get(0).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                closeSubmitOption();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                closeSubmitOption();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                closeSubmitOption();
            }
        });
        searchMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideMemberPanel();
                displayErrors(4);
                String text = inputs.get(0).getText();
                if(text.isEmpty()){
                    displayErrors(1);
                    return;
                }else{
                    try{
                        int memberId = Integer.parseInt(text);
                        Member member = Library.getInstance().getMemberById(memberId);
                        if(member == null){
                            throw new NullPointerException("MEMBER WASN'T FOUND");
                        }
                        updateMemberPanel(member);
                        submit.setEnabled(true);

                    }catch (NullPointerException nullPointerException){
                        displayErrors(2);
                        return;
                    }catch (NumberFormatException numberFormatException){
                        displayErrors(3);
                        return;
                    }
                }
            }
        });
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // arriving to this code only after verifying that the user exists, and it's what we want
                String text = inputs.get(0).getText();
                int memberId = Integer.parseInt(text);
                if(Library.getInstance().loanBook(book, memberId)){
                    JOptionPane.showMessageDialog(null,String.format("book successfully loaned by member with id: %d", memberId), "book borrowed confirmation", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                }else{
                    JOptionPane.showMessageDialog(null,"couldn't complete process, something went wrong", "book borrow error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return form;
    }

    private void closeSubmitOption(){
        this.submit.setEnabled(false);
    }

}

package LibrerianUI;

import Logic.Loan;
import Logic.Library;
import Logic.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;

public class MemberLoanPanel extends JPanel {
    Library library;
    LoanTableModel loanModel;

    Member member;
    JTable results;
    JScrollPane scroll;
    ButtonGroup choiceButtonGroup;

    public MemberLoanPanel(Member member){
        this.member = member;
        this.library = Library.getInstance();
        this.loanModel = new LoanTableModel(member.getAllActiveLoans());
        // add the result table to the screen
        this.results = new JTable(loanModel);
        this.scroll = new JScrollPane(results);
        this.add(scroll);
        results.setDragEnabled(false);

        // add listener for table actions
        this.addTableListenersAndActions();


    }

    public void addTableListenersAndActions(){
        results.addMouseListener(new java.awt.event.MouseAdapter() {
            // get book in row selected
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = results.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    Loan selectedLoan = loanModel.getLoanAtRow(row);
                    String[] options = { "close", "return book", "extend loan time"};
                    int selection = JOptionPane.showOptionDialog(null, loanModel.getLoanAtRow(row), "what would you like to do?",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    if (selection == 1) {
                        selection = JOptionPane.showConfirmDialog(null, "are you sure?", "confirm return of a book", JOptionPane.OK_CANCEL_OPTION);
                        if(selection == JOptionPane.OK_OPTION) {
                            library.returnBook(selectedLoan.getBookId());
                            loanModel.update();
                        }
                    }
                    if(selection == 2){
                        String[] durations = { "cancel", "3 days", "1 week",  "2 weeks"};
                        selection = JOptionPane.showOptionDialog(null, "choose length", "extend loan time", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, durations, durations[0]);
                        if(selection > 0 && selection <=3){
                            if(JOptionPane.showConfirmDialog(null, "are you sure?", "confirm return of a book", JOptionPane.OK_CANCEL_OPTION)== JOptionPane.OK_OPTION){
                                switch (selection){
                                    case(1):
                                        selectedLoan.extendReturnDateBy(3);
                                        break;
                                    case(2):
                                        selectedLoan.extendReturnDateBy(7);
                                        break;
                                    case(3):
                                        selectedLoan.extendReturnDateBy(14);
                                        break;
                                }
                            }
                        }



                    }

                }
            }
        });
    }
}

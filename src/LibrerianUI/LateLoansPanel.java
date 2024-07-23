package LibrerianUI;

import Logic.Library;
import Logic.Loan;
import Logic.Member;

import javax.swing.*;
import java.awt.*;

public class LateLoansPanel extends JPanel {
    Library library;
    LateLoansTableModel lateLoanModel;

    JTable results;
    JScrollPane scroll;

    public LateLoansPanel(){
        this.library = Library.getInstance();
        this.lateLoanModel = new LateLoansTableModel();
        // add the result table to the screen
        this.results = new JTable(this.lateLoanModel);
        this.scroll = new JScrollPane(results);
        // set size - center the frame
        this.results.setPreferredScrollableViewportSize(new Dimension(1000,700));

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
                    Loan selectedLoan = lateLoanModel.getLoanAtRow(row);
                    String[] options = { "close", "extend loan time"};
                    int selection = JOptionPane.showOptionDialog(null, lateLoanModel.getLoanAtRow(row), "what would you like to do?",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    if (selection == 1) {
                        String[] durations = { "cancel", "3 days", "1 week",  "2 weeks"};
                        selection = JOptionPane.showOptionDialog(null, "choose length", "extend loan time", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, durations, durations[0]);
                        if(selection > 0 && selection <=3){
                            if(JOptionPane.showConfirmDialog(null, "are you sure?", "confirm extension of loan time", JOptionPane.OK_CANCEL_OPTION)== JOptionPane.OK_OPTION){
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

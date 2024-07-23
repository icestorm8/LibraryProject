package LibrerianUI;

import Logic.Library;
import Logic.Loan;
import Logic.Observer;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class LateLoansTableModel extends AbstractTableModel implements Observer {
    private ArrayList<Loan> loans ;
    private String[] headers ;

    public LateLoansTableModel(){
        super();
        Library.getInstance().addObserver(this);
        this.loans = Library.getInstance().getAllBorrowedPastTime();
        headers = new String[]{ "member id" ,"book id", "loan date", "return date", "is late"};
    }

    // Number of column of your table
    public int getColumnCount() {
        return headers.length ;
    }

    // Number of row of your table
    public int getRowCount() {
        return this.loans.size();
    }

    // The object to render in a cell
    public Object getValueAt(int row, int col) {
        Loan loan = loans.get(row);
        switch(col) {
            case 0: return Library.getInstance().getMemberByLoan(loan);
            case 1: return loan.getBookId();
            case 2: return loan.getLoanDate();
            case 3: return loan.getReturnDate();
            case 4: return loan.hasReturnDatePassed();

            // to complete here...
            default: return null;
        }
    }
    public void passNewResults(ArrayList<Loan> results){
        this.loans = results;
        this.update();
    }
    public Loan getLoanAtRow(int row){
        return loans.get(row);
    }
    // Optional, the name of your column
    public String getColumnName(int col) {
        return headers[col] ;
    }

    public void update(){
        this.fireTableDataChanged();
    }

}

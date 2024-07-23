package LibrerianUI;

import Logic.Library;
import Logic.Loan;
import Logic.Loan;

import javax.swing.table.AbstractTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoanTableModel extends AbstractTableModel{
        private ArrayList<Loan> loans ;
        private String[] headers ;

        public LoanTableModel(ArrayList<Loan> loans){
            super();
            this.loans = loans ;
            headers = new String[]{"book id", "loan date", "return date", "is late"};
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
                case 0: return Library.getInstance().getBookById(loan.getBookId());
                case 1: return loan.getLoanDate();
                case 2: return loan.getReturnDate();
                case 3: return loan.hasReturnDatePassed();

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

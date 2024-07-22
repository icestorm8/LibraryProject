package LibrerianUI;

import Logic.Book;
import Logic.Member;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class MemberTableModel extends AbstractTableModel{
        private ArrayList<Member> members ;
        private String[] headers ;

        public MemberTableModel(ArrayList<Member> members){
            super();
            this.members = members ;
            headers = new String[]{"id", "name", "phone number", "amount of borrowed books"};
        }

        // Number of column of your table
        public int getColumnCount() {
            return headers.length ;
        }

        // Number of row of your table
        public int getRowCount() {
            return this.members.size();
        }

        // The object to render in a cell
        public Object getValueAt(int row, int col) {
            Member member = members.get(row);
            switch(col) {
                case 0: return member.getMemberId();
                case 1: return member.getName();
                case 2: return member.getPhoneNumber();
                case 3: return member.getAllActiveLoans().size();
                // to complete here...
                default: return null;
            }
        }

        public void passNewResults(ArrayList<Member> results){
            this.members = results;
            this.update();
        }
        public Member getMemberAtRow(int row){
            return members.get(row);
        }
        // Optional, the name of your column
        public String getColumnName(int col) {
            return headers[col] ;
        }

        public void update(){
            this.fireTableDataChanged();
        }

}

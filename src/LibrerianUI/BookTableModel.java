package LibrerianUI;

import Logic.Book;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class BookTableModel extends AbstractTableModel {
        private ArrayList<Book> books ;
        private String[] headers ;

        public BookTableModel(ArrayList<Book> books){
            super();
            this.books = books ;
            headers = new String[]{"id", "title", "author", "publish year", "availability"};
        }

    // Number of column of your table
        public int getColumnCount() {
            return headers.length ;
        }

        // Number of row of your table
        public int getRowCount() {
            return this.books.size();
        }

        // The object to render in a cell
        public Object getValueAt(int row, int col) {
            Book book = books.get(row);
            switch(col) {
                case 0: return book.getBookId();
                case 1: return book.getTitle();
                case 2: return book.getAuthor();
                case 3: return book.getPublishYear();
                case 4: return book.getState();
                // to complete here...
                default: return null;
            }
        }

        public void passNewResults(ArrayList<Book> results){
            this.books = results;
            this.update();
        }
        public Book getBookAtRow(int row){
            return books.get(row);
        }
        // Optional, the name of your column
        public String getColumnName(int col) {
            return headers[col] ;
        }

        public void update(){
            this.fireTableDataChanged();
        }


}

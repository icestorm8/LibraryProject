package LibrerianUI;

import Logic.Book;
import Logic.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class BookPanel extends JPanel {
    Library library;
    JTextField searchBook;
    JButton submitSearchButton;
    JButton resetSearchButton;
    BookTableModel bookModel;
    JTable results;
    JScrollPane scroll;
    ButtonGroup choiceButtonGroup;

    public BookPanel(){
        this.library = Library.getInstance();
        this.bookModel = new BookTableModel(this.library.getAllBooks());

        // display search bar
        this.add(this.searchBar());
        // add search listeners
        this.addSearchListeners();

        // add the result table to the screen
        this.results = new JTable(bookModel);
        this.scroll = new JScrollPane(results);

        this.results.setPreferredScrollableViewportSize(new Dimension(1000,600));

        this.add(scroll);
        results.setDragEnabled(false);

        // add listener for table actions
        this.addTableListenersAndActions();




        // add book form (opens on click of the add button)
        NewBookForm form = new NewBookForm(bookModel); // create form
        JButton addButton = new JButton("add book"); // create button to open form
        this.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form.changeFrameVisibility();
            }
        }); // listen for button click



    }
    public JPanel radioOptionGroup(){

        // choose if by id or by title, author, publish year (radio button)
        JRadioButton byId = new JRadioButton("id");
        byId.setActionCommand("id");
        JRadioButton byTitle = new JRadioButton("title");
        byTitle.setActionCommand("title");
        JRadioButton byAuthor = new JRadioButton("author");
        byAuthor.setActionCommand("author");
        JRadioButton byYear = new JRadioButton("year");
        byYear.setActionCommand("year");
        byId.setSelected(true);
        this.choiceButtonGroup = new ButtonGroup();
        choiceButtonGroup.add(byId);
        choiceButtonGroup.add(byTitle);
        choiceButtonGroup.add(byAuthor);
        choiceButtonGroup.add(byYear);
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());
        radioPanel.add(byId);
        radioPanel.add(byTitle);
        radioPanel.add(byAuthor);
        radioPanel.add(byYear);
        return radioPanel;
    }
    public JPanel searchBar(){
        JPanel searchPanel = new JPanel();
        searchPanel.add(this.radioOptionGroup());
        // search bar + search and reset buttons:
        this.searchBook = new JTextField(50);
        this.submitSearchButton = new JButton("search");
        this.resetSearchButton = new JButton("reset");
        this.searchBook.setFont(new Font("Serif", Font.PLAIN, 25));
        this.submitSearchButton.setFont(new Font("Serif", Font.PLAIN, 20));
        this.resetSearchButton.setFont(new Font("Serif", Font.PLAIN, 20));
        searchPanel.add(this.searchBook);
        searchPanel.add(this.submitSearchButton);
        searchPanel.add(this.resetSearchButton);

        return searchPanel;

    }

    public void addSearchListeners(){
        // listen for click on reset search button
        resetSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBook.setText("");
                bookModel.passNewResults(library.getAllBooks());
            }
        });
        // listen for click on search button
        submitSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchBook.getText();
                if (!query.isEmpty()){
                    String selection = choiceButtonGroup.getSelection().getActionCommand();
                    query = searchBook.getText();
                    if (selection.equals("id")) {
                        try{
                            int id = Integer.parseInt(query);
                            ArrayList<Book> bookfound = new ArrayList<>();
                            Book book = library.getBookById(id);
                            if(book != null){
                                bookfound.add(book);
                            }
                            bookModel.passNewResults(bookfound);
                        }catch (Exception exception){
                            JOptionPane.showMessageDialog(Application.getInstance().getMainFrame(), exception,"invalid input", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else if(selection.equals("title")){
                        bookModel.passNewResults(library.getBookByTitle(query));
                    }
                    else if(selection.equals("year")){
                        int year;
                        try {
                            year = Year.parse(query).getValue();
                            int currentYear = Year.now().getValue();
                            if(year > currentYear){
                                throw new InvalidParameterException();
                            }
                            bookModel.passNewResults(library.getBooksByYear(year));
                        }catch (InvalidParameterException invalid){
                            JOptionPane.showMessageDialog(Application.getInstance().getMainFrame(), "invalid parameter - no books available for future years","invalid input", JOptionPane.ERROR_MESSAGE);
                        }catch(DateTimeParseException exception){
                            JOptionPane.showMessageDialog(Application.getInstance().getMainFrame(), "invalid parameter - year must be numeric","invalid input", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                    else{
                        bookModel.passNewResults(library.getBookByAuthor(query));
                    }

                }
                else{
                    query = "";
                    bookModel.passNewResults(library.getAllBooks());
                }

            }
        });

    }

    public void addTableListenersAndActions(){
        results.addMouseListener(new java.awt.event.MouseAdapter() {
            // get book in row selected
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = results.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    Book selectedBook = bookModel.getBookAtRow(row);
                    int selection;
                    String[] options;

                    if(!selectedBook.getState().equals("available")){
                        options = new String[]{"close", "remove", "add copy"};
                        selection = JOptionPane.showOptionDialog(Application.getInstance().getMainFrame(), bookModel.getBookAtRow(row), "what would you like to do?",
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    }else{
                        options = new String[]{"close", "remove", "add copy", "loan"};
                        selection = JOptionPane.showOptionDialog(Application.getInstance().getMainFrame(), bookModel.getBookAtRow(row), "what would you like to do?",
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    }

                    if (selection == 1) {
                        selection = JOptionPane.showConfirmDialog(Application.getInstance().getMainFrame(), "are you sure?", "confirm removal of book", JOptionPane.OK_CANCEL_OPTION);
                        if(selection == JOptionPane.OK_OPTION) {
                            library.removeBook(selectedBook.getBookId());
                            bookModel.update();
                        }
                    }
                    if(selection == 2){
                        selection = JOptionPane.showConfirmDialog(Application.getInstance().getMainFrame(), "are you sure?", "confirm addition of this book copy", JOptionPane.OK_CANCEL_OPTION);
                        if(selection == JOptionPane.OK_OPTION) {
                            library.addCopyofBook(selectedBook);
                            bookModel.update();
                        }
                    }
                    if(selection == 3){
                        LoanBookFrame loanBook = new LoanBookFrame(selectedBook);
                        loanBook.setVisible(true);
                    }
                }
            }
        });
    }
}

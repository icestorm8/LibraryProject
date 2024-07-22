package LibrerianUI;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import Logic.Book;
import Logic.Library;

public class MainWindow extends JFrame{
    public static void main(String[] args) {
        Library library = Library.getInstance();
        JFrame mainScreen = new JFrame("Library Management");
        mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        mainScreen.setLayout(layout);

        JLabel title = new JLabel("Library Management", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.PLAIN, 40));

        // SET TABS
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        JPanel homePanel, bookPanel, memberPanel, loanPanel;
        homePanel = new JPanel();
        bookPanel = new JPanel();
        memberPanel = new JPanel();
        loanPanel = new JPanel();
        tabbedPane.addTab("home", homePanel);
        tabbedPane.addTab("book actions", bookPanel);
        tabbedPane.addTab("member actions ", memberPanel);
        tabbedPane.addTab("loans & returns", loanPanel);


        // home panel
        JTextArea summery = new JTextArea(library.getSummery());
        summery.setBounds(0,0, 500,500);
        summery.setFont(new Font("Serif", Font.PLAIN, 25));
        summery.setEditable(false);
        homePanel.add(summery);


        // book panel
        // add a book
//        JButton addBookButton = new JButton("add new book");
//        bookPanel.add(addBookButton);
        // view all books (table)
        // create a copy of a book
        // delete a book
        // search for book
        // search bar:
        JTextField searchBook = new JTextField(50);
        JButton submitSearchButton = new JButton("search");
        searchBook.setFont(new Font("Serif", Font.PLAIN, 25));
        submitSearchButton.setFont(new Font("Serif", Font.PLAIN, 20));
        bookPanel.add(searchBook);
        bookPanel.add(submitSearchButton);

        // result list:
        BookTableModel bookModel = new BookTableModel(library.getAllBooks());

        JTable results = new JTable(bookModel);
//
        // adding it to JScrollPane
        JScrollPane scroll = new JScrollPane(results);
        bookPanel.add(scroll);
        results.setDragEnabled(false);

        results.addMouseListener(new java.awt.event.MouseAdapter() {
            // get book in row selected
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = results.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    Book selectedBook = bookModel.getBookAtRow(row);
                    String[] options = { "close", "remove", "add copy"};
                    int selection = JOptionPane.showOptionDialog(mainScreen, bookModel.getBookAtRow(row), "what would you like to do?",
                            0, 2, null, options, options[0]);
                    if (selection == 1) {
                        selection = JOptionPane.showConfirmDialog(mainScreen, "are you sure?", "confirm removal of book", JOptionPane.OK_CANCEL_OPTION);
                        if(selection == JOptionPane.OK_OPTION) {
                            library.removeBook(selectedBook.getBookId());
                            bookModel.update();
                        }
                    }
                    if(selection == 2){
                        selection = JOptionPane.showConfirmDialog(mainScreen, "are you sure?", "confirm addition of this book copy", JOptionPane.OK_CANCEL_OPTION);
                        if(selection == JOptionPane.OK_OPTION) {
                            library.addCopyofBook(selectedBook);
                            bookModel.update();
                        }
                    }
                }
            }
        });

        // listen for click on search button
        submitSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchBook.getText();
                if (!query.isEmpty()){
                    JOptionPane.showMessageDialog(mainScreen, searchBook.getText());
                    query = searchBook.getText();
                    bookModel.passNewResults(library.getBookByTitle(query));

                }
                else{
                    query = "";
                    bookModel.passNewResults(library.getAllBooks());
                }

            }
        });


        // choose if by id or by title, author, publish year (radio button)
        // add book form
        NewBookForm form = new NewBookForm(bookModel);

        // button that opens the form
        JButton addButton = new JButton("add book");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                form.changeFrameVisibility();
            }
        });
        bookPanel.add(addButton);




        // set size to fit screen size
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());

        mainScreen.add(title, BorderLayout.NORTH);
        tabbedPane.setPreferredSize(new Dimension(xSize-50,0));
        mainScreen.add(tabbedPane, BorderLayout.LINE_START);


        mainScreen.setSize(xSize,ySize);
        mainScreen.setVisible(true);



    }


}

package LibrerianUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        // listen for click on search button
        submitSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchBook.getText();
                if (!query.isEmpty()){
                    JOptionPane.showMessageDialog(mainScreen, searchBook.getText());
                }
            }
        });

        // result list:
        JTable results = new JTable();
        bookPanel.add(results);
        // choose if by id or by title, author, publish year (radio button)
        // add book frame
        JFrame addBookForm = new JFrame();


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

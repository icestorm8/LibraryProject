package LibrerianUI;

import javax.swing.*;
import java.awt.*;

import Logic.Book;
import Logic.Library;
import Logic.Observer;

public class MainWindow extends JFrame {

    public MainWindow() {
        Library library = Library.getInstance();
        setTitle("Library Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        // SET TABS
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        JPanel homePanel, bookPanel, memberPanel, loanPanel;
        homePanel = new HomePanel();
        bookPanel = new BookPanel();
        memberPanel = new MemberPanel();
        loanPanel = new LateLoansPanel();
        tabbedPane.addTab("home", homePanel);
        tabbedPane.addTab("book actions", bookPanel);
        tabbedPane.addTab("member actions ", memberPanel);
        tabbedPane.addTab("late loans", loanPanel);

        // set size to fit screen size
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());

        JLabel title = new JLabel("Library Management", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.PLAIN, 40));
        tabbedPane.setPreferredSize(new Dimension(xSize - 50, 0));
        this.add(tabbedPane, BorderLayout.LINE_START);
        this.add(title, BorderLayout.NORTH);
        this.setSize(xSize, ySize);
    }


}
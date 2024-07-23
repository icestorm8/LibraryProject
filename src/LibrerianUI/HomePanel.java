package LibrerianUI;

import Logic.Library;
import Logic.Observer;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel implements Observer {
    private JTextArea summery;
    public HomePanel(){
        Library.getInstance().addObserver(this);
        this.summery = new JTextArea(Library.getInstance().getSummery());
        this.summery.setBounds(0,0, 500,500);
        this.summery.setFont(new Font("Serif", Font.PLAIN, 25));
        this.summery.setEditable(false);
        this.add(summery);
    }

    /**
     *
     */
    @Override
    public void update() {
        this.summery.setText(Library.getInstance().getSummery());
    }
}

package LibrerianUI;

import Logic.Library;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel(){
        JTextArea summery = new JTextArea(Library.getInstance().getSummery());
        summery.setBounds(0,0, 500,500);
        summery.setFont(new Font("Serif", Font.PLAIN, 25));
        summery.setEditable(false);
        this.add(summery);

    }
}

package LibrerianUI;

import Logic.Book;
import Logic.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import java.util.ArrayList;

public class NewBookForm extends JFrame{
    private static final JLabel error_empty_fields = new JLabel("must fill all fields to add a new book!");
    private static final JLabel error_invalid_year = new JLabel("must enter valid year!");
    private ArrayList<JTextField> inputs;
    BookTableModel bookModel;
    public NewBookForm(BookTableModel bookModel){
        this.bookModel = bookModel;
        this.inputs = new ArrayList<>();
        this.setTitle("new book form");
        this.setVisible(false);
        this.setLocation(300,30);
        GridLayout formLayout = new GridLayout(10,3);
        this.setLayout(formLayout);
        NewBookForm.error_empty_fields.setVisible(false);
        this.add(NewBookForm.error_empty_fields);
        NewBookForm.error_invalid_year.setVisible(false);
        this.add(NewBookForm.error_invalid_year);

        JLabel title = new JLabel("New Book Form", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.PLAIN, 25));
        this.add(title);

        this.displayInputs();
        JButton submit = new JButton("sumbit");
        this.add(submit);

        // set size - center the frame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int xSize = ((int) dim.getWidth());
        int ySize = ((int) dim.getHeight());
        this.setSize(xSize/2, ySize/2);
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayError(3);
                String[] data = {"", "", ""};
                int index = 0;
                for(JTextField input: inputs){
                    if (input.getText().isEmpty()){
                        displayError(1);
                        return;
                    }
                    data[index] = input.getText();
                    index++;
                }

                int year;
                try {
                    year = Year.parse(data[2]).getValue();
                    int currentYear = Year.now().getValue();
                    if(year > currentYear){
                        throw new Exception();
                    }
                }catch (Exception error){
                    displayError(2);
                    return;
                }
                int id = Library.getInstance().addBook(data[0], data[1], year);
                JOptionPane.showMessageDialog(null, Library.getInstance().getBookById(id).toString(), "book added", JOptionPane.INFORMATION_MESSAGE);
                resetInputFields();
//                bookModel.update();
            }
        });

    }
    private void displayError(int errorCode){
        switch (errorCode){
            case 1:
                NewBookForm.error_empty_fields.setVisible(true);
                break;
            case 2:
                NewBookForm.error_invalid_year.setVisible(true);
                break;
            default:
                NewBookForm.error_invalid_year.setVisible(false);
                NewBookForm.error_empty_fields.setVisible(false);
        }

    }

    // new frame
    public void changeFrameVisibility(){
        this.setVisible(true);
    }

    private void resetInputFields(){
        for(JTextField input : this.inputs){
            input.setText("");
        }
    }
   private void displayInputs(){

       // define inputs and button
       final String[] labels = {"title: ", "author: ", "publish year: "};
       for (String label : labels) {
           JLabel l = new JLabel(label, SwingConstants.LEFT);
           this.add(l);
           JTextField textField = new JTextField(10);
           this.inputs.add(textField);
           l.setLabelFor(textField);
           this.add(textField);
       }
   }




}

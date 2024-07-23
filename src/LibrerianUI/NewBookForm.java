package LibrerianUI;

import Logic.Book;
import Logic.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import java.util.ArrayList;

public class NewBookForm {
    private JFrame addBookForm;
    private static final JLabel error_empty_fields = new JLabel("must fill all fields to add a new book!");
    private static final JLabel error_invalid_year = new JLabel("must enter valid year!");
    private ArrayList<JTextField> inputs;
    BookTableModel bookModel;
    public NewBookForm(BookTableModel bookModel){
        this.bookModel = bookModel;
        this.inputs = new ArrayList<>();
        this.addBookForm = new JFrame();
        this.addBookForm.setTitle("new book form");
        this.addBookForm.setSize(500, 500);
        this.addBookForm.setVisible(false);
        this.addBookForm.setLocation(300,30);
        GridLayout formLayout = new GridLayout(10,3);
        this.addBookForm.setLayout(formLayout);
        NewBookForm.error_empty_fields.setVisible(false);
        this.addBookForm.add(NewBookForm.error_empty_fields);
        NewBookForm.error_invalid_year.setVisible(false);
        this.addBookForm.add(NewBookForm.error_invalid_year);
        this.displayInputs();
        JButton submit = new JButton("sumbit");
        this.addBookForm.add(submit);
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
                JOptionPane.showMessageDialog(addBookForm, Library.getInstance().getBookById(id).toString(), "book added", JOptionPane.INFORMATION_MESSAGE);
                resetInputFields();
                bookModel.update();
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
        this.addBookForm.setVisible(true);
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
           JLabel l = new JLabel(label, JLabel.TRAILING);
           this.addBookForm.add(l);
           JTextField textField = new JTextField(10);
           this.inputs.add(textField);
           l.setLabelFor(textField);
           this.addBookForm.add(textField);
       }
   }




}

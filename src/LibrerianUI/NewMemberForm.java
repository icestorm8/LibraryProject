package LibrerianUI;

import Logic.Library;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


import Logic.Book;

public class NewMemberForm extends JFrame {
        private static final JLabel error_empty_fields = new JLabel("must fill all fields to add a new member!");
        private static final JLabel error_member_exists = new JLabel("member already exists!");
        private static final JLabel error_illegal_phone_number = new JLabel("bad phone number");
        private ArrayList<JTextField> inputs;
        MemberTableModel memberModel;
        public NewMemberForm(MemberTableModel memberModel){
            this.memberModel = memberModel;
            this.inputs = new ArrayList<>();
            this.setTitle("new member form");
            this.setSize(500, 500);
            this.setVisible(false);
            this.setLocation(300,30);
            GridLayout formLayout = new GridLayout(10,3);
            this.setLayout(formLayout);
            LibrerianUI.NewMemberForm.error_empty_fields.setVisible(false);
            this.add(LibrerianUI.NewMemberForm.error_empty_fields);
            LibrerianUI.NewMemberForm.error_member_exists.setVisible(false);
            this.add(LibrerianUI.NewMemberForm.error_member_exists);
            NewMemberForm.error_illegal_phone_number.setVisible(false);
            this.add(LibrerianUI.NewMemberForm.error_illegal_phone_number);

            this.displayInputs();
            JButton submit = new JButton("submit");
            this.add(submit);
            submit.addActionListener(e -> {
                displayError(4);
                String[] data = {"", ""};
                int index = 0;
                for(JTextField input: inputs){
                    if (input.getText().isEmpty()){
                        displayError(1);
                        return;
                    }
                    data[index] = input.getText();
                    index++;
                }
                String allCountryRegex = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$";
                if(!data[1].matches(allCountryRegex)){
                    displayError(3);
                    return;
                }

                int id = Library.getInstance().addMember(data[0], data[1]);
                JOptionPane.showMessageDialog(null, Library.getInstance().getMemberById(id).toString(), "member added", JOptionPane.INFORMATION_MESSAGE);
                resetInputFields();
                this.memberModel.update();
            });

        }
        private void displayError(int errorCode){
            switch (errorCode){
                case 1:
                    LibrerianUI.NewMemberForm.error_empty_fields.setVisible(true);
                    break;
                case 2:
                    LibrerianUI.NewMemberForm.error_member_exists.setVisible(true);
                    break;
                case 3:
                    NewMemberForm.error_illegal_phone_number.setVisible(true);
                    break;
                default:
                    LibrerianUI.NewMemberForm.error_member_exists.setVisible(false);
                    LibrerianUI.NewMemberForm.error_empty_fields.setVisible(false);
                    NewMemberForm.error_illegal_phone_number.setVisible(false);
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
            final String[] labels = {"name", "phone number"};
            for (String label : labels) {
                JLabel l = new JLabel(label, JLabel.TRAILING);
                this.add(l);
                JTextField textField = new JTextField(10);
                this.inputs.add(textField);
                l.setLabelFor(textField);
                this.add(textField);
            }
        }






}

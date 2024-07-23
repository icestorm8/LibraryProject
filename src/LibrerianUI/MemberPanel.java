package LibrerianUI;

import Logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Logic.Book;
import Logic.Library;


public class MemberPanel extends JPanel {
        JPanel currentPanel;
        Library library;
        JTextField searchMember;
        JButton submitSearchButton;
        JButton resetSearchButton;
        MemberTableModel memberModel;
        JTable results;
        JScrollPane scroll;
        ButtonGroup choiceButtonGroup;

        public MemberPanel(){
            this.currentPanel = this;
            this.library = Library.getInstance();
            this.memberModel = new MemberTableModel(this.library.getAllMembers());

            // display search bar
            this.add(this.searchBar());
            // add search listeners
            this.addSearchListeners();

            // add the result table to the screen
            this.results = new JTable(memberModel);
            this.scroll = new JScrollPane(results);

            this.results.setPreferredScrollableViewportSize(new Dimension(1000,600));

            this.add(scroll);
            results.setDragEnabled(false);

            // add listener for table actions
            this.addTableListenersAndActions();




            // add member form (opens on click of the add button)
            NewMemberForm form = new NewMemberForm(this.memberModel); // create form
            JButton addButton = new JButton("add member"); // create button to open form
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
            JRadioButton byName = new JRadioButton("name");
            byName.setActionCommand("name");
            JRadioButton byPhoneNumber = new JRadioButton("phone");
            byPhoneNumber.setActionCommand("phone");
            byId.setSelected(true);
            this.choiceButtonGroup = new ButtonGroup();
            choiceButtonGroup.add(byId);
            choiceButtonGroup.add(byName);
            choiceButtonGroup.add(byPhoneNumber);
            JPanel radioPanel = new JPanel();
            radioPanel.setLayout(new FlowLayout());
            radioPanel.add(byId);
            radioPanel.add(byName);
            radioPanel.add(byPhoneNumber);
            return radioPanel;
        }
        public JPanel searchBar(){
            JPanel searchPanel = new JPanel();
            searchPanel.add(this.radioOptionGroup());
            // search bar + search and reset buttons:
            this.searchMember = new JTextField(50);
            this.submitSearchButton = new JButton("search");
            this.resetSearchButton = new JButton("reset");
            this.searchMember.setFont(new Font("Serif", Font.PLAIN, 25));
            this.submitSearchButton.setFont(new Font("Serif", Font.PLAIN, 20));
            this.resetSearchButton.setFont(new Font("Serif", Font.PLAIN, 20));
            searchPanel.add(this.searchMember);
            searchPanel.add(this.submitSearchButton);
            searchPanel.add(this.resetSearchButton);

            return searchPanel;

        }

        public void addSearchListeners(){
            // listen for click on reset search button
            resetSearchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    searchMember.setText("");
                    memberModel.passNewResults(library.getAllMembers());
                }
            });
            // listen for click on search button
            submitSearchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String query = searchMember.getText();
                    if (!query.isEmpty()){
                        String selection = choiceButtonGroup.getSelection().getActionCommand();
                        query = searchMember.getText();
                        if (selection.equals("id")) {
                            try{
                                int id = Integer.parseInt(query);
                                ArrayList<Member> memberFound = new ArrayList<>();
                                Member member = library.getMemberById(id);
                                if(member != null){
                                    memberFound.add(member);
                                }
                                memberModel.passNewResults(memberFound);
                            }catch (Exception exception){
                                JOptionPane.showMessageDialog(currentPanel, exception,"invalid input", JOptionPane.ERROR_MESSAGE);
                                searchMember.setText("");
                            }
                        }
                        else if(selection.equals("name")){
                            memberModel.passNewResults(library.getMembersByName(query));
                        }
                        else if(selection.equals("phone")){
                            memberModel.passNewResults(library.getMembersByPhone(query));
                        }
                    }
                    else{
                        query = "";
                        memberModel.passNewResults(library.getAllMembers());
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
                        Member selectedMember = memberModel.getMemberAtRow(row);
                        String[] options = { "close", "remove","view extended details"};
                        int selection = JOptionPane.showOptionDialog(currentPanel, selectedMember, "what would you like to do?",
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                        if (selection == 1) {
                            selection = JOptionPane.showConfirmDialog(currentPanel, "are you sure?", "confirm removal of book", JOptionPane.OK_CANCEL_OPTION);
                            if(selection == JOptionPane.OK_OPTION) {
                                library.removeMember(selectedMember.getMemberId());
                                memberModel.update();
                            }
                        }
                        if(selection == 2) {
                            MemberDataFrame memberDate = new MemberDataFrame(selectedMember);
                            memberDate.setVisible(true);
                        }
                    }
                }
            });
        }
    }



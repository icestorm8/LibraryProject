package LibrerianUI;

import Logic.Member;

import javax.swing.*;
import java.awt.*;

public class MemberDataFrame extends JFrame {
    Member member;
    public MemberDataFrame(Member member){
        this.member = member;
        this.setTitle(String.format("member [%d] -  %s", this.member.getMemberId(),this.member.getName()));
        this.setLayout(new GridLayout(4, 8));

        JLabel title = new JLabel("Member Details",  SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.PLAIN, 25));
        this.add(title, BorderLayout.NORTH);
        JPanel info = this.getInfoPanel();
        JPanel loan = this.getLoanPanel();

        this.add(info, BorderLayout.CENTER);
        this.add(loan, BorderLayout.SOUTH);
        // set size - center the frame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int xSize = ((int) dim.getWidth());
        int ySize = ((int) dim.getHeight());
        this.setSize(xSize/2, ySize/2);
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

    }
    private JPanel getInfoPanel(){
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JLabel memberName = new JLabel(String.format("name: %s", this.member.getName().toLowerCase()), SwingConstants.CENTER);
        memberName.setFont(new Font("Serif", Font.PLAIN, 15));
        JLabel memberPhoneNumber = new JLabel(String.format("phone number: %s", this.member.getPhoneNumber()), SwingConstants.CENTER);
        memberPhoneNumber.setFont(new Font("Serif", Font.PLAIN, 15));

        infoPanel.add(memberName);
        infoPanel.add(memberPhoneNumber);
        return infoPanel;
    }
    private JPanel getLoanPanel(){
        JPanel loanPanel = new JPanel();
        JLabel loansTitle = new JLabel("Active Loans: ");
        loanPanel.add(loansTitle);
        JLabel noLoansFound = new JLabel("member has no active loans");
        MemberLoanPanel memberLoans = new MemberLoanPanel(this.member);
        if (this.member.getAllActiveLoans().isEmpty()) loanPanel.add(noLoansFound);
        else loanPanel.add(memberLoans);
        return loanPanel;
    }

}

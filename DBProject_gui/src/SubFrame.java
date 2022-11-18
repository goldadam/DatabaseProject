import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubFrame extends JFrame{

    SubFrame() {
        JPanel InsertPanel = new JPanel();
        InsertPanel.setLayout(new GridLayout(10,2));
        String[] Sex = {"M", "F"}; // 성별 선택

        JTextField InputFname = new JTextField(20);
        JTextField InputMiddleInit = new JTextField(20);
        JTextField InputLname = new JTextField(20);
        JTextField InputSsn = new JTextField(20);
        JTextField InputBdate = new JTextField(20);
        JTextField InputAddress = new JTextField(20);
        JComboBox<String> SexCategory = new JComboBox<>(Sex);
        JTextField InputSalary = new JTextField(20);
        JTextField InputSuper_ssn = new JTextField(20);
        JTextField InputDno = new JTextField(20);


        InsertPanel.add(new JLabel("First Name: "));
        InsertPanel.add(InputFname);
        InsertPanel.add(new JLabel("Middle Initial: "));
        InsertPanel.add(InputMiddleInit);
        InsertPanel.add(new JLabel("Last name: "));
        InsertPanel.add(InputLname);
        InsertPanel.add(new JLabel("Ssn: "));
        InsertPanel.add(InputSsn);
        InsertPanel.add(new JLabel("Birthdate: "));
        InsertPanel.add(InputBdate);
        InsertPanel.add(new JLabel("Address: "));
        InsertPanel.add(InputAddress);
        InsertPanel.add(new JLabel("Sex: "));
        InsertPanel.add(SexCategory);
        InsertPanel.add(new JLabel("Salary: "));
        InsertPanel.add(InputSalary);
        InsertPanel.add(new JLabel("Super_Ssn: "));
        InsertPanel.add(InputSuper_ssn);
        InsertPanel.add(new JLabel("Dno: "));
        InsertPanel.add(InputDno);

        JPanel InsertButtonPanel = new JPanel();
        JButton InsertButton = new JButton("정보 추가하기");
        InsertButtonPanel.add(InsertButton);
//        InsertButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                dispose();
//            }
//        });

        InsertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JdbcConnection conn = new JdbcConnection();
                conn.Connect();
                String query = "INSERT INTO EMPLOYEE(Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno) "
                        + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try {
                    PreparedStatement pstm = conn.getConnection().prepareStatement(query);
                    pstm.setString(1, String.valueOf(InputFname));
                    pstm.setString(2, String.valueOf(InputMiddleInit));
                    pstm.setString(3, String.valueOf(InputLname));
                    pstm.setString(4, String.valueOf(InputSsn));
                    pstm.setString(5, String.valueOf(InputBdate));
                    pstm.setString(6, String.valueOf(InputAddress));
                    pstm.setString(7, String.valueOf(SexCategory));
                    pstm.setString(8, String.valueOf(InputSalary));
                    pstm.setString(9, String.valueOf(InputSuper_ssn));
                    pstm.setString(10, String.valueOf(InputDno));

                    pstm.executeUpdate();

                }catch (SQLException x){
                    x.printStackTrace();
                    System.out.println("에러가 발생함");
                }
            }
        });
        add(InsertPanel, BorderLayout.CENTER);
        add(InsertButtonPanel, BorderLayout.SOUTH);

        setTitle("New Employee Insert System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(200, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        new SubFrame();
    }
    // 기본 설정

}

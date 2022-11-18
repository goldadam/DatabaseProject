package com.employee.GUI;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;
import com.employee.JDBCConnection.JdbcConnection;

public class MainFrame extends JFrame implements ActionListener {
    private JComboBox<String> DeptComboBox = new JComboBox<>();
    private JComboBox<String> SexComboBox = new JComboBox<>();
    private JComboBox<String> BdateComboBox = new JComboBox<>();
    private JTextField Salary = new JTextField();
    private JTextField Underling = new JTextField();

    private JPanel comboBoxPanel = new JPanel();
    private JPanel SearchResultPanel = new JPanel();
    private JLabel ShowSelectedEmployee = new JLabel(); // 선택한 직원 보여주기
    private JPanel TotalPanel = new JPanel();
    private JPanel UpdatePanel = new JPanel();

    JPanel DeletePanel = new JPanel();
    JPanel InsertPanel = new JPanel();
    JPanel CheckBoxPanel = new JPanel(); // 생성
    JPanel panel;

    private JCheckBox nameCheckBox;
    private JCheckBox ssnCheckBox;
    private JCheckBox bdateCheckBox;
    private JCheckBox addressCheckBox;
    private JCheckBox sexCheckBox;
    private JCheckBox salaryCheckBox;
    private JCheckBox supervisorCheckBox;
    private JCheckBox departmentCheckBox;
    private JButton SearchButton;

    private final String[] SearchScope = {"전체", "부서", "성별", "연봉(초과)", "생일(월)", "부하직원"}; // 검색 범위
    private final String[] Bdate = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private final String[] Department = {"Research", "Administration", "Headquarters"};
    private final String[] Sexs = {"M", "F"};
    private final String[] UpdateScope = {"Address", "Sex", "Salary"}; // 수정 가능한 항목


    // list처럼 콤보박스에 String 추가
    private final JComboBox<String> Category = new JComboBox<>(SearchScope);
    JComboBox<String> UpdateCategory = new JComboBox<>(UpdateScope);

    public Connection conn;
    private Vector<String> Head = new Vector<>();
    Container me=this;
    private int count;
    private JTable table;
    private DefaultTableModel model;
    private static final int BOOLEAN_COLUMN = 0;
    private int NAME_COLUMN = 0;
    private int SALARY_COLUMN = 0;
    private String dShow;
    public Statement s;
    public ResultSet r;
    JScrollPane ScPane;
    private JLabel ShowSelectedEmp = new JLabel();
    JPanel ShowSelectedPanel = new JPanel();

    // 크게 7개의 패널로 구분 (기능별)
    public MainFrame() {
        // -----첫번째 패널 : 콤보박스들만 있는 패널-----
        Category.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeptComboBox.setVisible(false);
                SexComboBox.setVisible(false);
                Salary.setVisible(false);
                BdateComboBox.setVisible(false);
                Underling.setVisible(false);

                if ("부서".equals(Category.getSelectedItem().toString())) {
                    DeptComboBox.setVisible(true);
                } else if ("성별".equals(Category.getSelectedItem().toString())) {
                    SexComboBox.setVisible(true);
                } else if ("연봉(초과)".equals(Category.getSelectedItem().toString())) {
                    Salary.setVisible(true);
                } else if (("생일(월)".equals(Category.getSelectedItem().toString()))) {
                    BdateComboBox.setVisible(true);
                } else if (("부하직원".equals(Category.getSelectedItem().toString()))) {
                    Underling.setVisible(true);
                }
            }
        });
        // 생성
        comboBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 왼쪽에 배치
        comboBoxPanel.add(new JLabel("검색 범위 "));
        comboBoxPanel.add(Category);

        DeptComboBox = new JComboBox<>(Department);
        comboBoxPanel.add(DeptComboBox);

        SexComboBox = new JComboBox<>(Sexs);
        comboBoxPanel.add(SexComboBox);

        Salary = new JTextField(10);
        comboBoxPanel.add(Salary);

        BdateComboBox = new JComboBox<>(Bdate);
        comboBoxPanel.add(BdateComboBox);

        Underling = new JTextField(10);
        comboBoxPanel.add(Underling);

        DeptComboBox.setVisible(false);
        SexComboBox.setVisible(false);
        Salary.setVisible(false);
        BdateComboBox.setVisible(false);
        Underling.setVisible(false);

        // ----------

        // ----- 두번째 패널 : 체크박스들만 있는 패널 -----

        CheckBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 왼쪽에 배치
        CheckBoxPanel.add(new JLabel("검색 항목 "));

        // 일단 전부 check된 채로 표시
        nameCheckBox = new JCheckBox("Name", true);
        ssnCheckBox = new JCheckBox("Ssn", true);
        bdateCheckBox = new JCheckBox("Bdate", true);
        addressCheckBox = new JCheckBox("Address", true);
        sexCheckBox = new JCheckBox("Sex", true);
        salaryCheckBox = new JCheckBox("Salary", true);
        supervisorCheckBox = new JCheckBox("Supervisor", true);
        departmentCheckBox = new JCheckBox("Department", true);
        SearchButton = new JButton("검색");
        CheckBoxPanel.add(nameCheckBox);
        CheckBoxPanel.add(ssnCheckBox);
        CheckBoxPanel.add(bdateCheckBox);
        CheckBoxPanel.add(addressCheckBox);
        CheckBoxPanel.add(sexCheckBox);
        CheckBoxPanel.add(salaryCheckBox);
        CheckBoxPanel.add(supervisorCheckBox);
        CheckBoxPanel.add(departmentCheckBox);
        CheckBoxPanel.add(SearchButton);

        SearchButton.addActionListener(this);
        // ----------

        // ----- 세번째 패널 : 선택한 직원을 보여주는 패널-----

        SearchResultPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel SelectedEmployee = new JLabel("선택한 직원: ");
        SelectedEmployee.setFont(new Font("Dialog", Font.BOLD, 18));

        ShowSelectedEmployee.setFont(new Font("Dialog", Font.BOLD, 18));
        SearchResultPanel.add(SelectedEmployee);
        SearchResultPanel.add(ShowSelectedEmployee);
        // ----------

        // ----- 네번째 패널 : 인원수 보여주는 패널 -----

        TotalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel PeopleCount = new JLabel("인원수 : ");
        JLabel ShowPeopleCount = new JLabel();
        TotalPanel.add(PeopleCount);
        TotalPanel.add(ShowPeopleCount);
        // ----------

        // ----- 다섯번째 패널 : 내용 수정하는 패널 -----

        UpdatePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel UpdateLabel = new JLabel("수정");
        JTextField Input = new JTextField(20); // 입력
        JButton UpdateButton = new JButton("UPDATE");

        // list처럼 콤보박스에 String 추가

        UpdatePanel.add(UpdateLabel);
        UpdatePanel.add(UpdateCategory);
        UpdatePanel.add(Input);
        UpdatePanel.add(UpdateButton);
        // -----------

        // ----- 여섯번째 패널 : 데이터 삭제하는 패널-----

        DeletePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton DeleteButton = new JButton("선택한 데이터 삭제");
        DeletePanel.add(DeleteButton);
        // ----------

        // ----- 일곱번째 패널 : 새로운 직원 정보 추가하는 패널 -----

        InsertPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton InsertButton = new JButton("새로운 직원 정보 추가");
        InsertPanel.add(InsertButton);
        InsertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SubFrame();
            }
        });
        // ----------

        // ----------

        ShowSelectedPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ShowPeopleCount.setFont(new Font("Dialog", Font.BOLD, 16));
        ShowSelectedEmp.setFont(new Font("Dialog", Font.BOLD, 16));
        dShow = "";
        ShowSelectedPanel.add(ShowPeopleCount);
        ShowSelectedPanel.add(ShowSelectedEmp);

        //JPanel TotalPanel = new JPanel();
        //TotalPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //TotalPanel.add(totalEmp);
        //TotalPanel.add(totalCount);
        // ----------

        // ----- 패널 레아아웃 구성 -----
        JPanel Top = new JPanel();
        Top.setLayout(new BoxLayout(Top, BoxLayout.Y_AXIS));
        Top.add(comboBoxPanel);
        Top.add(CheckBoxPanel);

        JPanel Half = new JPanel();
        Half.setLayout(new BoxLayout(Half, BoxLayout.X_AXIS));
        Half.add(SearchResultPanel);

        JPanel Bottom = new JPanel();
        Bottom.setLayout(new BoxLayout(Bottom, BoxLayout.X_AXIS));
        Bottom.add(TotalPanel);
        Bottom.add(UpdatePanel);
        Bottom.add(DeletePanel);
        Bottom.add(InsertPanel);

        JPanel ShowVertical = new JPanel();
        ShowVertical.setLayout(new BoxLayout(ShowVertical, BoxLayout.Y_AXIS));
        ShowVertical.add(Half);
        ShowVertical.add(Bottom);


        add(Top, BorderLayout.NORTH);
        add(ShowVertical, BorderLayout.SOUTH);
        // ----------

        // 기본 설정
        setTitle("Information Retrival System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JdbcConnection conn= new JdbcConnection();
        conn.Connect();

        if (count == 1) {
            me.remove(panel);
            revalidate();
        }
        if (e.getSource() == SearchButton) {
            if (nameCheckBox.isSelected() || ssnCheckBox.isSelected() || bdateCheckBox.isSelected() || addressCheckBox.isSelected() || sexCheckBox.isSelected()
                    || salaryCheckBox.isSelected() || supervisorCheckBox.isSelected() || departmentCheckBox.isSelected()) {
                Head.clear();
                Head.add("선택");

                String stmt = "select";
                if (nameCheckBox.isSelected()) {
                    stmt += " concat(e.Fname,' ', e.minit,' ', e.lname,' ') as Name";
                    Head.add("NAME");
                }
                if (ssnCheckBox.isSelected()) {
                    if (!nameCheckBox.isSelected())
                        stmt += " e.ssn";
                    else
                        stmt += ", e.ssn";
                    Head.add("SSN");
                }
                if (bdateCheckBox.isSelected()) {
                    if (!nameCheckBox.isSelected() && !ssnCheckBox.isSelected())
                        stmt += " e.bdate";
                    else
                        stmt += ", e.bdate";
                    Head.add("BDATE");
                }
                if (addressCheckBox.isSelected()) {
                    if (!nameCheckBox.isSelected() && !ssnCheckBox.isSelected() && !bdateCheckBox.isSelected())
                        stmt += " e.address";
                    else
                        stmt += ", e.address";
                    Head.add("ADDRESS");
                }
                if (sexCheckBox.isSelected()) {
                    if (!nameCheckBox.isSelected() && !ssnCheckBox.isSelected() && !bdateCheckBox.isSelected() && !addressCheckBox.isSelected())
                        stmt += " e.sex";
                    else
                        stmt += ", e.sex";
                    Head.add("SEX");
                }
                if (salaryCheckBox.isSelected()) {
                    if (!nameCheckBox.isSelected() && !ssnCheckBox.isSelected() && !bdateCheckBox.isSelected() && !addressCheckBox.isSelected()
                            && !sexCheckBox.isSelected())
                        stmt += " e.salary";
                    else
                        stmt += ", e.salary";
                    Head.add("SALARY");
                }
                if (supervisorCheckBox.isSelected()) {
                    if (!nameCheckBox.isSelected() && !ssnCheckBox.isSelected() && !bdateCheckBox.isSelected() && !addressCheckBox.isSelected() && !sexCheckBox.isSelected()
                            && !salaryCheckBox.isSelected())
                        stmt += " concat(s.fname, ' ', s.minit, ' ',s.lname,' ') as Supervisor ";
                    else
                        stmt += ", concat(s.fname, ' ', s.minit, ' ',s.lname,' ') as Supervisor ";
                    Head.add("SUPERVISOR");
                }
                if (departmentCheckBox.isSelected()) {
                    if (!nameCheckBox.isSelected() && !ssnCheckBox.isSelected() && !bdateCheckBox.isSelected() && !addressCheckBox.isSelected() && !sexCheckBox.isSelected()
                            && !salaryCheckBox.isSelected() && !departmentCheckBox.isSelected())
                        stmt += " dname";
                    else
                        stmt += ", dname";
                    Head.add("DEPARTMENT");
                }
                stmt += " from employee e left outer join employee s on e.super_ssn=s.ssn, department where e.dno = dnumber";

                //
                if (Category.getSelectedItem().toString() == "부서") {
                    if (DeptComboBox.getSelectedItem().toString() == "Research")
                        stmt += " and dname = \"Research\";";
                    else if (DeptComboBox.getSelectedItem().toString() == "Administration")
                        stmt += " and dname = \"Administration\";";
                    else if (DeptComboBox.getSelectedItem().toString() == "Headquarters")
                        stmt += " and dname = \"Headquarters\";";
                }

                model = new DefaultTableModel(Head, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        if (column > 0) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                };
                for (int i = 0; i < Head.size(); i++) {
                    if (Head.get(i) == "NAME") {
                        NAME_COLUMN = i;
                    } else if (Head.get(i) == "SALARY") {
                        SALARY_COLUMN = i;
                    }
                }
                table = new JTable(model) {
                    @Override
                    public Class getColumnClass(int column) {
                        if (column == 0) {
                            return Boolean.class;
                        } else
                            return String.class;
                    }
                };

                ShowSelectedEmp.setText(" ");

                try {
                    count = 1;
                    s = conn.getConnection().createStatement();
                    r = s.executeQuery(stmt);
                    ResultSetMetaData rsmd = r.getMetaData();
                    int columnCnt = rsmd.getColumnCount();
                    int rowCnt = table.getRowCount();

                    while (r.next()) {
                        Vector<Object> tuple = new Vector<Object>();
                        tuple.add(false);
                        for (int i = 1; i < columnCnt + 1; i++) {
                            tuple.add(r.getString(rsmd.getColumnName(i)));
                        }
                        model.addRow(tuple);
                        rowCnt++;
                    }
                    ShowSelectedEmployee.setText(String.valueOf(rowCnt));

                } catch (SQLException ee) {
                    System.out.println("actionPerformed err : " + ee);
                    ee.printStackTrace();

                }
                panel = new JPanel();
                ScPane = new JScrollPane(table);
                //table.getModel().addTableModelListener(new CheckBoxModelListener());
                ScPane.setPreferredSize(new Dimension(1100, 400));
                panel.add(ScPane);
                add(panel, BorderLayout.CENTER);
                revalidate();

            } else {
                JOptionPane.showMessageDialog(null, "검색 항목을 한개 이상 선택하세요.");
            }

        }
    }
}
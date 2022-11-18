package com.employee.queryString;

import com.employee.JDBCConnection.JdbcConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class printAllAttribute {
    public List<String> showAllAttr(Model model, boolean[] isSelected){ //아직 GUI단에서 어떻게 받을 지 안정함./.
        try{
            JdbcConnection dbCon = new JdbcConnection();
            Connection conn = DriverManager.getConnection("url" + "user" + "parameter");

            dbCon.Connect();
            ArrayList<String> ssnList = new ArrayList<>();
            String query = "SELECT * FROM (EMPLOYEE E LEFT OUTER JOIN EMPLOYEE S ON E.Super_ssn = S.Ssn) JOIN DEPARTMENT ON E.Dno = Dnumber";
            Statement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                String name = rs.getString("E.Fname") + " " + rs.getString("E.Minit") + " " + rs.getString("E.Lname");
                String Ssn = rs.getString("E.Ssn");
                String Bdate = rs.getString("E.Bdate");
                String Address = rs.getString("E.Address");
                String Sex = rs.getString("E.Sex");
                String Salary = rs.getString("E.Salary");
                String superName = rs.getString("S.Fname") + " " + rs.getString("S.Minit") + " " + rs.getString("S.Lname");
                String Department = rs.getString("Dname");

                Object[] selectedList = {name, Ssn, Bdate, Address, Sex, Salary, superName, Department};
                //체크 되었다면 반환 및 추출 코드

                if(stmt != null){
                    try{
                        stmt.close();
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }
                dbCon.close(stmt, conn);
                //return 저장한 list
            }

        } catch (SQLException e) {
            System.out.println("데이터 출력에 에러가 발생하였습니다.");
            throw new RuntimeException(e);
        }
        return null;
    }

}

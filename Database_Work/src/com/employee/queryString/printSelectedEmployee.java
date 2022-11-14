package com.employee.queryString;

import com.employee.JDBCConnection.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class printSelectedEmployee {
    public static String firstAdd = "SELECT * FROM (EMPLOYEE E LEFT OUTER JOIN EMPLOYEE S ON E.Super_ssn = S.Ssn) JOIN DEPARTMENT" +
            "ON E.Dno = Dnumber";
    public ArrayList<String> showEmpDept(/*모델 들어가야됨*/String s, boolean[] isSelected){
        try{
            JDBCConnection dbCon = new JDBCConnection();

            dbCon.Connect();
            ArrayList<String> ssnList = new ArrayList<>();
            String query = firstAdd + "WHERE Dname = ? ";
            PreparedStatement stmt = dbCon.getConnection().prepareStatement(query);
            stmt.setString(1, s);

            ResultSet rs = stmt.executeQuery();

            if(stmt!=null){
                try{
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            JDBCConnection.close(rs, stmt, stmt.getConnection());
            return ssnList;
        } catch (SQLException e) {
            System.out.println("검색에 에러가 발생하였습니다.");
            e.printStackTrace();
        }
        return null;
    }
    //나머지 코드들은 구현이 필요합니다. GUI단과 회의 후 결정하겠습니다.
}

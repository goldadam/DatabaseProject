package com.employee.queryString;

import com.employee.JDBCConnection.JDBCConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class selectEmployeeAndDelete {
    public void insertEmpIntoTable(String F,String M, String L, String Ssn, String B, String A, String Sex, String sal, String S_ssn, String Dno){
        String[] arr = new String[]{"F", "M", "L", "Ssn", "B", "A", "Sex", "sal", "S_ssn", "Dno"};

        try{
            JDBCConnection db = new JDBCConnection();
            db.Connect();

            String query = "INSERT INTO EMPLOYEE (Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno" +
                    "values(?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement stmt = db.Connect().prepareStatement(query);
            for(int i=1;i<=10;i++){
                stmt.setString(i, arr[i]);
            }
            stmt.executeUpdate();

            if(stmt!=null){
                try{
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            db.close(stmt, stmt.getConnection()); //디비 설정 세팅 나중에 바꿔줘야됨
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //추가시 중복값 제거 코드 구현해야됨
}

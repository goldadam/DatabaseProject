package com.employee.JDBCConnection;

import com.sun.source.tree.StatementTree;

import java.sql.*;

public class JDBCConnection {
    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String user = "root";
    private static final String password = "tlswlsdn12!@";

    private Connection conn = null;


//    public static void getConnection() throws SQLException {
//        Connection conn = null;
//        Statement stmt = null;
//        try {
//            conn = DriverManager.getConnection(url, user, password);
//            stmt = conn.createStatement();
//        } catch (Exception e) {
//            System.out.println("Database 연결 실패함");
//            e.printStackTrace();
//        }
//        stmt.close();
//        conn.close();
//    }
    public void Connect(){
        try{
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("DB 연결이 완료되었습니다.");
        }catch (SQLException e){
            System.out.println("DB 설정에 에러가 발생하였습니다");
            e.printStackTrace();
        }
    }

    public void close(PreparedStatement stmt, Connection conn){
        if(stmt != null){
            try{
                if(!stmt.isClosed()) stmt.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                stmt = null;
            }
        }
        if(conn != null){
            try{
                if(!conn.isClosed()) conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                conn = null;
            }
        }
    }
    public static void close(ResultSet rs, PreparedStatement stmt, Connection conn){
        if(rs != null){
            try{
                if(!rs.isClosed()) rs.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                rs = null;
            }
        }
        if(stmt != null){
            try{
                if(!stmt.isClosed()) stmt.close();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                stmt = null;
            }
        }
        if(conn != null){
            try{
                if(!conn.isClosed()) conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                conn = null;
            }
        }
    }
    public Connection getConnection(){
        return conn;
    }
}

import java.sql.*;

public class JdbcConnection {
    private Connection conn = null;

    public void Connect(){
        try{
            final String url = "jdbc:mysql://localhost:3306/PROJECT";
            final String user = "root";
            final String password = "tlswlsdn12!@";
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
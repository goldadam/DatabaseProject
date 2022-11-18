import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class selectEmployeeAndAdd {
    public void insertEmpIntoTable(String F,String M, String L, String Ssn, String B, String A, String Sex, String sal, String S_ssn, String Dno){
        String[] arr = new String[]{"F", "M", "L", "Ssn", "B", "A", "Sex", "sal", "S_ssn", "Dno"};

        try{
            JdbcConnection db = new JdbcConnection();
            db.Connect();

            String query = "INSERT INTO EMPLOYEE (Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno" +
                    "values(?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement stmt = db.getConnection().prepareStatement(query);
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

    public int isSsnExists(String ssn){
        int exist  = -1;
        try{
            JdbcConnection db = new JdbcConnection();
            db.Connect();

            String query = "SELECT COUNT(*) AS A FROM EMPLOYEE WHERE Ssn = ?";

            PreparedStatement stmt = db.getConnection().prepareStatement(query);
            stmt.setString(1, ssn);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            exist = rs.getInt("A");

            if(stmt != null){
                try{
                    stmt.close();
                }catch (SQLException e){
                    System.out.println("에러가 발생하였습니다");
                    e.printStackTrace();
                }
            }
            db.close(stmt, stmt.getConnection());
        }catch (SQLException e){
            System.out.println("에러가 발생하였습니다");
            e.printStackTrace();
        }
        return exist;
    }
    public int isDnumExist(String Dnum){
        int exist  = -1;
        try{
            JdbcConnection db = new JdbcConnection();
            db.Connect();

            String query = "SELECT COUNT(*) AS A FROM DEPARTMENT WHERE Dnumber = ?";

            PreparedStatement stmt = db.getConnection().prepareStatement(query);
            stmt.setString(1, Dnum);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            exist = rs.getInt("A");

            if(stmt != null){
                try{
                    stmt.close();
                }catch (SQLException e){
                    System.out.println("에러가 발생하였습니다");
                    e.printStackTrace();
                }
            }
            db.close(stmt, stmt.getConnection());
        }catch (SQLException e){
            System.out.println("에러가 발생하였습니다");
            e.printStackTrace();
        }
        return exist;
    }
}

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class selectEmployeeAndUpdate {
    public void updateEmployee(String s, String data, String ssn){
        try{
            JdbcConnection dbCon = new JdbcConnection();
            dbCon.Connect();

            String query = "UPDATE EMPLOYEE SET" + s + " = ? WHERE Ssn = ?";
            PreparedStatement stmt = dbCon.getConnection().prepareStatement(query);
            stmt.setString(1, data);
            stmt.setString(2, ssn);

            stmt.executeUpdate();

            dbCon.close(stmt, stmt.getConnection());//이부분도 수정 필요?
        }catch (SQLException e){
            System.out.println("update에 문제가 발생하였습니다.");
            e.printStackTrace();
        }
    }
}

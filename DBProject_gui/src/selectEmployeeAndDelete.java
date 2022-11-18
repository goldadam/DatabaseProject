import java.sql.PreparedStatement;
import java.sql.SQLException;

public class selectEmployeeAndDelete {
    public void DeleteEmp(String ssn){
        try{
            DeleteDependent(ssn);
            DeleteWorksOn(ssn);

            JdbcConnection db = new JdbcConnection();
            db.Connect();

            String query = "DELETE FROM EMPLOYEE WHERE Ssn = ?";

            PreparedStatement stmt = db.getConnection().prepareStatement(query);
            stmt.setString(1, ssn);

            stmt.executeUpdate();

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            db.close(stmt, stmt.getConnection());
        } catch (SQLException e) {
            System.out.println("DELETE문에 에러 발생");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void DeleteWorksOn(String ssn){
        try{
            JdbcConnection db = new JdbcConnection();
            db.Connect();

            String query = "DELETE FROM WORKS_ON WHERE ESsn = ?";

            PreparedStatement stmt = db.getConnection().prepareStatement(query);
            stmt.setString(1, ssn);

            stmt.executeUpdate();

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            db.close(stmt, stmt.getConnection());
        } catch (SQLException e) {
            System.out.println("WORKS_ON DELETE문에 에러 발생");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void DeleteDependent(String ssn){
        try{
            JdbcConnection db = new JdbcConnection();
            db.Connect();

            String query = "DELETE FROM DEPENDENT WHERE Essn = ?";

            PreparedStatement stmt = db.getConnection().prepareStatement(query);
            stmt.setString(1, ssn);

            stmt.executeUpdate();

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            db.close(stmt, stmt.getConnection());
        } catch (SQLException e) {
            System.out.println("DEPENDENT DELETE문에 에러 발생");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

import com.exception.DataNotFoundException;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class Employee_Management {


    public static void main(String[] args) throws SQLException {

    }
    public static void runSQL(Connection conn) throws SQLException, DataNotFoundException {
        try(Statement stmt = conn.createStatement()){
            getResult(stmt);
        }
    }
    public static void getResult(Statement stmt) throws SQLException, DataNotFoundException {
        String sql = "SELECT Fname, Salary FROM EMPLOYEE WHERE sex = 'M' ";
        String Fname;
        double Salary;

        try(ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                Fname = rs.getString(1);
                Salary = rs.getDouble("Salary");
                System.out.printf("Fname : %s, Salary : %f\n", Fname, Salary);
            }
        }
    }

}

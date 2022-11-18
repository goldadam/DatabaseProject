import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Q7 {
    //직원별 가족 모두 출력하기
    List<String> answer = new ArrayList<>();
    public List<String> showAllEmpdep(int dno){
        try{
            JdbcConnection db = new JdbcConnection();
            db.Connect();

            String query = "SELECT Dependent_name FROM DEPENDENT LEFT OUTER JOIN EMPLOYEE ON Ssn = ESsn " +
                    "GROUP BY Dno";
            PreparedStatement stmt = db.getConnection().prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            int i=1;
            while(rs.next()){
                answer.add(rs.getString(i));
                i++;
            }
            if(stmt!=null){
                try{
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }catch (SQLException e){
            System.out.println("데이터 베이스 연결이 실패하였습니다");
            e.printStackTrace();
        }
        return answer;
    }
}

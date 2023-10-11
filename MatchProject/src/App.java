import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App{
    static final String dbURL = "jdbc:mysql://localhost:3306/World";
    static final String user = "MySQLUser";
    static final String pass = "";
    static final String query = "SELECT * FROM CITY";
    public static void main(String[] args){
        try (
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);)
            {
                while(rs.next()){
                    System.out.print("fieldX"+rs.getString("Name"));
                }
            }
               catch (SQLException e) {
                e.printStackTrace();
            }
    }
}

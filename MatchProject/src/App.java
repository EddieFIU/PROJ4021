import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.DatabaseMetaData;

public class App{
   /*  static final String dbURL = "jdbc:mysql://localhost:3306/World";
    static final String user = "MySQLUser";
    static final String pass = "!";
    static final String query = "SELECT * FROM CITY";*/
    public static void main(String[] args){
    
          try {
            DatabaseRepo repo = new DatabaseRepo();
            Model.LoginUser loggedInUser = repo.GetLoginByUserName("edi");
            System.out.println(loggedInUser.UserType);

          } catch (Exception e) {
            // TODO: handle exception
          }  
    }
}

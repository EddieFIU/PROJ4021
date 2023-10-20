import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
            System.out.println(loggedInUser.UserType + " is the user type we are reading");
            Model.Candidate loggedInCandidate = repo.GetCandidateByLoginID(loggedInUser.ID);
            System.out.println(loggedInCandidate.FirstName + " is logged in");
            Model.Company co = repo.GetCompanyByLoginID(0);
            if (co.ID==0)
            {
                System.out.println("No records found for ID"); 
            }
            else{
                System.out.println(co.Name  + " is logged in");
            }
            
            List<Model.Candidate> allCandidates =  repo.GetAllCandidates();
            for (Model.Candidate candidate : allCandidates) {
                System.out.println("Candidate is: " + candidate.FirstName);
            }
            
          } catch (Exception e) {
            // TODO: handle exception
          }  
    }
}

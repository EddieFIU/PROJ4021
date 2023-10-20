import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Model.LoginUser;

public class DatabaseRepo {
    
    static final String dbURL = "jdbc:mysql://localhost:3306/Match";
    static final String user = "root";
    static final String pass = "M";
    
    public DatabaseRepo()
    {
        
    }

    /**
     * @param userName
     * @return
     */
    public Model.LoginUser GetLoginByUserName(String userName)
    {
        final String query = "SELECT * FROM loginuser where username = '" + userName + "'";
          LoginUser userLoggedIn = new LoginUser();  
         try (
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            Statement stmt = conn.createStatement();
           
            ResultSet rs = stmt.executeQuery(query);)
            {
                rs.next();
                    
                    userLoggedIn.ID = rs.getInt("ID");
                    userLoggedIn.Password = rs.getString("Password");
                    userLoggedIn.UserName = rs.getString("Username");
                    userLoggedIn.UserType = rs.getString("UserType");
                    userLoggedIn.CreatedDateTime = rs.getDate("CreatedDateTime");
                
            }
               catch (SQLException e) {
                e.printStackTrace();
            }

        return userLoggedIn;
    }

    public Model.Candidate GetCandidateByLoginID(int loginID)
    {
        final String query = "SELECT * FROM candidate where loginuserID = " + loginID;
          Model.Candidate candidate = new Model.Candidate();
         try (
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            Statement stmt = conn.createStatement();
           
            ResultSet rs = stmt.executeQuery(query);)
            {
                rs.next();
                    
                    candidate.ID = rs.getInt("ID");
                    candidate.FirstName = rs.getString("FirstName");
                    candidate.LastName = rs.getString("LastName");
                    candidate.DOB = rs.getDate("DOB");
                    candidate.Email = rs.getString("EMail");
                    candidate.CellPhone = rs.getString("CellPhone");
                    candidate.LogInUserID = rs.getInt("loginuserID");                
            }
               catch (SQLException e) {
                e.printStackTrace();
            }

        return candidate;
    }

 public Model.Company GetCompanyByLoginID(int loginID)
    {
        final String query = "SELECT * FROM company where loginuserID = " + loginID;
          Model.Company company = new Model.Company();
         try (
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            Statement stmt = conn.createStatement();
           
            ResultSet rs = stmt.executeQuery(query);)
            {
                 if (rs.next())
                    {
                    company.ID = rs.getInt("ID");
                    company.Name = rs.getString("Name");
                    company.Industry = rs.getString("Industry");
                    company.Email = rs.getString("EMail");
                    company.PhoneNumber = rs.getString("PhoneNumber");
                    company.LogInUserID = rs.getInt("loginuserID");                
                    }
            }
               catch (SQLException e) {
                e.printStackTrace();
            }

        return company;
    }

}

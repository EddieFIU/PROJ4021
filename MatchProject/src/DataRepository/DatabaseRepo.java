package DataRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.LoginUser;

public class DatabaseRepo {
    
    static final String dbURL = "jdbc:mysql://localhost:3306/Match";
    static final String user = "root";
    static final String pass = "Moli0128!";
    
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
 /**
 * @return
 */
public List<Model.Candidate> GetAllCandidates()
    {
        final String query = "SELECT * FROM candidate";
        //get list ready to return recrods from call
         List<Model.Candidate> candidates = new ArrayList<Model.Candidate>();

         try (
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            Statement stmt = conn.createStatement();
            
            
            ResultSet rs = stmt.executeQuery(query);)
            {
                while (rs.next())
                {
                     Model.Candidate candidate = new Model.Candidate();
                    candidate.ID = rs.getInt("ID");
                    candidate.FirstName = rs.getString("FirstName");
                    candidate.LastName = rs.getString("LastName");
                    candidate.DOB = rs.getDate("DOB");
                    candidate.Email = rs.getString("EMail");
                    candidate.CellPhone = rs.getString("CellPhone");
                    candidate.LogInUserID = rs.getInt("loginuserID");  
                    candidates.add(candidate);
                    }              
            }
               catch (SQLException e) {
                e.printStackTrace();
            }

        return candidates;
    }
public List<Model.CandidateQualification> GetCandidateQualifications(int CandidateID)
    {
        final String query = "SELECT * FROM candidatequalification where CandidateID = " + CandidateID;
          List<Model.CandidateQualification> qualifications = new ArrayList<Model.CandidateQualification>();
          try (
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            Statement stmt = conn.createStatement();
           
            ResultSet rs = stmt.executeQuery(query);)
            {
                 while (rs.next())
                {
                     Model.CandidateQualification qualification = new Model.CandidateQualification();
                    qualification.ID = rs.getInt("ID");
                    qualification.Qualification = rs.getString("Qualification");
                    qualification.LevelOfExperience = rs.getInt("LevelOfExperience");
                    qualification.CandidateID = rs.getInt("CandidateID");
                    qualifications.add(qualification);
                    }
            }
               catch (SQLException e) {
                e.printStackTrace();
            }

        return qualifications;
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
 public List<Model.Company> GetAllCompanies()
    {
        final String query = "SELECT * FROM company";
        List<Model.Company> companies = new ArrayList<Model.Company>();
          
         try (
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            Statement stmt = conn.createStatement();
           
            ResultSet rs = stmt.executeQuery(query);)
            {
                  while (rs.next())
                {
                     Model.Company company = new Model.Company();
                    
                    company.ID = rs.getInt("ID");
                    company.Name = rs.getString("Name");
                    company.Industry = rs.getString("Industry");
                    company.Email = rs.getString("EMail");
                    company.PhoneNumber = rs.getString("PhoneNumber");
                    company.LogInUserID = rs.getInt("loginuserID"); 
                    companies.add(company);
                    }
            }
               catch (SQLException e) {
                e.printStackTrace();
            }

        return companies;
    }

public List<Model.CompanyProject> GetCompanyProjects(int CompanyID)
    {
        final String query = "SELECT * FROM companyproject where CompanyID = " + CompanyID;
          List<Model.CompanyProject> projects = new ArrayList<Model.CompanyProject>();
          try (
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            Statement stmt = conn.createStatement();
           
            ResultSet rs = stmt.executeQuery(query);)
            {
                 while (rs.next())
                {
                     Model.CompanyProject project = new Model.CompanyProject();
                    project.ID = rs.getInt("ID");
                    project.ProjectManagerEmail = rs.getString("ProjectManagerEmail");
                    project.ProjectManagerName = rs.getString("ProjectManagerName");
                    project.ProjectName = rs.getString("ProjectName");
                    project.CompanyID = rs.getInt("CompanyID");
                    projects.add(project);
                    }
            }
               catch (SQLException e) {
                e.printStackTrace();
            }

        return projects;
    }
    public List<Model.ProjectRequirement> GetProjectRequirements(int ProjectID)
    {
        final String query = "SELECT * FROM projectrequirements where companyprojectId = " + ProjectID;
          List<Model.ProjectRequirement> requirements = new ArrayList<Model.ProjectRequirement>();
          try (
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            Statement stmt = conn.createStatement();
           
            ResultSet rs = stmt.executeQuery(query);)
            {
                 while (rs.next())
                {
                     Model.ProjectRequirement requirement = new Model.ProjectRequirement();
                    requirement.ID = rs.getInt("ID");
                    requirement.Requirement = rs.getString("Requirement");
                    requirement.LevelofExperty = rs.getInt("LevelofExperty");
                    requirement.CompanyProjectID = rs.getInt("companyprojectid");
                    requirements.add(requirement);
                    }
            }
               catch (SQLException e) {
                e.printStackTrace();
            }

        return requirements;
    }
}

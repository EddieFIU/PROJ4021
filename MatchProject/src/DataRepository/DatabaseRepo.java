package DataRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
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


/**
 * @param qualification - qualificatoin looking for
 * @param level - minimum level of experience needed
 * @return
 */
public List<Model.QualifiedCandidate> GetCandidatesWithQualification(String qualification, int level)
    {
        final String query = "SELECT c.ID, c.FirstName, c.Lastname, c.Email, c.CellPhone, cq.Qualification, cq.levelofExperience " + 
                                "FROM match.candidate c inner join match.candidatequalification cq on c.ID = cq.candidateID where cq.levelofExperience >= " + level + " and cq.Qualification = '" + qualification + "'";
        //get list ready to return recrods from call
         List<Model.QualifiedCandidate> candidates = new ArrayList<Model.QualifiedCandidate>();

         try (
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);)
            {
                while (rs.next())
                {
                     Model.QualifiedCandidate candidate = new Model.QualifiedCandidate();
                    candidate.ID = rs.getInt("ID");
                    candidate.FirstName = rs.getString("FirstName");
                    candidate.LastName = rs.getString("LastName");
                    candidate.Email = rs.getString("EMail");
                    candidate.CellPhone = rs.getString("CellPhone");
                    candidate.Qualification = rs.getString("Qualification");  
                    candidate.LevelOfExperience = rs.getInt("levelofExperience");
                    candidates.add(candidate);
                    }              
            }
               catch (SQLException e) {
                e.printStackTrace();
            }

        return candidates;
    }

    public Model.Candidate CreateCandidate(Model.Candidate newCandidate)
    {
        final String query = "INSERT INTO candidate(FirstName, LastName, DOB, EMail, CellPhone, logInUserID) values(?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);) {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newCandidate.FirstName);
            ps.setString(2, newCandidate.LastName);
            ps.setDate(3, newCandidate.DOB);
            ps.setString(4, newCandidate.Email);
            ps.setString(5, newCandidate.CellPhone);
            ps.setInt(6, newCandidate.LogInUserID);
            int numRowsAffected = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newCandidate.ID = rs.getInt(1);
                }
            } catch (SQLException s) {
                s.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newCandidate;
    }
 public Model.Company CreateCompany(Model.Company newCompany)
    {
        final String query = "INSERT INTO company(Name, Industry, EMail, PhoneNumber, logInUserID) values(?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);) {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newCompany.Name);
            ps.setString(2, newCompany.Industry);
            ps.setString(3, newCompany.Email);
            ps.setString(4, newCompany.PhoneNumber);
            ps.setInt(5, newCompany.LogInUserID);
            int numRowsAffected = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newCompany.ID = rs.getInt(1);
                }
            } catch (SQLException s) {
                s.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newCompany;
    }
     public Model.CandidateQualification CreateCandidateQualification(Model.CandidateQualification newCandidateQaulification)
    {
        final String query = "INSERT INTO candidatequalification(Qualification, levelOfExperience, CandidateID) values(?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);) {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newCandidateQaulification.Qualification);
            ps.setInt(2, newCandidateQaulification.LevelOfExperience);
            ps.setInt(3, newCandidateQaulification.CandidateID);
            int numRowsAffected = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newCandidateQaulification.ID = rs.getInt(1);
                }
            } catch (SQLException s) {
                s.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newCandidateQaulification;
    }
    
    public Model.CompanyProject CreateNewCompanyProject(Model.CompanyProject newCompanyProject)
    {
        final String query = "INSERT INTO companyproject(ProjectName, ProjectManagerName, ProjectManagerEMail, CompanyID) values(?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);) {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newCompanyProject.ProjectName);
            ps.setString(2, newCompanyProject.ProjectManagerName);
            ps.setString(3, newCompanyProject.ProjectManagerEmail);
            ps.setInt(4, newCompanyProject.CompanyID);
            int numRowsAffected = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newCompanyProject.ID = rs.getInt(1);
                }
            } catch (SQLException s) {
                s.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newCompanyProject;
    }

    public Model.ProjectRequirement CreateNewProjectRequirement(Model.ProjectRequirement newProjectRequirement)
    {
        final String query = "INSERT INTO projectrequirements(Requirement, Levelofexperty, companyprojectid) values(?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);) {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newProjectRequirement.Requirement);
            ps.setInt(2, newProjectRequirement.LevelofExperty);
            ps.setInt(3, newProjectRequirement.CompanyProjectID);

            int numRowsAffected = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newProjectRequirement.ID = rs.getInt(1);
                }
            } catch (SQLException s) {
                s.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newProjectRequirement;
    }
    
    public Model.LoginUser CreateLogin(Model.LoginUser newLoginUser)
    {
        final String query = "INSERT INTO loginuser(UserName, Password, UserType, CreatedDateTime) values(?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);) {
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newLoginUser.UserName);
            ps.setString(2, newLoginUser.Password);
            ps.setString(3, newLoginUser.UserType);
            ps.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
            int numRowsAffected = ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    newLoginUser.ID = rs.getInt(1);
                }
            } catch (SQLException s) {
                s.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newLoginUser;
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

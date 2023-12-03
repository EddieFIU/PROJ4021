package DataRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import Model.CompanyProject;
import Model.LoginUser;

public class DatabaseRepo {
    
    static final String dbURL = "jdbc:mysql://localhost:3306/match";
    static final String user = "root";
    static final String pass = "SergioMatch";
    
    public DatabaseRepo()
    {
        
    }
    public boolean addProjectRequirement(Model.ProjectRequirement requirement) {
        final String query = "INSERT INTO projectrequirements (Requirement, Levelofexperty, companyprojectid) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, requirement.getRequirement());
            ps.setInt(2, requirement.getLevelofExperty());
            ps.setInt(3, requirement.getCompanyProjectID());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean applyToCompany(int candidateID, int projectID) {
        final String query = "INSERT INTO projectapplication (CandidateID, ProjectID, ApplicationDate, Status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, candidateID);
            ps.setInt(2, projectID);
            ps.setDate(3, new java.sql.Date(new Date().getTime())); // Sets the current date
            ps.setString(4, "Pending"); // Default status

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Model.Company> getMatchingCompanyProfiles(int candidateID) {
        List<Model.Company> matchingCompanies = new ArrayList<>();
        final String query = "SELECT c.* FROM company c " +
                "JOIN projectrequirements pr ON c.ID = pr.companyprojectid " +
                "JOIN candidatequalification cq ON pr.Requirement = cq.Qualification " +
                "WHERE cq.CandidateID = ? AND cq.LevelOfExperience >= pr.Levelofexperty";

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, candidateID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Model.Company company = new Model.Company();
                    // Populate company details from ResultSet
                    matchingCompanies.add(company);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matchingCompanies;
    }

    public int countMatchedCandidates(int companyID) {
        final String query = "SELECT COUNT(DISTINCT CandidateID) as totalMatches " +
                "FROM candidatequalification cq " +
                "JOIN projectrequirements pr ON cq.Qualification = pr.Requirement " +
                "WHERE pr.companyprojectid IN " +
                "(SELECT ID FROM companyproject WHERE CompanyID = ?) " +
                "AND cq.LevelOfExperience >= pr.Levelofexperty";

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, companyID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("totalMatches");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Integer> getCompanyProjectIDs(int companyID) {
        List<Integer> projectIDs = new ArrayList<>();
        final String query = "SELECT ID FROM match.companyproject WHERE CompanyID = ?";

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, companyID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    projectIDs.add(rs.getInt("ID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectIDs;
    }

    public List<Model.Candidate> getMatchedCandidates(int companyID) {
        List<Model.Candidate> matchedCandidates = new ArrayList<>();
        List<Integer> projectIDs = getCompanyProjectIDs(companyID);

        final String query =
                "SELECT DISTINCT c.* FROM match.candidatequalification cq " +
                        "JOIN match.candidate c ON cq.CandidateID = c.ID " +
                        "JOIN match.projectrequirements pr ON cq.Qualification = pr.Requirement " +
                        "WHERE pr.companyprojectid IN (?) AND cq.LevelOfExperience >= pr.Levelofexperty";

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);
             PreparedStatement ps = conn.prepareStatement(query)) {

            String projectIDsStr = projectIDs.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            ps.setString(1, projectIDsStr);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Model.Candidate candidate = new Model.Candidate();
                    // Populate candidate details from ResultSet
                    matchedCandidates.add(candidate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matchedCandidates;
    }

    public List<Model.Candidate> getCandidatesForCompanyProjects(int companyID) {
        List<Model.Candidate> candidates = new ArrayList<>();
        final String query = "SELECT DISTINCT c.* FROM candidate c " +
                "JOIN projectapplication pa ON c.ID = pa.CandidateID " +
                "JOIN companyproject cp ON pa.ProjectID = cp.ID " +
                "WHERE cp.CompanyID = ?";

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, companyID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Model.Candidate candidate = new Model.Candidate();
                    candidate.ID = rs.getInt("ID");
                    candidate.FirstName = rs.getString("FirstName");
                    candidate.LastName = rs.getString("LastName");
                    candidate.Email = rs.getString("EMail");
                    candidate.CellPhone = rs.getString("CellPhone");
                    candidates.add(candidate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
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

    public Model.Candidate GetCandidateByLoginID(int loginID) {
        final String query = "SELECT * FROM candidate where loginuserID = " + loginID;
        System.out.println("GetCandidateByLoginID: Fetching candidate for loginID: " + loginID);

        Model.Candidate candidate = new Model.Candidate();
        try (
                Connection conn = DriverManager.getConnection(dbURL, user, pass);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
        ) {
            System.out.println("Executing query: " + query);

            if (rs.next()) {
                candidate.ID = rs.getInt("ID");
                candidate.FirstName = rs.getString("FirstName");
                candidate.LastName = rs.getString("LastName");
                candidate.DOB = rs.getDate("DOB");
                candidate.Email = rs.getString("EMail");
                candidate.CellPhone = rs.getString("CellPhone");
                candidate.LogInUserID = rs.getInt("loginuserID");

                System.out.println("Candidate found: " + candidate.FirstName + " " + candidate.LastName);
            } else {
                System.out.println("No candidate found for loginID: " + loginID);
            }
        } catch (SQLException e) {
            System.err.println("SQLException in GetCandidateByLoginID for loginID " + loginID + ": " + e.getMessage());
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

    public boolean doesProfileExist(int loginUserID) {
        final String query = "SELECT COUNT(*) FROM match.candidate WHERE loginuserID = ?";

        System.out.println("doesProfileExist: Checking for the existence of profile for loginUserID: " + loginUserID);

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, loginUserID);
            System.out.println("Executing query: " + ps);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Profile existence check for loginUserID " + loginUserID + ": " + (count > 0));
                    return count > 0; // Returns true if the count is more than 0, indicating the profile exists
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException in doesProfileExist for loginUserID " + loginUserID + ": " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Profile not found for loginUserID: " + loginUserID);
        return false; // Returns false if no profile is found or in case of an exception
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
            ps.setDate(3, new java.sql.Date(newCandidate.DOB.getTime()));
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
    public Model.Company updateCompany(Model.Company companyToUpdate) {
        final String query = "UPDATE company SET Name = ?, Industry = ?, Email = ?, PhoneNumber = ? WHERE loginUserId = ?";

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass)) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, companyToUpdate.Name);
            ps.setString(2, companyToUpdate.Industry);
            ps.setString(3, companyToUpdate.Email);
            ps.setString(4, companyToUpdate.PhoneNumber);
            ps.setInt(5, companyToUpdate.LogInUserID);

            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected > 0) {
                System.out.println("Update successful");
            } else {
                System.out.println("Update failed: No such company found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyToUpdate;
    }

    public boolean UpdateCandidate(Model.Candidate candidate) {
        System.out.println("Updating candidatssse: " + candidate.ID);
        final String query = "UPDATE match.candidate SET FirstName = ?, LastName = ?, DOB = ?, EMail = ?, CellPhone = ? WHERE loginuserID = ?";
        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, candidate.FirstName);
            ps.setString(2, candidate.LastName);
            ps.setDate(3, new java.sql.Date(candidate.DOB.getTime()));
            ps.setString(4, candidate.Email);
            ps.setString(5, candidate.CellPhone);
            ps.setInt(6, candidate.LogInUserID);

            int numRowsAffected = ps.executeUpdate();

            // Log the result of the update
            System.out.println("Number of rows affected: " + numRowsAffected);

            // Check if update was successful
            boolean isUpdated = numRowsAffected > 0;

            if (isUpdated) {
                // Optionally, retrieve and log the updated information for verification
                System.out.println("Update successful. Verifying updated information...");
                // Code to retrieve and log updated information goes here
            } else {
                System.out.println("Update failed.");
            }

            return isUpdated;
        } catch (SQLException e) {
            // Detailed logging for SQLException
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            e.printStackTrace();
            return false;
        }
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
    public boolean UpdateCandidateQualification(Model.CandidateQualification qualification) {
        final String query = "UPDATE candidatequalification SET Qualification = ?, levelOfExperience = ? WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, qualification.Qualification);
            ps.setInt(2, qualification.LevelOfExperience);
            ps.setInt(3, qualification.ID);
            int numRowsAffected = ps.executeUpdate();
            return numRowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean DeleteCandidateQualification(int qualificationID) {
        final String query = "DELETE FROM candidatequalification WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, qualificationID);
            int numRowsAffected = ps.executeUpdate();
            return numRowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
    public boolean addCompanyProject(Model.CompanyProject companyProject) {
        final String query = "INSERT INTO companyproject (ProjectName, ProjectManagerName, ProjectManagerEmail, CompanyID) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, companyProject.getProjectName());
            ps.setString(2, companyProject.getProjectManagerName());
            ps.setString(3, companyProject.getProjectManagerEmail());
            ps.setInt(4, companyProject.getCompanyID());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CompanyProject> getCompanyProjectsByCompanyID(int companyID) {
        List<Model.CompanyProject> projects = new ArrayList<>();
        final String query = "SELECT * FROM companyproject WHERE CompanyID = ?";

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, companyID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Model.CompanyProject project = new Model.CompanyProject();
                    project.ID = rs.getInt("ID");
                    project.ProjectName = rs.getString("ProjectName");
                    project.ProjectManagerName = rs.getString("ProjectManagerName");
                    project.ProjectManagerEmail = rs.getString("ProjectManagerEMail");
                    project.CompanyID = rs.getInt("CompanyID");
                    projects.add(project);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
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
    public int getCandidateId (int loginUserForeignKey) {
        final String query = "SELECT ID FROM match.candidate WHERE loginuserID = ?";

        try (Connection conn = DriverManager.getConnection(dbURL, user, pass);) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, loginUserForeignKey);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return a default value or handle the case when no candidate is found
        return -1; // You can choose an appropriate default value or error handling approach
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

public List<Model.QualifyingCompany> GetQualifyingCompanies(String qualificationSearching, int levelofExperience)
    {
        final String query = "SELECT c.ID, c.Name, c.Industry, p.ProjectName, p.ProjectManagerName, p.ProjectManagerEmail, r.Requirement, r.Levelofexperty " + 
                "from company c inner join companyproject p on c.id = p.companyId " + 
                "inner join projectrequirements r on r.companyprojectid = p.id where r.Requirement = '" + qualificationSearching + "' and Levelofexperty >= " + levelofExperience;

        List<Model.QualifyingCompany> companies = new ArrayList<Model.QualifyingCompany>();
          
         try (
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            Statement stmt = conn.createStatement();
           
            ResultSet rs = stmt.executeQuery(query);)
            {
                  while (rs.next())
                {
                     Model.QualifyingCompany company = new Model.QualifyingCompany();
                    
                    company.ID = rs.getInt("ID");
                    company.Name = rs.getString("Name");
                    company.Industry = rs.getString("Industry");
                    company.ProjectName = rs.getString("ProjectName");
                    company.ProjectManagerName = rs.getString("ProjectManagerName");
                    company.ProjectManagerEmail = rs.getString("ProjectManagerEmail");
                    company.Requirement = rs.getString("Requirement");                    
                    company.LevelOfExperty = rs.getInt("Levelofexperty"); 
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

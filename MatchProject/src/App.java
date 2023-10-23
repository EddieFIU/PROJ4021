
import java.util.List;
import Model.*;
public class App{
   /*  static final String dbURL = "jdbc:mysql://localhost:3306/World";
    static final String user = "MySQLUser";
    static final String pass = "!";
    static final String query = "SELECT * FROM CITY";*/
    public static void main(String[] args){
    
          try {
            DatabaseRepo repo = new DatabaseRepo();
            LoginUser loggedInUser = repo.GetLoginByUserName("edi");
            System.out.println(loggedInUser.UserType + " is the user type we are reading");
            Candidate loggedInCandidate = repo.GetCandidateByLoginID(loggedInUser.ID);
            System.out.println(loggedInCandidate.FirstName + " is logged in");
            
            Company co = repo.GetCompanyByLoginID(0);
            if (co.ID==0)
            {
                System.out.println("No records found for ID"); 
            }
            else{
                System.out.println(co.Name  + " is logged in");
            }
            
            List<Company> allCompanies = repo.GetAllCompanies();
            for (Company company : allCompanies) {
              System.out.println("Company is: " + company.Name);
              
              List<CompanyProject> projects = repo.GetCompanyProjects(company.ID);
              for(CompanyProject project : projects)
              {
                System.out.println("project name is: " + project.ProjectName);
                List<ProjectRequirement> requirements = repo.GetProjectRequirements(project.ID);
                for(ProjectRequirement requirement:requirements)
                {
                  System.out.println("requirement is: " + requirement.Requirement);
                  System.out.println("rating is: " + requirement.LevelofExperty);
                  
                }
              }

            }
            List<Candidate> allCandidates =  repo.GetAllCandidates();
            for (Candidate candidate : allCandidates) {
                System.out.println("Candidate is: " + candidate.FirstName);
                List<CandidateQualification> qualifications = repo.GetCandidateQualifications(candidate.ID);
                for(CandidateQualification qualification : qualifications)
                {
                  System.out.println("qualifications are: " + qualification.Qualification);
                  System.out.println("qualifications level: " + qualification.LevelOfExperience);
                }
            }
            
          } catch (Exception e) {
            // TODO: handle exception
          }  
    }
}

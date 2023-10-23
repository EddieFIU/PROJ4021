
import java.util.List;

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
            
            List<Model.Company> allCompanies = repo.GetAllCompanies();
            for (Model.Company company : allCompanies) {
              System.out.println("Company is: " + company.Name);
              
              List<Model.CompanyProject> projects = repo.GetCompanyProjects(company.ID);
              for(Model.CompanyProject project : projects)
              {
                System.out.println("project name is: " + project.ProjectName);
                List<Model.ProjectRequirement> requirements = repo.GetProjectRequirements(project.ID);
                for(Model.ProjectRequirement requirement:requirements)
                {
                  System.out.println("requirement is: " + requirement.Requirement);
                  System.out.println("rating is: " + requirement.LevelofExperty);
                  
                }
              }

            }
            List<Model.Candidate> allCandidates =  repo.GetAllCandidates();
            for (Model.Candidate candidate : allCandidates) {
                System.out.println("Candidate is: " + candidate.FirstName);
                List<Model.CandidateQualification> qualifications = repo.GetCandidateQualifications(candidate.ID);
                for(Model.CandidateQualification qualification : qualifications)
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


import java.util.List;

import Business.*;
public class App{
   /*  static final String dbURL = "jdbc:mysql://localhost:3306/World";
    static final String user = "MySQLUser";
    static final String pass = "!";
    static final String query = "SELECT * FROM CITY";*/
    public static void main(String[] args){
    
          try {
             
            LoginAccount loggedInUser = new LoginAccount();
            Model.LoginUser user = loggedInUser.GetAccountByUserName("eddie");
            System.out.println(user.UserType + " is the user type we are reading");

            Candidate candidateInfo = new Candidate(); 
            Model.Candidate loggedinUser = candidateInfo.GetCandidateByLoginID(user.ID);

            System.out.println(loggedinUser.FirstName + " is logged in");
            
            Company co = new Company();
            Model.Company companyInfo = co.GetCompanyByLoginID(2);

            if (companyInfo.ID==0)
            {
                System.out.println("No records found for ID"); 
            }
            else{
                System.out.println(companyInfo.Name  + " is logged in");
            }
            

            List<Model.Company> allCompanies = co.GetAllCompanies();
            for (Model.Company company : allCompanies) {
              System.out.println("Company is: " + company.Name);
              
              List<Model.CompanyProject> projects = co.GetCompanyProjects(company.ID);
              for(Model.CompanyProject project : projects)
              {
                System.out.println("project name is: " + project.ProjectName);
                List<Model.ProjectRequirement> requirements = co.GetProjectRequirements(project.ID);
                for(Model.ProjectRequirement requirement:requirements)
                {
                  System.out.println("requirement is: " + requirement.Requirement);
                  System.out.println("rating is: " + requirement.LevelofExperty);
                  
                }
              }

            }
            List<Model.Candidate> allCandidates =  candidateInfo.GetAllCandidates();
            for (Model.Candidate candidate : allCandidates) {
                System.out.println("Candidate is: " + candidate.FirstName);
                List<Model.CandidateQualification> qualifications = candidateInfo.GetCandidateQualifications(candidate.ID);
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


import java.util.List;

import Business.*;
import Model.LoginUser;
public class App{
   /*  static final String dbURL = "jdbc:mysql://localhost:3306/World";
    static final String user = "MySQLUser";
    static final String pass = "!";
    static final String query = "SELECT * FROM CITY";*/
    public static void main(String[] args){
    
          try {
             //creating login business object
            LoginAccount loggedInUser = new LoginAccount();

            LoginUser newUser = new LoginUser();
            newUser.Password="abc";
            newUser.UserName="apple";
            newUser.UserType="candidate";

            newUser = loggedInUser.CreateUserLogin(newUser);
            
            System.out.println("new user id created is:" + newUser.ID);

            //calling method in business object to return our model
            Model.LoginUser user = loggedInUser.GetAccountByUserName("eddie");
            System.out.println(user.UserType + " is the user type we are reading");

            //creating candidate business object
            Candidate candidateInfo = new Candidate(); 
            //returning model of logged in user by id
            Model.Candidate loggedinUser = candidateInfo.GetCandidateByLoginID(user.ID);
            System.out.println(loggedinUser.FirstName + " is logged in");
            
            //creating company business object
            Company co = new Company();
            //calling method in business object to return company model
            Model.Company companyInfo = co.GetCompanyByLoginID(2);

            if (companyInfo.ID==0)
            {
                System.out.println("No records found for ID"); 
            }
            else{
                System.out.println(companyInfo.Name  + " is logged in");
            }
            
            //calling method of business object instantiated above to return all companies
            List<Model.Company> allCompanies = co.GetAllCompanies();
            for (Model.Company company : allCompanies) {
              System.out.println("Company is: " + company.Name);
              
              //using the same business object to return the projects based on company currently in for loop
              List<Model.CompanyProject> projects = co.GetCompanyProjects(company.ID);
              for(Model.CompanyProject project : projects)
              {
                System.out.println("project name is: " + project.ProjectName);
                List<Model.ProjectRequirement> requirements = co.GetProjectRequirements(project.ID);
                //calling same business object for company to return requirements by project.  all based on the company we are on in the for loop
                for(Model.ProjectRequirement requirement:requirements)
                {
                  System.out.println("requirement is: " + requirement.Requirement);
                  System.out.println("rating is: " + requirement.LevelofExperty);
                  
                }
              }
            }

            //calling candidate object already instantiated above to return all candiates
            List<Model.Candidate> allCandidates =  candidateInfo.GetAllCandidates();
            for (Model.Candidate candidate : allCandidates) {
                System.out.println("Candidate is: " + candidate.FirstName);
                //calling method to return the candidate's qualifications based on the current candidate ID in the for loop
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

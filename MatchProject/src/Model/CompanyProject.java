package Model;
public class CompanyProject {
    public  int ID;
    public  String ProjectName;
    public  String ProjectManagerName;    
    public  String ProjectManagerEmail;
    public  int CompanyID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getProjectManagerName() {
        return ProjectManagerName;
    }

    public void setProjectManagerName(String projectManagerName) {
        ProjectManagerName = projectManagerName;
    }

    public String getProjectManagerEmail() {
        return ProjectManagerEmail;
    }

    public void setProjectManagerEmail(String projectManagerEmail) {
        ProjectManagerEmail = projectManagerEmail;
    }

    public int getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(int companyID) {
        CompanyID = companyID;
    }
}

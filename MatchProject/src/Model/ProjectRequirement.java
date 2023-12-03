package Model;

public class ProjectRequirement {
    public  int ID;
    public  String Requirement;
    public  int LevelofExperty;    
    public  int CompanyProjectID;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getRequirement() {
        return Requirement;
    }

    public void setRequirement(String requirement) {
        Requirement = requirement;
    }

    public int getLevelofExperty() {
        return LevelofExperty;
    }

    public void setLevelofExperty(int levelofExperty) {
        LevelofExperty = levelofExperty;
    }

    public int getCompanyProjectID() {
        return CompanyProjectID;
    }

    public void setCompanyProjectID(int companyProjectID) {
        CompanyProjectID = companyProjectID;
    }
}

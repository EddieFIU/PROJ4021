package Model;
public class Company {
    public  int ID;
    public  String Name;
    public  String Industry;    
    public  String Email;
    public  String PhoneNumber;
    public  int LogInUserID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String industry) {
        Industry = industry;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getLogInUserID() {
        return LogInUserID;
    }

    public void setLogInUserID(int logInUserID) {
        LogInUserID = logInUserID;
    }
}

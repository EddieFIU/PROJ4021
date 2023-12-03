package Model;

public class CandidateQualification {
    public  int ID;
    public  String Qualification;
    public  int LevelOfExperience;    
    public  int CandidateID;

    public CandidateQualification() {
    }

    public CandidateQualification(String qualification, int levelOfExperience) {
        Qualification = qualification;
        LevelOfExperience = levelOfExperience;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public int getLevelOfExperience() {
        return LevelOfExperience;
    }

    public void setLevelOfExperience(int levelOfExperience) {
        LevelOfExperience = levelOfExperience;
    }

    public int getCandidateID() {
        return CandidateID;
    }

    public void setCandidateID(int candidateID) {
        CandidateID = candidateID;
    }
}

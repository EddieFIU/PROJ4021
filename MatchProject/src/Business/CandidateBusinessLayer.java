package Business;

import java.util.List;

import DataRepository.DatabaseRepo;
import Model.Candidate;

public class CandidateBusinessLayer {
 DatabaseRepo repo;
 
    public CandidateBusinessLayer(){
        repo = new DatabaseRepo();
    }
    public Model.Candidate GetCandidateByLoginID(int loginID)
    {
        return  repo.GetCandidateByLoginID(loginID);

    }
    public int  countMatchedCandidates(int loginID)
    {
        return  repo.countMatchedCandidates(loginID);

    }
    public boolean    applyToCompany(int candidateID, int projectID){

        return repo. applyToCompany(candidateID, projectID);
    }
    public Model.Candidate CreatenewCandidate(Model.Candidate newCandidate)
    {
        return  repo.CreateCandidate(newCandidate);

    }
    public boolean UpdateCandidateProfile(Candidate candidate) {
        // Update the candidate profile in the database and return the success status.
        return repo.UpdateCandidate(candidate);
    }
    public Boolean doesProfileExist(int id)
    {
        return  repo.doesProfileExist(id);

    }
    public Model.CandidateQualification CreatenewCandidateQualification(Model.CandidateQualification newCandidateQualification)
    {
        return  repo.CreateCandidateQualification(newCandidateQualification);

    }
    public int getCandidateId(int candidateId )
    {
        return  repo.getCandidateId(candidateId );

    }
    public List<Model.Company>   getMatchingCompanyProfiles(int candidateID){
        return repo.getMatchingCompanyProfiles(candidateID);
    }
    public boolean DeleteCandidateQualification(int qualificationID)
    {
        return  repo.DeleteCandidateQualification(qualificationID);

    }
    public boolean UpdateCandidateQualification(Model.CandidateQualification qualification)
    {
        return  repo.UpdateCandidateQualification(qualification);

    }
    public List<Model.QualifiedCandidate>GetCandidatesWithQualifications(String qualificationLookingFor, int minLevelOfExperience)
    {
        return  repo.GetCandidatesWithQualification(qualificationLookingFor, minLevelOfExperience);
    } 

    public List<Model.Candidate>GetAllCandidates()
    {
        return  repo.GetAllCandidates();
    } 

    public List<Model.CandidateQualification> GetCandidateQualifications(int id)
    {
        return repo.GetCandidateQualifications(id);
    } 
    
    public List<Model.QualifiedCandidate> GetQualifiedCandidates(String qualification,int minLevelOfExperience)
    {
        return repo.GetCandidatesWithQualification(qualification,minLevelOfExperience);
    } 

} 
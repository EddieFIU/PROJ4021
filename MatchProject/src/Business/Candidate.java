package Business;

import java.util.List;

import DataRepository.DatabaseRepo;

public class Candidate {
 DatabaseRepo repo;
    public Candidate(){
        repo = new DatabaseRepo();
    }
    public Model.Candidate GetCandidateByLoginID(int loginID)
    {
        return  repo.GetCandidateByLoginID(loginID);

    }

    public List<Model.Candidate>GetAllCandidates()
    {
        return  repo.GetAllCandidates();
    } 

    public List<Model.CandidateQualification> GetCandidateQualifications(int id)
    {
        return repo.GetCandidateQualifications(id);
    } 
              
} 
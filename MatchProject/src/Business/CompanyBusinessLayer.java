package Business;

import java.util.List;

import DataRepository.DatabaseRepo;
import Model.CompanyProject;


public class CompanyBusinessLayer {
DatabaseRepo repo;

public CompanyBusinessLayer()
    {
        repo = new DatabaseRepo();
    }

public List<Model.Company> GetAllCompanies()
    {
        return repo.GetAllCompanies();
    }

public List<Model.CompanyProject> GetCompanyProjects(int CompanyId)
    {
        return  repo.GetCompanyProjects(CompanyId);
    }
    
public Model.Company GetCompanyByLoginID(int loginID)
    {           
        return repo.GetCompanyByLoginID(loginID);
    }





    public List<Model.Candidate> getMatchedCandidates(int loginID)
    {
        return repo.getMatchedCandidates(loginID);
    }

    public Model.Company CreateNewCompany(Model.Company newCompany)
    {           
        return repo.CreateCompany(newCompany);
    }

    public List<Model.Candidate> getCandidatesForCompanyProjects(int  id)
    {
        return repo.getCandidatesForCompanyProjects(id);
    }
public Model.CompanyProject CreateNewCompanyProject(Model.CompanyProject newCompanyProject)
    {           
        return repo.CreateNewCompanyProject(newCompanyProject);
    }

    public List<CompanyProject>  getCompanyProjectsByCompanyID(int id)
    {
        return repo.getCompanyProjectsByCompanyID(id);
    }
    public Boolean  addCompanyProject(Model.CompanyProject id)
    {
        return repo.addCompanyProject(id);
    }
    public Model.Company updateCompany(Model.Company newCompanyProject)
    {
        return repo.updateCompany(newCompanyProject);
    }
    public Boolean addProjectRequirement(Model.ProjectRequirement newProjectRequirement)
    {
        return repo.addProjectRequirement(newProjectRequirement);
    }
public Model.ProjectRequirement CreateNewProjectRequirement(Model.ProjectRequirement newProjectRequirement)
{
    return repo.CreateNewProjectRequirement(newProjectRequirement);
}
    
public List<Model.ProjectRequirement> GetProjectRequirements(int projectID) 
    {
        return repo.GetProjectRequirements(projectID);
    }

public List<Model.QualifyingCompany> GetQualifyingCompanies(String qualification, int levelOfExperience) 
    {
        return repo.GetQualifyingCompanies(qualification, levelOfExperience);
    }
}
            
            



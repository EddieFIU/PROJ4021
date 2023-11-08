package Business;

import java.util.List;

import DataRepository.DatabaseRepo;


public class Company {
DatabaseRepo repo;

public Company()
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

public Model.Company CreateNewCompany(Model.Company newCompany)
    {           
        return repo.CreateCompany(newCompany);
    } 

public Model.CompanyProject CreateNewCompanyProject(Model.CompanyProject newCompanyProject)
    {           
        return repo.CreateNewCompanyProject(newCompanyProject);
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
            
            



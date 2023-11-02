package Business;

import DataRepository.DatabaseRepo;

public class LoginAccount {
    DatabaseRepo repo;
    public LoginAccount(){
        repo = new DatabaseRepo();
    }
    public Model.LoginUser GetAccountByUserName(String userName)
    {
        return repo.GetLoginByUserName(userName);
    }

    public Model.LoginUser CreateUserLogin(Model.LoginUser newUser)
    {
        return repo.CreateLogin(newUser);
    }
    
}

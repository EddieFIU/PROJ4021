package Business;

import DataRepository.DatabaseRepo;
import Model.LoginUser;
import Model.Candidate;

public class LoginAccount {
    private DatabaseRepo repo;

    public LoginAccount() {
        this.repo = new DatabaseRepo();
    }

    public LoginUser GetAccountByUserName(String userName) {
        // Retrieve the account details for the given username from the database.
        return repo.GetLoginByUserName(userName);
    }

    public LoginUser CreateUserLogin(LoginUser newUser) {
        // Create a new login account in the database and return the created user with its ID set.
        return repo.CreateLogin(newUser);
    }



    public Candidate GetCandidateProfile(int loginUserID) {
        // Retrieve the candidate profile for the given login user ID from the database.
        return repo.GetCandidateByLoginID(loginUserID);
    }
}

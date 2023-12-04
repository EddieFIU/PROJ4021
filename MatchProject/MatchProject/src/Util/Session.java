package Util;
import Model.LoginUser;

public class Session {
    private static LoginUser currentUser;

    public static void setCurrentUser(LoginUser user) {
        currentUser = user;
    }

    public static LoginUser getCurrentUser() {
        return currentUser;
    }

    public static void clearCurrentUser() {
        currentUser = null;
    }

    public static boolean isUserLoggedIn() {
        return currentUser != null;
    }

    public static boolean isCurrentUserACompany() {
        return isUserLoggedIn() && "company".equalsIgnoreCase(currentUser.UserType);
    }

    public static boolean isCurrentUserACandidate() {
        return isUserLoggedIn() && "candidate".equalsIgnoreCase(currentUser.UserType);
    }
}

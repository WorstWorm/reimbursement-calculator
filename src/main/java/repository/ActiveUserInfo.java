package repository;

import entities.User;

public class ActiveUserInfo {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        ActiveUserInfo.user = user;
    }
}

package repository;

import entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final List<User> userList = new ArrayList<>();

    public static List<User> getUserList() {
        return userList;
    }

    public static User getUserByLogin(String login) {
        for(User u : userList) {
            if(u.getLogin().equals(login)) {
                return u;
            }
        }
        return null;
    }

    public static void addUser(User user) {
        userList.add(user);
    }

    public static void updateUserPassword(User user, String password) {
        for(User u : userList) {
            if(u.equals(user)){
                u.setPassword(password);
            }
        }
    }
}

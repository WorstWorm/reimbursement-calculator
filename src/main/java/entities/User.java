package entities;

import enums.UserStatus;
import service.AdminOperationsService;
import service.AvailableOperations;
import service.UserOperationsService;

public class User {
    private String login;
    private String password;
    private UserStatus status;

    private AvailableOperations availableOperations;

    public User() {
    }

    public User(String login, String password, UserStatus status) {
        this.login = login;
        this.password = password;
        this.status = status;
        if(status.equals(UserStatus.USER)) {
            this.availableOperations = new UserOperationsService();
        } else if (status.equals(UserStatus.ADMIN)) {
            this.availableOperations = new AdminOperationsService();
        } else {
            this.availableOperations = null;
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public AvailableOperations getAvailableOperations() {
        return availableOperations;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
        if (status.equals(UserStatus.USER)) {
            this.availableOperations = new UserOperationsService();
        } else if (status.equals(UserStatus.ADMIN)) {
            this.availableOperations = new AdminOperationsService();
        } else {
            this.availableOperations = null;
        }
    }
}

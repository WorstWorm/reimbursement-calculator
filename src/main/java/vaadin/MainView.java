package vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import entities.User;
import enums.UserStatus;
import repository.ActiveUserInfo;
import repository.UserRepository;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        BasicData.generateData();
        this.setAlignItems(Alignment.CENTER);
        this.setSizeFull();

        TextField loginField = new TextField("Your login");
        loginField.setLabel("Your login");
        loginField.setWidth(50, Unit.PERCENTAGE);

        TextField passwordField = new TextField("Your password");
        passwordField.setLabel("Your password");
        passwordField.setWidth(50, Unit.PERCENTAGE);

        TextArea infoLabel = new TextArea();
        infoLabel.setEnabled(false);
        infoLabel.setWidth(50, Unit.PERCENTAGE);
        infoLabel.setValue("For test there were created free accounts:\n" +
                "login: userLogin - password: userPassword - status: USER\n" +
                "login: anotherUser - password: somePassword - status: USER\n" +
                "login: adminLogin - password: adminPassword - status: ADMIN\n"
        );
        UserRepository.addUser(new User("anotherUser", "somePassword", UserStatus.USER));
        UserRepository.addUser(new User("adminLogin", "adminPassword", UserStatus.ADMIN));

        Button loginButton = new Button("confirm");
        loginButton.addClickListener(clickEvent -> {
            if(UserRepository.getUserByLogin(loginField.getValue())!=null) {
                User tempUser = UserRepository.getUserByLogin(loginField.getValue());
                if (loginField.getValue().equals(tempUser.getLogin()) && passwordField.getValue().equals(tempUser.getPassword())){
                    ActiveUserInfo.setUser(tempUser);
                    if(tempUser.getStatus() == UserStatus.USER) {
                        UI.getCurrent().navigate(UserView.class);
                    } else if (tempUser.getStatus() == UserStatus.ADMIN) {
                        UI.getCurrent().navigate(AdminView.class);
                    }
                }
            } else {
                Notification notification = new Notification("Wrong login or password", 5000);
                notification.open();
            }
        });

        add(infoLabel, loginField, passwordField, loginButton);
    }
}

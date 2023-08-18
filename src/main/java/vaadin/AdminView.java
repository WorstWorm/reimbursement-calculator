package vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import repository.ActiveUserInfo;

@Route("adminView")
public class AdminView extends VerticalLayout {
    public AdminView() {
        HorizontalLayout userInfo = new HorizontalLayout();

        if(ActiveUserInfo.getUser()!=null) {

            TextField infoLabel1 = new TextField();
            infoLabel1.setLabel("username");
            infoLabel1.setValue(ActiveUserInfo.getUser().getLogin());

            TextField infoLabel2 = new TextField();
            infoLabel2.setLabel("status");
            infoLabel2.setValue(ActiveUserInfo.getUser().getStatus().toString());

            Button logout = new Button("log out");
            logout.addClickListener(eventClicked -> {
                ActiveUserInfo.setUser(null);
                UI.getCurrent().navigate(MainView.class);
            });

            userInfo.add(infoLabel1, infoLabel2, logout);
            add(userInfo);
        }
    }
}

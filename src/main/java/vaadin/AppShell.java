package vaadin;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

@PWA(name = "Business Trip Reimbursement Calculation Application", shortName = "Reimbursement Calculator")
@Theme("my-theme")
public class AppShell implements AppShellConfigurator {
}

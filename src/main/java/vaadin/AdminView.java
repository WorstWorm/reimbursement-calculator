package vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import entities.ReceiptCategory;
import repository.ActiveUserInfo;
import repository.ReceiptCategoryRepository;
import repository.ReimbursementValues;

@Route("adminView")
public class AdminView extends VerticalLayout {

    private TextField usernameField;
    private TextField userStatusField;
    private TextField dailyAllowanceValueField;
    private TextField mileageValueField;
    private TextField totalReimbursementLimitField;
    private TextField mileageLimitField;
    private TextField categoryNameField;
    private TextField categoryLimitField;
    private Button logoutButton;
    private Button confirmDailyAllowanceButton;
    private Button confirmCarMileageValueButton;
    private Button addNewReceiptCategoryButton;
    private Button confirmCategoryModificationButton;
    private Button removeCategoryButton;
    private Button totalReimbursementLimitButton;
    private Button mileageLimitButton;
    private final Grid<ReceiptCategory> receiptCategoryGrid = new Grid<>(ReceiptCategory.class);
    private HorizontalLayout userInfoToolbar;
    private HorizontalLayout dailyAllowanceToolbar;
    private HorizontalLayout carMileageToolbar;
    private HorizontalLayout receiptCategoryToolbar;
    private ReceiptCategory selectedCategory;

    private void createUserInfoToolbar() {
        userInfoToolbar = new HorizontalLayout();

        usernameField = new TextField();
        usernameField.setLabel("username");
        usernameField.setValue(ActiveUserInfo.getUser().getLogin());

        userStatusField = new TextField();
        userStatusField.setLabel("status");
        userStatusField.setValue(ActiveUserInfo.getUser().getStatus().toString());

        logoutButton = new Button("log out");
        logoutButton.addClickListener(eventClicked -> {
            ActiveUserInfo.setUser(null);
            UI.getCurrent().navigate(MainView.class);
        });

        userInfoToolbar.add(usernameField, userStatusField, logoutButton);
        add(userInfoToolbar);
    }

    private void createDailyAllowanceToolbar() {
        dailyAllowanceToolbar = new HorizontalLayout();

        dailyAllowanceValueField = new TextField("daily allowance value");
        dailyAllowanceValueField.setValue(ReimbursementValues.getDailyAllowanceValue().toString());

        confirmDailyAllowanceButton = new Button("confirm");
        confirmDailyAllowanceButton.addClickListener(clickEvent -> {
            ActiveUserInfo.getUser().getAvailableOperations().setDailyAllowanceValue(Double.parseDouble(dailyAllowanceValueField.getValue()));
            Notification notification = new Notification("daily allowance changed", 5000);
            notification.open();
        });

        totalReimbursementLimitField = new TextField("total reimbursement limit");
        totalReimbursementLimitField.setValue(ReimbursementValues.getTotalReimbursementLimit().toString());

        totalReimbursementLimitButton = new Button("confirm");
        totalReimbursementLimitButton.addClickListener(clickEvent -> {
            ActiveUserInfo.getUser().getAvailableOperations().setTotalReimbursementLimit(Double.parseDouble(totalReimbursementLimitField.getValue()));
            Notification notification = new Notification("total reimbursement limit changed", 5000);
            notification.open();
        });

        dailyAllowanceToolbar.add(dailyAllowanceValueField, confirmDailyAllowanceButton, totalReimbursementLimitField, totalReimbursementLimitButton);
        add(dailyAllowanceToolbar);
    }

    private void createCarMileageToolbar() {
        carMileageToolbar = new HorizontalLayout();

        mileageValueField = new TextField("car mileage value");
        mileageValueField.setValue(ReimbursementValues.getCarMileageValue().toString());

        confirmCarMileageValueButton = new Button("confirm");
        confirmCarMileageValueButton.addClickListener(clickEvent -> {
            (ActiveUserInfo.getUser().getAvailableOperations()).setCarMileageValue(Double.parseDouble(mileageValueField.getValue()));
            Notification notification = new Notification("car mileage changed", 5000);
            notification.open();
        });

        mileageLimitField = new TextField("mileage limit");
        mileageLimitField.setValue(ReimbursementValues.getMileageLimit().toString());

        mileageLimitButton = new Button("confirm");
        mileageLimitButton.addClickListener(clickEvent -> {
            ActiveUserInfo.getUser().getAvailableOperations().setMileageLimit(Double.parseDouble(mileageLimitField.getValue()));
            Notification notification = new Notification("mileage limit changed", 5000);
            notification.open();
        });

        carMileageToolbar.add(mileageValueField, confirmCarMileageValueButton, mileageLimitField, mileageLimitButton);
        add(carMileageToolbar);
    }

    private void createReceiptCategoryGrid() {
        receiptCategoryGrid.setItems(ReceiptCategoryRepository.getReceiptCategoryList());
        receiptCategoryGrid.addColumn(new ComponentRenderer<>(category -> {
            Button categoryButton = new Button();
            categoryButton.setText("modify");
            categoryButton.addClickListener(clickEvent -> {
                selectedCategory = category;
                categoryNameField.setValue(selectedCategory.getCategoryName());
                categoryLimitField.setValue(selectedCategory.getReceiptCategoryLimit().toString());
                select();
            });
            return categoryButton;
        }));
        add(receiptCategoryGrid);
    }

    private void createReceiptCategoryToolbar() {
        receiptCategoryToolbar = new HorizontalLayout();

        addNewReceiptCategoryButton = new Button("add new receipt category");
        addNewReceiptCategoryButton.addClickListener(clickEvent -> select());

        categoryNameField = new TextField("category name");
        categoryNameField.setEnabled(false);

        categoryLimitField = new TextField("category limit");
        categoryLimitField.setEnabled(false);

        confirmCategoryModificationButton = new Button("confirm");
        confirmCategoryModificationButton.setEnabled(false);
        confirmCategoryModificationButton.addClickListener(clickEvent -> {
            ReceiptCategory category = new ReceiptCategory(categoryNameField.getValue(), categoryLimitField.getValue());
            ActiveUserInfo.getUser().getAvailableOperations().setReceiptCategory(category);
            unselect();
            refreshCategoryGrid();
        });

        removeCategoryButton = new Button("remove category");
        removeCategoryButton.setEnabled(false);
        removeCategoryButton.addClickListener(clickEvent -> {
           if(selectedCategory!=null) {
               ActiveUserInfo.getUser().getAvailableOperations().removeReceiptCategory(selectedCategory);
           }
           unselect();
           refreshCategoryGrid();
        });

        receiptCategoryToolbar.add(addNewReceiptCategoryButton, categoryNameField, categoryLimitField, confirmCategoryModificationButton, removeCategoryButton);
        add(receiptCategoryToolbar);
    }

    private void select() {
        categoryNameField.setEnabled(true);
        categoryLimitField.setEnabled(true);
        confirmCategoryModificationButton.setEnabled(true);
        removeCategoryButton.setEnabled(true);
        if(selectedCategory!=null) {
            categoryNameField.setValue(selectedCategory.getCategoryName());
            categoryLimitField.setValue(selectedCategory.getReceiptCategoryLimit().toString());
        }
    }

    private void unselect() {
        selectedCategory = null;
        categoryNameField.setValue("");
        categoryNameField.setEnabled(false);
        categoryLimitField.setValue("");
        categoryLimitField.setEnabled(false);
        confirmCategoryModificationButton.setEnabled(false);
        removeCategoryButton.setEnabled(false);
    }

    private void refreshCategoryGrid() {
        receiptCategoryGrid.setItems(ReceiptCategoryRepository.getReceiptCategoryList());
    }

    public AdminView() {

        if(ActiveUserInfo.getUser() != null) {
            createUserInfoToolbar();
            createDailyAllowanceToolbar();
            createCarMileageToolbar();
            createReceiptCategoryGrid();
            createReceiptCategoryToolbar();
        } else {
            this.setAlignItems(Alignment.CENTER);
            TextArea area = new TextArea();
            area.setEnabled(false);
            area.setValue("You are not logged in");
            add(area);
        }
    }
}

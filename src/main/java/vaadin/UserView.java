package vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import dto.ReimbursementClaimDto;
import entities.Claim;
import entities.Receipt;
import enums.ReceiptCategory;
import mapper.ReimbursementClaimMapper;
import repository.ActiveUserInfo;
import service.UserOperationsService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route("userView")
public class UserView extends VerticalLayout {

    ReimbursementClaimMapper reimbursementClaimMapper = new ReimbursementClaimMapper();

    TextField infoLabel1;
    TextField infoLabel2;
    TextField valueOfReceiptTextField;
    TextField carMillageTextField;
    Button logout;
    Button createReimbursementButton;
    Button confirmReimbursementButton;
    Button addReceiptButton;
    ComboBox<ReceiptCategory> receiptCategoryComboBox;
    DatePicker dateFromPicker;
    DatePicker dateToPicker;
    Grid<ReimbursementClaimDto> claimGrid;
    Grid<Receipt> receiptGrid;
    HorizontalLayout userInfo;
    HorizontalLayout claimButtonsToolbar;
    HorizontalLayout tripDatesToolbar;
    HorizontalLayout receiptsToolbar;
    Claim selectedClaim;
    List<Receipt> receiptListForSelectedClaim = new ArrayList<>();

    private void createUserInfoToolbar() {
        userInfo = new HorizontalLayout();

        infoLabel1 = new TextField();
        infoLabel1.setLabel("username");
        infoLabel1.setValue(ActiveUserInfo.getUser().getLogin());

        infoLabel2 = new TextField();
        infoLabel2.setLabel("status");
        infoLabel2.setValue(ActiveUserInfo.getUser().getStatus().toString());

        logout = new Button("log out");
        logout.addClickListener(eventClicked -> {
            ActiveUserInfo.setUser(null);
            UI.getCurrent().navigate(MainView.class);
        });

        userInfo.add(infoLabel1, infoLabel2, logout);
        add(userInfo);
    }

    private void createClaimGrid() {
        claimGrid = new Grid<>(ReimbursementClaimDto.class);
        refreshClaimGrid();
        claimGrid.removeColumnByKey("user");
        claimGrid.setHeight(300, Unit.PIXELS);
        add(claimGrid);
    }

    private void createClaimButtonsToolbar() {
        claimButtonsToolbar = new HorizontalLayout();
        createReimbursementButton = new Button("create new reimbursement");
        createReimbursementButton.addClickListener(clickEvent -> {
            claimSelectedPrepare(new Claim());
            claimSelectedUnlock();
        });

        confirmReimbursementButton = new Button("confirm reimbursement");
        confirmReimbursementButton.setEnabled(false);
        confirmReimbursementButton.addClickListener(clickEvent -> {
            selectedClaim.setTripDateFrom(dateFromPicker.getValue());
            selectedClaim.setTripDateTo(dateToPicker.getValue());
            selectedClaim.setDisabledDays(new ArrayList<>());
            selectedClaim.setReceiptList(receiptListForSelectedClaim);
            selectedClaim.setDrivenDistance(Integer.parseInt(carMillageTextField.getValue()));
            ((UserOperationsService)ActiveUserInfo.getUser().getAvailableOperations()).makeReimbursementClaim(ActiveUserInfo.getUser(), selectedClaim);
            refreshClaimGrid();
            unselectClaimLocks();
        });

        claimButtonsToolbar.add(createReimbursementButton, confirmReimbursementButton);
        add(claimButtonsToolbar);
    }

    private void createTripDatesToolbar() {
        tripDatesToolbar = new HorizontalLayout();
        dateFromPicker = new DatePicker("start of trip");
        dateFromPicker.setEnabled(false);
        dateToPicker = new DatePicker("end of trip");
        dateToPicker.setEnabled(false);
        tripDatesToolbar.add(dateFromPicker, dateToPicker);
        add(tripDatesToolbar);
    }

    private void createReceiptGrid() {
        receiptGrid = new Grid<>(Receipt.class);
        receiptGrid.setHeight(200, Unit.PIXELS);
        refreshReceiptGrid();
        add(receiptGrid);
    }

    private void createReceiptsToolbar() {
        receiptsToolbar = new HorizontalLayout();

        receiptCategoryComboBox = new ComboBox<>("receipt type");
        receiptCategoryComboBox.setItems(ReceiptCategory.values());
        receiptCategoryComboBox.setEnabled(false);
        valueOfReceiptTextField = new TextField("receipt value");
        valueOfReceiptTextField.setEnabled(false);

        addReceiptButton = new Button("add receipt");
        addReceiptButton.setEnabled(false);
        addReceiptButton.addClickListener(clickEvent -> {
            receiptListForSelectedClaim.add(new Receipt(receiptCategoryComboBox.getValue(), new BigDecimal(valueOfReceiptTextField.getValue())));
            refreshReceiptGrid();
        });
        receiptsToolbar.add(receiptCategoryComboBox, valueOfReceiptTextField, addReceiptButton);
        add(receiptsToolbar);
    }

    private void claimSelectedUnlock() {
        valueOfReceiptTextField.setEnabled(true);
        carMillageTextField.setEnabled(true);
        confirmReimbursementButton.setEnabled(true);
        addReceiptButton.setEnabled(true);
        receiptCategoryComboBox.setEnabled(true);
        dateFromPicker.setEnabled(true);
        dateToPicker.setEnabled(true);
    }

    private void claimSelectedPrepare(Claim claim) {
        selectedClaim = claim;
        if(claim.getReceiptsList() != null) {
            receiptListForSelectedClaim = claim.getReceiptsList();
        } else {
            receiptListForSelectedClaim = new ArrayList<>();
        }
        refreshReceiptGrid();
    }

    private void unselectClaimLocks() {
        valueOfReceiptTextField.setValue("");
        valueOfReceiptTextField.setEnabled(false);
        carMillageTextField.setValue("");
        carMillageTextField.setEnabled(false);
        confirmReimbursementButton.setEnabled(false);
        addReceiptButton.setEnabled(false);
        receiptCategoryComboBox.setEnabled(false);
        dateFromPicker.setEnabled(false);
        dateToPicker.setEnabled(false);
        selectedClaim = null;
        receiptListForSelectedClaim = new ArrayList<>();
        refreshReceiptGrid();
    }

    private void refreshReceiptGrid() {
        receiptGrid.setItems(receiptListForSelectedClaim);
    }

    private void refreshClaimGrid() {
        claimGrid.setItems(reimbursementClaimMapper.mapToClaimDtoList(((UserOperationsService)ActiveUserInfo.getUser().getAvailableOperations()).getOwnClaims(ActiveUserInfo.getUser())));
    }

    public UserView() {

        if(ActiveUserInfo.getUser() != null) {
            createUserInfoToolbar();
            createClaimGrid();
            createClaimButtonsToolbar();
            createTripDatesToolbar();
            createReceiptGrid();
            createReceiptsToolbar();
            carMillageTextField = new TextField("car millage");
            add(carMillageTextField);
        } else {
            this.setAlignItems(Alignment.CENTER);
            TextArea area = new TextArea();
            area.setEnabled(false);
            area.setValue("You are not logged in");
            add(area);
        }
    }
}

package vaadin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import dto.DisabledDateDto;
import dto.ReimbursementClaimDto;
import entities.Claim;
import entities.Receipt;
import entities.ReceiptCategory;
import mapper.DateMapper;
import mapper.ReimbursementClaimMapper;
import repository.ActiveUserInfo;
import repository.ReceiptCategoryRepository;
import service.UserOperationsService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route("userView")
public class UserView extends VerticalLayout {
    private final ReimbursementClaimMapper reimbursementClaimMapper = new ReimbursementClaimMapper();
    private final DateMapper dateMapper = new DateMapper();
    private TextField usernameField;
    private TextField userStatusField;
    private TextField spaceHolder;
    private TextField valueOfReceiptTextField;
    private TextField carMillageTextField;
    private Button logoutButton;
    private Button createReimbursementButton;
    private Button confirmReimbursementButton;
    private Button addDisabledDayButton;
    private Button addReceiptButton;
    private ComboBox<ReceiptCategory> receiptCategoryComboBox;
    private DatePicker dateFromPicker;
    private DatePicker dateToPicker;
    private DatePicker disabledDayPicker;
    private Grid<ReimbursementClaimDto> claimGrid;
    private Grid<DisabledDateDto> disabledDaysGrid;
    private Grid<Receipt> receiptGrid;
    private HorizontalLayout userInfoToolbar;
    private HorizontalLayout claimButtonsToolbar;
    private HorizontalLayout tripDatesToolbar;
    private HorizontalLayout receiptsAndMileageToolbar;
    private Claim selectedClaim;
    private List<LocalDate> disabledDaysList = new ArrayList<>();
    private List<Receipt> receiptListForSelectedClaim = new ArrayList<>();

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

    private void createClaimGrid() {
        claimGrid = new Grid<>(ReimbursementClaimDto.class);
        refreshClaimGrid();
        claimGrid.removeColumnByKey("user");
        claimGrid.setHeight(180, Unit.PIXELS);
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
        confirmReimbursementButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        confirmReimbursementButton.setEnabled(false);
        confirmReimbursementButton.addClickListener(clickEvent -> {
            selectedClaim.setTripDateFrom(dateFromPicker.getValue());
            selectedClaim.setTripDateTo(dateToPicker.getValue());
            selectedClaim.setDisabledDays(disabledDaysList);
            selectedClaim.setReceiptList(receiptListForSelectedClaim);
            selectedClaim.setDrivenDistance(Integer.parseInt(carMillageTextField.getValue()));
            ((UserOperationsService)ActiveUserInfo.getUser().getAvailableOperations()).makeReimbursementClaim(ActiveUserInfo.getUser(), selectedClaim);
            refreshClaimGrid();
            unselectClaimLocks();
        });

        claimButtonsToolbar.add(createReimbursementButton, confirmReimbursementButton);
        add(claimButtonsToolbar);
    }

    private void createDisabledDaysGrid() {
        disabledDaysGrid = new Grid<>(DisabledDateDto.class);
        disabledDaysGrid.setHeight(180, Unit.PIXELS);
        refreshDisabledDaysGrid();
        add(disabledDaysGrid);
    }

    private void createTripDatesToolbar() {
        tripDatesToolbar = new HorizontalLayout();

        dateFromPicker = new DatePicker("start of trip");
        dateFromPicker.setEnabled(false);

        dateToPicker = new DatePicker("end of trip");
        dateToPicker.setEnabled(false);

        disabledDayPicker = new DatePicker("day to be disabled");

        addDisabledDayButton = new Button("add disabled day");
        addDisabledDayButton.setEnabled(false);
        addDisabledDayButton.addClickListener(clickEvent -> {
            disabledDaysList.add(disabledDayPicker.getValue());
            refreshDisabledDaysGrid();
        });

        tripDatesToolbar.add(dateFromPicker, dateToPicker, disabledDayPicker, addDisabledDayButton);
        add(tripDatesToolbar);
    }

    private void createReceiptGrid() {
        receiptGrid = new Grid<>(Receipt.class);
        receiptGrid.setHeight(180, Unit.PIXELS);
        refreshReceiptGrid();
        add(receiptGrid);
    }

    private void createReceiptsToolbar() {
        receiptsAndMileageToolbar = new HorizontalLayout();

        receiptCategoryComboBox = new ComboBox<>("receipt type");
        receiptCategoryComboBox.setItems(ReceiptCategoryRepository.getReceiptCategoryList());
        receiptCategoryComboBox.setItemLabelGenerator(category -> category.getCategoryName());
        receiptCategoryComboBox.setEnabled(false);
        valueOfReceiptTextField = new TextField("receipt value");
        valueOfReceiptTextField.setEnabled(false);

        addReceiptButton = new Button("add receipt");
        addReceiptButton.setEnabled(false);
        addReceiptButton.addClickListener(clickEvent -> {
            receiptListForSelectedClaim.add(new Receipt(receiptCategoryComboBox.getValue(), BigDecimal.valueOf(Long.parseLong(valueOfReceiptTextField.getValue()))));
            refreshReceiptGrid();
        });
        spaceHolder = new TextField();
        spaceHolder.setWidth(100, Unit.PIXELS);
        spaceHolder.setVisible(false);

        carMillageTextField = new TextField("car millage");
        receiptsAndMileageToolbar.add(receiptCategoryComboBox, valueOfReceiptTextField, addReceiptButton, spaceHolder, carMillageTextField);
        add(receiptsAndMileageToolbar);
    }

    private void claimSelectedUnlock() {
        valueOfReceiptTextField.setEnabled(true);
        carMillageTextField.setEnabled(true);
        confirmReimbursementButton.setEnabled(true);
        addReceiptButton.setEnabled(true);
        receiptCategoryComboBox.setEnabled(true);
        dateFromPicker.setEnabled(true);
        dateToPicker.setEnabled(true);
        disabledDaysGrid.setEnabled(true);
        addDisabledDayButton.setEnabled(true);
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
        disabledDayPicker.setEnabled(false);
        addDisabledDayButton.setEnabled(false);
        selectedClaim = null;
        disabledDaysList = new ArrayList<>();
        refreshDisabledDaysGrid();
        receiptListForSelectedClaim = new ArrayList<>();
        refreshReceiptGrid();
    }

    private void refreshReceiptGrid() {
        receiptGrid.setItems(receiptListForSelectedClaim);
    }

    private void refreshDisabledDaysGrid() {
        disabledDaysGrid.setItems(dateMapper.mapToDateDtoList(disabledDaysList));
    }

    private void refreshClaimGrid() {
        claimGrid.setItems(reimbursementClaimMapper.mapToClaimDtoList(((UserOperationsService)ActiveUserInfo.getUser().getAvailableOperations()).getOwnClaims(ActiveUserInfo.getUser())));
    }

    public UserView() {

        if(ActiveUserInfo.getUser() != null) {
            createUserInfoToolbar();
            createClaimGrid();
            createClaimButtonsToolbar();
            createDisabledDaysGrid();
            createTripDatesToolbar();
            createReceiptGrid();
            createReceiptsToolbar();
        } else {
            this.setAlignItems(Alignment.CENTER);
            TextArea area = new TextArea();
            area.setEnabled(false);
            area.setValue("You are not logged in");
            add(area);
        }
    }
}

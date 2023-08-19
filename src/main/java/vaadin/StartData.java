package vaadin;

import entities.Receipt;
import entities.Claim;
import entities.ReceiptCategory;
import entities.User;
import enums.UserStatus;
import repository.ClaimRepository;
import repository.ReceiptCategoryRepository;
import repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;

public class StartData {

    private static boolean firstRun = true;
    public static void generateData() {
        //example data
        if(firstRun) {

            ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("taxi", -1));
            ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("hotel", -1));
            ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("ticket", -1));
            ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("other", -1));

            UserRepository.addUser(new User("userLogin", "userPassword", UserStatus.USER));
            UserRepository.addUser(new User("anotherUser", "somePassword", UserStatus.USER));
            UserRepository.addUser(new User("adminLogin", "adminPassword", UserStatus.ADMIN));

            ArrayList<Receipt> receipts = new ArrayList<>();
            receipts.add(new Receipt(ReceiptCategoryRepository.getReceiptCategory("hotel"), 400));
            ClaimRepository.addClaim(new Claim(UserRepository.getUserByLogin("userLogin"),
                    LocalDate.of(2020, 1, 2), LocalDate.of(2020, 1, 5),
                    new ArrayList<>(), receipts, 200));

            ClaimRepository.addClaim(new Claim(UserRepository.getUserByLogin("userLogin"),
                    LocalDate.of(2020, 1, 12), LocalDate.of(2020, 1, 12),
                    new ArrayList<>(), new ArrayList<>(), 120));

            ClaimRepository.addClaim(new Claim(UserRepository.getUserByLogin("anotherUser"),
                    LocalDate.of(2020, 1, 12), LocalDate.of(2020, 1, 18),
                    new ArrayList<>(), new ArrayList<>(), 540));

            firstRun = false;
        }
    }
}

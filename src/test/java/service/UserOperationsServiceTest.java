package service;

import entities.Receipt;
import entities.Claim;
import entities.User;
import enums.ReceiptCategory;
import enums.UserStatus;
import org.junit.jupiter.api.Test;
import repository.ClaimRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserOperationsServiceTest {

    @Test
    public void testMakeReimbursementClaim() {
        //GIVEN
        User user = new User("ExampleUser", "Some password", UserStatus.USER);

        //WHEN
        Claim givenClaim = new Claim(LocalDate.now(), LocalDate.now().plusDays(1), new ArrayList<>(), new ArrayList<>(), 0);
        ((UserOperationsService)user.getAvailableOperations()).makeReimbursementClaim(user, givenClaim);

        long givenId = givenClaim.getClaimId();
        Claim receivedClaim = ClaimRepository.getClaimById(givenId);

        //THEN
        System.out.println("Expected claim of \"" + givenClaim.getUser().getLogin() + "\" with id " + givenClaim.getClaimId() + " - "
                + "Received claim of \"" + givenClaim.getUser().getLogin() + "\" with id " + givenClaim.getClaimId());
        assertEquals(givenClaim, receivedClaim);

        //CLEANUP
        ClaimRepository.deleteAll();
    }

    @Test
    public void testGetOwnClaims() {
        //GIVEN
        User mainUser = new User("ExampleUser", "Some password", UserStatus.USER);
        User anotherUser = new User("AnotherUser", "Some password", UserStatus.USER);

        //WHEN
        Claim givenClaim1 = new Claim(LocalDate.now(), LocalDate.now().plusDays(1), new ArrayList<>(), new ArrayList<>(), 120);
        ((UserOperationsService)mainUser.getAvailableOperations()).makeReimbursementClaim(mainUser, givenClaim1);

        Claim givenClaim2 = new Claim(LocalDate.now(), LocalDate.now().plusDays(3), new ArrayList<>(), new ArrayList<>(), 150);
        ((UserOperationsService)anotherUser.getAvailableOperations()).makeReimbursementClaim(anotherUser, givenClaim2);

        Claim givenClaim3 = new Claim(LocalDate.now(), LocalDate.now().plusDays(2), new ArrayList<>(), new ArrayList<>(), 100);
        ((UserOperationsService)mainUser.getAvailableOperations()).makeReimbursementClaim(mainUser, givenClaim3);

        //THEN
        int expectedAmountOfClaimsTotal = 3;
        int expectedAmountOfUserClaims = 2;
        int receivedAmountOfClaimsTotal = ClaimRepository.getClaimList().size();
        int receivedAmountOfUserClaims = ((UserOperationsService)mainUser.getAvailableOperations()).getOwnClaims(mainUser).size();

        System.out.println("Expected amount of claims in total: " + expectedAmountOfClaimsTotal + " - Received amount of claims in total: " + receivedAmountOfClaimsTotal);
        System.out.println("Expected amount of user claims: " + expectedAmountOfUserClaims + " - Received amount of user claims: " + receivedAmountOfUserClaims);
        assertEquals(expectedAmountOfClaimsTotal, receivedAmountOfClaimsTotal);
        assertEquals(expectedAmountOfUserClaims, receivedAmountOfUserClaims);

        //CLEANUP
        ClaimRepository.deleteAll();
    }

    @Test
    public void testUpdateOwnReimbursementClaim() {
        //GIVEN
        User user = new User("ExampleUser", "Some password", UserStatus.USER);

        Claim givenClaim = new Claim(LocalDate.now(), LocalDate.now().plusDays(1), new ArrayList<>(), new ArrayList<>(), 0);
        ((UserOperationsService)user.getAvailableOperations()).makeReimbursementClaim(user, givenClaim);

        long claimId = givenClaim.getClaimId();
        String expectedClaimBeforeUpdate = givenClaim.toString();
        String receivedClaimBeforeUpdate = ClaimRepository.getClaimById(claimId).toString();

        //WHEN
        Receipt receipt = new Receipt(ReceiptCategory.HOTEL, BigDecimal.valueOf(100));

        LocalDate disabledDay = LocalDate.now();

        Claim modifiedClaim = new Claim(givenClaim.getClaimId(), givenClaim.getTripDateFrom(), givenClaim.getTripDateTo(), givenClaim.getDisabledDays(), givenClaim.getReceiptsList(), 150);
        modifiedClaim.getDisabledDays().add(disabledDay);
        modifiedClaim.getReceiptsList().add(receipt);

        ((UserOperationsService)user.getAvailableOperations()).updateOwnReimbursementClaim(user, givenClaim, modifiedClaim);

        //THEN
        String expectedClaimAfterUpdate = modifiedClaim.toString();
        String receivedClaimAfterUpdate = ClaimRepository.getClaimById(claimId).toString();

        System.out.println("Expected claim values before update: " + expectedClaimBeforeUpdate + "\nReceived claims values before update: " + receivedClaimBeforeUpdate);
        System.out.println("Expected claim values after update: " + expectedClaimAfterUpdate + "\nReceived claims values after update: " + receivedClaimAfterUpdate);
        assertEquals(expectedClaimBeforeUpdate, receivedClaimBeforeUpdate);
        assertEquals(expectedClaimAfterUpdate, receivedClaimAfterUpdate);

        //CLEANUP
        ClaimRepository.deleteAll();

    }

    @Test
    public void testRemoveOwnReimbursementClaim() {
        //GIVEN
        User user = new User("ExampleUser", "Some password", UserStatus.USER);

        //WHEN
        Claim givenClaim = new Claim(LocalDate.now(), LocalDate.now().plusDays(1), new ArrayList<>(), new ArrayList<>(), 0);
        ((UserOperationsService)user.getAvailableOperations()).makeReimbursementClaim(user, givenClaim);

        int receivedAmountOfClaimsBeforeErase = ClaimRepository.getClaimList().size();

        ((UserOperationsService)user.getAvailableOperations()).removeOwnReimbursementClaim(user, givenClaim);

        //THEN
        int expectedAmountOfClaimsBeforeErase = 1;
        int expectedAmountOfClaimsAfterErase = 0;

        int receivedAmountOfClaimsAfterErase = ClaimRepository.getClaimList().size();
        System.out.println("Expected claims before removing: " + expectedAmountOfClaimsBeforeErase + " - Received claims before removing: " + receivedAmountOfClaimsBeforeErase);
        System.out.println("Expected claims before removing: " + expectedAmountOfClaimsAfterErase + " - Received claims before removing: " + receivedAmountOfClaimsAfterErase);
        assertEquals(expectedAmountOfClaimsBeforeErase, receivedAmountOfClaimsBeforeErase);
        assertEquals(expectedAmountOfClaimsAfterErase, receivedAmountOfClaimsAfterErase);

        //CLEANUP
        ClaimRepository.deleteAll();
    }
}

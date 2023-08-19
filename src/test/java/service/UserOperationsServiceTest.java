package service;

import entities.Claim;
import entities.User;
import enums.UserStatus;
import org.junit.jupiter.api.Test;
import repository.ClaimRepository;

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
        user.getAvailableOperations().makeReimbursementClaim(user, givenClaim);

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
        mainUser.getAvailableOperations().makeReimbursementClaim(mainUser, givenClaim1);

        Claim givenClaim2 = new Claim(LocalDate.now(), LocalDate.now().plusDays(3), new ArrayList<>(), new ArrayList<>(), 150);
        anotherUser.getAvailableOperations().makeReimbursementClaim(anotherUser, givenClaim2);

        Claim givenClaim3 = new Claim(LocalDate.now(), LocalDate.now().plusDays(2), new ArrayList<>(), new ArrayList<>(), 100);
        mainUser.getAvailableOperations().makeReimbursementClaim(mainUser, givenClaim3);

        //THEN
        int expectedAmountOfClaimsTotal = 3;
        int expectedAmountOfUserClaims = 2;
        int receivedAmountOfClaimsTotal = ClaimRepository.getClaimList().size();
        int receivedAmountOfUserClaims = mainUser.getAvailableOperations().getOwnClaims(mainUser).size();

        System.out.println("Expected amount of claims in total: " + expectedAmountOfClaimsTotal + " - Received amount of claims in total: " + receivedAmountOfClaimsTotal);
        System.out.println("Expected amount of user claims: " + expectedAmountOfUserClaims + " - Received amount of user claims: " + receivedAmountOfUserClaims);
        assertEquals(expectedAmountOfClaimsTotal, receivedAmountOfClaimsTotal);
        assertEquals(expectedAmountOfUserClaims, receivedAmountOfUserClaims);

        //CLEANUP
        ClaimRepository.deleteAll();
    }
}

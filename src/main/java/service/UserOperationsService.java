package service;

import entities.Claim;
import entities.ReceiptCategory;
import entities.User;
import repository.ClaimRepository;

import java.util.List;

public class UserOperationsService implements AvailableOperations {

    public void makeReimbursementClaim(User user, Claim claim) {
        claim.setUser(user);
        claim.setClaimId();
        claim.setExpectedReimbursement(CalculationService.getExpectedTotal(claim));
        ClaimRepository.addClaim(claim);
    }

    public List<Claim> getOwnClaims(User user) {
        return ClaimRepository.getClaimListByUser(user);
    }


    /* EMPTY IMPLEMENTATIONS OF OPERATIONS USED BY ADMINISTRATOR ONLY  */

    @Override
    public void setDailyAllowanceValue(double dailyAllowanceValue) {

    }

    @Override
    public void setCarMileageValue(double carMileageValue) {

    }

    @Override
    public void setTotalReimbursementLimit(double totalReimbursementLimit) {

    }

    @Override
    public void setMileageLimit(double mileageLimit) {

    }

    @Override
    public void setReceiptCategory(ReceiptCategory newReceiptCategory) {

    }

    @Override
    public void removeReceiptCategory(ReceiptCategory receiptCategoryToDelete) {

    }

    /* ==============================================================  */


    /* METHODS WHICH WERE NOT INTRODUCED TO UI YET ============================================

    public void updateOwnReimbursementClaim(User user, Claim originalClaim, Claim modifiedClaim) {
        if(originalClaim.getUser().equals(user)) {
            modifiedClaim.setUser(user);
            ClaimRepository.updateClaim(originalClaim.getClaimId(), modifiedClaim);
        }
    }

    public void removeOwnReimbursementClaim(User user, Claim claim) {
        if(claim.getUser().equals(user)) {
            ClaimRepository.getClaimList().remove(claim);
        }
    }

    ======================================================================================== */
}

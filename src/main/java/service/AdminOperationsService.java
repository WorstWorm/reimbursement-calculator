package service;

import entities.Claim;
import entities.ReceiptCategory;
import entities.User;
import repository.ReceiptCategoryRepository;
import repository.ReimbursementValues;

import java.util.List;

public class AdminOperationsService implements AvailableOperations {

    public void setDailyAllowanceValue(double dailyAllowanceValue) {
        ReimbursementValues.setDailyAllowanceValue(dailyAllowanceValue);
    }

    public void setCarMileageValue(double carMileageValue) {
        ReimbursementValues.setCarMileageValue(carMileageValue);
    }

    public void setTotalReimbursementLimit(double totalReimbursementLimit) {
        ReimbursementValues.setTotalReimbursementLimit(totalReimbursementLimit);
    }

    public void setMileageLimit(double mileageLimit) {
        ReimbursementValues.setMileageLimit(mileageLimit);
    }

    public void setReceiptCategory(ReceiptCategory newReceiptCategory) {
        for(ReceiptCategory existingReceiptCategories : ReceiptCategoryRepository.getReceiptCategoryList()) {
            if(existingReceiptCategories.getCategoryName().equals(newReceiptCategory.getCategoryName())) {
                ReceiptCategoryRepository.removeReceiptCategory(existingReceiptCategories);
                break;
            }
        }
        ReceiptCategoryRepository.addReceiptCategory(newReceiptCategory);
    }

    public void removeReceiptCategory(ReceiptCategory receiptCategoryToDelete) {
        ReceiptCategoryRepository.removeReceiptCategory(receiptCategoryToDelete);
    }


    /* EMPTY IMPLEMENTATIONS OF OPERATIONS USED FOR USER ONLY */

    @Override
    public void makeReimbursementClaim(User user, Claim claim) {
    }

    @Override
    public List<Claim> getOwnClaims(User user) {
        return null;
    }

    /* ======================================================== */



    /* METHODS WHICH WERE NOT INTRODUCED TO UI YET ============================================

    public List<User> getAllUsers() {
        return UserRepository.getUserList();
    }

    public void updateUserPassword(User user, String password) {
        UserRepository.updateUserPassword(user, password);
    }

    public void addUser(User user) {
        UserRepository.addUser(user);
    }

    public User getUserByLogin(String login) {
        return UserRepository.getUserByLogin(login);
    }

    public List<Claim> getAllClaims() {
        return ClaimRepository.getClaimList();
    }

    public void updateReimbursementClaim(long originalClaimId, Claim modifiedClaim) {
        ClaimRepository.updateClaim(originalClaimId, modifiedClaim);
    }

    public void removeReimbursementClaim(Claim claim) {
        ClaimRepository.getClaimList().remove(claim);
    }

     ======================================================================================== */
}

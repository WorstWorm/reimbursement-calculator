package service;

import entities.Claim;
import entities.ReceiptCategory;
import entities.User;

import java.util.List;

public interface AvailableOperations {
    void makeReimbursementClaim(User user, Claim claim);
    List<Claim> getOwnClaims(User user);
    void setDailyAllowanceValue(double dailyAllowanceValue);
    void setCarMileageValue(double carMileageValue);
    void setTotalReimbursementLimit(double totalReimbursementLimit);
    void setMileageLimit(double mileageLimit);
    void setReceiptCategory(ReceiptCategory newReceiptCategory);
    void removeReceiptCategory(ReceiptCategory receiptCategoryToDelete);

    /* METHODS WHICH WERE NOT INTRODUCED TO UI YET ============================================

    public void updateOwnReimbursementClaim(User user, Claim originalClaim, Claim modifiedClaim);
    public void removeOwnReimbursementClaim(User user, Claim claim);
    public List<User> getAllUsers();
    public void updateUserPassword(User user, String password);
    public void addUser(User user);
    public User getUserByLogin(String login);
    public List<Claim> getAllClaims();
    public void updateReimbursementClaim(long originalClaimId, Claim modifiedClaim);
    public void removeReimbursementClaim(Claim claim);

    ======================================================================================== */
}

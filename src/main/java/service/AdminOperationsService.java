package service;

import config.ReimbursementValues;
import entities.ReimbursementClaim;
import entities.User;
import repository.ClaimRepository;
import repository.UserRepository;

import java.util.List;

public class AdminOperationsService {
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

    public void setTaxiReceiptLimit(double taxiReceiptLimit) {
        ReimbursementValues.setTaxiReceiptLimit(taxiReceiptLimit);
    }

    public void setHotelReceiptLimit(double hotelReceiptLimit) {
        ReimbursementValues.setHotelReceiptLimit(hotelReceiptLimit);
    }

    public void setTicketReceiptLimit(double ticketReceiptLimit) {
        ReimbursementValues.setTicketReceiptLimit(ticketReceiptLimit);
    }

    public void setOtherReceiptLimit(double otherReceiptLimit) {
        ReimbursementValues.setOtherReceiptLimit(otherReceiptLimit);
    }

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

    public List<ReimbursementClaim> getAllClaims() {
        return ClaimRepository.getClaimList();
    }

    public void updateReimbursementClaim(ReimbursementClaim originalClaim, ReimbursementClaim modifiedClaim) {
        ClaimRepository.updateClaim(originalClaim, modifiedClaim);
    }

    public void removeReimbursementClaim(ReimbursementClaim claim) {
        ClaimRepository.getClaimList().remove(claim);
    }
}

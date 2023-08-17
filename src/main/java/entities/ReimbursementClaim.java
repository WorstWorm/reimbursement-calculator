package entities;

import service.CalculationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReimbursementClaim {

    private User user;
    private LocalDate tripDateFrom;
    private LocalDate tripDateTo;
    private List<LocalDate> disabledDays;
    private List<Receipt> receiptList;
    private int drivenDistance;
    private BigDecimal expectedReimbursement;
    private BigDecimal confirmedReimbursement;

    public ReimbursementClaim(User user, LocalDate tripDateFrom, LocalDate tripDateTo, List<LocalDate> disabledDays, List<Receipt> receiptList, Integer drivenDistance) {
        this.user = user;
        this.tripDateFrom = tripDateFrom;
        this.tripDateTo = tripDateTo;
        this.disabledDays = disabledDays;
        this.receiptList = receiptList;
        this.drivenDistance = drivenDistance;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getTripDateFrom() {
        return tripDateFrom;
    }

    public LocalDate getTripDateTo() {
        return tripDateTo;
    }

    public List<LocalDate> getDisabledDays() {
        return disabledDays;
    }

    public List<Receipt> getReceiptsList() {
        return receiptList;
    }

    public int getDrivenDistance() {
        return drivenDistance;
    }

    public BigDecimal getExpectedReimbursement() {
        return expectedReimbursement;
    }

    public BigDecimal getConfirmedReimbursement() {
        return confirmedReimbursement;
    }

    public void setReceiptsList(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

    public void setExpectedReimbursement() {
        this.expectedReimbursement = CalculationService.getExpectedTotal(this);
    }

    public void setConfirmedReimbursement(BigDecimal confirmedReimbursement) {
        this.confirmedReimbursement = confirmedReimbursement;
    }
}

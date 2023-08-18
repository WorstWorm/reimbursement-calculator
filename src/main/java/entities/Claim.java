package entities;

import service.CalculationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Claim {

    private static long claimIdCounter = 0;
    private long claimId;
    private User user;
    private LocalDate tripDateFrom;
    private LocalDate tripDateTo;
    private List<LocalDate> disabledDays;
    private List<Receipt> receiptList;
    private int drivenDistance;
    private BigDecimal expectedReimbursement;
    private BigDecimal confirmedReimbursement;

    public Claim() {
    }

    public Claim(LocalDate tripDateFrom, LocalDate tripDateTo, List<LocalDate> disabledDays, List<Receipt> receiptList, Integer drivenDistance) {
        this.claimId = ++claimIdCounter;
        this.tripDateFrom = tripDateFrom;
        this.tripDateTo = tripDateTo;
        this.disabledDays = disabledDays;
        this.receiptList = receiptList;
        this.drivenDistance = drivenDistance;
        this.expectedReimbursement = CalculationService.getExpectedTotal(this);
    }

    public Claim(User user, LocalDate tripDateFrom, LocalDate tripDateTo, List<LocalDate> disabledDays, List<Receipt> receiptList, Integer drivenDistance) {
        this.claimId = ++claimIdCounter;
        this.user = user;
        this.tripDateFrom = tripDateFrom;
        this.tripDateTo = tripDateTo;
        this.disabledDays = disabledDays;
        this.receiptList = receiptList;
        this.drivenDistance = drivenDistance;
        this.expectedReimbursement = CalculationService.getExpectedTotal(this);
    }

    public Claim(Long claimId, LocalDate tripDateFrom, LocalDate tripDateTo, List<LocalDate> disabledDays, List<Receipt> receiptList, Integer drivenDistance) {
        this.claimId = claimId;
        this.tripDateFrom = tripDateFrom;
        this.tripDateTo = tripDateTo;
        this.disabledDays = disabledDays;
        this.receiptList = receiptList;
        this.drivenDistance = drivenDistance;
        this.expectedReimbursement = CalculationService.getExpectedTotal(this);
    }

    public long getClaimId() {
        return claimId;
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

    public void setClaimId() {
        this.claimId = ++claimIdCounter;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setConfirmedReimbursement(BigDecimal confirmedReimbursement) {
        this.confirmedReimbursement = confirmedReimbursement;
    }

    public void setReceiptList(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

    public void setDrivenDistance(int drivenDistance) {
        this.drivenDistance = drivenDistance;
    }

    public void setTripDateFrom(LocalDate tripDateFrom) {
        this.tripDateFrom = tripDateFrom;
    }

    public void setTripDateTo(LocalDate tripDateTo) {
        this.tripDateTo = tripDateTo;
    }

    public void setDisabledDays(List<LocalDate> disabledDays) {
        this.disabledDays = disabledDays;
    }

    public void setExpectedReimbursement(BigDecimal expectedReimbursement) {
        this.expectedReimbursement = expectedReimbursement;
    }

    @Override
    public String toString() {
        return "ReimbursementClaim{" +
                "claimId=" + claimId +
                " user=" + user +
                ", tripDateFrom=" + tripDateFrom +
                ", tripDateTo=" + tripDateTo +
                ", disabledDays=" + disabledDays +
                ", receiptList=" + receiptList +
                ", drivenDistance=" + drivenDistance +
                '}';
    }
}

package entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReimbursementClaim {

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

    public ReimbursementClaim(LocalDate tripDateFrom, LocalDate tripDateTo, List<LocalDate> disabledDays, List<Receipt> receiptList, Integer drivenDistance) {
        this.claimId = ++claimIdCounter;
        this.tripDateFrom = tripDateFrom;
        this.tripDateTo = tripDateTo;
        this.disabledDays = disabledDays;
        this.receiptList = receiptList;
        this.drivenDistance = drivenDistance;
    }

    public ReimbursementClaim(User user, LocalDate tripDateFrom, LocalDate tripDateTo, List<LocalDate> disabledDays, List<Receipt> receiptList, Integer drivenDistance) {
        this.claimId = ++claimIdCounter;
        this.user = user;
        this.tripDateFrom = tripDateFrom;
        this.tripDateTo = tripDateTo;
        this.disabledDays = disabledDays;
        this.receiptList = receiptList;
        this.drivenDistance = drivenDistance;
    }

    public ReimbursementClaim(Long claimId, LocalDate tripDateFrom, LocalDate tripDateTo, List<LocalDate> disabledDays, List<Receipt> receiptList, Integer drivenDistance) {
        this.claimId = claimId;
        this.tripDateFrom = tripDateFrom;
        this.tripDateTo = tripDateTo;
        this.disabledDays = disabledDays;
        this.receiptList = receiptList;
        this.drivenDistance = drivenDistance;
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
                ", expectedReimbursement=" + expectedReimbursement +
                ", confirmedReimbursement=" + confirmedReimbursement +
                '}';
    }
}

package dto;

import entities.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReimbursementClaimDto {
    private long claimId;
    private User user;
    private LocalDate tripDateFrom;
    private LocalDate tripDateTo;
    private String disabledDays;
    private int receiptAmount;
    private int drivenDistance;
    private BigDecimal expectedReimbursement;

    public ReimbursementClaimDto(long claimId, User user, LocalDate tripDateFrom, LocalDate tripDateTo, String disabledDays, int receiptAmount, int drivenDistance, BigDecimal expectedReimbursement) {
        this.claimId = claimId;
        this.user = user;
        this.tripDateFrom = tripDateFrom;
        this.tripDateTo = tripDateTo;
        this.disabledDays = disabledDays;
        this.receiptAmount = receiptAmount;
        this.drivenDistance = drivenDistance;
        this.expectedReimbursement = expectedReimbursement;
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

    public String getDisabledDays() {
        return disabledDays;
    }

    public int getReceiptAmount() {
        return receiptAmount;
    }

    public int getDrivenDistance() {
        return drivenDistance;
    }

    public BigDecimal getExpectedReimbursement() {
        return expectedReimbursement;
    }
}

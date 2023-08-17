package repository;

import entities.ReimbursementClaim;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class ClaimRepository {

    private static final List<ReimbursementClaim> claimList = new ArrayList<>();

    public static List<ReimbursementClaim> getClaimList() {
        return claimList;
    }

    public static List<ReimbursementClaim> getClaimListByUser(User user) {
        List<ReimbursementClaim> claimsToBeReturned = new ArrayList<>();
        for(ReimbursementClaim c : claimList) {
            if(c.getUser().equals(user)) {
                claimsToBeReturned.add(c);
            }
        }
        return claimsToBeReturned;
    }

    public static ReimbursementClaim getClaimById(long claimId) {
        for(ReimbursementClaim c : claimList) {
            if(c.getClaimId() == claimId) {
                return c;
            }
        }
        return null;
    }

    public static void addClaim(ReimbursementClaim claim) {
        claimList.add(claim);
    }

    public static void updateClaim(long idOfOrginalClaim, ReimbursementClaim modifiedClaim) {
        for(ReimbursementClaim c : claimList) {
            if(c.getClaimId()==idOfOrginalClaim) {
                c.setReceiptList(modifiedClaim.getReceiptsList());
                c.setConfirmedReimbursement(modifiedClaim.getConfirmedReimbursement());
                c.setDrivenDistance(modifiedClaim.getDrivenDistance());
            }
        }
    }

    public static void deleteClaim(ReimbursementClaim claim) {
        claimList.remove(claim);
    }

    public static void deleteAll() {
        claimList.clear();
    }
}

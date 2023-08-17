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
            if(c.getUser().equals(user)){
                claimsToBeReturned.add(c);
            }
        }
        return claimsToBeReturned;
    }

    public static void addClaim(ReimbursementClaim claim) {
        claimList.add(claim);
    }

    public static void updateClaim(ReimbursementClaim originalClaim, ReimbursementClaim modifiedClaim) {
        claimList.remove(originalClaim);
        claimList.add(modifiedClaim);
    }
}

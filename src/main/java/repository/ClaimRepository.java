package repository;

import entities.Claim;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class ClaimRepository {

    private static final List<Claim> claimList = new ArrayList<>();

    public static List<Claim> getClaimList() {
        return claimList;
    }

    public static List<Claim> getClaimListByUser(User user) {
        List<Claim> claimsToBeReturned = new ArrayList<>();
        for(Claim c : claimList) {
            if(c.getUser().equals(user)) {
                claimsToBeReturned.add(c);
            }
        }
        return claimsToBeReturned;
    }

    public static Claim getClaimById(long claimId) {
        for(Claim c : claimList) {
            if(c.getClaimId() == claimId) {
                return c;
            }
        }
        return null;
    }

    public static void addClaim(Claim claim) {
        claimList.add(claim);
    }

    public static void updateClaim(long idOfOrginalClaim, Claim modifiedClaim) {
        for(Claim c : claimList) {
            if(c.getClaimId()==idOfOrginalClaim) {
                c.setReceiptList(modifiedClaim.getReceiptsList());
                c.setDrivenDistance(modifiedClaim.getDrivenDistance());
            }
        }
    }

    public static void deleteClaim(Claim claim) {
        claimList.remove(claim);
    }

    public static void deleteAll() {
        claimList.clear();
    }
}

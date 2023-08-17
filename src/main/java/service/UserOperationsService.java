package service;

import entities.ReimbursementClaim;
import entities.User;
import repository.ClaimRepository;

import java.util.List;

public class UserOperationsService {

    public void makeReimbursementClaim(ReimbursementClaim claim) {
        ClaimRepository.addClaim(claim);
    }

    public List<ReimbursementClaim> getOwnClaims(User user) {
        return ClaimRepository.getClaimListByUser(user);
    }

    public void updateOwnReimbursementClaim(User user, ReimbursementClaim originalClaim, ReimbursementClaim modifiedClaim) {
        if(originalClaim.getUser().equals(user)) {
            ClaimRepository.updateClaim(originalClaim, modifiedClaim);
        }
    }

    public void removeOwnReimbursementClaim(User user, ReimbursementClaim claim) {
        if(claim.getUser().equals(user)) {
            ClaimRepository.getClaimList().remove(claim);
        }
    }
}

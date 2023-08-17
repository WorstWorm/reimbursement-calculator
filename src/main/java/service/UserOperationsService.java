package service;

import entities.ReimbursementClaim;
import entities.User;
import repository.ClaimRepository;

import java.util.List;

public class UserOperationsService implements AvailableOperations {

    public void makeReimbursementClaim(User user, ReimbursementClaim claim) {
        claim.setUser(user);
        ClaimRepository.addClaim(claim);
    }

    public List<ReimbursementClaim> getOwnClaims(User user) {
        return ClaimRepository.getClaimListByUser(user);
    }

    public void updateOwnReimbursementClaim(User user, ReimbursementClaim originalClaim, ReimbursementClaim modifiedClaim) {
        if(originalClaim.getUser().equals(user)) {
            modifiedClaim.setUser(user);
            ClaimRepository.updateClaim(originalClaim.getClaimId(), modifiedClaim);
        }
    }

    public void removeOwnReimbursementClaim(User user, ReimbursementClaim claim) {
        if(claim.getUser().equals(user)) {
            ClaimRepository.getClaimList().remove(claim);
        }
    }
}

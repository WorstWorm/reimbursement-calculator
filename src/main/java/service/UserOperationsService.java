package service;

import entities.Claim;
import entities.User;
import repository.ClaimRepository;

import java.util.List;

public class UserOperationsService implements AvailableOperations {

    public void makeReimbursementClaim(User user, Claim claim) {
        claim.setUser(user);
        claim.setClaimId();
        claim.setExpectedReimbursement(CalculationService.getExpectedTotal(claim));
        ClaimRepository.addClaim(claim);
    }

    public List<Claim> getOwnClaims(User user) {
        return ClaimRepository.getClaimListByUser(user);
    }

    public void updateOwnReimbursementClaim(User user, Claim originalClaim, Claim modifiedClaim) {
        if(originalClaim.getUser().equals(user)) {
            modifiedClaim.setUser(user);
            ClaimRepository.updateClaim(originalClaim.getClaimId(), modifiedClaim);
        }
    }

    public void removeOwnReimbursementClaim(User user, Claim claim) {
        if(claim.getUser().equals(user)) {
            ClaimRepository.getClaimList().remove(claim);
        }
    }
}

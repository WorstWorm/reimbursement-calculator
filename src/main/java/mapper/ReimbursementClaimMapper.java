package mapper;

import dto.ReimbursementClaimDto;
import entities.Claim;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReimbursementClaimMapper {
    public ReimbursementClaimDto mapToClaimDto(Claim claim) {
        String disabledDays = "";
        for(LocalDate date : claim.getDisabledDays()) {
            disabledDays += date + "; ";
        }
        return new ReimbursementClaimDto(
                claim.getClaimId(),
                claim.getUser(),
                claim.getTripDateFrom(),
                claim.getTripDateTo(),
                disabledDays,
                claim.getReceiptsList().size(),
                claim.getDrivenDistance(),
                claim.getExpectedReimbursement(),
                claim.getConfirmedReimbursement()
        );
    }

    public List<ReimbursementClaimDto> mapToClaimDtoList(final List<Claim> reimbursementsClaims) {
        return reimbursementsClaims.stream().map(this::mapToClaimDto).collect(Collectors.toList());
    }
}

package service;

import repository.ReimbursementValues;
import entities.Receipt;
import entities.Claim;
import entities.ReceiptCategory;
import repository.ReceiptCategoryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculationService {

    public static BigDecimal getDailyAllowanceSum(Claim claim) {

        int amountOfDays = (int) claim.getTripDateFrom().until(claim.getTripDateTo(), ChronoUnit.DAYS) + 1;

        for(LocalDate date : claim.getDisabledDays()) {

            if(date.isAfter(claim.getTripDateFrom().plusDays(-1)) && date.isBefore(claim.getTripDateTo().plusDays(1))) {
                amountOfDays--;
            }
        }

        return ReimbursementValues.getDailyAllowanceValue().multiply(BigDecimal.valueOf(amountOfDays));
    }

    public static BigDecimal getMillageSum(Claim claim) {

        BigDecimal mileageAllowanceSum = ReimbursementValues.getCarMileageValue().multiply(BigDecimal.valueOf(claim.getDrivenDistance()));

        if(ReimbursementValues.getMileageLimit().compareTo(BigDecimal.valueOf(0)) > 0) {

            if (mileageAllowanceSum.compareTo(ReimbursementValues.getMileageLimit()) > 0) {
                mileageAllowanceSum = ReimbursementValues.getMileageLimit();
            }
        }

        return mileageAllowanceSum;
    }

    public static BigDecimal getReceiptSum(Claim claim) {

        BigDecimal sum = BigDecimal.valueOf(0.0);

        List<Receipt> receipts = claim.getReceiptsList();

        for(ReceiptCategory receiptCategory : ReceiptCategoryRepository.getReceiptCategoryList()) {
            BigDecimal sumForCategory = BigDecimal.valueOf(0);

            for (Receipt receipt : receipts) {
                if(receipt.getReceiptCategory().equals(receiptCategory)) {
                    sumForCategory = sumForCategory.add(receipt.getReceiptSum());
                }
            }

            if(receiptCategory.getReceiptCategoryLimit().compareTo(BigDecimal.valueOf(0)) > 0) {
                if (sumForCategory.compareTo(receiptCategory.getReceiptCategoryLimit()) > 0) {
                    sumForCategory = receiptCategory.getReceiptCategoryLimit();
                }
            }

            sum = sum.add(sumForCategory);
        }

        return sum;
    }

    public static BigDecimal getExpectedTotal(Claim claim) {
        BigDecimal sum = BigDecimal.valueOf(0.0);

        sum = sum.add(getReceiptSum(claim));
        sum = sum.add(getDailyAllowanceSum(claim));
        sum = sum.add(getMillageSum(claim));

        if(ReimbursementValues.getTotalReimbursementLimit().compareTo(BigDecimal.valueOf(0)) > 0) {
            if (ReimbursementValues.getTotalReimbursementLimit().compareTo(ReimbursementValues.getMileageLimit()) > 0) {
                sum = ReimbursementValues.getTotalReimbursementLimit();
            }
        }

        return sum;
    }
}

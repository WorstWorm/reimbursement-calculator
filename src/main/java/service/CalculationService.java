package service;

import config.ReimbursementValues;
import entities.Receipt;
import entities.Claim;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalculationService {

    public static BigDecimal getDailyAllowanceSum(Claim claim) {
        int amountOfDays = (int) claim.getTripDateFrom().until(claim.getTripDateTo(), ChronoUnit.DAYS) + 1;
        amountOfDays = amountOfDays - claim.getDisabledDays().size();
        return ReimbursementValues.getDailyAllowanceValue().multiply(BigDecimal.valueOf(amountOfDays));

    }

    public static BigDecimal getMillageSum(Claim claim) {
        BigDecimal mileageAllowenceSum = ReimbursementValues.getCarMileageValue().multiply(BigDecimal.valueOf(claim.getDrivenDistance()));
        if(!Objects.equals(ReimbursementValues.getMileageLimit(), BigDecimal.valueOf(-1.0))) {
            if (mileageAllowenceSum.compareTo(ReimbursementValues.getMileageLimit()) > 0) {
                mileageAllowenceSum = ReimbursementValues.getMileageLimit();
            }
        }
        return mileageAllowenceSum;
    }

    public static BigDecimal getReceiptSum(Claim claim) {

        List<Receipt> taxiReceipts = new ArrayList<>();
        List<Receipt> hotelReceipts = new ArrayList<>();
        List<Receipt> ticketReceipts = new ArrayList<>();
        List<Receipt> otherReceipts = new ArrayList<>();

        for(Receipt r : claim.getReceiptsList()) {
            switch (r.getReceiptCategory()) {
                case TAXI:
                    taxiReceipts.add(r);
                    break;
                case HOTEL:
                    hotelReceipts.add(r);
                    break;
                case TICKET:
                    ticketReceipts.add(r);
                    break;
                case OTHER:
                    otherReceipts.add(r);
            }
        }

        BigDecimal taxiReceiptsSum = BigDecimal.valueOf(0.0);
        for(Receipt r : taxiReceipts) {
            taxiReceiptsSum = taxiReceiptsSum.add(r.getReceiptSum());
        }
        if(!Objects.equals(ReimbursementValues.getTaxiReceiptLimit(), BigDecimal.valueOf(-1.0))){
            if (taxiReceiptsSum.compareTo(ReimbursementValues.getTaxiReceiptLimit()) > 0) {
                taxiReceiptsSum = ReimbursementValues.getTaxiReceiptLimit();
            }
        }

        BigDecimal hotelReceiptsSum = BigDecimal.valueOf(0.0);
        for(Receipt r : hotelReceipts) {
            hotelReceiptsSum = hotelReceiptsSum.add(r.getReceiptSum());
        }
        if(!Objects.equals(ReimbursementValues.getHotelReceiptLimit(), BigDecimal.valueOf(-1.0))) {
            if (hotelReceiptsSum.compareTo(ReimbursementValues.getHotelReceiptLimit()) > 0) {
                hotelReceiptsSum = ReimbursementValues.getHotelReceiptLimit();
            }
        }

        BigDecimal ticketReceiptsSum = BigDecimal.valueOf(0.0);
        for(Receipt r : ticketReceipts) {
            ticketReceiptsSum = ticketReceiptsSum.add(r.getReceiptSum());
        }
        if(!Objects.equals(ReimbursementValues.getTicketReceiptLimit(), BigDecimal.valueOf(-1.0))) {
            if(ticketReceiptsSum.compareTo(ReimbursementValues.getTicketReceiptLimit()) > 0) {
                ticketReceiptsSum = ReimbursementValues.getTicketReceiptLimit();
            }
        }

        BigDecimal otherReceiptsSum = BigDecimal.valueOf(0.0);
        for(Receipt r : otherReceipts) {
            otherReceiptsSum = otherReceiptsSum.add(r.getReceiptSum());
        }
        if(!Objects.equals(ReimbursementValues.getOtherReceiptLimit(), BigDecimal.valueOf(-1.0))) {
            if (otherReceiptsSum.compareTo(ReimbursementValues.getOtherReceiptLimit()) > 0) {
                otherReceiptsSum = ReimbursementValues.getOtherReceiptLimit();
            }
        }

        BigDecimal sum = BigDecimal.valueOf(0.0);

        sum = sum.add(taxiReceiptsSum);
        sum = sum.add(hotelReceiptsSum);
        sum = sum.add(ticketReceiptsSum);
        sum = sum.add(otherReceiptsSum);

        return sum;
    }

    public static BigDecimal getExpectedTotal(Claim claim) {
        BigDecimal sum = BigDecimal.valueOf(0.0);
        sum = sum.add(getReceiptSum(claim));
        sum = sum.add(getDailyAllowanceSum(claim));
        sum = sum.add(getMillageSum(claim));
        if(!Objects.equals(ReimbursementValues.getTotalReimbursementLimit(), BigDecimal.valueOf(-1.0))) {
            if (sum.compareTo(ReimbursementValues.getTotalReimbursementLimit()) > 0) {
                sum = ReimbursementValues.getTotalReimbursementLimit();
            }
        }
        return sum;
    }
}

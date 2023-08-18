package service;

import config.ReimbursementValues;
import entities.Receipt;
import entities.Claim;
import entities.User;
import enums.ReceiptCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class CalculationServiceTest {

    @Test
    void testGetDailyAllowanceSum() {
        //GIVEN
        LocalDate tripDateFrom = LocalDate.of(2023, 8, 1);
        LocalDate tripDateTo = LocalDate.of(2023, 8, 5);
        List<LocalDate> disabledDays = Collections.emptyList();

        List<Receipt> receiptList = Collections.emptyList();

        int drivenDistance = 0;

        Claim givenClaim = new Claim(new User(), tripDateFrom, tripDateTo, disabledDays, receiptList, drivenDistance);

        //WHEN
        BigDecimal expectedSum = BigDecimal.valueOf(5 * ReimbursementValues.getDailyAllowanceValue().doubleValue());
        BigDecimal receivedSum = CalculationService.getDailyAllowanceSum(givenClaim);

        //THEN
        System.out.println("Expected: " + expectedSum + " - Received: " + receivedSum);
        assertEquals(expectedSum, receivedSum);
    }

    @Test
    void testGetMillageSum() {
        //GIVEN
        int drivenDistance = 150;

        LocalDate tripDateFrom = LocalDate.now();
        LocalDate tripDateTo = tripDateFrom.plusDays(1);
        List<LocalDate> disabledDays = Collections.emptyList();

        List<Receipt> receiptList = Collections.emptyList();

        Claim givenClaim = new Claim(new User(), tripDateFrom, tripDateTo, disabledDays, receiptList, drivenDistance);

        //WHEN
        BigDecimal expectedSum = ReimbursementValues.getCarMileageValue().multiply(BigDecimal.valueOf(drivenDistance));
        BigDecimal receivedSum = CalculationService.getMillageSum(givenClaim);

        //THEN
        System.out.println("Expected: " + expectedSum + " - Received: " + receivedSum);
        assertEquals(expectedSum, receivedSum);
    }

    @Test
    void testGetMillageSumWithLimit() {
        //GIVEN

        ReimbursementValues.setMileageLimit(40.0);
        int drivenDistance = 150;

        LocalDate tripDateFrom = LocalDate.now();
        LocalDate tripDateTo = tripDateFrom.plusDays(1);
        List<LocalDate> disabledDays = Collections.emptyList();

        List<Receipt> receiptList = Collections.emptyList();

        Claim givenClaim = new Claim(new User(), tripDateFrom, tripDateTo, disabledDays, receiptList, drivenDistance);

        //WHEN
        BigDecimal expectedSum = ReimbursementValues.getMileageLimit();
        BigDecimal receivedSum = CalculationService.getMillageSum(givenClaim);

        //THEN
        System.out.println("Expected: " + expectedSum + " - Received: " + receivedSum);
        assertEquals(expectedSum, receivedSum);

        //CLEANUP
        ReimbursementValues.setMileageLimit(-1);
    }


    @Test
    void testGetReceiptSum() {
        //GIVEN
        Receipt taxiReceipt1 = new Receipt(ReceiptCategory.TAXI, BigDecimal.valueOf(21.3));
        Receipt taxiReceipt2 = new Receipt(ReceiptCategory.TAXI, BigDecimal.valueOf(18.7));
        Receipt hotelReceipt = new Receipt(ReceiptCategory.HOTEL, BigDecimal.valueOf(100.0));
        Receipt ticketReceipt = new Receipt(ReceiptCategory.TICKET, BigDecimal.valueOf(50.0));
        Receipt otherReceipt = new Receipt(ReceiptCategory.OTHER, BigDecimal.valueOf(150.20));
        List<Receipt> receiptList = Arrays.asList(taxiReceipt1, taxiReceipt2, hotelReceipt, ticketReceipt, otherReceipt);
        BigDecimal temp = BigDecimal.valueOf(0.0);
        for(Receipt r : receiptList){
            temp = temp.add(r.getReceiptSum());
        }

        LocalDate tripDateFrom = LocalDate.now();
        LocalDate tripDateTo = tripDateFrom.plusDays(1);

        List<LocalDate> disabledDays = Collections.emptyList();

        int drivenDistance = 0;

        Claim claim = new Claim(new User(), tripDateFrom, tripDateTo, disabledDays, receiptList, drivenDistance);

        //WHEN
        BigDecimal expectedSum = BigDecimal.valueOf(0.0);
        expectedSum = expectedSum.add(taxiReceipt1.getReceiptSum());
        expectedSum = expectedSum.add(taxiReceipt2.getReceiptSum());
        expectedSum = expectedSum.add(hotelReceipt.getReceiptSum());
        expectedSum = expectedSum.add(ticketReceipt.getReceiptSum());
        expectedSum = expectedSum.add(otherReceipt.getReceiptSum());
        BigDecimal receivedSum = CalculationService.getReceiptSum(claim);

        //THEN
        System.out.println("Expected: " + expectedSum + " - Received: " + receivedSum);
        assertEquals(expectedSum, receivedSum);
    }

    @Test
    void testGetReceiptSumWithLimits() {
        //GIVEN
        ReimbursementValues.setTaxiReceiptLimit(30);
        ReimbursementValues.setHotelReceiptLimit(80);
        ReimbursementValues.setTicketReceiptLimit(40);
        ReimbursementValues.setOtherReceiptLimit(100);

        Receipt taxiReceipt1 = new Receipt(ReceiptCategory.TAXI, BigDecimal.valueOf(21.3));
        Receipt taxiReceipt2 = new Receipt(ReceiptCategory.TAXI, BigDecimal.valueOf(18.7));
        Receipt hotelReceipt = new Receipt(ReceiptCategory.HOTEL, BigDecimal.valueOf(100.0));
        Receipt ticketReceipt = new Receipt(ReceiptCategory.TICKET, BigDecimal.valueOf(50.0));
        Receipt otherReceipt = new Receipt(ReceiptCategory.OTHER, BigDecimal.valueOf(150.20));
        List<Receipt> receiptList = Arrays.asList(taxiReceipt1, taxiReceipt2, hotelReceipt, ticketReceipt, otherReceipt);
        BigDecimal temp = BigDecimal.valueOf(0.0);
        for(Receipt r : receiptList){
            temp = temp.add(r.getReceiptSum());
        }

        LocalDate tripDateFrom = LocalDate.now();
        LocalDate tripDateTo = tripDateFrom.plusDays(1);

        List<LocalDate> disabledDays = Collections.emptyList();

        int drivenDistance = 0;

        Claim claim = new Claim(new User(), tripDateFrom, tripDateTo, disabledDays, receiptList, drivenDistance);

        //WHEN
        BigDecimal expectedSum = BigDecimal.valueOf(0.0);
        expectedSum = expectedSum.add(ReimbursementValues.getTaxiReceiptLimit());
        expectedSum = expectedSum.add(ReimbursementValues.getHotelReceiptLimit());
        expectedSum = expectedSum.add(ReimbursementValues.getTicketReceiptLimit());
        expectedSum = expectedSum.add(ReimbursementValues.getOtherReceiptLimit());
        BigDecimal receivedSum = CalculationService.getReceiptSum(claim);

        //THEN
        System.out.println("Expected: " + expectedSum + " - Received: " + receivedSum);
        assertEquals(expectedSum, receivedSum);

        //CLEANUP
        ReimbursementValues.setTaxiReceiptLimit(-1);
        ReimbursementValues.setHotelReceiptLimit(-1);
        ReimbursementValues.setTicketReceiptLimit(-1);
        ReimbursementValues.setOtherReceiptLimit(-1);
    }

    @Test
    void testGetExpectedTotal() {
        //GIVEN
        LocalDate tripDateFrom = LocalDate.of(2023, 8, 1);
        LocalDate tripDateTo = LocalDate.of(2023, 8, 5);
        List<LocalDate> disabledDays = Collections.emptyList();

        List<Receipt> receiptList = new ArrayList<>();
        receiptList.add(new Receipt(ReceiptCategory.TAXI, BigDecimal.valueOf(20.0)));
        receiptList.add(new Receipt(ReceiptCategory.HOTEL, BigDecimal.valueOf(100.0)));
        receiptList.add(new Receipt(ReceiptCategory.TICKET, BigDecimal.valueOf(50.0)));

        int drivenDistance = 150;

        Claim givenClaim = new Claim(new User(), tripDateFrom, tripDateTo, disabledDays, receiptList, drivenDistance);

        //WHEN
        BigDecimal expectedSum = BigDecimal.valueOf(0.0);
        expectedSum = expectedSum.add(BigDecimal.valueOf(20.0+100.0+50.0));
        expectedSum = expectedSum.add(ReimbursementValues.getCarMileageValue().multiply(BigDecimal.valueOf(150)));
        expectedSum = expectedSum.add(ReimbursementValues.getDailyAllowanceValue().multiply(BigDecimal.valueOf(5)));

        BigDecimal receivedSum = CalculationService.getExpectedTotal(givenClaim);

        //THEN
        System.out.println("Expected: " + expectedSum + " - Received: " + receivedSum);
        assertEquals(expectedSum, receivedSum);
    }
}

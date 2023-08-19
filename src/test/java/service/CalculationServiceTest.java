package service;

import repository.ReimbursementValues;
import entities.Claim;
import entities.Receipt;
import entities.ReceiptCategory;
import entities.User;
import org.junit.jupiter.api.Test;
import repository.ReceiptCategoryRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("taxi", -1));
        ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("hotel", -1));
        ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("ticket", -1));
        ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("other", -1));

        Receipt taxiReceipt1 = new Receipt(ReceiptCategoryRepository.getReceiptCategory("taxi"), BigDecimal.valueOf(21.3));
        Receipt taxiReceipt2 = new Receipt(ReceiptCategoryRepository.getReceiptCategory("taxi"), BigDecimal.valueOf(18.7));
        Receipt hotelReceipt = new Receipt(ReceiptCategoryRepository.getReceiptCategory("hotel"), BigDecimal.valueOf(100.0));
        Receipt ticketReceipt = new Receipt(ReceiptCategoryRepository.getReceiptCategory("ticket"), BigDecimal.valueOf(50.0));
        Receipt otherReceipt = new Receipt(ReceiptCategoryRepository.getReceiptCategory("other"), BigDecimal.valueOf(150.20));

        List<Receipt> receiptList = Arrays.asList(taxiReceipt1, taxiReceipt2, hotelReceipt, ticketReceipt, otherReceipt);

//        BigDecimal temp = BigDecimal.valueOf(0.0);
//        for(Receipt r : receiptList){
//            temp = temp.add(r.getReceiptSum());
//        }

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

        //CLEAN-UP
        ReceiptCategoryRepository.getReceiptCategoryList().clear();
    }

    @Test
    void testGetReceiptSumWithLimits() {
        //GIVEN
        ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("taxi", 30));
        ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("hotel", 80));
        ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("ticket", 40));
        ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("other", 100));

        Receipt taxiReceipt1 = new Receipt(ReceiptCategoryRepository.getReceiptCategory("taxi"), BigDecimal.valueOf(21.3));
        Receipt taxiReceipt2 = new Receipt(ReceiptCategoryRepository.getReceiptCategory("taxi"), BigDecimal.valueOf(18.7));
        Receipt hotelReceipt = new Receipt(ReceiptCategoryRepository.getReceiptCategory("hotel"), BigDecimal.valueOf(100.0));
        Receipt ticketReceipt = new Receipt(ReceiptCategoryRepository.getReceiptCategory("ticket"), BigDecimal.valueOf(50.0));
        Receipt otherReceipt = new Receipt(ReceiptCategoryRepository.getReceiptCategory("other"), BigDecimal.valueOf(150.20));
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
        expectedSum = expectedSum.add(ReceiptCategoryRepository.getReceiptCategory("taxi").getReceiptCategoryLimit());
        expectedSum = expectedSum.add(ReceiptCategoryRepository.getReceiptCategory("hotel").getReceiptCategoryLimit());
        expectedSum = expectedSum.add(ReceiptCategoryRepository.getReceiptCategory("ticket").getReceiptCategoryLimit());
        expectedSum = expectedSum.add(ReceiptCategoryRepository.getReceiptCategory("other").getReceiptCategoryLimit());
        BigDecimal receivedSum = CalculationService.getReceiptSum(claim);

        //THEN
        System.out.println("Expected: " + expectedSum + " - Received: " + receivedSum);
        assertEquals(expectedSum, receivedSum);

        //CLEANUP
        ReceiptCategoryRepository.getReceiptCategoryList().clear();
    }

    @Test
    void testGetExpectedTotal() {
        //GIVEN
        LocalDate tripDateFrom = LocalDate.of(2023, 8, 1);
        LocalDate tripDateTo = LocalDate.of(2023, 8, 5);
        List<LocalDate> disabledDays = Collections.emptyList();
        ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("taxi", -1));
        ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("hotel", -1));
        ReceiptCategoryRepository.addReceiptCategory(new ReceiptCategory("ticket", -1));

        List<Receipt> receiptList = new ArrayList<>();
        receiptList.add(new Receipt(ReceiptCategoryRepository.getReceiptCategory("taxi"), BigDecimal.valueOf(20.0)));
        receiptList.add(new Receipt(ReceiptCategoryRepository.getReceiptCategory("hotel"), BigDecimal.valueOf(100.0)));
        receiptList.add(new Receipt(ReceiptCategoryRepository.getReceiptCategory("ticket"), BigDecimal.valueOf(50.0)));

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

        //CLEANUP
        ReceiptCategoryRepository.getReceiptCategoryList().clear();
    }
}

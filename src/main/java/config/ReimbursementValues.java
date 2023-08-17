package config;

import java.math.BigDecimal;

public class ReimbursementValues {
    private static BigDecimal dailyAllowanceValue = BigDecimal.valueOf(15.0);
    private static BigDecimal carMileageValue = BigDecimal.valueOf(0.3);
    private static BigDecimal totalReimbursementLimit = BigDecimal.valueOf(-1.0);
    private static BigDecimal mileageLimit = BigDecimal.valueOf(-1.0);
    private static BigDecimal taxiReceiptLimit = BigDecimal.valueOf(-1.0);
    private static BigDecimal hotelReceiptLimit = BigDecimal.valueOf(-1.0);
    private static BigDecimal ticketReceiptLimit = BigDecimal.valueOf(-1.0);
    private static BigDecimal otherReceiptLimit = BigDecimal.valueOf(-1.0);

    public static BigDecimal getDailyAllowanceValue() {
        return dailyAllowanceValue;
    }

    public static BigDecimal getCarMileageValue() {
        return carMileageValue;
    }

    public static BigDecimal getTotalReimbursementLimit() {
        return totalReimbursementLimit;
    }

    public static BigDecimal getMileageLimit() {
        return mileageLimit;
    }

    public static BigDecimal getTaxiReceiptLimit() {
        return taxiReceiptLimit;
    }

    public static BigDecimal getHotelReceiptLimit() {
        return hotelReceiptLimit;
    }

    public static BigDecimal getTicketReceiptLimit() {
        return ticketReceiptLimit;
    }

    public static BigDecimal getOtherReceiptLimit() {
        return otherReceiptLimit;
    }

    public static void setDailyAllowanceValue(double dailyAllowanceValue) {
        ReimbursementValues.dailyAllowanceValue = BigDecimal.valueOf(dailyAllowanceValue);
    }

    public static void setCarMileageValue(double carMileageValue) {
        ReimbursementValues.carMileageValue = BigDecimal.valueOf(carMileageValue);
    }

    public static void setTotalReimbursementLimit(double totalReimbursementLimit) {
        ReimbursementValues.totalReimbursementLimit = BigDecimal.valueOf(totalReimbursementLimit);
    }

    public static void setMileageLimit(double mileageLimit) {
        ReimbursementValues.mileageLimit = BigDecimal.valueOf(mileageLimit);
    }

    public static void setTaxiReceiptLimit(double taxiReceiptLimit) {
        ReimbursementValues.taxiReceiptLimit = BigDecimal.valueOf(taxiReceiptLimit);
    }

    public static void setHotelReceiptLimit(double hotelReceiptLimit) {
        ReimbursementValues.hotelReceiptLimit = BigDecimal.valueOf(hotelReceiptLimit);
    }

    public static void setTicketReceiptLimit(double ticketReceiptLimit) {
        ReimbursementValues.ticketReceiptLimit = BigDecimal.valueOf(ticketReceiptLimit);
    }

    public static void setOtherReceiptLimit(double otherReceiptLimit) {
        ReimbursementValues.otherReceiptLimit = BigDecimal.valueOf(otherReceiptLimit);
    }
}
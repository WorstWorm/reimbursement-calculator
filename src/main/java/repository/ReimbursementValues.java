package repository;

import java.math.BigDecimal;

public class ReimbursementValues {
    private static BigDecimal dailyAllowanceValue = BigDecimal.valueOf(15.0);
    private static BigDecimal carMileageValue = BigDecimal.valueOf(0.3);
    private static BigDecimal totalReimbursementLimit = BigDecimal.valueOf(-1.0);
    private static BigDecimal mileageLimit = BigDecimal.valueOf(-1.0);

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
}
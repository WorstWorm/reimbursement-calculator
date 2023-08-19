package dto;

public class DisabledDateDto {
    private final int year;
    private final int month;
    private final int disabledDay;

    public DisabledDateDto(int year, int month, int disabledDay) {
        this.year = year;
        this.month = month;
        this.disabledDay = disabledDay;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDisabledDay() {
        return disabledDay;
    }
}

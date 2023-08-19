package entities;

import java.math.BigDecimal;

public class ReceiptCategory {
    private String categoryName;
    private BigDecimal receiptCategoryLimit;

    public ReceiptCategory(String categoryName, double receiptCategoryLimit) {
        this.categoryName = categoryName;
        this.receiptCategoryLimit = BigDecimal.valueOf(receiptCategoryLimit);
    }

    public ReceiptCategory(String categoryName, String receiptCategoryLimit) {
        this.categoryName = categoryName;
        this.receiptCategoryLimit = BigDecimal.valueOf(Long.parseLong(receiptCategoryLimit));
    }

    public String getCategoryName() {
        return categoryName;
    }

    public BigDecimal getReceiptCategoryLimit() {
        return receiptCategoryLimit;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setReceiptCategoryLimit(BigDecimal receiptCategoryLimit) {
        this.receiptCategoryLimit = receiptCategoryLimit;
    }
}

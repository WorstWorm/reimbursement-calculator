package entities;

import java.math.BigDecimal;

public class Receipt {
    private final ReceiptCategory receiptCategory;
    private final String receiptType;
    private final BigDecimal receiptSum;

    public Receipt(ReceiptCategory receiptCategory, double receiptSum) {
        this.receiptCategory = receiptCategory;
        this.receiptType = receiptCategory.getCategoryName();
        this.receiptSum = BigDecimal.valueOf(receiptSum);
    }

    public ReceiptCategory getReceiptCategory() {
        return receiptCategory;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public BigDecimal getReceiptSum() {
        return receiptSum;
    }
}

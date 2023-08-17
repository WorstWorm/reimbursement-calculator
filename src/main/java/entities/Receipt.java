package entities;

import enums.ReceiptCategory;

import java.math.BigDecimal;

public class Receipt {
    private final ReceiptCategory receiptCategory;
    private final BigDecimal receiptSum;

    public Receipt(ReceiptCategory receiptCategory, BigDecimal receiptSum) {
        this.receiptCategory = receiptCategory;
        this.receiptSum = receiptSum;
    }

    public ReceiptCategory getReceiptCategory() {
        return receiptCategory;
    }

    public BigDecimal getReceiptSum() {
        return receiptSum;
    }
}

package repository;

import entities.ReceiptCategory;

import java.util.ArrayList;
import java.util.List;

public class ReceiptCategoryRepository {

    private static List<ReceiptCategory> categoryList = new ArrayList<>();

    public static List<ReceiptCategory> getReceiptCategoryList() {
        return categoryList;
    }

    public static void addReceiptCategory(ReceiptCategory receiptCategory) {
        categoryList.add(receiptCategory);
    }

    public static ReceiptCategory getReceiptCategory(String categoryName) {
        for(ReceiptCategory category : categoryList) {
            if (category.getCategoryName().equals(categoryName)) {
                return category;
            }
        }
        return null;
    }

    public static void removeReceiptCategory(ReceiptCategory receiptCategory) {
        categoryList.remove(receiptCategory);
    }
}

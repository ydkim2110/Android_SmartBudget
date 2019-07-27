package com.example.smartbudget.Model.EventBus;

import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.SubCategory;

public class CategorySelectedEvent {

    private boolean success;
    private int categoryType;
    private int transactionType;
    private Category mCategory;
    private SubCategory mSubCategory;

    public CategorySelectedEvent(boolean success, int categoryType, int transactionType, Category category) {
        this.success = success;
        this.categoryType = categoryType;
        this.transactionType = transactionType;
        mCategory = category;
    }

    public CategorySelectedEvent(boolean success, int type, int transactionType, SubCategory subCategory) {
        this.success = success;
        this.categoryType = type;
        this.transactionType = transactionType;
        mSubCategory = subCategory;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public Category getCategory() {
        return mCategory;
    }

    public SubCategory getSubCategory() {
        return mSubCategory;
    }
}

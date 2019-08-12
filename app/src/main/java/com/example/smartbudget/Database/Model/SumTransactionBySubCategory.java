package com.example.smartbudget.Database.Model;

public class SumTransactionBySubCategory {
    private String subCategoryId;
    private double sumBySubCategory;

    public SumTransactionBySubCategory() {
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public double getSumBySubCategory() {
        return sumBySubCategory;
    }

    public void setSumBySubCategory(double sumBySubCategory) {
        this.sumBySubCategory = sumBySubCategory;
    }
}

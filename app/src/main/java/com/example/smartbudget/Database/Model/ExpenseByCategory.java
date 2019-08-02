package com.example.smartbudget.Database.Model;

public class ExpenseByCategory {
    private String categoryId;
    private int sumByCategory;
    private int countByCategory;

    public ExpenseByCategory() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getSumByCategory() {
        return sumByCategory;
    }

    public void setSumByCategory(int sumByCategory) {
        this.sumByCategory = sumByCategory;
    }

    public int getCountByCategory() {
        return countByCategory;
    }

    public void setCountByCategory(int countByCategory) {
        this.countByCategory = countByCategory;
    }
}

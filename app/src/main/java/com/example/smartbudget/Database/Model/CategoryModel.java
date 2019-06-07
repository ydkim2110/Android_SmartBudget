package com.example.smartbudget.Database.Model;

public class CategoryModel {

    private int id;
    private String category_name;
    private int category_icon;
    private double category_budget;

    public CategoryModel(String category_name, int category_icon, double category_budget) {
        this.category_name = category_name;
        this.category_icon = category_icon;
        this.category_budget = category_budget;
    }

    public CategoryModel(String category_name, int category_icon) {
        this.category_name = category_name;
        this.category_icon = category_icon;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getCategory_icon() {
        return category_icon;
    }

    public void setCategory_icon(int category_icon) {
        this.category_icon = category_icon;
    }

    public double getCategory_budget() {
        return category_budget;
    }

    public void setCategory_budget(double category_budget) {
        this.category_budget = category_budget;
    }
}

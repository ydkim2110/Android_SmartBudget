package com.example.smartbudget.Model;

public class BudgetModel {

    private int id;
    private String description;
    private String startDate;
    private String endDate;
    private double amount;
    private int accountId;

    public BudgetModel() {
    }

    public BudgetModel(String name, String startDate, String endDate, double amount, int accountId) {
        this.description = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}

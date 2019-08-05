package com.example.smartbudget.Model;

public class BudgetModel {

    private int id;
    private String name;
    private String startDate;
    private String endDate;
    private double targetAmount;
    private int accountId;

    public BudgetModel() {
    }

    public BudgetModel(String name, String startDate, String endDate, double targetAmount, int accountId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetAmount = targetAmount;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}

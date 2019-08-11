package com.example.smartbudget.Database.Model;

public class SpendingByPattern {

    private String pattern;
    private double sum;
    private int count;

    public SpendingByPattern() {
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

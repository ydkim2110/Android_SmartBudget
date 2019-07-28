package com.example.smartbudget.Database.Model;

public class SpendingPattern {

    private String pattern;
    private double sum;
    private int count;

    public SpendingPattern() {
    }

    public SpendingPattern(String pattern, double sum, int count) {
        this.pattern = pattern;
        this.sum = sum;
        this.count = count;
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

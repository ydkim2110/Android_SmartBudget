package com.example.smartbudget.Ui.Home;

public class DateItem extends ListItem {

    private String date;
    private int total;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int getType() {
        return TYPE_DATE;
    }
}

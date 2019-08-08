package com.example.smartbudget.Model;

public class Account {

    private int id;
    private String visibleName;

    public Account(int id, String visibleName) {
        this.id = id;
        this.visibleName = visibleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String visibleName) {
        this.visibleName = visibleName;
    }
}

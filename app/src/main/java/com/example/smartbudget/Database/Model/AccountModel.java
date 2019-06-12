package com.example.smartbudget.Database.Model;

import java.util.Date;

public class AccountModel {

    private int id;
    private String account_name;
    private double account_amount;
    private String account_type;
    private Date account_create_at;
    private String account_currency;

    public AccountModel(String account_name, double account_amount, String account_type, Date account_create_at, String account_currency) {
        this.account_name = account_name;
        this.account_amount = account_amount;
        this.account_type = account_type;
        this.account_create_at = account_create_at;
        this.account_currency = account_currency;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public double getAccount_amount() {
        return account_amount;
    }

    public void setAccount_amount(double account_amount) {
        this.account_amount = account_amount;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public Date getAccount_create_at() {
        return account_create_at;
    }

    public void setAccount_create_at(Date account_create_at) {
        this.account_create_at = account_create_at;
    }

    public String getAccount_currency() {
        return account_currency;
    }

    public void setAccount_currency(String account_currency) {
        this.account_currency = account_currency;
    }
}

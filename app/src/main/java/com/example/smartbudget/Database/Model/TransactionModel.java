package com.example.smartbudget.Database.Model;

import java.util.Date;

public class TransactionModel {

    private int id;
    private String transaction_description;
    private double transaction_amount;
    private String transaction_type;
    private Date transaction_date;
    private int category_id;
    private int account_id;
    private int to_account;

    public TransactionModel() {}

    public TransactionModel(String transaction_description, double transaction_amount, String transaction_type, Date transaction_date, int category_id, int account_id) {
        this.transaction_description = transaction_description;
        this.transaction_amount = transaction_amount;
        this.transaction_type = transaction_type;
        this.transaction_date = transaction_date;
        this.category_id = category_id;
        this.account_id = account_id;
    }

    public TransactionModel(String transaction_description, double transaction_amount, String transaction_type, Date transaction_date, int category_id, int account_id, int to_account) {
        this.transaction_description = transaction_description;
        this.transaction_amount = transaction_amount;
        this.transaction_type = transaction_type;
        this.transaction_date = transaction_date;
        this.category_id = category_id;
        this.account_id = account_id;
        this.to_account = to_account;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransaction_description() {
        return transaction_description;
    }

    public void setTransaction_description(String transaction_description) {
        this.transaction_description = transaction_description;
    }

    public double getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(double transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public Date getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(Date transaction_date) {
        this.transaction_date = transaction_date;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getTo_account() {
        return to_account;
    }

    public void setTo_account(int to_account) {
        this.to_account = to_account;
    }
}

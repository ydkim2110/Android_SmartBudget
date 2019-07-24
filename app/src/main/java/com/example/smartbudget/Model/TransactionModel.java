package com.example.smartbudget.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class TransactionModel implements Parcelable {

    private int id;
    private String transaction_description;
    private double transaction_amount;
    private String transaction_type;
    private String transaction_date;
    private int category_id;
    private int account_id;
    private int to_account;

    public TransactionModel() {}

    public TransactionModel(String transaction_description, double transaction_amount, String transaction_type, String transaction_date, int category_id, int account_id) {
        this.transaction_description = transaction_description;
        this.transaction_amount = transaction_amount;
        this.transaction_type = transaction_type;
        this.transaction_date = transaction_date;
        this.category_id = category_id;
        this.account_id = account_id;
    }

    public TransactionModel(String transaction_description, double transaction_amount, String transaction_type, String transaction_date, int category_id, int account_id, int to_account) {
        this.transaction_description = transaction_description;
        this.transaction_amount = transaction_amount;
        this.transaction_type = transaction_type;
        this.transaction_date = transaction_date;
        this.category_id = category_id;
        this.account_id = account_id;
        this.to_account = to_account;
    }


    protected TransactionModel(Parcel in) {
        id = in.readInt();
        transaction_description = in.readString();
        transaction_amount = in.readDouble();
        transaction_type = in.readString();
        category_id = in.readInt();
        account_id = in.readInt();
        to_account = in.readInt();
    }

    public static final Creator<TransactionModel> CREATOR = new Creator<TransactionModel>() {
        @Override
        public TransactionModel createFromParcel(Parcel in) {
            return new TransactionModel(in);
        }

        @Override
        public TransactionModel[] newArray(int size) {
            return new TransactionModel[size];
        }
    };

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

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(transaction_description);
        dest.writeDouble(transaction_amount);
        dest.writeString(transaction_type);
        dest.writeInt(category_id);
        dest.writeInt(account_id);
        dest.writeInt(to_account);
    }
}

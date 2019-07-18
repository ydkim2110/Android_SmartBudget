package com.example.smartbudget.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class AccountModel implements Parcelable {

    private int id;
    private String account_name;
    private String account_description;
    private double account_amount;
    private String account_type;
    private Date account_create_at;
    private String account_currency;

    public AccountModel() {
    }

    public AccountModel(int id, String account_name, String account_description, double account_amount, String account_type, Date account_create_at, String account_currency) {
        this.id = id;
        this.account_name = account_name;
        this.account_description = account_description;
        this.account_amount = account_amount;
        this.account_type = account_type;
        this.account_create_at = account_create_at;
        this.account_currency = account_currency;
    }

    public AccountModel(String account_name, String account_description, double account_amount, String account_type, Date account_create_at, String account_currency) {
        this.account_name = account_name;
        this.account_description = account_description;
        this.account_amount = account_amount;
        this.account_type = account_type;
        this.account_create_at = account_create_at;
        this.account_currency = account_currency;
    }

    protected AccountModel(Parcel in) {
        id = in.readInt();
        account_name = in.readString();
        account_description = in.readString();
        account_amount = in.readDouble();
        account_type = in.readString();
        account_currency = in.readString();
    }

    public static final Creator<AccountModel> CREATOR = new Creator<AccountModel>() {
        @Override
        public AccountModel createFromParcel(Parcel in) {
            return new AccountModel(in);
        }

        @Override
        public AccountModel[] newArray(int size) {
            return new AccountModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_description() {
        return account_description;
    }

    public void setAccount_description(String account_description) {
        this.account_description = account_description;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(account_name);
        dest.writeString(account_description);
        dest.writeDouble(account_amount);
        dest.writeString(account_type);
        dest.writeString(account_currency);
    }
}

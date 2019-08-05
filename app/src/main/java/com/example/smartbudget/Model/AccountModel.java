package com.example.smartbudget.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class AccountModel implements Parcelable {

    private int id;
    private String name;
    private String description;
    private double amount;
    private String highCategory;
    private String type;
    private Date createAt;
    private String currency;

    public AccountModel() {
    }

    public AccountModel(int id, String name, String description, double amount, String highCategory, String type, Date createAt, String currency) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.highCategory = highCategory;
        this.type = type;
        this.createAt = createAt;
        this.currency = currency;
    }

    public AccountModel(String name, String description, double amount, String highCategory, String type, Date createAt, String currency) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.highCategory = highCategory;
        this.type = type;
        this.createAt = createAt;
        this.currency = currency;
    }

    protected AccountModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        amount = in.readDouble();
        highCategory = in.readString();
        type = in.readString();
        currency = in.readString();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getHighCategory() {
        return highCategory;
    }

    public void setHighCategory(String highCategory) {
        this.highCategory = highCategory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(amount);
        dest.writeString(highCategory);
        dest.writeString(type);
        dest.writeString(currency);
    }
}

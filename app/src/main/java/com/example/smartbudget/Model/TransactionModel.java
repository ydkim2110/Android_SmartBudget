package com.example.smartbudget.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class TransactionModel implements Parcelable, Comparable<TransactionModel> {

    private int id;
    private String note;
    private double amount;
    private String type;
    private String pattern;
    private String date;
    private String categoryId;
    private String subCategoryId;
    private int accountId;
    private int toAccount;

    public TransactionModel() {}

    public TransactionModel(int id, String note, double amount, String type, String pattern, String date, String categoryId, String subCategoryId, int accountId) {
        this.id = id;
        this.note = note;
        this.amount = amount;
        this.type = type;
        this.pattern = pattern;
        this.date = date;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.accountId = accountId;
    }

    public TransactionModel(String note, double amount, String type, String pattern, String date, String categoryId, String subCategoryId, int accountId) {
        this.note = note;
        this.amount = amount;
        this.type = type;
        this.pattern = pattern;
        this.date = date;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.accountId = accountId;
    }

    public TransactionModel(int id, String note, double amount, String type, String pattern, String date, String categoryId, String subCategoryId, int accountId, int toAccount) {
        this.id = id;
        this.note = note;
        this.amount = amount;
        this.type = type;
        this.pattern = pattern;
        this.date = date;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.accountId = accountId;
        this.toAccount = toAccount;
    }

    public TransactionModel(String note, double amount, String type, String pattern, String date, String categoryId, String subCategoryId, int accountId, int toAccount) {
        this.note = note;
        this.amount = amount;
        this.type = type;
        this.pattern = pattern;
        this.date = date;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.accountId = accountId;
        this.toAccount = toAccount;
    }

    protected TransactionModel(Parcel in) {
        id = in.readInt();
        note = in.readString();
        amount = in.readDouble();
        type = in.readString();
        pattern = in.readString();
        date = in.readString();
        categoryId = in.readString();
        subCategoryId = in.readString();
        accountId = in.readInt();
        toAccount = in.readInt();
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getToAccount() {
        return toAccount;
    }

    public void setToAccount(int toAccount) {
        this.toAccount = toAccount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(note);
        dest.writeDouble(amount);
        dest.writeString(type);
        dest.writeString(pattern);
        dest.writeString(date);
        dest.writeString(categoryId);
        dest.writeString(subCategoryId);
        dest.writeInt(accountId);
        dest.writeInt(toAccount);
    }

    @Override
    public int compareTo(TransactionModel t) {
        return date.compareTo(t.date);
    }
}

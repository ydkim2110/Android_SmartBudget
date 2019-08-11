package com.example.smartbudget.Database.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class SumBudget implements Parcelable {
    private int id;
    private String description;
    private double amount;
    private String startDate;
    private String endDate;
    private String type;
    private int accountId;
    private double sumTransaction;

    public SumBudget() {
    }

    protected SumBudget(Parcel in) {
        id = in.readInt();
        description = in.readString();
        amount = in.readDouble();
        startDate = in.readString();
        endDate = in.readString();
        type = in.readString();
        accountId = in.readInt();
        sumTransaction = in.readDouble();
    }

    public static final Creator<SumBudget> CREATOR = new Creator<SumBudget>() {
        @Override
        public SumBudget createFromParcel(Parcel in) {
            return new SumBudget(in);
        }

        @Override
        public SumBudget[] newArray(int size) {
            return new SumBudget[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getSumTransaction() {
        return sumTransaction;
    }

    public void setSumTransaction(double sumTransaction) {
        this.sumTransaction = sumTransaction;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeDouble(amount);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(type);
        dest.writeInt(accountId);
        dest.writeDouble(sumTransaction);
    }
}

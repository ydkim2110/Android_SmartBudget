package com.example.smartbudget.Travel;

import android.os.Parcel;
import android.os.Parcelable;

public class Travel implements Parcelable {

    private String name;
    private String country;
    private String start_date;
    private String end_date;
    private double amount;

    public Travel() {
    }

    public Travel(String name, String country, String start_date, String end_date, double amount) {
        this.name = name;
        this.country = country;
        this.start_date = start_date;
        this.end_date = end_date;
        this.amount = amount;
    }

    protected Travel(Parcel in) {
        name = in.readString();
        country = in.readString();
        start_date = in.readString();
        end_date = in.readString();
        amount = in.readDouble();
    }

    public static final Creator<Travel> CREATOR = new Creator<Travel>() {
        @Override
        public Travel createFromParcel(Parcel in) {
            return new Travel(in);
        }

        @Override
        public Travel[] newArray(int size) {
            return new Travel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(country);
        dest.writeString(start_date);
        dest.writeString(end_date);
        dest.writeDouble(amount);
    }
}

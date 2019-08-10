package com.example.smartbudget.Database.AccountRoom;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_table")
public class AccountItem implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "high_category")
    private String highCategory;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "create_at")
    private String createAt;

    @ColumnInfo(name = "currency")
    private String currency;

    public AccountItem() {
    }

    protected AccountItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        amount = in.readDouble();
        highCategory = in.readString();
        type = in.readString();
        createAt = in.readString();
        currency = in.readString();
    }

    public static final Creator<AccountItem> CREATOR = new Creator<AccountItem>() {
        @Override
        public AccountItem createFromParcel(Parcel in) {
            return new AccountItem(in);
        }

        @Override
        public AccountItem[] newArray(int size) {
            return new AccountItem[size];
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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
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
        dest.writeString(createAt);
        dest.writeString(currency);
    }
}

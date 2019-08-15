package com.example.smartbudget.Database.TransactionRoom;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.smartbudget.Database.AccountRoom.AccountItem;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "transaction_table", foreignKeys = @ForeignKey(entity = AccountItem.class,
            parentColumns = "id", childColumns = "account_id", onDelete = CASCADE),
            indices = {@Index("account_id")})
public class TransactionItem implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "pattern")
    private String pattern;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "category_id")
    private String categoryId;

    @ColumnInfo(name = "sub_category_id")
    private String subCategoryId;

    @ColumnInfo(name = "account_id")
    private int accountId;

    @ColumnInfo(name = "to_account")
    private int toAccount;

    public TransactionItem() {
    }

    @Ignore
    public TransactionItem(int id, String description, double amount, String type, String pattern, String date, String categoryId, String subCategoryId, int accountId, int toAccount) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.pattern = pattern;
        this.date = date;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.accountId = accountId;
        this.toAccount = toAccount;
    }

    @Ignore
    public TransactionItem(int id, String description, double amount, String type, String pattern, String date, String categoryId, String subCategoryId, int accountId) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.pattern = pattern;
        this.date = date;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.accountId = accountId;
    }

    protected TransactionItem(Parcel in) {
        id = in.readInt();
        description = in.readString();
        amount = in.readDouble();
        type = in.readString();
        pattern = in.readString();
        date = in.readString();
        categoryId = in.readString();
        subCategoryId = in.readString();
        accountId = in.readInt();
        toAccount = in.readInt();
    }

    public static final Creator<TransactionItem> CREATOR = new Creator<TransactionItem>() {
        @Override
        public TransactionItem createFromParcel(Parcel in) {
            return new TransactionItem(in);
        }

        @Override
        public TransactionItem[] newArray(int size) {
            return new TransactionItem[size];
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
        if (subCategoryId == null || subCategoryId.isEmpty()) {
            return categoryId;
        }
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
        dest.writeString(description);
        dest.writeDouble(amount);
        dest.writeString(type);
        dest.writeString(pattern);
        dest.writeString(date);
        dest.writeString(categoryId);
        dest.writeString(subCategoryId);
        dest.writeInt(accountId);
        dest.writeInt(toAccount);
    }
}



package com.example.smartbudget.Database.ExpenseBudgetRoom;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "expense_budget_table")
public class ExpenseBudgetItem implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "category_id")
    private String categoryId;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "create_at")
    private String createAt;

    public ExpenseBudgetItem() {
    }

    protected ExpenseBudgetItem(Parcel in) {
        id = in.readInt();
        categoryId = in.readString();
        amount = in.readDouble();
        createAt = in.readString();
    }

    public static final Creator<ExpenseBudgetItem> CREATOR = new Creator<ExpenseBudgetItem>() {
        @Override
        public ExpenseBudgetItem createFromParcel(Parcel in) {
            return new ExpenseBudgetItem(in);
        }

        @Override
        public ExpenseBudgetItem[] newArray(int size) {
            return new ExpenseBudgetItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(categoryId);
        dest.writeDouble(amount);
        dest.writeString(createAt);
    }
}

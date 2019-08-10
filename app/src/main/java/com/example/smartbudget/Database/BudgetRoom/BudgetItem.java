package com.example.smartbudget.Database.BudgetRoom;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.smartbudget.Database.AccountRoom.AccountItem;
import com.example.smartbudget.Database.Model.SumBudget;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "budget_table", foreignKeys = @ForeignKey(entity = AccountItem.class,
            parentColumns = "id", childColumns = "account_id", onDelete = CASCADE),
            indices = {@Index("account_id")})
public class BudgetItem implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "amount")
    private double amount;

    @ColumnInfo(name = "start_date")
    private String startDate;

    @ColumnInfo(name = "end_date")
    private String endDate;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "account_id")
    private int accountId;

    public BudgetItem() {
    }

    protected BudgetItem(Parcel in) {
        id = in.readInt();
        description = in.readString();
        amount = in.readDouble();
        startDate = in.readString();
        endDate = in.readString();
        type = in.readString();
        accountId = in.readInt();
    }

    public static final Creator<BudgetItem> CREATOR = new Creator<BudgetItem>() {
        @Override
        public BudgetItem createFromParcel(Parcel in) {
            return new BudgetItem(in);
        }

        @Override
        public BudgetItem[] newArray(int size) {
            return new BudgetItem[size];
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
    }
}

package com.example.smartbudget.Database.AccountRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class AccountDAO {

    @Query("SELECT * FROM account_table")
    abstract List<AccountItem> getAllAccounts();

    @Query("SELECT * FROM account_table WHERE id = :id")
    abstract AccountItem getAccount(int id);

    @Query("SELECT * FROM account_table WHERE type = :type")
    abstract List<AccountItem> getAccountsByType(String type);

    @Query("SELECT id, type, SUM(amount) AS amount FROM account_table GROUP BY type")
    abstract List<AccountItem> getSumAccountsByType();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertAccount(AccountItem... accountItems);

    @Update
    abstract void updateAccount(AccountItem... accountItems);

    @Delete
    abstract int deleteAccount(AccountItem accountItem);

    @Query("UPDATE account_table SET amount = amount - :amount WHERE id = :fromId")
    abstract void updateFromAccount(double amount, int fromId);

    @Query("UPDATE account_table SET amount = amount + :amount WHERE id = :toId")
    abstract void updateToAccount(double amount, int toId);

    @Transaction
    void updateAccountByTransfer(double amount, int fromId, int toId) {
        updateFromAccount(amount, fromId);
        updateToAccount(amount, toId);
    }

}

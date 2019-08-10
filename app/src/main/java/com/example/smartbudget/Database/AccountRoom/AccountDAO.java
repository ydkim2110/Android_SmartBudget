package com.example.smartbudget.Database.AccountRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccountDAO {

    @Query("SELECT * FROM account_table")
    List<AccountItem> getAllAccounts();

    @Query("SELECT * FROM account_table WHERE id = :id")
    AccountItem getAccount(int id);

    @Query("SELECT * FROM account_table WHERE type = :type")
    List<AccountItem> getAccountsByType(String type);

    @Query("SELECT id, type, SUM(amount) AS amount FROM account_table GROUP BY type")
    List<AccountItem> getSumAccountsByType();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAccount(AccountItem... accountItems);

    @Delete
    int deleteAccount(AccountItem accountItem);

}

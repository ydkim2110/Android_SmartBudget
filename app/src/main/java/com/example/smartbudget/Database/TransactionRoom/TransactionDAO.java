package com.example.smartbudget.Database.TransactionRoom;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.smartbudget.Database.DBContract;
import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Database.Model.SpendingByPattern;

import java.util.List;

@Dao
public interface TransactionDAO {

    @Query("SELECT * FROM transaction_table")
    List<TransactionItem> getAllTransactions();

    @Query("SELECT * FROM transaction_table WHERE id = :id")
    List<TransactionItem> getTransaction(int id);

    @Query("SELECT * FROM transaction_table"
            + " WHERE date BETWEEN DATE(:startDate) AND DATE(:endDate)")
    List<TransactionItem> getLastFewDaysTransactions(String startDate, String endDate);

    @Query("SELECT * FROM transaction_table"
            + " WHERE date BETWEEN DATE(:date, 'start of month') AND DATE(:date, 'start of month', '+1 months', '-1 day')"
            + " ORDER BY date DESC")
    List<TransactionItem> getThisMonthTransactions(String date);

    @Query("SELECT pattern, SUM(amount) AS sum, COUNT(amount) AS count FROM transaction_table"
            + " WHERE date BETWEEN DATE(:date, 'start of month') AND DATE(:date, 'start of month', '+1 months', '-1 day')"
            + " AND type LIKE 'Expense'"
            + " GROUP BY pattern")
    List<SpendingByPattern> getThisMonthTransactionsByPattern(String date);

    @Query("SELECT * FROM transaction_table"
            + " WHERE date BETWEEN DATE(:date, 'start of month') AND DATE(:date, 'start of month', '+1 months', '-1 day')"
            + " AND pattern LIKE :pattern"
            + " AND type LIKE 'Expense'"
            + " ORDER BY date DESC")
    List<TransactionItem> getThisMonthTransactionListByPattern(String date, String pattern);

    @Query("SELECT category_id AS categoryId, SUM(amount) AS sumByCategory, COUNT(amount) AS countByCategory FROM transaction_table"
            + " WHERE date BETWEEN DATE(:date, 'start of month') AND DATE(:date, 'start of month', '+1 months', '-1 day')"
            + " AND type LIKE 'Expense'"
            + " GROUP BY category_id")
    List<ExpenseByCategory> getThisMonthTransactionsByCategory(String date);

    @Query("SELECT SUM(amount) AS amount FROM transaction_table"
            + " WHERE type LIKE :type"
            + " AND account_id LIKE :accountId"
            + " AND date BETWEEN DATE(:startDate) AND DATE(:endDate)")
    double sumAmountByBudget(String startDate, String endDate, String type, int accountId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransaction(TransactionItem... transactionItems);

    @Delete
    void deleteTransaction(TransactionItem transactionItem);

}

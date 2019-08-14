package com.example.smartbudget.Database.TransactionRoom;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.smartbudget.Database.AccountRoom.AccountDAO;
import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Database.Model.SpendingByPattern;
import com.example.smartbudget.Database.Model.SumTransactionBySubCategory;

import java.util.List;

@Dao
public abstract class TransactionDAO {

    private static final String TAG = "TransactionDAO";

    @Query("SELECT * FROM transaction_table")
    abstract List<TransactionItem> getAllTransactions();

    @Query("SELECT * FROM transaction_table WHERE id = :id")
    abstract List<TransactionItem> getTransaction(int id);

    @Query("SELECT * FROM transaction_table"
            + " WHERE date BETWEEN DATE(:startDate) AND DATE(:endDate)")
    abstract List<TransactionItem> getLastFewDaysTransactions(String startDate, String endDate);

    @Query("SELECT * FROM transaction_table"
            + " WHERE date BETWEEN DATE(:date, 'start of month') AND DATE(:date, 'start of month', '+1 months', '-1 day')"
            + " ORDER BY date DESC")
    abstract List<TransactionItem> getThisMonthTransactions(String date);

    @Query("SELECT pattern, SUM(amount) AS sum, COUNT(amount) AS count FROM transaction_table"
            + " WHERE date BETWEEN DATE(:date, 'start of month') AND DATE(:date, 'start of month', '+1 months', '-1 day')"
            + " AND type LIKE 'Expense'"
            + " GROUP BY pattern")
    abstract List<SpendingByPattern> getThisMonthTransactionsByPattern(String date);

    @Query("SELECT * FROM transaction_table"
            + " WHERE date BETWEEN DATE(:date, 'start of month') AND DATE(:date, 'start of month', '+1 months', '-1 day')"
            + " AND pattern LIKE :pattern"
            + " AND type LIKE 'Expense'"
            + " ORDER BY date DESC")
    abstract List<TransactionItem> getThisMonthTransactionListByPattern(String date, String pattern);

    @Query("SELECT * FROM transaction_table"
            + " WHERE date BETWEEN DATE(:date, 'start of month') AND DATE(:date, 'start of month', '+1 months', '-1 day')"
            + " AND account_id LIKE :accountId"
            + " ORDER BY date DESC")
    abstract List<TransactionItem> getThisMonthTransactionsByAccount(String date, int accountId);

    @Query("SELECT * FROM transaction_table"
            + " WHERE date BETWEEN DATE(:date, 'start of month') AND DATE(:date, 'start of month', '+1 months', '-1 day')"
            + " AND category_id LIKE :categoryId"
            + " AND type LIKE 'Expense'"
            + " ORDER BY date DESC")
    abstract List<TransactionItem> getThisMonthTransactionListByCategory(String date, String categoryId);

    @Query("SELECT sub_category_id AS subCategoryId, SUM(amount) AS sumBySubCategory FROM transaction_table"
            + " WHERE date BETWEEN DATE(:date, 'start of month') AND DATE(:date, 'start of month', '+1 months', '-1 day')"
            + " AND category_id LIKE :categoryId"
            + " AND type LIKE 'Expense'"
            + " GROUP BY sub_category_id"
            + " ORDER BY date DESC")
    abstract List<SumTransactionBySubCategory> getThisMonthSumTransactionBySubCategory(String date, String categoryId);

    @Query("SELECT category_id AS categoryId, SUM(amount) AS sumByCategory, COUNT(amount) AS countByCategory FROM transaction_table"
            + " WHERE date BETWEEN DATE(:date, 'start of month') AND DATE(:date, 'start of month', '+1 months', '-1 day')"
            + " AND type LIKE 'Expense'"
            + " GROUP BY category_id")
    abstract List<ExpenseByCategory> getThisMonthTransactionsByCategory(String date);

    @Query("SELECT SUM(amount) AS amount FROM transaction_table"
            + " WHERE type LIKE :type"
            + " AND account_id LIKE :accountId"
            + " AND date BETWEEN DATE(:startDate) AND DATE(:endDate)")
    abstract double sumAmountByBudget(String startDate, String endDate, String type, int accountId);

    @Delete
    abstract void deleteTransaction(TransactionItem transactionItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertTransaction(TransactionItem... transactionItems);

    @Query("UPDATE account_table SET amount = amount - :amount WHERE id = :accountId")
    abstract void updateSubtractAccount(double amount, int accountId);

    @Query("UPDATE account_table SET amount = amount + :amount WHERE id = :accountId")
    abstract void updateAddAccount(double amount, int accountId);

    @Query("UPDATE account_table SET amount = CASE WHEN :type = 'Expense' THEN amount - :amount"
            + " WHEN :type = 'Income' THEN amount + :amount"
            + " END WHERE id = :accountId")
    abstract void updateAccount(String type, double amount, int accountId);

    @Query("UPDATE account_table SET amount = CASE WHEN (SELECT type FROM transaction_table WHERE id = :transactionId) = 'Expense' THEN amount + (SELECT amount FROM transaction_table WHERE id = :transactionId)"
            + " WHEN (SELECT type FROM transaction_table WHERE id = :transactionId) = 'Income' THEN amount - (SELECT amount FROM transaction_table WHERE id = :transactionId) END"
            + " WHERE id = (SELECT account_id FROM transaction_table WHERE id = :transactionId)")
    abstract void updateAccountBefore(int transactionId);

    @Update
    abstract void updateTransaction(TransactionItem transactionItem);

    @Transaction
    void insertTransactionUpdateAccount(TransactionItem transactionItem) {
        insertTransaction(transactionItem);
        updateAccount(transactionItem.getType(), transactionItem.getAmount(), transactionItem.getAccountId());
    }

    @Transaction
    void updateTransactionUpdateAccount(TransactionItem transactionItem) {
        updateAccountBefore(transactionItem.getId());
        updateTransaction(transactionItem);
        updateAccount(transactionItem.getType(), transactionItem.getAmount(), transactionItem.getAccountId());
    }
}

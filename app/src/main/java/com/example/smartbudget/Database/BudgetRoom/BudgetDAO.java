package com.example.smartbudget.Database.BudgetRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.smartbudget.Database.Model.SumBudget;

import java.util.List;

@Dao
public interface BudgetDAO {

    @Query("SELECT id, description, amount, start_date AS startDate, end_date AS endDate, account_id AS accountId, "
            + "(SELECT SUM(amount) FROM transaction_table"
            + " WHERE type LIKE X.type AND account_id LIKE X.account_id AND date BETWEEN DATE(X.start_date) AND DATE(X.end_date)) AS sumTransaction"
            + " FROM budget_table X WHERE end_date >= DATE(:date)")
    List<SumBudget> getRunningBudgets(String date);

    @Query("SELECT id, description, amount, start_date AS startDate, end_date AS endDate, account_id AS accountId, "
            + "(SELECT SUM(amount) FROM transaction_table"
            + " WHERE type LIKE X.type AND account_id LIKE X.account_id AND date BETWEEN DATE(X.start_date) AND DATE(X.end_date)) AS sumTransaction"
            + " FROM budget_table X WHERE end_date < DATE(:date)")
    List<SumBudget> getExpiredBudgets(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBudget(BudgetItem... budgetItems);

    @Delete
    void deleteBudget(BudgetItem budgetItem);

}

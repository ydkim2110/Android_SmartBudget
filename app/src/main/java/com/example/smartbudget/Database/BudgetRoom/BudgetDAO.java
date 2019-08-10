package com.example.smartbudget.Database.BudgetRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BudgetDAO {

    @Query("SELECT id, description, amount, start_date, end_date, account_id"
            + " FROM budget_table WHERE end_date >= DATE(:date)")
    List<BudgetItem> getRunningBudgets(String date);

    @Query("SELECT * FROM budget_table WHERE end_date < DATE(:date)")
    List<BudgetItem> getExpiredBudgets(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBudget(BudgetItem... budgetItems);

    @Delete
    void deleteBudget(BudgetItem budgetItem);

}

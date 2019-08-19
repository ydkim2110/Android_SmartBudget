package com.example.smartbudget.Database.ExpenseBudgetRoom;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseBudgetDAO {
    @Query("SELECT * FROM expense_budget_table")
    List<ExpenseBudgetItem> getExpenseBudgetList();

    @Update
    void updateExpenseBudget(ExpenseBudgetItem expenseBudgetItem);
}

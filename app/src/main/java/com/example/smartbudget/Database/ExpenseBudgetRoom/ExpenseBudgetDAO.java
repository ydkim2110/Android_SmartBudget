package com.example.smartbudget.Database.ExpenseBudgetRoom;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExpenseBudgetDAO {
    @Query("SELECT * FROM expense_budget_table")
    List<ExpenseBudgetItem> getExpenseBudgetList();
}

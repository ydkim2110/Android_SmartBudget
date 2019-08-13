package com.example.smartbudget.Database.ExpenseBudgetRoom;

import androidx.room.Query;

import java.util.List;

public interface ExpenseBudgetDAO {
    @Query("SELECT * FROM expense_budget_table")
    List<ExpenseBudgetItem> getExpenseBudgetList();
}

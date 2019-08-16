package com.example.smartbudget.Interface;

import com.example.smartbudget.Database.ExpenseBudgetRoom.ExpenseBudgetItem;

import java.util.List;

public interface IExpenseBudgetLoadListener {
    void onExpenseBudgetLoadSuccess(List<ExpenseBudgetItem> expenseBudgetItemList);
    void onExpenseBudgetLoadFailed(String message);
}

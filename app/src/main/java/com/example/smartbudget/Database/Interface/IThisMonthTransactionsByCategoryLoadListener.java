package com.example.smartbudget.Database.Interface;

import com.example.smartbudget.Database.Model.ExpenseByCategory;

import java.util.List;

public interface IThisMonthTransactionsByCategoryLoadListener {
    void onThisMonthTransactionByCategoryLoadSuccess(List<ExpenseByCategory> expenseByCategoryList);
    void onThisMonthTransactionByCategoryLoadFailed(String message);
}

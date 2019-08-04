package com.example.smartbudget.Interface;

import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Database.Model.SpendingPattern;
import com.example.smartbudget.Model.TransactionModel;

import java.util.List;

public interface IThisMonthTransactionByCategoryLoadListener {
    void onThisMonthTransactionByCategoryLoadSuccess(List<ExpenseByCategory> expenseByCategoryList);
    void onThisMonthTransactionByCategoryLoadFailed(String message);
}

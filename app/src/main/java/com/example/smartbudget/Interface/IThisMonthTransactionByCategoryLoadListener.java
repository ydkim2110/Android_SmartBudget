package com.example.smartbudget.Interface;

import com.example.smartbudget.Database.Model.SpendingPattern;
import com.example.smartbudget.Model.TransactionModel;

import java.util.List;

public interface IThisMonthTransactionByCategoryLoadListener {
    void onThisMonthTransactionByCategoryLoadSuccess(List<TransactionModel> transactionModelList);
    void onThisMonthTransactionByCategoryLoadFailed(String message);
}

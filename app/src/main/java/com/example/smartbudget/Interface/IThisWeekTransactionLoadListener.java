package com.example.smartbudget.Interface;

import com.example.smartbudget.Model.TransactionModel;

import java.util.List;

public interface IThisWeekTransactionLoadListener {
    void onThisWeekTransactionLoadSuccess(List<TransactionModel> transactionList);
    void onThisWeekTransactionDeleteSuccess(boolean isSuccess);
}

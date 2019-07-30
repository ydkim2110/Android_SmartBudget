package com.example.smartbudget.Interface;

import com.example.smartbudget.Model.TransactionModel;

import java.util.List;

public interface IThisMonthTransactionLoadListener {
    void onTransactionLoadSuccess(List<TransactionModel> transactionList);
    void onTransactionDeleteSuccess(boolean isSuccess);
}

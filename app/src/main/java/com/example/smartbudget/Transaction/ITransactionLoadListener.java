package com.example.smartbudget.Transaction;

import com.example.smartbudget.Database.Model.TransactionModel;

import java.util.List;

public interface ITransactionLoadListener {
    void onTransactionLoadSuccess(List<TransactionModel> transactionList);
    void onTransactionDeleteSuccess(boolean isSuccess);
}

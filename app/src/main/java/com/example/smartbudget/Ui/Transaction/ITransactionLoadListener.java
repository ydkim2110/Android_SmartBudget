package com.example.smartbudget.Ui.Transaction;

import com.example.smartbudget.Model.TransactionModel;

import java.util.List;

public interface ITransactionLoadListener {
    void onTransactionLoadSuccess(List<TransactionModel> transactionList);
    void onTransactionDeleteSuccess(boolean isSuccess);
}

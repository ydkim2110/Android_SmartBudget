package com.example.smartbudget.Database.Interface;

import com.example.smartbudget.Database.TransactionRoom.TransactionItem;

import java.util.List;

public interface ILastFewDaysTransactionsLoadListener {
    void onLastFewDaysTransactionsLoadSuccess(List<TransactionItem> transactionItemList);
    void onLastFewDaysTransactionsFailed(String message);
}

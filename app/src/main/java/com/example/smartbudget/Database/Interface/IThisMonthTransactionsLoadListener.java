package com.example.smartbudget.Database.Interface;

import com.example.smartbudget.Database.TransactionRoom.TransactionItem;

import java.util.List;

public interface IThisMonthTransactionsLoadListener {
    void onThisMonthTransactionsLoadSuccess(List<TransactionItem> transactionItemList);
    void onThisMonthTransactionsLoadFailed(String message);
}

package com.example.smartbudget.Database.Interface;

import com.example.smartbudget.Database.TransactionRoom.TransactionItem;

import java.util.List;

public interface ITransactionsLoadListener {
    void onTransactionsLoadSuccess(List<TransactionItem> transactionItemList);
    void onTransactionsLoadFailed(String message);
}

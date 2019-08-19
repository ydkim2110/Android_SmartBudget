package com.example.smartbudget.Database.Interface;

import com.example.smartbudget.Database.Model.SumTransactionByDate;
import com.example.smartbudget.Database.TransactionRoom.TransactionItem;

import java.util.List;

public interface ISumTransactionByDateLoadListener {
    void onSumTransactionByDateLoadSuccess(List<SumTransactionByDate> sumTransactionByDateList);
    void onSumTransactionByDateLoadFailed(String message);
}

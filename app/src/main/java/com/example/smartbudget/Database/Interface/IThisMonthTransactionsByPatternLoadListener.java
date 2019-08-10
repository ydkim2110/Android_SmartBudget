package com.example.smartbudget.Database.Interface;

import com.example.smartbudget.Database.Model.SpendingByPattern;

import java.util.List;

public interface IThisMonthTransactionsByPatternLoadListener {
    void onThisMonthTransactionsByPatternLoadSuccess(List<SpendingByPattern> spendingPatterns);
    void onThisMonthTransactionsByPatternLoadFailed(String message);
}

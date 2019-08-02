package com.example.smartbudget.Interface;

import com.example.smartbudget.Database.Model.SpendingPattern;

import java.util.List;

public interface IThisMonthTransactionByPatternLoadListener {
    void onThisMonthTransactionByPatternLoadSuccess(List<SpendingPattern> spendingPatternList);
    void onThisMonthTransactionByPatternLoadFailed(String message);
}

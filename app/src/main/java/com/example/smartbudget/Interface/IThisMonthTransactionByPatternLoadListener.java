package com.example.smartbudget.Interface;

import com.example.smartbudget.Database.Model.SpendingByPattern;

import java.util.List;

public interface IThisMonthTransactionByPatternLoadListener {
    void onThisMonthTransactionByPatternLoadSuccess(List<SpendingByPattern> spendingPatternList);
    void onThisMonthTransactionByPatternLoadFailed(String message);
}

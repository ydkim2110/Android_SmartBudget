package com.example.smartbudget.Ui.Home;

import com.example.smartbudget.Database.Model.SpendingPattern;

import java.util.List;

public interface IThisMonthTransactionPatternLoadListener {
    void onThisMonthTransactionPatternLoadSuccess(List<SpendingPattern> spendingPatternList);
    void onThisMonthTransactionPatternLoadFailed(String message);
}

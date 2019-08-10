package com.example.smartbudget.Interface;

import com.example.smartbudget.Model.BudgetModel;

import java.util.List;

public interface IBudgetLoadListener {
    void onBudgetLoadSuccess(List<BudgetModel> budgetModel);
    void onBudgetLoadFailed(String message);
}

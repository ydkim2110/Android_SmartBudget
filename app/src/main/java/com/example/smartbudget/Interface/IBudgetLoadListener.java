package com.example.smartbudget.Interface;

import com.example.smartbudget.Database.BudgetRoom.BudgetItem;
import com.example.smartbudget.Database.Model.SumBudget;
import com.example.smartbudget.Model.BudgetModel;

import java.util.List;

public interface IBudgetLoadListener {
    void onBudgetLoadSuccess(List<SumBudget> budgetItemList);
    void onBudgetLoadFailed(String message);
}

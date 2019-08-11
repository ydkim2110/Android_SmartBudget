package com.example.smartbudget.Database.Interface;

public interface IBudgetInsertListener {
    void onBudgetInsertSuccess(Boolean isInserted);
    void onBudgetInsertFailed(String message);
}

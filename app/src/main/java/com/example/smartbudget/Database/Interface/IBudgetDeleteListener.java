package com.example.smartbudget.Database.Interface;

public interface IBudgetDeleteListener {
    void onBudgetDeleteSuccess(boolean isDeleted);
    void onBudgetDeleteFailed(String message);
}

package com.example.smartbudget.Database.Interface;

public interface ITransactionDeleteListener {
    void onTransactionDeleteSuccess(boolean isDeleted);
    void onTransactionDeleteFailed(String message);
}

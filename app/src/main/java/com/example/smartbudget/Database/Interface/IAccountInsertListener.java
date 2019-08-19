package com.example.smartbudget.Database.Interface;

public interface IAccountInsertListener {
    void onAccountInsertSuccess(Boolean isInserted);
    void onAccountInsertFailed(String message);
}

package com.example.smartbudget.Interface;

public interface IDBInsertListener {
    void onDBInsertSuccess(Boolean isInserted);
    void onDBInsertFailed(String message);
}

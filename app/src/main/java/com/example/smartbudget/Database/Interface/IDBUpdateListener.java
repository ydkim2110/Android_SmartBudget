package com.example.smartbudget.Database.Interface;

public interface IDBUpdateListener {
    void onDBUpdateSuccess(boolean isUpdated);
    void onDBUpdateFailed(String message);
}

package com.example.smartbudget.Interface;

public interface IDBUpdateListener {
    void onDBUpdateSuccess(boolean isUpdated);
    void onDBUpdateFailed(String message);
}

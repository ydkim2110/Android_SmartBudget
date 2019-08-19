package com.example.smartbudget.Database.Interface;

public interface IAccountUpdateListener {
    void onAccountUpdateSuccess(boolean isUpdated);
    void onAccountUpdateFailed(String message);
}

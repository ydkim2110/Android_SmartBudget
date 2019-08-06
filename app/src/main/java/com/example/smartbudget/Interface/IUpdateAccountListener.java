package com.example.smartbudget.Interface;

public interface IUpdateAccountListener {
    void onUpdateAccountSuccess(boolean isUpdate);
    void onUpdateAccountFailed(String message);
}

package com.example.smartbudget.Interface;

import com.example.smartbudget.Model.AccountModel;

public interface IAccountDeleteListener {
    void onAccountDeleteSuccess(boolean isDeleted);
    void onAccountDeleteFailed(String message);
}

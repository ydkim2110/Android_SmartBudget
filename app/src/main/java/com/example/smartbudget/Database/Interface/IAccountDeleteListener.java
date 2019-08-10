package com.example.smartbudget.Database.Interface;

import com.example.smartbudget.Model.AccountModel;

public interface IAccountDeleteListener {
    void onAccountDeleteSuccess(boolean isDeleted);
    void onAccountDeleteFailed(String message);
}

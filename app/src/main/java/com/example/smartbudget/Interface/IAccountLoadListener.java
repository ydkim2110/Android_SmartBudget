package com.example.smartbudget.Interface;

import com.example.smartbudget.Model.AccountModel;

import java.util.List;

public interface IAccountLoadListener {
    void onAccountLoadSuccess(AccountModel accountModel);
    void onAccountLoadFailed(String message);
}

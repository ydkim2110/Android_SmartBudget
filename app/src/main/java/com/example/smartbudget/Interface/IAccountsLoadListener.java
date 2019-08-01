package com.example.smartbudget.Interface;

import com.example.smartbudget.Model.AccountModel;

import java.util.List;

public interface IAccountsLoadListener {
    void onAccountsLoadSuccess(List<AccountModel> accountList);
    void onAccountDeleteSuccess(boolean isSuccess);
}

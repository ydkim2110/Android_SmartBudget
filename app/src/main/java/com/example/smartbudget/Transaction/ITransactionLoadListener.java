package com.example.smartbudget.Account;

import com.example.smartbudget.Database.Model.AccountModel;

import java.util.List;

public interface IAccountLoadListener {
    void onAccountLoadSuccess(List<AccountModel> accountList);
    void onAccountDeleteSuccess(boolean isSuccess);
}

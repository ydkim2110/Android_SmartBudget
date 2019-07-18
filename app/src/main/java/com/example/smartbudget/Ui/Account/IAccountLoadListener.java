package com.example.smartbudget.Ui.Account;

import com.example.smartbudget.Model.AccountModel;

import java.util.List;

public interface IAccountLoadListener {
    void onAccountLoadSuccess(List<AccountModel> accountList);
    void onAccountDeleteSuccess(boolean isSuccess);
}

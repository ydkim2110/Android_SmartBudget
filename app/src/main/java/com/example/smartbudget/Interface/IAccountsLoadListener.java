package com.example.smartbudget.Interface;

import com.example.smartbudget.Database.AccountRoom.AccountItem;
import com.example.smartbudget.Model.AccountModel;

import java.util.List;

public interface IAccountsLoadListener {
    void onAccountsLoadSuccess(List<AccountItem> accountList);
    void onAccountDeleteSuccess(boolean isSuccess);
}

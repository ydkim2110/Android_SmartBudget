package com.example.smartbudget.Database.Interface;

import com.example.smartbudget.Database.AccountRoom.AccountItem;

import java.util.List;

public interface IAccountsLoadListener {
    void onAccountsLoadSuccess(List<AccountItem> accountItemList);
    void onAccountsLoadFailed(String message);
}

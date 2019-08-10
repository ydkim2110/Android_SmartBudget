package com.example.smartbudget.Database.Interface;

import com.example.smartbudget.Database.AccountRoom.AccountItem;

public interface IAccountLoadListener {
    void onAccountLoadSuccess(AccountItem accountItem);
    void onAccountLoadFailed(String message);
}

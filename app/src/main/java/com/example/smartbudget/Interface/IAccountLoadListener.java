package com.example.smartbudget.Interface;

import com.example.smartbudget.Database.AccountRoom.AccountItem;
import com.example.smartbudget.Model.AccountModel;

import java.util.List;

public interface IAccountLoadListener {
    void onAccountLoadSuccess(AccountItem accountItem);
    void onAccountLoadFailed(String message);
}

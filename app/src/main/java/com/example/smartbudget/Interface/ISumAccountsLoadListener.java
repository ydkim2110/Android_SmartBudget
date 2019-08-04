package com.example.smartbudget.Interface;

import com.example.smartbudget.Model.AccountModel;

import java.util.List;

public interface ISumAccountsLoadListener {
    void onSumAccountsLoadSuccess(List<AccountModel> accountModelList);
    void onSumAccountsLoadFailed(String message);
}

package com.example.smartbudget.Account;

import java.util.List;

public interface IAccountLoadListener {
    void onAccountLoadSuccess(List<Account> accountList);
}

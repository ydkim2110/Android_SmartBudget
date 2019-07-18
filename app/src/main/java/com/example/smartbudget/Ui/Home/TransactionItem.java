package com.example.smartbudget.Ui.Home;

import com.example.smartbudget.Model.TransactionModel;

public class TransactionItem extends ListItem {

    private TransactionModel transactionModel;

    public TransactionModel getTransaction() {
        return transactionModel;
    }

    public void setTransaction(TransactionModel transactionModel) {
        this.transactionModel = transactionModel;
    }

    @Override
    public int getType() {
        return TYPE_TRANSACTION;
    }
}

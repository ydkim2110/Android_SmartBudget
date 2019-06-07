package com.example.smartbudget.Home;

public class TransactionItem extends ListItem {

    private Transaction transaction;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public int getType() {
        return TYPE_TRANSACTION;
    }
}

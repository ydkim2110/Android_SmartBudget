package com.example.smartbudget.Model.EventBus;

public class DeleteTransactionFromAddEvent {
    private boolean success;

    public DeleteTransactionFromAddEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}

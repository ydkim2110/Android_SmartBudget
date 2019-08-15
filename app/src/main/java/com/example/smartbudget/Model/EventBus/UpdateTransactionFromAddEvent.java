package com.example.smartbudget.Model.EventBus;

public class UpdateTransactionFromAddEvent {
    private boolean success;

    public UpdateTransactionFromAddEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}

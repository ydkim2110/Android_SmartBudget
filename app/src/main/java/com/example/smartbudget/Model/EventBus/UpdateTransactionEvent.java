package com.example.smartbudget.Model.EventBus;

public class UpdateTransactionEvent {
    private boolean success;

    public UpdateTransactionEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}

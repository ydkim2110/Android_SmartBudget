package com.example.smartbudget.Model.EventBus;

public class DeleteTransactionEvent {
    private boolean success;

    public DeleteTransactionEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}

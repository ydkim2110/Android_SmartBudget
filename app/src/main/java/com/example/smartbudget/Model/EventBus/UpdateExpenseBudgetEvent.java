package com.example.smartbudget.Model.EventBus;

public class UpdateExpenseBudgetEvent {
    private boolean success;

    public UpdateExpenseBudgetEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}

package com.example.smartbudget.Model.EventBus;

public class AddBudgetEvent {
    private boolean success;

    public AddBudgetEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}

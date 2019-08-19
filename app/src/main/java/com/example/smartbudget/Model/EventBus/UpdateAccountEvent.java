package com.example.smartbudget.Model.EventBus;

public class UpdateAccountEvent {
    private boolean success;

    public UpdateAccountEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}

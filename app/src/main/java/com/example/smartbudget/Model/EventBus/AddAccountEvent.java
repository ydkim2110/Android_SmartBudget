package com.example.smartbudget.Model.EventBus;

public class AddAccountEvent {
    private boolean success;

    public AddAccountEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}

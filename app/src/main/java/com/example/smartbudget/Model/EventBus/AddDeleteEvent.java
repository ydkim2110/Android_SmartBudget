package com.example.smartbudget.Model.EventBus;

public class AddDeleteEvent {
    private boolean success;

    public AddDeleteEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}

package com.example.smartbudget.Model.EventBus;

public class CalendarToggleEvent {
    private int type;

    public CalendarToggleEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

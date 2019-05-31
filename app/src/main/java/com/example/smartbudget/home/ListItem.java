package com.example.smartbudget.home;

public abstract class ListItem {
    public static final int TYPE_DATE = 0;
    public static final int TYPE_TRANSACTION = 1;

    public abstract int getType();
}

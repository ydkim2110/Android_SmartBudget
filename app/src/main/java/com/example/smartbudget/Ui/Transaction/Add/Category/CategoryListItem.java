package com.example.smartbudget.Ui.Transaction.Add.Category;

public abstract class CategoryListItem {
    public static final int TYPE_CATEGORY = 0;
    public static final int TYPE_SUB_CATEGORY = 1;

    public abstract int getType();
}

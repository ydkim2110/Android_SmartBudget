package com.example.smartbudget.Ui.Transaction.Add.Category;

import com.example.smartbudget.Model.Category;

public class CategoryItem extends CategoryListItem {

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int getType() {
        return TYPE_CATEGORY;
    }
}

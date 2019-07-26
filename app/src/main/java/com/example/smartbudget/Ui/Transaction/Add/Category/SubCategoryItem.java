package com.example.smartbudget.Ui.Transaction.Add.Category;

import com.example.smartbudget.Model.SubCategory;

public class SubCategoryItem extends CategoryListItem {

    private SubCategory subCategory;

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    @Override
    public int getType() {
        return TYPE_SUB_CATEGORY;
    }
}

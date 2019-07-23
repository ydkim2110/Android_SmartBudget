package com.example.smartbudget.Ui.Transaction.Add;

import com.example.smartbudget.Model.CategoryModel;

import java.util.List;

public interface ICategoryLoadListener {
    void onCategoryLoadSuccess(List<CategoryModel> categoryList);
    void onCategoryLoadFailed(String message);
}

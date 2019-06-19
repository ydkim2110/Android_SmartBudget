package com.example.smartbudget.AddTransaction;

import com.example.smartbudget.AddTransaction.Category.Category;
import com.example.smartbudget.Database.Model.CategoryModel;

import java.util.List;

public interface ICategoryLoadListener {
    void onCategoryLoadSuccess(List<CategoryModel> categoryList);
}

package com.example.smartbudget.AddTransaction;

import com.example.smartbudget.AddTransaction.Category.Category;

import java.util.List;

public interface ICategoryLoadListener {
    void onCategoryLoadSuccess(List<Category> categoryList);
}

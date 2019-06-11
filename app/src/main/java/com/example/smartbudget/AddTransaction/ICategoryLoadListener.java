package com.example.smartbudget.AddTransaction;

import com.example.smartbudget.AddTransaction.Dialog.Category;

import java.util.List;

public interface ICategoryLoadListener {
    void onCategoryLoadSuccess(List<Category> categoryList);
}

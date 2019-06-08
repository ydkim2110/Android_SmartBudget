package com.example.smartbudget.Transaction;

import com.example.smartbudget.Transaction.Dialog.Category;

import java.util.List;

public interface ICategoryLoadListener {
    void onCategoryLoadSuccess(List<Category> categoryList);
}

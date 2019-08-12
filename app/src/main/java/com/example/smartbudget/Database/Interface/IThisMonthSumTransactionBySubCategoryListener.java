package com.example.smartbudget.Database.Interface;

import com.example.smartbudget.Database.Model.SumTransactionBySubCategory;

import java.util.List;

public interface IThisMonthSumTransactionBySubCategoryListener {
    void onThisMonthSumTransactionBySubCategorySuccess(List<SumTransactionBySubCategory> sumTransactionBySumCategoryList);
    void onThisMonthSumTransactionBySubCategoryFailed(String message);
}

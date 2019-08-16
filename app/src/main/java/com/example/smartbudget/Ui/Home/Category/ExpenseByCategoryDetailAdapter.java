package com.example.smartbudget.Ui.Home.Category;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Database.Model.SumTransactionBySubCategory;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.DefaultCategories;
import com.example.smartbudget.Model.SubCategory;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ExpenseByCategoryDetailAdapter extends RecyclerView.Adapter<ExpenseByCategoryDetailAdapter.MyViewHolder> {

    private static final String TAG = ExpenseByCategoryDetailAdapter.class.getSimpleName();

    private Context mContext;
    private List<SumTransactionBySubCategory> mSumTransactionBySubCategoryList;
    private double total;

    private String moneyUnit;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ExpenseByCategoryDetailAdapter(Context mContext, List<SumTransactionBySubCategory> sumTransactionBySubCategoryList) {
        this.mContext = mContext;
        this.mSumTransactionBySubCategoryList = sumTransactionBySubCategoryList;

        moneyUnit = mContext.getResources().getString(R.string.money_unit);

        Collections.sort(sumTransactionBySubCategoryList, (o1, o2) -> {
            if (o1.getSumBySubCategory() > o2.getSumBySubCategory())
                return -1;
            else if (o1.getSumBySubCategory() < o2.getSumBySubCategory())
                return 1;
            else
                return 0;
        });

        total = sumTransactionBySubCategoryList.stream().mapToDouble(SumTransactionBySubCategory::getSumBySubCategory).sum();
    }

    private SubCategory subCategory;
    private Category category;
    ;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_sub, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (Common.getExpenseSubCategory(mSumTransactionBySubCategoryList.get(position).getSubCategoryId()) != null) {
            subCategory = Common.getExpenseSubCategory(mSumTransactionBySubCategoryList.get(position).getSubCategoryId());
            holder.tv_sub_category_name.setText(subCategory.getCategoryVisibleName(mContext));
        } else {
            category = Common.getExpenseCategory(mSumTransactionBySubCategoryList.get(position).getSubCategoryId());
            holder.tv_sub_category_name.setText(category.getCategoryVisibleName(mContext));
        }

        holder.tv_amount.setText(new StringBuilder(Common.changeNumberToComma((int) mSumTransactionBySubCategoryList.get(position).getSumBySubCategory()))
                .append(moneyUnit));

        holder.pb_circle.setMax((int) total);
        ObjectAnimator progressAnim = ObjectAnimator.ofInt(holder.pb_circle, "progress", 0, (int) mSumTransactionBySubCategoryList.get(position).getSumBySubCategory());
        progressAnim.setDuration(1500);
        progressAnim.setInterpolator(new LinearInterpolator());
        progressAnim.start();
    }

    @Override
    public int getItemCount() {
        return mSumTransactionBySubCategoryList != null ? mSumTransactionBySubCategoryList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_sub_category_name)
        TextView tv_sub_category_name;
        @BindView(R.id.tv_amount)
        TextView tv_amount;
        @BindView(R.id.pb_circle)
        ProgressBar pb_circle;

        Unbinder mUnbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mUnbinder = ButterKnife.bind(this, itemView);
        }
    }
}

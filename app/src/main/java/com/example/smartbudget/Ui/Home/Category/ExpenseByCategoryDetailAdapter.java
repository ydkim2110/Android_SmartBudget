package com.example.smartbudget.Ui.Home.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Database.Model.SumTransactionBySubCategory;
import com.example.smartbudget.Model.SubCategory;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ExpenseByCategoryDetailAdapter extends RecyclerView.Adapter<ExpenseByCategoryDetailAdapter.MyViewHolder> {

    private Context mContext;
    private List<SumTransactionBySubCategory> sumTransactionBySubCategoryList;

    public ExpenseByCategoryDetailAdapter(Context mContext, List<SumTransactionBySubCategory> mTransactionItemList) {
        this.mContext = mContext;
        this.sumTransactionBySubCategoryList = mTransactionItemList;
        Collections.sort(sumTransactionBySubCategoryList, new Comparator<SumTransactionBySubCategory>() {
            @Override
            public int compare(SumTransactionBySubCategory o1, SumTransactionBySubCategory o2) {
                if (o1.getSumBySubCategory() > o2.getSumBySubCategory())
                    return -1;
                else if (o1.getSumBySubCategory() < o2.getSumBySubCategory())
                    return 1;
                else
                    return 0;
            }
        });
    }

    private SubCategory subCategory;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_sub, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        subCategory = Common.getExpenseSubCategory(sumTransactionBySubCategoryList.get(position).getSubCategoryId());
        holder.tv_sub_category_name.setText(subCategory.getCategoryVisibleName(mContext));
        holder.tv_amount.setText(new StringBuilder(Common.changeNumberToComma((int) sumTransactionBySubCategoryList.get(position).getSumBySubCategory())).append("원"));
    }

    @Override
    public int getItemCount() {
        return sumTransactionBySubCategoryList != null ? sumTransactionBySubCategoryList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_sub_category_name)
        TextView tv_sub_category_name;
        @BindView(R.id.tv_amount)
        TextView tv_amount;

        Unbinder mUnbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mUnbinder = ButterKnife.bind(this, itemView);
        }
    }
}

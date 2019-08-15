package com.example.smartbudget.Ui.Home.Overview;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.R;
import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;
import com.example.smartbudget.Utils.Common;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OverviewCategoryAdapter extends RecyclerView.Adapter<OverviewCategoryAdapter.ViewHolder> {

    private Context mContext;
    private List<ExpenseByCategory> mCategoryList;
    private String passed_date;
    private int total;
    private String moneyUnit;
    private String percentageSign;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public OverviewCategoryAdapter(Context context, List<ExpenseByCategory> categoryList, String date) {
        mContext = context;
        mCategoryList = categoryList;
        passed_date = date;

        moneyUnit = mContext.getResources().getString(R.string.money_unit);
        percentageSign = mContext.getResources().getString(R.string.percentage_sign);

        Collections.sort(mCategoryList, (o1, o2) -> {
            if (o1.getSumByCategory() > o2.getSumByCategory())
                return -1;
            else if (o1.getSumByCategory() < o2.getSumByCategory())
                return 1;
            else
                return 0;
        });
        total = mCategoryList.stream().mapToInt(ExpenseByCategory::getSumByCategory).sum();
    }

    private Category category;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_overview_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        category = Common.getExpenseCategory(mCategoryList.get(i).getCategoryId());

        holder.tv_percentage.setText(new StringBuilder(Common.calcPercentage(mCategoryList.get(i).getSumByCategory(), total)).append(percentageSign));
        holder.tv_percentage.setBackgroundTintList(ColorStateList.valueOf(category.getIconColor()));
        holder.tv_category.setText(category.getCategoryVisibleName(mContext));
        holder.tv_amount.setText(new StringBuilder(Common.changeNumberToComma(mCategoryList.get(i).getSumByCategory())).append(moneyUnit));

        holder.setIRecyclerItemSelectedListener((view, position) -> {
            Intent intent = new Intent(mContext, TAListByCategoryActivity.class);
            intent.putExtra(TAListByCategoryActivity.EXTRA_PASSED_DATE, passed_date);
            intent.putExtra(TAListByCategoryActivity.EXTRA_CATEGORY_ID, mCategoryList.get(position).getCategoryId());
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_percentage)
        TextView tv_percentage;
        @BindView(R.id.tv_amount)
        TextView tv_amount;
        @BindView(R.id.tv_category)
        TextView tv_category;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener IRecyclerItemSelectedListener) {
            mIRecyclerItemSelectedListener = IRecyclerItemSelectedListener;
        }

        Unbinder mUnbinder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mUnbinder = ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelected(v, getAdapterPosition());
        }
    }
}

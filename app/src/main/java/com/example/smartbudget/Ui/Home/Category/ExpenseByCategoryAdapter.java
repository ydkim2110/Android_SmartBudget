package com.example.smartbudget.Ui.Home.Category;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ExpenseByCategoryAdapter extends RecyclerView.Adapter<ExpenseByCategoryAdapter.ViewHolder> {

    private static final String TAG = ExpenseByCategoryAdapter.class.getSimpleName();

    private Context mContext;
    private List<ExpenseByCategory> mExpenseByCategoryList;
    private String passed_date;
    private String moneyUnit;
    private String percentageSign;

    public ExpenseByCategoryAdapter(Context context, List<ExpenseByCategory> expenseByCategoryList, String passed_date) {
        this.mContext = context;
        this.mExpenseByCategoryList = expenseByCategoryList;
        this.passed_date = passed_date;

        moneyUnit = mContext.getResources().getString(R.string.money_unit);
        percentageSign = mContext.getResources().getString(R.string.percentage_sign);

        Collections.sort(mExpenseByCategoryList, (o1, o2) -> {
            if (o1.getSumByCategory() > o2.getSumByCategory())
                return -1;
            else if (o1.getSumByCategory() < o2.getSumByCategory())
                return 1;
            else
                return 0;
        });
    }

    private Category category;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_expense_by_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        category = Common.getExpenseCategory(mExpenseByCategoryList.get(position).getCategoryId());

        int amount = 500000;

        holder.iv_category_icon.setImageResource(category.getIconResourceID());
        holder.iv_category_icon.setColorFilter(category.getIconColor());
        holder.pb_budget.setProgressTintList(ColorStateList.valueOf(category.getIconColor()));

        holder.tv_budget_category.setText(category.getCategoryVisibleName(mContext));
        holder.tv_budget_used.setText(new StringBuilder(Common.changeNumberToComma(mExpenseByCategoryList.get(position).getSumByCategory())).append(moneyUnit));
        holder.tv_budget_total.setText(new StringBuilder(Common.changeNumberToComma(amount)).append(moneyUnit));
        holder.tv_budget_percentage.setText(new StringBuilder(Common.calcPercentage(mExpenseByCategoryList.get(position).getSumByCategory(), amount)).append(percentageSign));
        holder.tv_budget_total_count.setText(new StringBuilder("(").append(""+mExpenseByCategoryList.get(position).getCountByCategory()).append(")"));

        holder.pb_budget.setMax(amount);
        ObjectAnimator progressAnim = ObjectAnimator.ofInt(holder.pb_budget, "progress", 0, mExpenseByCategoryList.get(position).getSumByCategory());
        progressAnim.setDuration(1000);
        progressAnim.setInterpolator(new LinearInterpolator());
        progressAnim.start();

        holder.setIRecyclerItemSelectedListener((view, i) -> {
            Intent intent = new Intent(view.getContext(), ExpenseByCategoryDetailActivity.class);
            intent.putExtra(ExpenseByCategoryDetailActivity.EXTRA_PASSED_DATE, passed_date);
            intent.putExtra(ExpenseByCategoryDetailActivity.EXTRA_CATEGORY_ID, mExpenseByCategoryList.get(i).getCategoryId());
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mExpenseByCategoryList != null ? mExpenseByCategoryList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_budget_icon)
        ImageView iv_category_icon;
        @BindView(R.id.tv_budget_category)
        TextView tv_budget_category;
        @BindView(R.id.pb_budget)
        ProgressBar pb_budget;
        @BindView(R.id.tv_budget_used)
        TextView tv_budget_used;
        @BindView(R.id.tv_budget_total)
        TextView tv_budget_total;
        @BindView(R.id.tv_budget_percentage)
        TextView tv_budget_percentage;
        @BindView(R.id.tv_budget_detail)
        TextView tv_budget_detail;
        @BindView(R.id.tv_budget_total_count)
        TextView tv_budget_total_count;

        Unbinder mUnbinder;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener IRecyclerItemSelectedListener) {
            mIRecyclerItemSelectedListener = IRecyclerItemSelectedListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mUnbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

            tv_budget_detail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelected(v, getAdapterPosition());
        }
    }
}

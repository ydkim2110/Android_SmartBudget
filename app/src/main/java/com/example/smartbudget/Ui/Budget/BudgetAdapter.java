package com.example.smartbudget.Ui.Budget;

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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    private static final String TAG = BudgetAdapter.class.getSimpleName();

    private Context mContext;
    private List<ExpenseByCategory> mExpenseByCategoryList;

    public BudgetAdapter(Context context, List<ExpenseByCategory> expenseByCategoryList) {
        this.mContext = context;
        this.mExpenseByCategoryList = expenseByCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_expense_by_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Category category = Common.getExpenseCategory(mExpenseByCategoryList.get(position).getCategoryId());

        int amount = 500000;

        holder.iv_category_icon.setImageResource(category.getIconResourceID());
        holder.iv_category_icon.setColorFilter(category.getIconColor());
        holder.pb_budget.setProgressTintList(ColorStateList.valueOf(category.getIconColor()));

        holder.tv_budget_category.setText(category.getCategoryVisibleName(mContext));
        holder.tv_budget_used.setText(new StringBuilder(Common.changeNumberToComma(mExpenseByCategoryList.get(position).getSumByCategory())).append("원"));
        holder.tv_budget_total.setText(new StringBuilder(Common.changeNumberToComma(amount)).append("원"));
        holder.tv_budget_percentage.setText(new StringBuilder(Common.calcPercentage(mExpenseByCategoryList.get(position).getSumByCategory(), amount)).append("%"));
        holder.tv_budget_total_count.setText(new StringBuilder("(").append(""+mExpenseByCategoryList.get(position).getCountByCategory()).append(")"));

        holder.pb_budget.setMax(amount);
        ObjectAnimator progressAnim = ObjectAnimator.ofInt(holder.pb_budget, "progress", 0, mExpenseByCategoryList.get(position).getSumByCategory());
        progressAnim.setDuration(500);
        progressAnim.setInterpolator(new LinearInterpolator());
        progressAnim.start();

        holder.setIRecyclerItemSelectedListener((view, i) -> {
            Intent viewTransactionIntent = new Intent(view.getContext(), ViewTransactionActivity.class);
            viewTransactionIntent.putExtra(Common.EXTRA_PASS_BUDGET_CATEGORY, category.getCategoryVisibleName(mContext));
            view.getContext().startActivity(viewTransactionIntent);
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

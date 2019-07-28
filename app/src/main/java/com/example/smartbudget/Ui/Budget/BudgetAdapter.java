package com.example.smartbudget.Ui.Budget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smartbudget.Model.Category;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    private Context mContext;
    private List<Category> mCategoryList;

    public BudgetAdapter(Context context, List<Category> categoryList) {
        this.mContext = context;
        this.mCategoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_budget, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String category = mCategoryList.get(position).getCategoryVisibleName(mContext);
        int amount = 4000000;

        holder.iv_category_icon.setImageResource(mCategoryList.get(position).getIconResourceID());
        holder.iv_category_icon.setColorFilter(mCategoryList.get(position).getIconColor());

        holder.setData(category, amount);

        holder.setIRecyclerItemSelectedListener((view, i) -> {
            Intent viewTransactionIntent = new Intent(view.getContext(), ViewTransactionActivity.class);
            viewTransactionIntent.putExtra(Common.EXTRA_PASS_BUDGET_CATEGORY, category);
            view.getContext().startActivity(viewTransactionIntent);
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
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

        private void setData(String category, int amount) {
            tv_budget_category.setText(category);
            tv_budget_total.setText(new StringBuilder(Common.changeNumberToComma(amount)).append("원"));
            tv_budget_percentage.setText(new StringBuilder(Common.calcPercentageDownToTwo(100000, amount)).append("%"));

            pb_budget.setMax(amount);
            ObjectAnimator progressAnim = ObjectAnimator.ofInt(pb_budget, "progress", 0, 100000);
            progressAnim.setDuration(500);
            progressAnim.setInterpolator(new LinearInterpolator());
            progressAnim.start();
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelected(v, getAdapterPosition());
        }
    }
}

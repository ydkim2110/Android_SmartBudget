package com.example.smartbudget.Ui.Budget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Model.BudgetModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BudgetListAdapter extends RecyclerView.Adapter<BudgetListAdapter.MyViewHolder> {

    private Context mContext;
    private List<BudgetModel> mBudgetModelList;

    public BudgetListAdapter(Context mContext, List<BudgetModel> mBudgetModelList) {
        this.mContext = mContext;
        this.mBudgetModelList = mBudgetModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_budget, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_name.setText(mBudgetModelList.get(position).getDescription());
        holder.tv_target_amount.setText(new StringBuilder(Common.changeNumberToComma((int) mBudgetModelList.get(position).getAmount())).append("원"));
        holder.tv_date_period.setText(new StringBuilder("(")
                .append(mBudgetModelList.get(position).getStartDate())
                .append(" ~ ")
                .append(mBudgetModelList.get(position).getEndDate())
                .append(")"));
    }

    @Override
    public int getItemCount() {
        return mBudgetModelList != null ? mBudgetModelList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_target_amount)
        TextView tv_target_amount;
        @BindView(R.id.tv_date_period)
        TextView tv_date_period;

        Unbinder mUnbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mUnbinder = ButterKnife.bind(this, itemView);
        }
    }
}

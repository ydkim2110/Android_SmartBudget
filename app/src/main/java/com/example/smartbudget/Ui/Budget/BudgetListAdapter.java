package com.example.smartbudget.Ui.Budget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Database.BudgetRoom.BudgetItem;
import com.example.smartbudget.Database.Interface.ISumAmountByBudgetListener;
import com.example.smartbudget.Database.Model.SumBudget;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static org.greenrobot.eventbus.EventBus.TAG;

public class BudgetListAdapter extends RecyclerView.Adapter<BudgetListAdapter.MyViewHolder>
        implements ISumAmountByBudgetListener {

    private static final String TAG = BudgetListAdapter.class.getSimpleName();

    private Context mContext;
    private List<SumBudget> mBudgetItemList;
    private SumBudget mBudgetItem;

    public BudgetListAdapter(Context mContext, List<SumBudget> budgetItemList) {
        this.mContext = mContext;
        this.mBudgetItemList = budgetItemList;
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

        mBudgetItem = mBudgetItemList.get(position);

        holder.tv_name.setText(mBudgetItem.getDescription());
        holder.tv_used_amount.setText(new StringBuilder(Common.changeNumberToComma((int) mBudgetItem.getSumTransaction())).append("원"));
        holder.tv_target_amount.setText(new StringBuilder(Common.changeNumberToComma((int) mBudgetItem.getAmount())).append("원"));
        holder.tv_date_period.setText(new StringBuilder("(")
                .append(mBudgetItem.getStartDate())
                .append(" ~ ")
                .append(mBudgetItem.getEndDate())
                .append(")"));

        holder.pb_circle.setMax((int) mBudgetItem.getAmount());
        ObjectAnimator progressAnim = ObjectAnimator.ofInt(holder.pb_circle, "progress", 0, (int) mBudgetItem.getSumTransaction());
        progressAnim.setDuration(500);
        progressAnim.setInterpolator(new LinearInterpolator());
        progressAnim.start();

        int percentage = (int) (mBudgetItem.getSumTransaction() / mBudgetItem.getAmount() * 100);
        holder.tv_percenage.setText(new StringBuilder(""+percentage).append("%"));

    }

    @Override
    public int getItemCount() {
        return mBudgetItemList != null ? mBudgetItemList.size() : 0;
    }

    @Override
    public void onSumAmountByBudgeSuccess(double sumAmount) {
            Log.d(TAG, "onSumAmountByBudgeSuccess: "+sumAmount);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_used_amount)
        TextView tv_used_amount;
        @BindView(R.id.tv_target_amount)
        TextView tv_target_amount;
        @BindView(R.id.tv_date_period)
        TextView tv_date_period;
        @BindView(R.id.pb_circle)
        ProgressBar pb_circle;
        @BindView(R.id.tv_percentage)
        TextView tv_percenage;

        Unbinder mUnbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mUnbinder = ButterKnife.bind(this, itemView);
        }
    }
}

package com.example.smartbudget.Ui.Budget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
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
import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;
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
        holder.tv_type.setText(mBudgetItem.getType());
        holder.tv_used_amount.setText(new StringBuilder(Common.changeNumberToComma((int) mBudgetItem.getSumTransaction())).append("원"));
        holder.tv_target_amount.setText(new StringBuilder(Common.changeNumberToComma((int) mBudgetItem.getAmount())).append("원"));
        holder.tv_left_amount.setText(new StringBuilder(Common.changeNumberToComma((int) (mBudgetItem.getSumTransaction() - mBudgetItem.getAmount())))
                .append("원"));

        if ((mBudgetItem.getSumTransaction() - mBudgetItem.getAmount()) > 0) {
            holder.tv_left_title.setText("Over Amount");
        }
        else {
            holder.tv_left_title.setText("Left Amount");
        }

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
        holder.tv_percenage.setText(new StringBuilder("" + percentage).append("%"));

        holder.setListener((view, i) -> {
            BudgetItem budgetItem = new BudgetItem();
            budgetItem.setId(mBudgetItemList.get(i).getId());
            budgetItem.setDescription(mBudgetItemList.get(i).getDescription());
            budgetItem.setAmount(mBudgetItemList.get(i).getAmount());
            budgetItem.setStartDate(mBudgetItemList.get(i).getStartDate());
            budgetItem.setEndDate(mBudgetItemList.get(i).getEndDate());
            budgetItem.setType(mBudgetItemList.get(i).getType());
            budgetItem.setAccountId(mBudgetItemList.get(i).getAccountId());

            Intent intent = new Intent(mContext, AddBudgetActivity.class);
            intent.putExtra(Common.EXTRA_EDIT_BUDGET, budgetItem);
            view.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return mBudgetItemList != null ? mBudgetItemList.size() : 0;
    }

    @Override
    public void onSumAmountByBudgeSuccess(double sumAmount) {
        Log.d(TAG, "onSumAmountByBudgeSuccess: " + sumAmount);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_type)
        TextView tv_type;
        @BindView(R.id.tv_used_amount)
        TextView tv_used_amount;
        @BindView(R.id.tv_target_amount)
        TextView tv_target_amount;
        @BindView(R.id.tv_left_amount)
        TextView tv_left_amount;
        @BindView(R.id.tv_left_title)
        TextView tv_left_title;
        @BindView(R.id.tv_date_period)
        TextView tv_date_period;
        @BindView(R.id.pb_circle)
        ProgressBar pb_circle;
        @BindView(R.id.tv_percentage)
        TextView tv_percenage;

        private IRecyclerItemSelectedListener mListener;

        public void setListener(IRecyclerItemSelectedListener listener) {
            mListener = listener;
        }

        Unbinder mUnbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mUnbinder = ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemSelected(v, getAdapterPosition());
        }
    }
}

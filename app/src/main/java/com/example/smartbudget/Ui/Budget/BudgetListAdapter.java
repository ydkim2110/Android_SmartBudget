package com.example.smartbudget.Ui.Budget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Database.BudgetRoom.BudgetItem;
import com.example.smartbudget.Database.Interface.ISumAmountByBudgetListener;
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
    private List<BudgetItem> mBudgetItemList;
    private BudgetItem mBudgetItem;

    public BudgetListAdapter(Context mContext, List<BudgetItem> budgetItemList) {
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

//        DBTransactionUtils.getSumAmountByBudget(MainActivity.db, mBudgetItem.getStartDate(), mBudgetItem.getEndDate(),
//                mBudgetItem.getType(), mBudgetItem.getAccountId(), this);

        Log.d(TAG, "onBindViewHolder: startDate: "+mBudgetItem.getStartDate());
        Log.d(TAG, "onBindViewHolder: endDate: "+mBudgetItem.getEndDate());

        DBTransactionUtils.getSumAmountByBudget(MainActivity.db, mBudgetItem.getStartDate(), mBudgetItem.getEndDate(),
                mBudgetItem.getType(), mBudgetItem.getAccountId(),this);

        holder.tv_name.setText(mBudgetItemList.get(position).getDescription());
        holder.tv_target_amount.setText(new StringBuilder(Common.changeNumberToComma((int) mBudgetItemList.get(position).getAmount())).append("원"));
        holder.tv_date_period.setText(new StringBuilder("(")
                .append(mBudgetItemList.get(position).getStartDate())
                .append(" ~ ")
                .append(mBudgetItemList.get(position).getEndDate())
                .append(")"));
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

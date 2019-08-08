package com.example.smartbudget.Ui.Transaction;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {

    private Context mContext;
    private List<TransactionModel> mTransactionModelList;
    private TransactionModel transaction;

    public TransactionListAdapter(Context context, List<TransactionModel> transactionModelList) {
        mContext = context;
        mTransactionModelList = transactionModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        transaction = mTransactionModelList.get(position);

        if (transaction.getType().equals("Expense")) {
            Category expenseCategory = Common.getExpenseCategory(mTransactionModelList.get(position).getCategoryId());
            holder.iv_category_icon.setImageResource(expenseCategory.getIconResourceID());
            holder.iv_category_icon.setColorFilter(expenseCategory.getIconColor());
            holder.tv_transaction_category.setText(expenseCategory.getCategoryVisibleName(mContext));

            holder.tv_transaction_note.setText(transaction.getNote());
            holder.tv_transaction_amount.setText(new StringBuilder("-").append(Common.changeNumberToComma((int) transaction.getAmount())).append("원"));
            holder.tv_transaction_amount.setTextColor(mContext.getResources().getColor(R.color.colorExpense));
            holder.tv_transaction_date.setText(transaction.getDate());
        }
        else if (transaction.getType().equals("Income")) {
            Category IncomeCategory = Common.getIncomeCategory(mTransactionModelList.get(position).getCategoryId());
            holder.iv_category_icon.setImageResource(IncomeCategory.getIconResourceID());
            holder.iv_category_icon.setColorFilter(IncomeCategory.getIconColor());
            holder.tv_transaction_category.setText(IncomeCategory.getCategoryVisibleName(mContext));

            holder.tv_transaction_note.setText(transaction.getNote());
            holder.tv_transaction_amount.setText(new StringBuilder(Common.changeNumberToComma((int) transaction.getAmount())).append("원"));
            holder.tv_transaction_amount.setTextColor(mContext.getResources().getColor(R.color.colorRevenue));
            holder.tv_transaction_date.setText(transaction.getDate());
        }

        holder.itemView.startAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left));

        holder.setIRecyclerItemSelectedListener((view, i) -> {
            Toast.makeText(mContext, "[SELECTED]" + transaction.getCategoryId(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mTransactionModelList != null ? mTransactionModelList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_category_icon)
        ImageView iv_category_icon;
        @BindView(R.id.tv_transaction_category)
        TextView tv_transaction_category;
        @BindView(R.id.tv_transaction_note)
        TextView tv_transaction_note;
        @BindView(R.id.tv_transaction_amount)
        TextView tv_transaction_amount;
        @BindView(R.id.tv_transaction_date)
        TextView tv_transaction_date;

        IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

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

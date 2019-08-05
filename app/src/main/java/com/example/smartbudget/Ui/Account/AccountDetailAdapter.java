package com.example.smartbudget.Ui.Account;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AccountDetailAdapter extends RecyclerView.Adapter<AccountDetailAdapter.MyViewHolder> {

    private Context mContext;
    private List<TransactionModel> mTransactionModelList;
    private TransactionModel transaction;
    private Category expenseCategory;
    private Category incomeCategory;

    private int revenueColor;
    private int expenseColor;

    public AccountDetailAdapter(Context context, List<TransactionModel> transactionModelList) {
        mContext = context;
        mTransactionModelList = transactionModelList;
        revenueColor = ContextCompat.getColor(mContext, R.color.colorRevenue);
        expenseColor = ContextCompat.getColor(mContext, R.color.colorExpense);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        transaction = mTransactionModelList.get(position);

        if (transaction.getTransaction_type().equals("Expense")) {
            expenseCategory = Common.getExpenseCategory(transaction.getCategory_id());

            holder.iv_category_icon.setImageResource(expenseCategory.getIconResourceID());
            holder.iv_category_icon.setBackgroundTintList(ColorStateList.valueOf(expenseCategory.getIconColor()));
            holder.tv_transaction_category.setText(expenseCategory.getCategoryVisibleName(mContext));

            holder.tv_transaction_amount.setTextColor(expenseColor);
        }
        else if (transaction.getTransaction_type().equals("Income")) {
            incomeCategory = Common.getIncomeCategory(transaction.getCategory_id());

            holder.iv_category_icon.setImageResource(incomeCategory.getIconResourceID());
            holder.iv_category_icon.setBackgroundTintList(ColorStateList.valueOf(incomeCategory.getIconColor()));
            holder.tv_transaction_category.setText(incomeCategory.getCategoryVisibleName(mContext));

            holder.tv_transaction_amount.setTextColor(revenueColor);
        }

        holder.tv_transaction_note.setText(transaction.getTransaction_note());
        holder.tv_transaction_date.setText(transaction.getTransaction_date());
        holder.tv_transaction_amount.setText(new StringBuilder(Common.changeNumberToComma((int) transaction.getTransaction_amount())).append("원"));

        holder.setIRecyclerItemSelectedListener((view, i) -> {
            Toast.makeText(mContext, "[TR]", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mTransactionModelList != null ? mTransactionModelList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener IRecyclerItemSelectedListener) {
            mIRecyclerItemSelectedListener = IRecyclerItemSelectedListener;
        }

        @BindView(R.id.iv_category_icon)
        ImageView iv_category_icon;
        @BindView(R.id.tv_transaction_note)
        TextView tv_transaction_note;
        @BindView(R.id.tv_transaction_category)
        TextView tv_transaction_category;
        @BindView(R.id.tv_transaction_date)
        TextView tv_transaction_date;
        @BindView(R.id.tv_transaction_amount)
        TextView tv_transaction_amount;

        Unbinder mUnbinder;

        public MyViewHolder(@NonNull View itemView) {
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

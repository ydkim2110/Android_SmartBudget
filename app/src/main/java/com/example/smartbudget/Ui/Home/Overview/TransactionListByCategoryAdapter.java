package com.example.smartbudget.Ui.Home.Overview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Database.TransactionRoom.TransactionItem;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TransactionListByCategoryAdapter extends RecyclerView.Adapter<TransactionListByCategoryAdapter.MyViewHolder> {
    private Context mContext;
    private List<TransactionItem> mTransactionItemList;
    private TransactionItem mTransactionItem;

    public TransactionListByCategoryAdapter(Context context, List<TransactionItem> transactionModelList) {
        mContext = context;
        mTransactionItemList = transactionModelList;
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
        mTransactionItem = mTransactionItemList.get(position);

        Category category = Common.getExpenseCategory(mTransactionItemList.get(position).getCategoryId());

        holder.iv_category_icon.setImageResource(category.getIconResourceID());
        holder.iv_category_icon.setBackgroundTintList(ColorStateList.valueOf(category.getIconColor()));
        holder.transaction_category.setText(category.getCategoryVisibleName(mContext));
        holder.transaction_note.setText(mTransactionItem.getDescription());
        holder.transaction_amount.setText(new StringBuilder(Common.changeNumberToComma((int) mTransactionItem.getAmount())).append("원"));
        holder.transaction_amount.setTextColor(mContext.getResources().getColor(R.color.colorExpense));
        holder.transaction_list_date.setText(mTransactionItem.getDate());
    }

    @Override
    public int getItemCount() {
        return mTransactionItemList != null ? mTransactionItemList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_category_icon)
        ImageView iv_category_icon;
        @BindView(R.id.tv_transaction_category)
        TextView transaction_category;
        @BindView(R.id.tv_transaction_note)
        TextView transaction_note;
        @BindView(R.id.tv_transaction_amount)
        TextView transaction_amount;
        @BindView(R.id.tv_transaction_date)
        TextView transaction_list_date;

        Unbinder mUnbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mUnbinder = ButterKnife.bind(this, itemView);

        }
    }
}

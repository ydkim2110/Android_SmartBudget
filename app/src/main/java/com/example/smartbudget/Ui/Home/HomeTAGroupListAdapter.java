package com.example.smartbudget.Ui.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Database.TransactionRoom.TransactionItem;
import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.SubCategory;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Transaction.Add.AddTransactionActivity;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeTAGroupListAdapter extends RecyclerView.Adapter<HomeTAGroupListAdapter.MyViewHolder> {

    private static final String TAG = HomeTAGroupListAdapter.class.getSimpleName();

    private Context mContext;
    private List<TransactionItem> mTransactionItemList;
    private String moneyUnit;
    private String typeExpense;
    private String typeIncome;

    public HomeTAGroupListAdapter(Context mContext, List<TransactionItem> transactionItemList) {
        this.mContext = mContext;
        this.mTransactionItemList = transactionItemList;
        moneyUnit = mContext.getResources().getString(R.string.money_unit);
        typeExpense = mContext.getResources().getString(R.string.type_expense);
        typeIncome = mContext.getResources().getString(R.string.type_income);
    }

    private Category expenseCategory;
    private Category incomeCategory;
    private SubCategory subCategory;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_transaction, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        int id = mTransactionItemList.get(position).getId();
        String description = mTransactionItemList.get(position).getDescription();
        int amount = (int) mTransactionItemList.get(position).getAmount();
        String type = mTransactionItemList.get(position).getType();
        String pattern = mTransactionItemList.get(position).getPattern();
        String transactionDate = mTransactionItemList.get(position).getDate();
        String categoryId = mTransactionItemList.get(position).getCategoryId();
        String subCategoryId = mTransactionItemList.get(position).getSubCategoryId();
        int accountId = mTransactionItemList.get(position).getAccountId();

        if (mTransactionItemList.get(position).getType().equals(typeExpense)) {
            expenseCategory = Common.getExpenseCategory(mTransactionItemList.get(position).getCategoryId());

            if (expenseCategory != null) {
                holder.iv_transaction_icon.setImageResource(expenseCategory.getIconResourceID());
            }
            holder.iv_transaction_icon.setBackgroundTintList(ColorStateList.valueOf(expenseCategory.getIconColor()));
            holder.tv_transaction_category.setText(expenseCategory.getCategoryVisibleName(mContext));

            holder.tv_transaction_note.setText(mTransactionItemList.get(position).getDescription());
            holder.tv_transaction_amount.setText(new StringBuilder("-").append(Common.changeNumberToComma(amount)).append(moneyUnit));
            holder.tv_transaction_amount.setTextColor(mContext.getResources().getColor(R.color.colorExpense));
        }
        else if (mTransactionItemList.get(position).getType().equals(typeIncome)) {
            incomeCategory = Common.getIncomeCategory(mTransactionItemList.get(position).getCategoryId());

            if (incomeCategory != null) {
                holder.iv_transaction_icon.setImageResource(incomeCategory.getIconResourceID());
            }
            holder.iv_transaction_icon.setBackgroundTintList(ColorStateList.valueOf(incomeCategory.getIconColor()));
            holder.tv_transaction_category.setText(incomeCategory.getCategoryVisibleName(mContext));

            holder.tv_transaction_note.setText(mTransactionItemList.get(position).getDescription());
            holder.tv_transaction_amount.setText(new StringBuilder(Common.changeNumberToComma(amount)).append(moneyUnit));
            holder.tv_transaction_amount.setTextColor(mContext.getResources().getColor(R.color.colorRevenue));
        }

        subCategory = Common.getExpenseSubCategory(mTransactionItemList.get(position).getSubCategoryId());
        if (subCategory != null) {
            holder.tv_transaction_sub_category.setText(subCategory.getCategoryVisibleName(mContext));
        }

        holder.setIRecyclerItemSelectedListener((view, i) -> {
            TransactionItem transactionItem = new TransactionItem(id, description, amount, type, pattern, transactionDate, categoryId, subCategoryId, accountId);
            Intent intent = new Intent(view.getContext(), AddTransactionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(Common.EXTRA_EDIT_TRANSACTION, transactionItem);
            intent.putExtra(AddTransactionActivity.EXTRA_REQUEST_PAGE, "HomeFragment");
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mTransactionItemList != null ? mTransactionItemList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_transaction_icon)
        ImageView iv_transaction_icon;
        @BindView(R.id.tv_transaction_category)
        TextView tv_transaction_category;
        @BindView(R.id.tv_transaction_sub_category)
        TextView tv_transaction_sub_category;
        @BindView(R.id.tv_transaction_note)
        TextView tv_transaction_note;
        @BindView(R.id.tv_transaction_amount)
        TextView tv_transaction_amount;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener mIRecyclerItemSelectedListener) {
            this.mIRecyclerItemSelectedListener = mIRecyclerItemSelectedListener;
        }

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

package com.example.smartbudget.Ui.Home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Transaction.Add.AddTransactionActivity;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WeekSubTransactionAdapter extends RecyclerView.Adapter<WeekSubTransactionAdapter.MyViewHolder> {

    private static final String TAG = "WeekSubTransactionAdapter";

    private Context mContext;
    private List<TransactionModel> mTransactionModelList;

    public WeekSubTransactionAdapter(Context mContext, List<TransactionModel> mTransactionModelList) {
        this.mContext = mContext;
        this.mTransactionModelList = mTransactionModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_transaction, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String description = mTransactionModelList.get(position).getTransaction_note();
        int amount = (int) mTransactionModelList.get(position).getTransaction_amount();
        String type = mTransactionModelList.get(position).getTransaction_type();
        String transactionDate = mTransactionModelList.get(position).getTransaction_date();
        String categoryId =  mTransactionModelList.get(position).getCategory_id();
        String subCategoryId =  null;
        int accountId = mTransactionModelList.get(position).getAccount_id();

        Category category = Common.getCategory(mTransactionModelList.get(position).getCategory_id());

        holder.iv_transaction_icon.setImageResource(category.getIconResourceID());
        holder.iv_transaction_icon.setColorFilter(category.getIconColor());
        holder.tv_transaction_category.setText(category.getCategoryVisibleName(mContext));
        holder.tv_transaction_note.setText(mTransactionModelList.get(position).getTransaction_note());
        holder.tv_transaction_amount.setText(new StringBuilder(Common.changeNumberToComma(amount)).append("원"));

        final TransactionModel transactionModel = new TransactionModel(description, amount, type, transactionDate, categoryId, subCategoryId, accountId);

        holder.setIRecyclerItemSelectedListener((view, i) -> {
            Intent editTransactionIntent = new Intent(view.getContext(), AddTransactionActivity.class);
            editTransactionIntent.putExtra(Common.EXTRA_EDIT_TRANSACTION, transactionModel);
            view.getContext().startActivity(editTransactionIntent);
        });
    }

    @Override
    public int getItemCount() {
        return mTransactionModelList != null ? mTransactionModelList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_transaction_icon)
        ImageView iv_transaction_icon;
        @BindView(R.id.tv_transaction_category)
        TextView tv_transaction_category;
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

        private void setData(String category, String description, int amount) {
            tv_transaction_category.setText(category);
            tv_transaction_note.setText(description);
            tv_transaction_amount.setText(new StringBuilder(Common.changeNumberToComma(amount)).append("원"));
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelected(v, getAdapterPosition());
        }
    }

}

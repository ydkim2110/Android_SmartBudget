package com.example.smartbudget.Ui.Home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Transaction.Add.AddTransactionActivity;
import com.example.smartbudget.Utils.Common;

import java.util.Date;
import java.util.List;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.MyViewHolder> {

    private static final String TAG = "SectionListDataAdapter";

    private Context mContext;
    private List<TransactionModel> mTransactionModelList;

    public SectionListDataAdapter(Context mContext, List<TransactionModel> mTransactionModelList) {
        this.mContext = mContext;
        this.mTransactionModelList = mTransactionModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.transaction_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String description = mTransactionModelList.get(position).getTransaction_description();
        int amount = (int) mTransactionModelList.get(position).getTransaction_amount();
        String type = mTransactionModelList.get(position).getTransaction_type();
        String transactionDate = mTransactionModelList.get(position).getTransaction_date();
        int categoryId =  mTransactionModelList.get(position).getCategory_id();
        int accountId = mTransactionModelList.get(position).getAccount_id();

        holder.categoryTv.setText(""+mTransactionModelList.get(position).getCategory_id());
        holder.descriptionTv.setText(mTransactionModelList.get(position).getTransaction_description());
        holder.amountTv.setText(new StringBuilder(Common.changeNumberToComma(amount)).append("원"));

        final TransactionModel transactionModel = new TransactionModel(description, amount, type, transactionDate, categoryId, accountId);


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

        private TextView categoryTv;
        private TextView descriptionTv;
        private TextView amountTv;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener mIRecyclerItemSelectedListener) {
            this.mIRecyclerItemSelectedListener = mIRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTv = itemView.findViewById(R.id.transaction_category);
            descriptionTv = itemView.findViewById(R.id.transaction_description);
            amountTv = itemView.findViewById(R.id.transaction_amount);

            itemView.setOnClickListener(this);
        }

        private void setData(int category, String description, int amount) {
            categoryTv.setText(""+category);
            descriptionTv.setText(description);
            amountTv.setText(new StringBuilder(Common.changeNumberToComma(amount)).append("원"));
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelected(v, getAdapterPosition());
        }
    }

}

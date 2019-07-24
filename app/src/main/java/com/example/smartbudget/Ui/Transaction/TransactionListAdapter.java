package com.example.smartbudget.Ui.Transaction;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {

    private List<TransactionModel> mTransactionModelList;

    public TransactionListAdapter(List<TransactionModel> transactionModelList) {
        mTransactionModelList = transactionModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.transaction_list_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String category = String.valueOf(mTransactionModelList.get(i).getCategory_id());
        String description = mTransactionModelList.get(i).getTransaction_description();
        double amount = mTransactionModelList.get(i).getTransaction_amount();
        String date = mTransactionModelList.get(i).getTransaction_date();

        viewHolder.setData(category, description, amount, date);
    }

    @Override
    public int getItemCount() {
        return mTransactionModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView categoryTv;
        private TextView descriptionTv;
        private TextView amountTv;
        private TextView dateTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTv = itemView.findViewById(R.id.transaction_category);
            descriptionTv = itemView.findViewById(R.id.transaction_description);
            amountTv = itemView.findViewById(R.id.transaction_amount);
            dateTv = itemView.findViewById(R.id.transaction_list_date);
        }

        private void setData(String category, String description, double amount, String date) {
            categoryTv.setText(category);
            descriptionTv.setText(description);
            amountTv.setText(Common.changeNumberToComma((int) amount) + "원");
            dateTv.setText(date);
        }
    }
}

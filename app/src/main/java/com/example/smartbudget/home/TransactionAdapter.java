package com.example.smartbudget.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartbudget.R;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "TransactionAdapter";

    private List<ListItem> consolidatedList = new ArrayList<>();

    public TransactionAdapter(List<ListItem> consolidatedList) {
        this.consolidatedList = consolidatedList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case ListItem.TYPE_DATE:
                View dateView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.transaction_item_date_layout, viewGroup, false);
                viewHolder = new DateViewHolder(dateView);
                break;
            case ListItem.TYPE_TRANSACTION:
                View transactionView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.transaction_item_layout, viewGroup, false);
                viewHolder = new TransactionViewHolder(transactionView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (viewHolder.getItemViewType()) {
            case ListItem.TYPE_DATE:
                DateItem dateItem = (DateItem) consolidatedList.get(i);
                DateViewHolder dateViewHolder = (DateViewHolder) viewHolder;

                String date = dateItem.getDate();

                dateViewHolder.setDate(date);
                break;
            case ListItem.TYPE_TRANSACTION:
                TransactionItem transactionItem = (TransactionItem) consolidatedList.get(i);
                TransactionViewHolder transactionViewHolder = (TransactionViewHolder) viewHolder;

                String category = transactionItem.getTransaction().getCategory();
                String description = transactionItem.getTransaction().getDescription();
                int amount = transactionItem.getTransaction().getAmount();

                transactionViewHolder.setData(category, description, amount);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return consolidatedList != null ? consolidatedList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return consolidatedList.get(position).getType();
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTv;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTv = itemView.findViewById(R.id.transaction_date);
        }

        private void setDate(String date) {
            dateTv.setText(date);
        }
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryTv;
        private TextView descriptionTv;
        private TextView amountTv;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTv = itemView.findViewById(R.id.transaction_category);
            descriptionTv = itemView.findViewById(R.id.transaction_description);
            amountTv = itemView.findViewById(R.id.transaction_amount);
        }

        private void setData(String category, String description, int amount) {
            categoryTv.setText(category);
            descriptionTv.setText(description);
            amountTv.setText(amount + "원");
        }
    }
}

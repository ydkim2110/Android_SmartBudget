package com.example.smartbudget.Ui.Home;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Transaction.Add.AddTransactionActivity;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "TransactionListAdapter";

    private Context mContext;
    private List<ListItem> consolidatedList;

    public TransactionAdapter(Context context, List<ListItem> consolidatedList) {
        mContext = context;
        this.consolidatedList = consolidatedList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ListItem.TYPE_DATE:
                View dateView = LayoutInflater.from(mContext)
                        .inflate(R.layout.transaction_item_date_layout, viewGroup, false);
                viewHolder = new DateViewHolder(dateView);
                break;

            case ListItem.TYPE_TRANSACTION:
                View transactionView = LayoutInflater.from(mContext)
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
                dateViewHolder.setData(date, dateItem.getTotal());
                break;
            case ListItem.TYPE_TRANSACTION:
                TransactionItem transactionItem = (TransactionItem) consolidatedList.get(i);
                TransactionViewHolder transactionViewHolder = (TransactionViewHolder) viewHolder;

                String description = transactionItem.getTransaction().getTransaction_description();
                int amount = (int) transactionItem.getTransaction().getTransaction_amount();
                String type = transactionItem.getTransaction().getTransaction_type();
                String transactionDate = transactionItem.getTransaction().getTransaction_date();
                int categoryId =  transactionItem.getTransaction().getCategory_id();
                int accountId = transactionItem.getTransaction().getAccount_id();


                final TransactionModel transactionModel = new TransactionModel(description, amount, type, transactionDate, categoryId, accountId);

                transactionViewHolder.setData(categoryId, description, amount);

                transactionViewHolder.setIRecyclerItemSelectedListener((view, position) -> {
                    Intent editTransactionIntent = new Intent(view.getContext(), AddTransactionActivity.class);
                    editTransactionIntent.putExtra(Common.EXTRA_EDIT_TRANSACTION, transactionModel);
                    view.getContext().startActivity(editTransactionIntent);
                });
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

        private TextView transaction_date;
        private TextView transaction_date_total;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);

            transaction_date = itemView.findViewById(R.id.transaction_date);
            transaction_date_total = itemView.findViewById(R.id.transaction_date_total);
        }

        private void setData(String date, int total) {
            transaction_date.setText(date);
            transaction_date_total.setText(new StringBuilder(Common.changeNumberToComma(total))
                    .append("원"));
        }
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView categoryTv;
        private TextView descriptionTv;
        private TextView amountTv;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener IRecyclerItemSelectedListener) {
            mIRecyclerItemSelectedListener = IRecyclerItemSelectedListener;
        }

        public TransactionViewHolder(@NonNull final View itemView) {
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
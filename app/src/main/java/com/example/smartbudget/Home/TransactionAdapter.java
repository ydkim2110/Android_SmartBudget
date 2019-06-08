package com.example.smartbudget.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.R;
import com.example.smartbudget.Transaction.AddTransactionActivity;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.IRecyclerItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "TransactionAdapter";

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
//        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
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

                final Transaction transaction = new Transaction(category, description, amount, "2019-07-25");

                transactionViewHolder.setData(category, description, amount);

                transactionViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
                    @Override
                    public void onItemSelectedListener(View view, int position) {
                        Intent editTransactionIntent = new Intent(view.getContext(), AddTransactionActivity.class);
                        editTransactionIntent.putExtra(Common.EXTRA_EDIT_TRANSACTION, transaction);
                        view.getContext().startActivity(editTransactionIntent);
                    }
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

        private TextView dateTv;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTv = itemView.findViewById(R.id.transaction_date);
        }

        private void setDate(String date) {
            dateTv.setText(date);
        }
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        private TextView categoryTv;
        private TextView descriptionTv;
        private TextView amountTv;

        private IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public TransactionViewHolder(@NonNull final View itemView) {
            super(itemView);

            categoryTv = itemView.findViewById(R.id.transaction_category);
            descriptionTv = itemView.findViewById(R.id.transaction_description);
            amountTv = itemView.findViewById(R.id.transaction_amount);

            itemView.setOnClickListener(this);
        }

        private void setData(String category, String description, int amount) {
            categoryTv.setText(category);
            descriptionTv.setText(description);
            amountTv.setText(amount + "원");
        }

        @Override
        public void onClick(View v) {
            iRecyclerItemSelectedListener.onItemSelectedListener(v, getAdapterPosition());
        }
    }
}

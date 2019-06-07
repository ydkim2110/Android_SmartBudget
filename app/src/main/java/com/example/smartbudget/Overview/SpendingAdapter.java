package com.example.smartbudget.Overview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartbudget.R;
import com.example.smartbudget.Main.Spending;

import java.util.List;

public class SpendingAdapter extends RecyclerView.Adapter<SpendingAdapter.ViewHolder> {

    private List<Spending> spendingList;

    public SpendingAdapter(List<Spending> spendingList) {
        this.spendingList = spendingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.overview_category_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.percentage.setText(spendingList.get(i).getPercentage());
        viewHolder.category.setText(spendingList.get(i).getCategory());
        viewHolder.amount.setText(spendingList.get(i).getAmount());
    }

    @Override
    public int getItemCount() {
        return spendingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView percentage;
        private TextView category;
        private TextView amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            percentage = itemView.findViewById(R.id.overview_spending_percentage);
            category = itemView.findViewById(R.id.overview_spending_category);
            amount = itemView.findViewById(R.id.overview_spending_amount);
        }
    }
}

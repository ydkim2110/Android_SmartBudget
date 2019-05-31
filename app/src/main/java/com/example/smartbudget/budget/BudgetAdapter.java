package com.example.smartbudget.budget;

import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smartbudget.R;

import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    private List<Budget> budgetList;

    public BudgetAdapter(List<Budget> budgetList) {
        this.budgetList = budgetList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.budget_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String category = budgetList.get(i).getCategory();
        int amount = budgetList.get(i).getAmount();

        viewHolder.setData(category, amount);
    }

    @Override
    public int getItemCount() {
        return budgetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView categoryTv;
        private TextView amountTv;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTv = itemView.findViewById(R.id.budget_category);
            amountTv = itemView.findViewById(R.id.budget_amount);
            progressBar = itemView.findViewById(R.id.budget_progressBar);
        }

        private void setData(String category, int amount) {
            categoryTv.setText(category);
            amountTv.setText(amount+"원");
            ObjectAnimator progressAnim = ObjectAnimator.ofInt(progressBar, "progress", 0, 70);
            progressAnim.setDuration(500);
            progressAnim.setInterpolator(new LinearInterpolator());
            progressAnim.start();
        }
    }
}

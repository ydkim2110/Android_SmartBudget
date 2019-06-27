package com.example.smartbudget.Budget;

import android.animation.ObjectAnimator;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;

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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final String category = budgetList.get(i).getCategory();
        int amount = budgetList.get(i).getAmount();

        viewHolder.setData(category, amount);

        viewHolder.setIRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
                Intent viewTransactionIntent = new Intent(view.getContext(), ViewTransactionActivity.class);
                viewTransactionIntent.putExtra(Common.EXTRA_PASS_BUDGET_CATEGORY, category);
                view.getContext().startActivity(viewTransactionIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return budgetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView categoryTv;
        private TextView amountTv;
        private TextView viewAllTransactionTv;
        private ProgressBar progressBar;
        private TextView progressbar_percentage;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener IRecyclerItemSelectedListener) {
            mIRecyclerItemSelectedListener = IRecyclerItemSelectedListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTv = itemView.findViewById(R.id.budget_category);
            amountTv = itemView.findViewById(R.id.budget_amount);
            viewAllTransactionTv =  itemView.findViewById(R.id.budget_view_transacitons);
            progressBar = itemView.findViewById(R.id.budget_progressBar);
            progressbar_percentage = itemView.findViewById(R.id.progressbar_percentage);

            itemView.setOnClickListener(this);
            viewAllTransactionTv.setOnClickListener(this);
        }

        private void setData(String category, int amount) {
            categoryTv.setText(category);
            amountTv.setText(Common.changeNumberToComma(amount)+"원");
            progressbar_percentage.setText(Common.calcPercentage(100000, amount)+"%");

            progressBar.setMax(amount);
            ObjectAnimator progressAnim = ObjectAnimator.ofInt(progressBar, "progress", 0, 100000);
            progressAnim.setDuration(500);
            progressAnim.setInterpolator(new LinearInterpolator());
            progressAnim.start();
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelected(v, getAdapterPosition());
        }
    }
}

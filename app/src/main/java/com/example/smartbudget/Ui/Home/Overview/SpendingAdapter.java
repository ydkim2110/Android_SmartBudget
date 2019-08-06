package com.example.smartbudget.Ui.Home.Overview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.R;
import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;
import com.example.smartbudget.Ui.Home.Category.ExpenseByCategoryActivity;
import com.example.smartbudget.Utils.Common;

import java.util.List;

public class SpendingAdapter extends RecyclerView.Adapter<SpendingAdapter.ViewHolder> {

    private Context mContext;
    private List<ExpenseByCategory> mCategoryList;

    public SpendingAdapter(Context context, List<ExpenseByCategory> categoryList) {
        mContext = context;
        mCategoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_overview_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.category.setText(mCategoryList.get(i).getCategoryId());
        viewHolder.amount.setText(new StringBuilder(Common.changeNumberToComma(mCategoryList.get(i).getSumByCategory())).append("원"));
        //viewHolder.percentage.setText(spendingList.get(i).getPercentage());

        viewHolder.setIRecyclerItemSelectedListener((view, position) -> {
            Toast.makeText(view.getContext(), "category: "+ mCategoryList.get(position).getCategoryId(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView percentage;
        private TextView category;
        private TextView amount;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener IRecyclerItemSelectedListener) {
            mIRecyclerItemSelectedListener = IRecyclerItemSelectedListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            percentage = itemView.findViewById(R.id.overview_spending_percentage);
            category = itemView.findViewById(R.id.overview_spending_category);
            amount = itemView.findViewById(R.id.overview_spending_amount);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelected(v, getAdapterPosition());
        }
    }
}

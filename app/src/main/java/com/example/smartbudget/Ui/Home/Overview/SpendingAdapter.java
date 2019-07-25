package com.example.smartbudget.Ui.Home.Overview;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.CategoryModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.Spending;
import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;

import java.util.List;

public class SpendingAdapter extends RecyclerView.Adapter<SpendingAdapter.ViewHolder> {

    private List<CategoryModel> categoryList;

    public SpendingAdapter(List<CategoryModel> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.overview_category_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        //viewHolder.amount.setText(spendingList.get(i).getAmount());
        viewHolder.category.setText(categoryList.get(i).getCategory_name());
        //viewHolder.percentage.setText(spendingList.get(i).getPercentage());

        viewHolder.setIRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
                Toast.makeText(view.getContext(), "category: "+categoryList.get(position).getCategory_name(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
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
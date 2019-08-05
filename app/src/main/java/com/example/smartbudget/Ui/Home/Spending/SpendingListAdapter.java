package com.example.smartbudget.Ui.Home.Spending;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SpendingListAdapter extends RecyclerView.Adapter<SpendingListAdapter.MyViewHolder> {

    private Context mContext;
    private List<TransactionModel> mTransactionModelList;

    private int lastPosition = -1;

    public SpendingListAdapter(Context context, List<TransactionModel> transactionModelList) {
        mContext = context;
        mTransactionModelList = transactionModelList;
//        Collections.sort(mTransactionModelList, new Comparator<TransactionModel>() {
//            @Override
//            public int compare(TransactionModel o1, TransactionModel o2) {
//                return o1.getTransaction_date().compareTo(o2.getTransaction_date());
//            }
//        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TransactionModel transaction = mTransactionModelList.get(position);

        Category category = Common.getExpenseCategory(mTransactionModelList.get(position).getCategory_id());

        holder.iv_category_icon.setImageResource(category.getIconResourceID());
        holder.iv_category_icon.setBackgroundTintList(ColorStateList.valueOf(category.getIconColor()));
        holder.transaction_category.setText(category.getCategoryVisibleName(mContext));
        holder.transaction_note.setText(transaction.getTransaction_note());
        holder.transaction_amount.setText(new StringBuilder(Common.changeNumberToComma((int) transaction.getTransaction_amount())).append("원"));
        holder.transaction_amount.setTextColor(mContext.getResources().getColor(R.color.colorExpense));
        holder.transaction_list_date.setText(transaction.getTransaction_date());

        setAnimation(holder.itemView, position);

    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mTransactionModelList != null ? mTransactionModelList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_category_icon)
        ImageView iv_category_icon;
        @BindView(R.id.tv_transaction_category)
        TextView transaction_category;
        @BindView(R.id.tv_transaction_note)
        TextView transaction_note;
        @BindView(R.id.tv_transaction_amount)
        TextView transaction_amount;
        @BindView(R.id.tv_transaction_date)
        TextView transaction_list_date;

        Unbinder mUnbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mUnbinder = ButterKnife.bind(this, itemView);

        }
    }
}

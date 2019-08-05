package com.example.smartbudget.Ui.Account;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {

    private Context mContext;
    private List<AccountModel> mAccountModelList;
    public static BSAccountMenuFragment mBSAccountMenuFragment;
    private int colorRevenue;
    private int colorExpense;
    private int balance = 0;

    public AccountListAdapter(Context context, List<AccountModel> accountModelList) {
        mContext = context;
        mAccountModelList = accountModelList;
        colorRevenue = ContextCompat.getColor(mContext, R.color.colorRevenue);
        colorExpense = ContextCompat.getColor(mContext, R.color.colorExpense);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        balance = (int) mAccountModelList.get(position).getAccount_amount();

        holder.tv_name.setText(mAccountModelList.get(position).getAccount_name());
        holder.tv_description.setText(mAccountModelList.get(position).getAccount_description());
        holder.tv_amount.setText(new StringBuilder(Common.changeNumberToComma(balance)).append("원"));
        holder.tv_more_icon.setOnClickListener(v -> {
            mBSAccountMenuFragment = BSAccountMenuFragment.getInstance();
            mBSAccountMenuFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(),
                    mBSAccountMenuFragment.getTag());
        });

        if (mAccountModelList.get(position).getAccount_type().equals("asset")) {
            holder.tv_amount.setTextColor(colorRevenue);
        }
        else if (mAccountModelList.get(position).getAccount_type().equals("debt")) {
            holder.tv_amount.setTextColor(colorExpense);
        }

        holder.setIRecyclerItemSelectedListener((view, i) -> {
            mContext.startActivity(new Intent(mContext, AccountDetailActivity.class));
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_description)
        TextView tv_description;
        @BindView(R.id.tv_amount)
        TextView tv_amount;
        @BindView(R.id.tv_more_icon)
        ImageView tv_more_icon;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener IRecyclerItemSelectedListener) {
            mIRecyclerItemSelectedListener = IRecyclerItemSelectedListener;
        }

        Unbinder mUnbinder;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mUnbinder = ButterKnife.bind(this, itemLayoutView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelected(v, getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return mAccountModelList != null ? mAccountModelList.size() : 0;
    }

}

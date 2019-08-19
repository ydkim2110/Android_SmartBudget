package com.example.smartbudget.Ui.Account;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Database.AccountRoom.AccountItem;
import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private static final String TAG = AccountAdapter.class.getSimpleName();

    public static BSAccountMenuFragment mBSAccountMenuFragment;

    private Context mContext;
    private List<AccountItem> mAccountItemList;

    private int colorRevenue;
    private int colorExpense;
    private String moneyUnit;

    public AccountAdapter(Context context, List<AccountItem> accountItemList) {
        mContext = context;
        mAccountItemList = accountItemList;
        colorRevenue = ContextCompat.getColor(mContext, R.color.colorRevenue);
        colorExpense = ContextCompat.getColor(mContext, R.color.colorExpense);
        moneyUnit = mContext.getResources().getString(R.string.money_unit);
    }

    private int balance = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        balance = (int) mAccountItemList.get(position).getAmount();

        holder.tv_high_category.setText(Common.getAccountTitleById(mContext, mAccountItemList.get(position).getHighCategory()));
        holder.tv_name.setText(mAccountItemList.get(position).getName());
        holder.tv_description.setText(mAccountItemList.get(position).getDescription());
        holder.tv_amount.setText(new StringBuilder(Common.changeNumberToComma(balance)).append(moneyUnit));

        holder.tv_more_icon.setOnClickListener(v -> {
            Common.SELECTED_ACCOUNT = mAccountItemList.get(position);
            mBSAccountMenuFragment = BSAccountMenuFragment.getInstance();
            mBSAccountMenuFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(),
                    mBSAccountMenuFragment.getTag());
        });

        if (mAccountItemList.get(position).getType().equals("asset")) {
            holder.tv_amount.setTextColor(colorRevenue);
        }
        else if (mAccountItemList.get(position).getType().equals("debt")) {
            holder.tv_amount.setTextColor(colorExpense);
        }

        holder.setIRecyclerItemSelectedListener((view, i) -> {
            Intent intent = new Intent(mContext, AccountDetailActivity.class);
            intent.putExtra("account_name", mAccountItemList.get(i));
            mContext.startActivity(intent);
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_high_category)
        TextView tv_high_category;
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
        return mAccountItemList != null ? mAccountItemList.size() : 0;
    }

}

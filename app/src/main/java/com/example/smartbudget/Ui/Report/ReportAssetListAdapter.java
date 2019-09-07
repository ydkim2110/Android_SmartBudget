package com.example.smartbudget.Ui.Report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Database.AccountRoom.AccountItem;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReportAssetListAdapter extends RecyclerView.Adapter<ReportAssetListAdapter.MyViewHolder> {

    private Context mContext;
    private List<AccountItem> mAccountItemList;

    private String moneyUnit;
    public ReportAssetListAdapter(Context context, List<AccountItem> accountItemList) {
        mContext = context;
        mAccountItemList = accountItemList;
        moneyUnit = mContext.getResources().getString(R.string.money_unit);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report_asset, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_high_category.setText(Common.getAccountTitleById(mContext, mAccountItemList.get(position).getHighCategory()));
        holder.tv_amount.setText(new StringBuilder(Common.changeNumberToComma((int) mAccountItemList.get(position).getAmount())).append(moneyUnit));
    }

    @Override
    public int getItemCount() {
        return mAccountItemList != null ? mAccountItemList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_high_category)
        TextView tv_high_category;
        @BindView(R.id.tv_amount)
        TextView tv_amount;
        @BindView(R.id.tv_percentage)
        TextView tv_percentage;

        Unbinder mUnbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mUnbinder = ButterKnife.bind(this, itemView);
        }
    }
}

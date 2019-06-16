package com.example.smartbudget.Account;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.Database.Model.AccountModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.IRecyclerItemSelectedListener;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private static final String TAG = "AccountAdapter";

    public static BSAccountAddFragment mBSAccountAddFragment;
    public static BSAccountMenuFragment mBSAccountMenuFragment;

    private Context mContext;
    private List<AccountModel> accountList;

    private int count = 0;
    public static IAccountLoadListener mListener;

    public AccountAdapter(Context context, List<AccountModel> accountList, IAccountLoadListener listener) {
        this.mContext = context;
        this.accountList = accountList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        if (count >= accountList.size()) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.account_item_add_layout, viewGroup, false);
            view.setTag("ADD");
        } else {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.account_item2_layout, viewGroup, false);
            view.setTag(null);
        }
        count += 1;
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (i < accountList.size()) {
            viewHolder.accountName.setText(accountList.get(i).getAccount_name());
            viewHolder.accountDescription.setText(accountList.get(i).getAccount_description());
            viewHolder.accountAmount.setText(Common.changeNumberToComma((int) accountList.get(i).getAccount_amount())+"원");
            viewHolder.accountType.setText(accountList.get(i).getAccount_type());

            viewHolder.setIRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
                @Override
                public void onItemSelectedListener(View view, int position) {
                    mContext.startActivity(new Intent(mContext, AccountDetailActivity.class));
                }
            });

            viewHolder.accountMoreIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Common.SELECTED_ACCOUNT = accountList.get(i);
                    mBSAccountMenuFragment = BSAccountMenuFragment.getInstance();
                    mBSAccountMenuFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), mBSAccountMenuFragment.getTag());
                }
            });

        } else {
            viewHolder.setIRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
                @Override
                public void onItemSelectedListener(View view, int position) {
                    mBSAccountAddFragment = BSAccountAddFragment.getInstance();
                    mBSAccountAddFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), mBSAccountAddFragment.getTag());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return accountList.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView accountName;
        private TextView accountDescription;
        private TextView accountAmount;
        private TextView accountType;
        private ImageView accountMoreIcon;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener mIRecyclerItemSelectedListener) {
            this.mIRecyclerItemSelectedListener = mIRecyclerItemSelectedListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            if (itemView.getTag() == "ADD") {
                itemView.setOnClickListener(this);
            } else {
                accountName = itemView.findViewById(R.id.account_name);
                accountDescription = itemView.findViewById(R.id.account_description);
                accountAmount = itemView.findViewById(R.id.account_amount);
                accountType = itemView.findViewById(R.id.account_type);
                accountMoreIcon = itemView.findViewById(R.id.account_more_icon);

                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelectedListener(v, getAdapterPosition());
        }
    }
}

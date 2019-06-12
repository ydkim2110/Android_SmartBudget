package com.example.smartbudget.Account;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.Database.Model.AccountModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.IRecyclerItemSelectedListener;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private static final String TAG = "AccountAdapter";

    public static BottomSheetFragment bottomSheetFragment;

    private Context mContext;
    private List<Account> accountList;

    private int count = 0;

    public AccountAdapter(Context context, List<Account> accountList) {
        this.mContext = context;
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        if (count >= accountList.size()) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.account_add_item_layout, viewGroup, false);
            view.setTag("ADD");
        } else {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.account_item_layout, viewGroup, false);
            view.setTag(null);
        }
        count += 1;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i<accountList.size()) {
            viewHolder.accountName.setText(accountList.get(i).getName());
            viewHolder.accountAmount.setText(Common.changeNumberToComma((int) accountList.get(i).getAmount()));

            viewHolder.setmIRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
                @Override
                public void onItemSelectedListener(View view, int position) {
                    Toast.makeText(view.getContext(), "계좌명: "+accountList.get(position).getName(),
                            Toast.LENGTH_SHORT).show();
                }
            });
            
            viewHolder.accountEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "수정 클릭!", Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.accountSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "설정 클릭!", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            viewHolder.setmIRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
                @Override
                public void onItemSelectedListener(View view, int position) {
                    //view.getContext().startActivity(new Intent(view.getContext(), AddAccountActivity.class));

                    bottomSheetFragment = new BottomSheetFragment();
                    bottomSheetFragment.show(((AppCompatActivity) mContext).getSupportFragmentManager(), bottomSheetFragment.getTag());

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return accountList.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView accountName;
        private TextView accountAmount;
        private TextView accountEdit;
        private TextView accountSetting;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setmIRecyclerItemSelectedListener(IRecyclerItemSelectedListener mIRecyclerItemSelectedListener) {
            this.mIRecyclerItemSelectedListener = mIRecyclerItemSelectedListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            if (itemView.getTag() == "ADD") {
                itemView.setOnClickListener(this);
            } else {
                accountName = itemView.findViewById(R.id.account_name);
                accountAmount = itemView.findViewById(R.id.account_amount);
                accountEdit = itemView.findViewById(R.id.account_edit);
                accountSetting = itemView.findViewById(R.id.account_setting);

                itemView.setOnClickListener(this);
                accountEdit.setOnClickListener(this);
                accountSetting.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelectedListener(v, getAdapterPosition());
        }
    }
}

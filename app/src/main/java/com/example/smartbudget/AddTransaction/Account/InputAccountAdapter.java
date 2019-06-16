package com.example.smartbudget.AddTransaction.Account;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.Database.Model.AccountModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.IRecyclerItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class InputAccountAdapter extends RecyclerView.Adapter<InputAccountAdapter.ViewHolder> {

    private static final String TAG = InputAccountAdapter.class.getSimpleName();

    public interface SaveButtonListener {
        void onUpdate(boolean status, AccountModel accountModel);
    }

    public SaveButtonListener mSaveButtonListener;

    private List<AccountModel> mAccountModelList;
    private List<CardView> mCardViewList;

    public InputAccountAdapter(List<AccountModel> accountModelList, SaveButtonListener listener) {
        mAccountModelList = accountModelList;
        mCardViewList = new ArrayList<>();
        this.mSaveButtonListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.account_input_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.acccountName.setText(mAccountModelList.get(i).getAccount_name());
        viewHolder.accountAmount.setText(Common.changeNumberToComma((int) mAccountModelList.get(i).getAccount_amount()) +"원");
        viewHolder.accountType.setText(mAccountModelList.get(i).getAccount_type());

        if (!mCardViewList.contains(viewHolder.container)) {
            mCardViewList.add(viewHolder.container);
        }

        viewHolder.setIRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int position) {
                Toast.makeText(view.getContext(), "Name: "+mAccountModelList.get(position).getAccount_name(), Toast.LENGTH_SHORT).show();
                for (CardView cardView : mCardViewList) {
                    cardView.setBackgroundColor(Color.WHITE);
                }
                viewHolder.container.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorRevenue));

                mSaveButtonListener.onUpdate(true, mAccountModelList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAccountModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView container;
        private TextView acccountName;
        private TextView accountAmount;
        private TextView accountType;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener IRecyclerItemSelectedListener) {
            mIRecyclerItemSelectedListener = IRecyclerItemSelectedListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            acccountName = itemView.findViewById(R.id.input_account_name);
            accountAmount = itemView.findViewById(R.id.input_account_amount);
            accountType = itemView.findViewById(R.id.input_account_type);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelectedListener(v, getAdapterPosition());
        }
    }

}

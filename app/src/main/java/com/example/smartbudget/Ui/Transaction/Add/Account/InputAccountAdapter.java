package com.example.smartbudget.Ui.Transaction.Add.Account;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;

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
        if (mAccountModelList.size() == 0) {

        }
        else {
            viewHolder.accountName.setText(mAccountModelList.get(i).getName());
            viewHolder.accountAmount.setText(Common.changeNumberToComma((int) mAccountModelList.get(i).getAmount()) + "원");
            viewHolder.accountType.setText(mAccountModelList.get(i).getType());

            if (!mCardViewList.contains(viewHolder.container)) {
                mCardViewList.add(viewHolder.container);
            }

            viewHolder.setIRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
                @Override
                public void onItemSelected(View view, int position) {
                    Toast.makeText(view.getContext(), "Name: " + mAccountModelList.get(position).getId(), Toast.LENGTH_SHORT).show();
                    for (CardView cardView : mCardViewList) {
                        cardView.setBackgroundColor(Color.WHITE);
                    }
                    viewHolder.container.setBackgroundColor(view.getContext().getResources().getColor(R.color.colorRevenue));

                    mSaveButtonListener.onUpdate(true, mAccountModelList.get(i));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mAccountModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView container;
        private TextView accountName;
        private TextView accountAmount;
        private TextView accountType;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener IRecyclerItemSelectedListener) {
            mIRecyclerItemSelectedListener = IRecyclerItemSelectedListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            accountName = itemView.findViewById(R.id.input_account_name);
            accountAmount = itemView.findViewById(R.id.input_account_amount);
            accountType = itemView.findViewById(R.id.input_account_type);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelected(v, getAdapterPosition());
        }
    }

}

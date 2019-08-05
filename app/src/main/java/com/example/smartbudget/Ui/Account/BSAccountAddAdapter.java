package com.example.smartbudget.Ui.Account;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartbudget.R;
import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;

public class BSAccountAddAdapter extends RecyclerView.Adapter<BSAccountAddAdapter.ViewHolder> {

    private String[] title;
    private Context mContext;

    public BSAccountAddAdapter(Context context, String[] title) {
        this.mContext = context;
        this.title = title;
    }

    private String type;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_add_account, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.addTv.setText(title[i]);

        viewHolder.setIRecyclerItemSelectedListener((view, position) -> {
            Intent intent = new Intent(mContext, AddAccountActivity.class);
            intent.putExtra("high_category", title[position]);
            mContext.startActivity(intent);
            AccountActivity.mBSAccountAddFragment.dismiss();
        });
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView addTv;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener mIRecyclerItemSelectedListener) {
            this.mIRecyclerItemSelectedListener = mIRecyclerItemSelectedListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            addTv = itemView.findViewById(R.id.add_tv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelected(v, getAdapterPosition());
        }
    }
}

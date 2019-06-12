package com.example.smartbudget.Account;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.R;
import com.example.smartbudget.Utils.IRecyclerItemSelectedListener;

import java.util.List;

public class BottomSheetAdapter  extends RecyclerView.Adapter<BottomSheetAdapter.ViewHolder> {

    private String[] title;
    private Context mContext;

    public BottomSheetAdapter(Context context, String[] title) {
        this.mContext = context;
        this.title = title;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.account_name_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.addTv.setText(title[i]);

        viewHolder.setIRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int position) {
                Intent intent = new Intent(mContext, AddAccountActivity.class);
                intent.putExtra("type", title[position]);
                mContext.startActivity(intent);
                AccountAdapter.bottomSheetFragment.dismiss();
            }
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
            mIRecyclerItemSelectedListener.onItemSelectedListener(v, getAdapterPosition());
        }
    }
}
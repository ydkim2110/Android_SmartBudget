package com.example.smartbudget.Ui.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WeekTransactionAdapter extends RecyclerView.Adapter<WeekTransactionAdapter.MyViewHolder> {
    private static final String TAG = "WeekTransactionAdapter";

    private Context mContext;
    private HashMap<String, List<TransactionModel>> mHashMap;
    private ArrayList<String> mKeys = new ArrayList<>();

    public WeekTransactionAdapter(Context mContext, HashMap<String, List<TransactionModel>> hashMap) {
        this.mContext = mContext;
        this.mHashMap = hashMap;
        for (String aKey : mHashMap.keySet()) {
            mKeys.add(aKey);
        }
        Collections.sort(mKeys, Collections.reverseOrder());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_transaction_date, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int total = 0;
        Date date = Common.stringToDate(mKeys.get(position));
        holder.tv_date.setText(new SimpleDateFormat("dd").format(date));
        holder.tv_date_2.setText(new SimpleDateFormat("EE").format(date));
        holder.tv_date_3.setText(new SimpleDateFormat("MMMM yyyy").format(date));

        List<TransactionModel> value = mHashMap.get(mKeys.get(position));

        for (TransactionModel model : value) {
            if(model.getTransaction_type().equals("Expense")) {
                total -= model.getTransaction_amount();
            }
            else if (model.getTransaction_type().equals("Income")) {
                total += model.getTransaction_amount();
            }
        }

        holder.tv_date_total.setText(new StringBuilder(Common.changeNumberToComma(total)).append("원"));

        holder.rv_transaction_list.setHasFixedSize(true);
        holder.rv_transaction_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        WeekSubTransactionAdapter adapter = new WeekSubTransactionAdapter(mContext, value);
        holder.rv_transaction_list.setAdapter(adapter);

        holder.itemView.startAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+mHashMap.size());
        return mHashMap != null ? mHashMap.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_date;
        private TextView tv_date_2;
        private TextView tv_date_3;
        private TextView tv_date_total;
        private RecyclerView rv_transaction_list;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_date_2 = itemView.findViewById(R.id.tv_date_2);
            tv_date_3 = itemView.findViewById(R.id.tv_date_3);
            tv_date_total = itemView.findViewById(R.id.tv_date_total);
            rv_transaction_list = itemView.findViewById(R.id.rv_transaciton_list);
        }
    }

}

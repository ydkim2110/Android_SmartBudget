package com.example.smartbudget.Ui.Transaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DynamicFragment extends Fragment implements ITransactionLoadListener {

    private static final String TAG = DynamicFragment.class.getSimpleName();

    public static DynamicFragment newInstance(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putString("date", dateFormat.format(time));
        fragment.setArguments(args);
        return fragment;
    }


    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dynamic_fragment_layout, container, false);

        DatabaseUtils.getThisMonthTransaction(MainActivity.mDBHelper, getArguments().getString("date"), this);

        initView(view);

        return view;
    }

    @SuppressLint("WrongConstant")
    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.transaction_list_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {
        TransactionListAdapter adapter = new TransactionListAdapter(transactionList);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }

}

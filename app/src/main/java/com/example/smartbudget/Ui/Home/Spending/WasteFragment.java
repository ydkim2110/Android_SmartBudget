package com.example.smartbudget.Ui.Home.Spending;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsLoadListener;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.Database.TransactionRoom.TransactionItem;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Interface.IThisMonthTransactionLoadListener;
import com.example.smartbudget.Utils.Common;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WasteFragment extends Fragment implements IThisMonthTransactionsLoadListener {

    private static final String TAG = WasteFragment.class.getSimpleName();

    private static WasteFragment instance;

    public static WasteFragment getInstance(String date) {
        if (instance == null){
            instance = new WasteFragment();
        }
        Bundle args = new Bundle();
        args.putString("passed_date", date);
        instance.setArguments(args);

        return instance;
    }

    private String passed_date;

    public WasteFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_waste)
    RecyclerView rv_waste;
    @BindView(R.id.tv_no_transactions)
    TextView tv_no_transactions;

    Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waste, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        rv_waste.setHasFixedSize(true);
        rv_waste.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_waste.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        if (getArguments() != null) {
            passed_date = getArguments().getString("passed_date");
        }

        DBTransactionUtils.getThisMonthTransactionListByPattern(MainActivity.db, passed_date,"Waste", this);

        return view;
    }

    @Override
    public void onThisMonthTransactionsLoadSuccess(List<TransactionItem> transactionItemList) {
        if (transactionItemList != null) {
            if (transactionItemList.size() < 1) {
                tv_no_transactions.setVisibility(View.VISIBLE);
            }
            else {
                tv_no_transactions.setVisibility(View.GONE);
                SpendingListAdapter adapter = new SpendingListAdapter(getContext(), transactionItemList);
                rv_waste.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onThisMonthTransactionsLoadFailed(String message) {

    }
}

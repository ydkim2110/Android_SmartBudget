package com.example.smartbudget.Ui.Home.Spending;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Ui.Transaction.ITransactionLoadListener;
import com.example.smartbudget.Utils.Common;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InvestFragment extends Fragment implements ITransactionLoadListener {

    private static final String TAG = InvestFragment.class.getSimpleName();

    private static InvestFragment instance;

    public static InvestFragment getInstance() {
        return instance == null ? new InvestFragment() : instance;
    }

    public InvestFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_invest)
    RecyclerView rv_invest;

    Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invest, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        rv_invest.setHasFixedSize(true);
        rv_invest.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_invest.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        DatabaseUtils.getThisMonthTransactionListPattern(MainActivity.mDBHelper, Common.dateFormat.format(new Date()),"Invest", this);

        return view;
    }

    @Override
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {
        ListAdapter adapter = new ListAdapter(getContext(), transactionList);
        rv_invest.setAdapter(adapter);
    }

    @Override
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }
}
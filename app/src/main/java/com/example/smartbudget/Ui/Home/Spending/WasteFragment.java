package com.example.smartbudget.Ui.Home.Spending;


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
public class WasteFragment extends Fragment implements IThisMonthTransactionLoadListener {

    private static final String TAG = WasteFragment.class.getSimpleName();

    private static WasteFragment instance;

    public static WasteFragment getInstance() {
        return instance == null ? new WasteFragment() : instance;
    }

    public WasteFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_waste)
    RecyclerView rv_waste;

    Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waste, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        rv_waste.setHasFixedSize(true);
        rv_waste.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_waste.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        DatabaseUtils.getThisMonthTransactionListPattern(MainActivity.mDBHelper, Common.dateFormat.format(new Date()),"Waste", this);

        return view;
    }

    @Override
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {
        SpendingListAdapter adapter = new SpendingListAdapter(getContext(), transactionList);
        rv_waste.setAdapter(adapter);
    }

    @Override
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }
}

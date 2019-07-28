package com.example.smartbudget.Ui.Home.Spending;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Ui.Transaction.ITransactionLoadListener;
import com.example.smartbudget.Utils.Common;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class NormalFragment extends Fragment implements ITransactionLoadListener {

    private static final String TAG = NormalFragment.class.getSimpleName();

    private static NormalFragment instance;

    public static NormalFragment getInstance() {
        return instance == null ? new NormalFragment() : instance;
    }

    public NormalFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_normal)
    RecyclerView rv_normal;

    Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_normal, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        rv_normal.setHasFixedSize(true);
        rv_normal.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_normal.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        DatabaseUtils.getThisMonthTransactionListPattern(MainActivity.mDBHelper, Common.dateFormat.format(new Date()),"Normal", this);

        return view;
    }

    @Override
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {
        ListAdapter adapter = new ListAdapter(getContext(), transactionList);
        rv_normal.setAdapter(adapter);
    }

    @Override
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }
}

package com.example.smartbudget.Ui.Transaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Interface.IRVScrollChangeListener;
import com.example.smartbudget.Interface.ITransactionLoadListener;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;

import java.util.List;

public class TransactionListFragment extends Fragment implements ITransactionLoadListener {

    private static final String TAG = TransactionListFragment.class.getSimpleName();

    private IRVScrollChangeListener mIRVScrollChangeListener;

    public static TransactionListFragment newInstance(long time) {

        TransactionListFragment fragment = new TransactionListFragment();
        Bundle args = new Bundle();
        args.putString("date", Common.dateFormat.format(time));
        fragment.setArguments(args);
        return fragment;
    }

    private RecyclerView rv_transaction_list;
    private TextView tv_no_item;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mIRVScrollChangeListener = (IRVScrollChangeListener) context;
        } catch (Exception e) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dynamic_fragment_layout, container, false);
        initView(view);

        DatabaseUtils.getThisMonthTransaction(MainActivity.mDBHelper, getArguments().getString("date"), this);

        return view;
    }

    @SuppressLint("WrongConstant")
    private void initView(View view) {
        rv_transaction_list = view.findViewById(R.id.rv_transaction_list);
        tv_no_item = view.findViewById(R.id.tv_no_item);

        rv_transaction_list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_transaction_list.setLayoutManager(layoutManager);
        rv_transaction_list.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        rv_transaction_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mIRVScrollChangeListener.onRVScrollChangeListener(dy > 0);
            }
        });
    }

    @Override
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {
        if (transactionList.size() < 1) {
            rv_transaction_list.setVisibility(View.INVISIBLE);
            tv_no_item.setVisibility(View.VISIBLE);
        } else {
            rv_transaction_list.setVisibility(View.VISIBLE);
            tv_no_item.setVisibility(View.INVISIBLE);
        }
        TransactionListAdapter adapter = new TransactionListAdapter(getContext(), transactionList);
        rv_transaction_list.setAdapter(adapter);
    }

    @Override
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }

}

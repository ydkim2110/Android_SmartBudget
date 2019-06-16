package com.example.smartbudget.Account;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.Database.DBHelper;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Model.AccountModel;
import com.example.smartbudget.R;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment implements IAccountLoadListener {

    private static AccountFragment instance;

    public static AccountFragment getInstance() {
        if (instance == null) {
            instance = new AccountFragment();
        }
        return instance;
    }

    public AccountFragment() {}

    private RecyclerView mRecyclerView;
    private AccountAdapter mAccountAdapter;

    public static DBHelper mDBHelper;

    @Override
    public void onResume() {
        super.onResume();
        DatabaseUtils.getAllAccount(mDBHelper, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDBHelper = new DBHelper(getContext());
        DatabaseUtils.getAllAccount(mDBHelper, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        mRecyclerView = view.findViewById(R.id.account_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onAccountLoadSuccess(List<AccountModel> accountList) {
        if (accountList == null) {
            accountList = new ArrayList<>();
            mAccountAdapter = new AccountAdapter(getActivity(), accountList, this);
            mRecyclerView.setAdapter(mAccountAdapter);
            mAccountAdapter.notifyDataSetChanged();
        } else {
            mAccountAdapter = new AccountAdapter(getActivity(), accountList, this);
            mRecyclerView.setAdapter(mAccountAdapter);
            mAccountAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAccountDeleteSuccess(boolean isSuccess) {
        if (isSuccess) {
            DatabaseUtils.getAllAccount(mDBHelper, this);
        }
    }
}

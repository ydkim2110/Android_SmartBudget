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
import com.example.smartbudget.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements IAccountLoadListener {

    private static AccountFragment instance;

    public static AccountFragment getInstance() {
        if (instance == null) {
            instance = new AccountFragment();
        }
        return instance;
    }

    public AccountFragment() {
        // Required empty public constructor
    }

    private RecyclerView mRecyclerView;
    private AccountAdapter mAccountAdapter;

    private DBHelper mDBHelper;

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

//        DividerItemDecoration dividerItemDecoration =
//                new DividerItemDecoration(getContext(), new LinearLayoutManager(getContext()).getOrientation());
//        mRecyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    @Override
    public void onAccountLoadSuccess(List<Account> accountList) {
        mAccountAdapter = new AccountAdapter(accountList);
        mRecyclerView.setAdapter(mAccountAdapter);
        mAccountAdapter.notifyDataSetChanged();
    }
}

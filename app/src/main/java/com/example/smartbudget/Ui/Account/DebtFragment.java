package com.example.smartbudget.Ui.Account;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Interface.IAccountsLoadListener;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DebtFragment extends Fragment implements IAccountsLoadListener {

    private static DebtFragment instance;

    public static DebtFragment getInstance() {
        return instance == null ? new DebtFragment() : instance;
    }

    public DebtFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_debt)
    RecyclerView rv_debt;

    Unbinder mUnbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_debt, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        rv_debt.setLayoutManager(new LinearLayoutManager(getActivity()));

        DatabaseUtils.getAccountsByType(MainActivity.mDBHelper, "debt", this);
        return view;
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onAccountsLoadSuccess(List<AccountModel> accountList) {
        if (accountList != null) {
            AccountListAdapter adapter = new AccountListAdapter(getContext(), accountList);
            rv_debt.setAdapter(adapter);
        }
    }

    @Override
    public void onAccountDeleteSuccess(boolean isSuccess) {

    }
}

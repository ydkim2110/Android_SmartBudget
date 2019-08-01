package com.example.smartbudget.Ui.Account;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Interface.IAccountsLoadListener;
import com.example.smartbudget.Interface.IFABClickListener;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment implements IAccountsLoadListener, IFABClickListener {

    private static final String TAG = AccountFragment.class.getSimpleName();

    public static BSAccountAddFragment mBSAccountAddFragment;
    public static BSAccountMenuFragment mBSAccountMenuFragment;

    private static AccountFragment instance;

    public static AccountFragment getInstance() {
        return instance == null ? new AccountFragment() : instance;
    }

    public AccountFragment() {
    }

    private RecyclerView mRecyclerView;
    private AccountAdapter mAccountAdapter;

    @Override
    public void onResume() {
        super.onResume();
        Common.SELECTED_ACCOUNT = null;
        DatabaseUtils.getAllAccount(MainActivity.mDBHelper, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setmIFABClickListener(this);
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        DatabaseUtils.getAllAccount(MainActivity.mDBHelper, this);

        mRecyclerView = view.findViewById(R.id.rv_account);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(layoutManager.getOrientation());
        mRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onAccountsLoadSuccess(List<AccountModel> accountList) {
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
            DatabaseUtils.getAllAccount(MainActivity.mDBHelper, this);
        }
    }

    @Override
    public void onFABClicked() {
        mBSAccountAddFragment = BSAccountAddFragment.getInstance();
        mBSAccountAddFragment.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), mBSAccountAddFragment.getTag());
    }
}

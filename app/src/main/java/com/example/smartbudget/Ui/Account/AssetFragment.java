package com.example.smartbudget.Ui.Account;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
public class AssetFragment extends Fragment implements IAccountsLoadListener {

    private static final String TAG = AssetFragment.class.getSimpleName();

    private static AssetFragment instance;

    public static AssetFragment getInstance() {
        Log.d(TAG, "getInstance: called!!");
        return instance == null ? new AssetFragment() : instance;
    }

    public AssetFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_asset)
    RecyclerView rv_asset;

    Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_asset, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        rv_asset.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_asset.setLayoutManager(layoutManager);

        DatabaseUtils.getAccountsByType(MainActivity.mDBHelper, "asset", this);

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
            rv_asset.setAdapter(adapter);
        }
    }

    @Override
    public void onAccountDeleteSuccess(boolean isSuccess) {

    }
}

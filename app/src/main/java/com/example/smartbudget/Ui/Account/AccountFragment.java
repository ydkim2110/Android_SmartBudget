package com.example.smartbudget.Ui.Account;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.AccountModel;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment implements IAccountLoadListener {

    private static AccountFragment instance;

    public static AccountFragment getInstance() {
        return instance == null ? new AccountFragment() : instance;
    }

    public AccountFragment() {
    }

    private RecyclerView mRecyclerView;
    private AccountAdapter mAccountAdapter;

    private LinearLayout bottom_sheet;
    private BottomSheetBehavior sheetBehavior;
    private FloatingActionButton fab;

    @Override
    public void onResume() {
        super.onResume();
        Common.SELECTED_ACCOUNT = null;
        DatabaseUtils.getAllAccount(MainActivity.mDBHelper, this);
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        DatabaseUtils.getAllAccount(MainActivity.mDBHelper, this);

        bottom_sheet = view.findViewById(R.id.bottom_sheet);
        fab = view.findViewById(R.id.fab);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        sheetBehavior.setPeekHeight(180);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        fab.setTag("expanded");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        fab.setTag("collapsed");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        fab.setOnClickListener(v -> {
            toggleBottomSheet();
        });

        mRecyclerView = view.findViewById(R.id.rv_account);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(layoutManager.getOrientation());
        mRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    private void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
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
            DatabaseUtils.getAllAccount(MainActivity.mDBHelper, this);
        }
    }
}

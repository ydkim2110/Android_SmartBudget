package com.example.smartbudget.Ui.Account;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smartbudget.Utils.Common;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.R;

public class BSAccountAddFragment extends BottomSheetDialogFragment {

    private static BSAccountAddFragment instance;

    public static BSAccountAddFragment getInstance() {
        if (instance == null) {
            instance = new BSAccountAddFragment();
        }
        return instance;
    }

    public BSAccountAddFragment() {
    }

    private RecyclerView rv_add_account;
    private String[] title = {"현금", "수시입출금", "예금", "적금", "주식", "펀드", "보험", "부동산", "기타자산", "대출", "기타부채"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bsaccount_add, container, false);

        rv_add_account = view.findViewById(R.id.rv_add_account);

        BSAccountAddAdapter adapter = new BSAccountAddAdapter(getActivity(), title);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        rv_add_account.setLayoutManager(layoutManager);

        rv_add_account.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        Common.SELECTED_ACCOUNT = null;
        super.onDestroyView();
    }
}

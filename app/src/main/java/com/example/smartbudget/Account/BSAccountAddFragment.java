package com.example.smartbudget.Account;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private RecyclerView mRecyclerView;
    private String[] title = {"현금", "수시입출금", "예금", "적금", "주식", "펀드", "보험", "부동산", "기타자산", "대출", "기타부채"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bsaccount_add, container, false);

        mRecyclerView = view.findViewById(R.id.account_add_recyclerview);

        BSAccountAddAdapter adapter = new BSAccountAddAdapter(getActivity(), title);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(adapter);


        return view;
    }
}

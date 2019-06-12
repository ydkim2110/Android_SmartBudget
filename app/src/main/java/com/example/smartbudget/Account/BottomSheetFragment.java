package com.example.smartbudget.Account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.R;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    public BottomSheetFragment() {
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
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_dialog, container, false);

        mRecyclerView = view.findViewById(R.id.account_add_recyclerview);

        BottomSheetAdapter adapter = new BottomSheetAdapter(getActivity(), title);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(adapter);


        return view;
    }
}

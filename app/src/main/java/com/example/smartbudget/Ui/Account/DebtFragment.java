package com.example.smartbudget.Ui.Account;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DebtFragment extends Fragment {

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
        CommonRecycleAdapter adapter = new CommonRecycleAdapter(createItemList());
        rv_debt.setAdapter(adapter);

        return view;
    }

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
        for(int i=0;i<5;i++) {
            itemList.add("Item "+i);
        }
        return itemList;
    }


    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

}

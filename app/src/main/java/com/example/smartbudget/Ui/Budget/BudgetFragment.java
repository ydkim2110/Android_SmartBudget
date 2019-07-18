package com.example.smartbudget.Ui.Budget;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetFragment extends Fragment {

    private static BudgetFragment instance;

    public static BudgetFragment getInstance() {
        if (instance == null) {
            instance = new BudgetFragment();
        }
        return instance;
    }

    public BudgetFragment() {
        // Required empty public constructor
    }

    private RecyclerView budgetRecyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        budgetRecyclerview = view.findViewById(R.id.budget_recyclerview);
        budgetRecyclerview.setHasFixedSize(true);
        budgetRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Budget> budgetList = new ArrayList<>();
        budgetList.add(new Budget("교통", 200000));
        budgetList.add(new Budget("식비", 500000));
        budgetList.add(new Budget("쇼핑", 300000));
        budgetList.add(new Budget("엔터테이먼트", 400000));
        budgetList.add(new Budget("집", 700000));
        budgetList.add(new Budget("선물기부", 900000));
        budgetList.add(new Budget("건강", 1000000));
        budgetList.add(new Budget("기타", 550000));

        BudgetAdapter budgetAdapter = new BudgetAdapter(budgetList);
        budgetRecyclerview.setAdapter(budgetAdapter);
        budgetAdapter.notifyDataSetChanged();

        return view;
    }

}

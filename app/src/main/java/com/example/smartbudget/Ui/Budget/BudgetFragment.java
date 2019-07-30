package com.example.smartbudget.Ui.Budget;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Interface.IRVScrollChangeListener;
import com.example.smartbudget.Model.CategoryModel;
import com.example.smartbudget.Model.DefaultCategories;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Ui.Transaction.Add.ICategoryLoadListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BudgetFragment extends Fragment {

    private static BudgetFragment instance;

    private IRVScrollChangeListener mIRVScrollChangeListener;

    public static BudgetFragment getInstance() {
        if (instance == null) {
            instance = new BudgetFragment();
        }
        return instance;
    }

    public BudgetFragment() {
    }

    private RecyclerView rv_budget;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mIRVScrollChangeListener = (IRVScrollChangeListener) context;
        } catch (Exception e) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        rv_budget = view.findViewById(R.id.rv_budget);
        rv_budget.setHasFixedSize(true);
        rv_budget.setLayoutManager(new LinearLayoutManager(getContext()));

        rv_budget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mIRVScrollChangeListener.onRVScrollChangeListener(dy > 0);
            }
        });

        BudgetAdapter budgetAdapter = new BudgetAdapter(getContext(), Arrays.asList(DefaultCategories.getDefaultExpenseCategories()));
        rv_budget.setAdapter(budgetAdapter);
        budgetAdapter.notifyDataSetChanged();

        return view;
    }

}

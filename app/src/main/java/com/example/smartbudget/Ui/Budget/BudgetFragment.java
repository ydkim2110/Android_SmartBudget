package com.example.smartbudget.Ui.Budget;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Interface.IRVScrollChangeListener;
import com.example.smartbudget.Interface.IThisMonthTransactionByCategoryLoadListener;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BudgetFragment extends Fragment implements IThisMonthTransactionByCategoryLoadListener {

    private static final String TAG = BudgetFragment.class.getSimpleName();

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
    private Calendar calendar = Calendar.getInstance();

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

        DatabaseUtils.getThisMonthTransactionByCategory(MainActivity.mDBHelper, Common.dateFormat.format(new Date()), this);

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

        return view;
    }

    @Override
    public void onThisMonthTransactionByCategoryLoadSuccess(List<ExpenseByCategory> expenseByCategoryList) {
        if (expenseByCategoryList != null) {
            BudgetAdapter budgetAdapter = new BudgetAdapter(getContext(), expenseByCategoryList);
            rv_budget.setAdapter(budgetAdapter);
        }
    }

    @Override
    public void onThisMonthTransactionByCategoryLoadFailed(String message) {

    }
}

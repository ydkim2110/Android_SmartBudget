package com.example.smartbudget.Ui.Budget;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.CategoryModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Ui.Transaction.Add.ICategoryLoadListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetFragment extends Fragment implements ICategoryLoadListener {

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

        DatabaseUtils.getAllCategory(MainActivity.mDBHelper, this);

        return view;
    }

    @Override
    public void onCategoryLoadSuccess(List<CategoryModel> categoryList) {
        if (categoryList != null) {
            BudgetAdapter budgetAdapter = new BudgetAdapter(getContext(), categoryList);
            budgetRecyclerview.setAdapter(budgetAdapter);
            budgetAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCategoryLoadFailed(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}

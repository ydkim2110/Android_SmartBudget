package com.example.smartbudget.Ui.Transaction.Add.Category;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.Model.DefaultCategories;
import com.example.smartbudget.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {

    private static IncomeFragment instance;

    public static IncomeFragment getInstance() {
        return instance == null ? new IncomeFragment() : instance;
    }

    public IncomeFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_income_categories)
    RecyclerView rv_income_categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_income, container, false);

        ButterKnife.bind(this, view);

        rv_income_categories.setHasFixedSize(true);
        rv_income_categories.setLayoutManager(new LinearLayoutManager(getContext()));

        CategoryIncomeAdapter adapter = new CategoryIncomeAdapter(getContext(), Arrays.asList(DefaultCategories.getDefaultIncomeCategories()));
        rv_income_categories.setAdapter(adapter);

        return view;
    }

}

package com.example.smartbudget.Ui.Budget;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.Database.DBBudgetUtils;
import com.example.smartbudget.Interface.IBudgetLoadListener;
import com.example.smartbudget.Model.BudgetModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunningFragment extends Fragment implements IBudgetLoadListener {

    private static final String TAG = RunningFragment.class.getSimpleName();

    private static RunningFragment instance;

    public static RunningFragment getInstance() {
        return instance == null ? new RunningFragment() : instance;
    }

    public RunningFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_budget)
    RecyclerView rv_budget;

    Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called!!");
        View view = inflater.inflate(R.layout.fragment_running, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        rv_budget.setHasFixedSize(true);
        rv_budget.setLayoutManager(new LinearLayoutManager(getContext()));

        DBBudgetUtils.getRunningBudgets(MainActivity.mDBHelper, this);

        return view;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onBudgetLoadSuccess(List<BudgetModel> budgetModelList) {
        Log.d(TAG, "onBudgetLoadSuccess: called!!");
        BudgetListAdapter adapter = new BudgetListAdapter(getContext(), budgetModelList);
        rv_budget.setAdapter(adapter);
    }

    @Override
    public void onBudgetLoadFailed(String message) {
        Log.d(TAG, "onBudgetLoadFailed: called!!");
    }
}

package com.example.smartbudget.Ui.Budget;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartbudget.Database.BudgetRoom.BudgetItem;
import com.example.smartbudget.Database.BudgetRoom.DBBudgetUtils;
import com.example.smartbudget.Database.Interface.ISumAmountByBudgetListener;
import com.example.smartbudget.Database.Model.SumBudget;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.Interface.IBudgetLoadListener;
import com.example.smartbudget.Model.EventBus.AddBudgetEvent;
import com.example.smartbudget.Model.EventBus.AddDeleteEvent;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
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
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_running, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        rv_budget.setHasFixedSize(true);
        rv_budget.setLayoutManager(new LinearLayoutManager(getContext()));

        loadData();

        return view;
    }

    private void loadData() {
        Log.d(TAG, "loadData: called!!");
        DBBudgetUtils.getRunningBudgets(MainActivity.db, Common.dateFormat.format(new Date()), this);
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onInsertBudgetEvent(AddBudgetEvent event) {
        Log.d(TAG, "onInsertBudgetEvent: called!!");
        if (event.isSuccess()) {
            loadData();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onDeleteBudgetEvent(AddDeleteEvent event) {
        Log.d(TAG, "onDeleteBudgetEvent: called!!");
        if (event.isSuccess()) {
            loadData();
        }
    }

    @Override
    public void onBudgetLoadSuccess(List<SumBudget> budgetItemList) {
        Log.d(TAG, "onBudgetLoadSuccess: called!!");
        BudgetListAdapter adapter = new BudgetListAdapter(getContext(), budgetItemList);
        rv_budget.setAdapter(adapter);
    }

    @Override
    public void onBudgetLoadFailed(String message) {

    }

}

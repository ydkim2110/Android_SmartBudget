package com.example.smartbudget.Ui.Home;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.EventBus.AddTransactionEvent;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.Ui.Home.Overview.OverviewActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Ui.Transaction.ITransactionLoadListener;
import com.example.smartbudget.Utils.Common;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ITransactionLoadListener {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private static HomeFragment instance;

    public static HomeFragment getInstance() {
        return instance == null ? new HomeFragment() : instance;
    }

    public HomeFragment() {
    }

    public interface IBudgetContainerClickListener {
        void onBudgetContainerClicked();
    }

    private IBudgetContainerClickListener mIBudgetContainerClickListener;

    public void setIBudgetContainerClickListener(IBudgetContainerClickListener IBudgetContainerClickListener) {
        mIBudgetContainerClickListener = IBudgetContainerClickListener;
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private WeekTransactionAdapter mAdapter;
    private ProgressBar progressBar;
    private ProgressBar pb_circle_normal;
    private ProgressBar pb_circle_waste;
    private ProgressBar pb_circle_invest;
    private TextView progressBarPercentage;
    private TextView usedBudgetTv;
    private TextView totalBudgetTv;
    private TextView tv_weekday;

    private ConstraintLayout homeOverviewContainer;
    private ConstraintLayout homeBudgetContainer;

    private HashMap<String, List<TransactionModel>> groupedHashMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        setProgressBar();
        handleViewClick();

        mRecyclerView = view.findViewById(R.id.transaction_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseUtils.getThisWeekTransaction(MainActivity.mDBHelper, this);

        return view;
    }

    private void setProgressBar() {
        Log.d(TAG, "setProgressBar: called!!");
        int maxValue = 4000000;
        int currentValue = 3200000;

        usedBudgetTv.setText(new StringBuilder(Common.changeNumberToComma(currentValue)).append("원"));
        totalBudgetTv.setText(new StringBuilder(Common.changeNumberToComma(maxValue)).append("원"));
        progressBarPercentage.setText(new StringBuilder(Common.calcPercentage(currentValue, maxValue)).append("%"));

        progressBar.setMax(maxValue);
        ObjectAnimator progressAnim = ObjectAnimator.ofInt(progressBar, "progress", 0, currentValue);
        progressAnim.setDuration(500);
        progressAnim.setInterpolator(new LinearInterpolator());
        progressAnim.start();

        ObjectAnimator progressAnim2 = ObjectAnimator.ofInt(pb_circle_normal, "progress", 0, 65);
        progressAnim2.setDuration(500);
        progressAnim2.setInterpolator(new LinearInterpolator());
        progressAnim2.start();

        ObjectAnimator progressAnim3 = ObjectAnimator.ofInt(pb_circle_waste, "progress", 0, 25);
        progressAnim3.setDuration(500);
        progressAnim3.setInterpolator(new LinearInterpolator());
        progressAnim3.start();

        ObjectAnimator progressAnim4 = ObjectAnimator.ofInt(pb_circle_invest, "progress", 0, 15);
        progressAnim4.setDuration(500);
        progressAnim4.setInterpolator(new LinearInterpolator());
        progressAnim4.start();

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reloadData(AddTransactionEvent event) {
        if (event != null) {
            DatabaseUtils.getThisWeekTransaction(MainActivity.mDBHelper, this);
        }
    }

    @Override
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {

        groupedHashMap = groupDataIntoHashMap(transactionList);

        Set set = groupedHashMap.entrySet();

        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Log.d(TAG, "onTransactionLoadSuccess: " + entry.getKey());
        }

        loadData();
    }

    @Override
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }

    private void loadData() {
        Log.d(TAG, "loadData: called!!");
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mAdapter = new WeekTransactionAdapter(getContext(), groupedHashMap);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void handleViewClick() {
        homeOverviewContainer.setOnClickListener(v -> {
            getContext().startActivity(new Intent(getContext(), OverviewActivity.class));
        });
        homeBudgetContainer.setOnClickListener(v -> {
            mIBudgetContainerClickListener.onBudgetContainerClicked();
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            DatabaseUtils.getThisWeekTransaction(MainActivity.mDBHelper, this);
        });
    }

    private void initView(View view) {
        Log.d(TAG, "initView: called!!");
        homeOverviewContainer = view.findViewById(R.id.home_overview_container);
        homeBudgetContainer = view.findViewById(R.id.home_budget_container);
        mSwipeRefreshLayout = view.findViewById(R.id.refresh_home_fragment);
        progressBar = view.findViewById(R.id.progressBar);
        pb_circle_normal = view.findViewById(R.id.pb_circle_normal);
        pb_circle_waste = view.findViewById(R.id.pb_circle_waste);
        pb_circle_invest = view.findViewById(R.id.pb_circle_invest);
        progressBarPercentage = view.findViewById(R.id.progressbar_percentage);
        usedBudgetTv = view.findViewById(R.id.used_budget_tv);
        totalBudgetTv = view.findViewById(R.id.total_budget_tv);
        tv_weekday = view.findViewById(R.id.tv_weekday);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd");

        tv_weekday.setText(new StringBuilder("(")
                .append(dateFormat.format(Common.getWeekStartDate()))
                .append(" ~ ")
                .append(dateFormat.format(Common.getWeekEndDate()))
                .append(")"));
    }

    private HashMap<String, List<TransactionModel>> groupDataIntoHashMap(List<TransactionModel> listOfTransaction) {

        HashMap<String, List<TransactionModel>> groupedHashMap = new HashMap<>();

        if (listOfTransaction != null) {
            for (TransactionModel dataModel : listOfTransaction) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String hashMapkey = dataModel.getTransaction_date();

                if (groupedHashMap.containsKey(hashMapkey)) {
                    groupedHashMap.get(hashMapkey).add(dataModel);
                } else {
                    List<TransactionModel> list = new ArrayList<>();
                    list.add(dataModel);
                    groupedHashMap.put(hashMapkey, list);
                }
            }
        }

        return groupedHashMap;
    }

}

package com.example.smartbudget.Ui.Home;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.Overview.OverviewActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Ui.Transaction.ITransactionLoadListener;
import com.example.smartbudget.Utils.Common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private TransactionAdapter mAdapter;
    private ProgressBar progressBar;
    private TextView progressBarPercentage;
    private TextView usedBudgetTv;
    private TextView totalBudgetTv;

    private ConstraintLayout homeOverviewContainer;
    private ConstraintLayout homeBudgetContainer;

    private List<ListItem> mConsolidatedList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        handleViewClick();

        int maxValue = 4000000;
        int currentValue = 3200000;

        usedBudgetTv.setText(Common.changeNumberToComma(currentValue) +"원");
        totalBudgetTv.setText(Common.changeNumberToComma(maxValue)+"원");
        progressBarPercentage.setText(Common.calcPercentage(currentValue, maxValue)+"%");

        progressBar.setMax(maxValue);
        ObjectAnimator progressAnim = ObjectAnimator.ofInt(progressBar, "progress", 0, currentValue);
        progressAnim.setDuration(500);
        progressAnim.setInterpolator(new LinearInterpolator());
        progressAnim.start();

        mRecyclerView = view.findViewById(R.id.transaction_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(), new LinearLayoutManager(getContext()).getOrientation());

        mRecyclerView.addItemDecoration(dividerItemDecoration);

        DatabaseUtils.getAllTransaction(MainActivity.mDBHelper, this);

/*
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-02"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-02"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-04"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-04"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-04"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-05"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-05"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-12"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-12"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-15"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-15"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-15"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-18"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-18"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-19"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-19"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-22"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-22"));
        mTransactionList.add(new Transaction("교통", "택시", 12000, "2019-05-22"));
*/
        return view;
    }

    @Override
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {
        mConsolidatedList = new ArrayList<>();

        HashMap<String, List<TransactionModel>> groupedHashMap = groupDataIntoHashMap(transactionList);

        int total;

        for (String date : groupedHashMap.keySet()) {
            total = 0;

            DateItem dateItem = new DateItem();
            dateItem.setDate(date);

            for (TransactionModel transactionModel : groupedHashMap.get(date)) {
                total += transactionModel.getTransaction_amount();
            }

            dateItem.setTotal(total);
            mConsolidatedList.add(dateItem);

            for (TransactionModel transactionModel : groupedHashMap.get(date)) {
                TransactionItem transactionItem = new TransactionItem();
                transactionItem.setTransaction(transactionModel);
                mConsolidatedList.add(transactionItem);
            }
        }

        loadData();
    }

    @Override
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }

    private void loadData() {
        Log.d(TAG, "loadData: called!!");
        mAdapter = new TransactionAdapter(getContext(), mConsolidatedList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void handleViewClick() {
        homeOverviewContainer.setOnClickListener(v -> {
            getContext().startActivity(new Intent(getContext(), OverviewActivity.class));
        });
        homeBudgetContainer.setOnClickListener(v -> {
            mIBudgetContainerClickListener.onBudgetContainerClicked();
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    private void initView(View view) {
        Log.d(TAG, "initView: called!!");
        homeOverviewContainer = view.findViewById(R.id.home_overview_container);
        homeBudgetContainer = view.findViewById(R.id.home_budget_container);
        mSwipeRefreshLayout = view.findViewById(R.id.refresh_home_fragment);
        progressBar = view.findViewById(R.id.progressBar);
        progressBarPercentage = view.findViewById(R.id.progressbar_percentage);
        usedBudgetTv = view.findViewById(R.id.used_budget_tv);
        totalBudgetTv = view.findViewById(R.id.total_budget_tv);
    }

    private HashMap<String, List<TransactionModel>> groupDataIntoHashMap(List<TransactionModel> listOfTransaction) {

        HashMap<String, List<TransactionModel>> groupedHashMap = new HashMap<>();

        for (TransactionModel dataModel : listOfTransaction) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");

            String hashMapkey = ""+dateFormat.format(dataModel.getTransaction_date());

            if (groupedHashMap.containsKey(hashMapkey)) {
                // The key is already in the HashMap; add the pojo object
                // against the existing key.
                groupedHashMap.get(hashMapkey).add(dataModel);
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                List<TransactionModel> list = new ArrayList<>();
                list.add(dataModel);
                groupedHashMap.put(hashMapkey, list);
            }
        }

        return groupedHashMap;
    }
}

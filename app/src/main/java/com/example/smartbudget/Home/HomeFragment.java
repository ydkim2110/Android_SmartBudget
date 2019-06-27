package com.example.smartbudget.Home;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
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

import com.example.smartbudget.Main.ICalendarChangeListener;
import com.example.smartbudget.Main.MainActivity;
import com.example.smartbudget.Overview.OverviewActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ICalendarChangeListener {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private static HomeFragment instance;

    public static HomeFragment getInstance() {
        if (instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public interface IBudgetContainerClickListener {
        void onBudgetContainerClicked();
    }

    private IBudgetContainerClickListener mIBudgetContainerClickListener;

    public void setIBudgetContainerClickListener(IBudgetContainerClickListener IBudgetContainerClickListener) {
        mIBudgetContainerClickListener = IBudgetContainerClickListener;
    }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView transactionRecyclerview;
    private TransactionAdapter transactionAdapter;
    private ProgressBar progressBar;
    private TextView progressBarPercentage;
    private TextView usedBudgetTv;
    private TextView totalBudgetTv;

    private ConstraintLayout homeOverviewContainer;
    private ConstraintLayout homeBudgetContainer;

    List<Transaction> transactionList;
    List<ListItem> consolidatedList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setICalendarChangeListener(this);
    }

    @Override
    public void onCalendarClicked(Calendar selectedDate) {
        Log.d(TAG, "onCalendarClicked: called!!");
        Log.d(TAG, "onCalendarClicked: selected year: "+selectedDate.get(Calendar.YEAR));
        Log.d(TAG, "onCalendarClicked: selected month: "+(selectedDate.get(Calendar.MONTH)+1));
        Log.d(TAG, "onCalendarClicked: selected day: "+selectedDate.get(Calendar.DAY_OF_MONTH));
    }

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

        transactionRecyclerview = view.findViewById(R.id.transaction_recyclerview);
        transactionRecyclerview.setHasFixedSize(true);
        transactionRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(), new LinearLayoutManager(getContext()).getOrientation());

        transactionRecyclerview.addItemDecoration(dividerItemDecoration);

        transactionList = new ArrayList<>();
        consolidatedList = new ArrayList<>();

        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-02"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-02"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-04"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-04"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-04"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-05"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-05"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-12"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-12"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-15"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-15"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-15"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-18"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-18"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-19"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-19"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-22"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-22"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-22"));

        HashMap<String, List<Transaction>> groupedHashMap = groupDataIntoHashMap(transactionList);

        for (String date : groupedHashMap.keySet()) {
            DateItem dateItem = new DateItem();
            dateItem.setDate(date);
            consolidatedList.add(dateItem);

            for (Transaction transaction : groupedHashMap.get(date)) {
                TransactionItem transactionItem = new TransactionItem();
                transactionItem.setTransaction(transaction);
                consolidatedList.add(transactionItem);
            }
        }

        loadData();


        return view;
    }

    private void loadData() {
        Log.d(TAG, "loadData: called!!");

        transactionAdapter = new TransactionAdapter(getContext(), consolidatedList);
        transactionRecyclerview.setAdapter(transactionAdapter);
        transactionAdapter.notifyDataSetChanged();
    }

    private void handleViewClick() {
        homeOverviewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), OverviewActivity.class));
            }
        });
        homeBudgetContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIBudgetContainerClickListener.onBudgetContainerClicked();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initView(View view) {
        homeOverviewContainer = view.findViewById(R.id.home_overview_container);
        homeBudgetContainer = view.findViewById(R.id.home_budget_container);
        swipeRefreshLayout = view.findViewById(R.id.refresh_home_fragment);
        progressBar = view.findViewById(R.id.progressBar);
        progressBarPercentage = view.findViewById(R.id.progressbar_percentage);
        usedBudgetTv = view.findViewById(R.id.used_budget_tv);
        totalBudgetTv = view.findViewById(R.id.total_budget_tv);
    }

    private HashMap<String, List<Transaction>> groupDataIntoHashMap(List<Transaction> listOfTransaction) {

        HashMap<String, List<Transaction>> groupedHashMap = new HashMap<>();

        for (Transaction dataModel : listOfTransaction) {

            String hashMapkey = dataModel.getDate();

            if (groupedHashMap.containsKey(hashMapkey)) {
                // The key is already in the HashMap; add the pojo object
                // against the existing key.
                groupedHashMap.get(hashMapkey).add(dataModel);
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                List<Transaction> list = new ArrayList<>();
                list.add(dataModel);
                groupedHashMap.put(hashMapkey, list);
            }
        }

        return groupedHashMap;

    }
}

package com.example.smartbudget.Ui.Home;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Model.SpendingPattern;
import com.example.smartbudget.Interface.IBudgetContainerClickListener;
import com.example.smartbudget.Interface.INSVScrollChangeListener;
import com.example.smartbudget.Interface.IThisMonthTransactionByPatternLoadListener;
import com.example.smartbudget.Interface.IThisWeekTransactionLoadListener;
import com.example.smartbudget.Model.EventBus.AddTransactionEvent;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.Ui.Home.Overview.OverviewActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Home.Spending.SpendingActivity;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Interface.IThisMonthTransactionLoadListener;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.DateHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements
        IThisMonthTransactionLoadListener, IThisMonthTransactionByPatternLoadListener, IThisWeekTransactionLoadListener {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private static HomeFragment instance;

    public static HomeFragment getInstance() {
        return instance == null ? new HomeFragment() : instance;
    }

    public HomeFragment() {
    }

    private IBudgetContainerClickListener mIBudgetContainerClickListener;
    private INSVScrollChangeListener mINSVScrollChangeListener;

    Unbinder mUnbinder;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView rv_transaction;
    private WeekTransactionAdapter mAdapter;
    private ProgressBar progressBar;
    private TextView progressBarPercentage;
    private TextView usedBudgetTv;
    private TextView totalBudgetTv;
    private TextView tv_weekday;
    
    @BindView(R.id.tv_income_total)
    TextView tv_income_total;
    @BindView(R.id.tv_expense_total)
    TextView tv_expense_total;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    
    @BindView(R.id.pb_circle_normal)
    ProgressBar pb_circle_normal;
    @BindView(R.id.pb_circle_waste)
    ProgressBar pb_circle_waste;
    @BindView(R.id.pb_circle_invest)
    ProgressBar pb_circle_invest;
    @BindView(R.id.tv_normal)
    TextView tv_normal;
    @BindView(R.id.tv_waste)
    TextView tv_waste;
    @BindView(R.id.tv_invest)
    TextView tv_invest;
    @BindView(R.id.tv_normal_sum)
    TextView tv_normal_sum;
    @BindView(R.id.tv_waste_sum)
    TextView tv_waste_sum;
    @BindView(R.id.tv_invest_sum)
    TextView tv_invest_sum;
    @BindView(R.id.tv_total_spending)
    TextView tv_total_spending;
    
    @BindView(R.id.nsv_container)
    NestedScrollView nsv_container;
    @BindView(R.id.ll_no_items)
    LinearLayout ll_no_items;

    private ConstraintLayout homeOverviewContainer;
    private ConstraintLayout homeBudgetContainer;
    @BindView(R.id.home_spending_pattern_container)
    ConstraintLayout home_spending_pattern_container;

    private HashMap<String, List<TransactionModel>> groupedHashMap;

    private int total = 0;
    private int normal_percentage = 0;
    private int waste_percentage = 0;
    private int invest_percentage = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIBudgetContainerClickListener = (IBudgetContainerClickListener) context;
        mINSVScrollChangeListener = (INSVScrollChangeListener) context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        initView(view);
        setProgressBar();
        handleViewClick();

        rv_transaction = view.findViewById(R.id.rv_transaction);
        rv_transaction.setHasFixedSize(true);
        rv_transaction.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseUtils.getThisMonthTransaction(MainActivity.mDBHelper, Common.dateFormat.format(new Date()),this);
        DatabaseUtils.getThisMonthTransactionByPattern(MainActivity.mDBHelper, Common.dateFormat.format(new Date()), this);
        DatabaseUtils.getThisWeekTransaction(MainActivity.mDBHelper, this);

        return view;
    }

    private void setProgressBar() {
        Log.d(TAG, "setProgressBar: called!!");
        int maxValue = 4000000;
        int currentValue = 3200000;

        usedBudgetTv.setText(new StringBuilder(Common.changeNumberToComma(currentValue)).append("원"));
        totalBudgetTv.setText(new StringBuilder(Common.changeNumberToComma(maxValue)).append("원"));
        progressBarPercentage.setText(new StringBuilder(Common.calcPercentageDownToTwo(currentValue, maxValue)).append("%"));

        progressBar.setMax(maxValue);
        ObjectAnimator progressAnim = ObjectAnimator.ofInt(progressBar, "progress", 0, currentValue);
        progressAnim.setDuration(500);
        progressAnim.setInterpolator(new LinearInterpolator());
        progressAnim.start();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reloadData(AddTransactionEvent event) {
        if (event != null) {
            DatabaseUtils.getThisMonthTransaction(MainActivity.mDBHelper, Common.dateFormat.format(new Date()),this);
            DatabaseUtils.getThisMonthTransactionByPattern(MainActivity.mDBHelper, Common.dateFormat.format(new Date()), this);
            DatabaseUtils.getThisWeekTransaction(MainActivity.mDBHelper, this);
        }
    }

    private void loadData() {
        Log.d(TAG, "loadData: called!!");
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mAdapter = new WeekTransactionAdapter(getContext(), groupedHashMap);
        rv_transaction.setAdapter(mAdapter);
    }

    private void handleViewClick() {
        homeOverviewContainer.setOnClickListener(v -> {
            getContext().startActivity(new Intent(getContext(), OverviewActivity.class));
        });
        homeBudgetContainer.setOnClickListener(v -> {
            mIBudgetContainerClickListener.onBudgetContainerClicked();
        });
        home_spending_pattern_container.setOnClickListener(v -> {
            getContext().startActivity(new Intent(getContext(), SpendingActivity.class));
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            DatabaseUtils.getThisMonthTransaction(MainActivity.mDBHelper, Common.dateFormat.format(new Date()),this);
            DatabaseUtils.getThisMonthTransactionByPattern(MainActivity.mDBHelper, Common.dateFormat.format(new Date()), this);
            DatabaseUtils.getThisWeekTransaction(MainActivity.mDBHelper, this);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
                .append(dateFormat.format(DateHelper.getWeekStartDate()))
                .append(" ~ ")
                .append(dateFormat.format(DateHelper.getWeekEndDate()))
                .append(")"));

        nsv_container.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                mINSVScrollChangeListener.onNSVScrollChangeListener(scrollY > oldScrollY);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private HashMap<String, List<TransactionModel>> groupDataIntoHashMap(List<TransactionModel> listOfTransaction) {

        HashMap<String, List<TransactionModel>> groupedHashMap = new HashMap<>();

        if (listOfTransaction != null) {
            for (TransactionModel dataModel : listOfTransaction) {
                String hashMapkey = Common.dateFormat.format(DateHelper.changeStringToDate(dataModel.getTransaction_date()));

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

    @Override
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {
        int expense = 0;
        int income = 0;
        if (transactionList != null) {
            for (TransactionModel model : transactionList) {
                if (model.getTransaction_type().equals("Income")) {
                    income += model.getTransaction_amount();
                } else if (model.getTransaction_type().equals("Expense")) {
                    expense += model.getTransaction_amount();
                }
            }
        }
        tv_income_total.setText(new StringBuilder(Common.changeNumberToComma(income)).append("원"));
        tv_expense_total.setText(new StringBuilder(Common.changeNumberToComma(expense)).append("원"));
        tv_balance.setText(new StringBuilder(Common.changeNumberToComma(income-expense)).append("원"));
    }


    @Override
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onThisWeekTransactionLoadSuccess(List<TransactionModel> transactionList) {
        if (transactionList.size() == 0) {
            rv_transaction.setVisibility(View.GONE);
            ll_no_items.setVisibility(View.VISIBLE);
        } else {
            rv_transaction.setVisibility(View.VISIBLE);
            ll_no_items.setVisibility(View.GONE);
        }

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
    public void onThisWeekTransactionDeleteSuccess(boolean isSuccess) {

    }

    @Override
    public void onThisMonthTransactionByPatternLoadSuccess(List<SpendingPattern> spendingPatternList) {
        total = 0;
        normal_percentage = 0;
        waste_percentage = 0;
        invest_percentage = 0;
        for (SpendingPattern spendingPattern : spendingPatternList) {{
            total += (int) spendingPattern.getSum();
        }}
        tv_total_spending.setText(new StringBuilder(Common.changeNumberToComma(total)).append("원"));

        for (SpendingPattern spendingPattern : spendingPatternList) {
            if (spendingPattern.getPattern().equals("Normal")) {
                normal_percentage = (int) (spendingPattern.getSum() / total * 100);
                tv_normal.setText(new StringBuilder("Normal\n").append(Common.calcPercentageDownToOne((int) spendingPattern.getSum(), total)).append("%"));
                tv_normal_sum.setText(new StringBuilder(Common.changeNumberToComma((int) spendingPattern.getSum())).append("원"));
            }
            else if (spendingPattern.getPattern().equals("Waste")) {
                waste_percentage =  (int) (spendingPattern.getSum() / total * 100);
                tv_waste.setText(new StringBuilder("Waste\n").append(Common.calcPercentageDownToOne((int) spendingPattern.getSum(), total)).append("%"));
                tv_waste_sum.setText(new StringBuilder(Common.changeNumberToComma((int) spendingPattern.getSum())).append("원"));
            }
            else if (spendingPattern.getPattern().equals("Invest")) {
                invest_percentage =  (int) (spendingPattern.getSum() / total * 100);
                tv_invest.setText(new StringBuilder("Invest\n").append(Common.calcPercentageDownToOne((int) spendingPattern.getSum(), total)).append("%"));
                tv_invest_sum.setText(new StringBuilder(Common.changeNumberToComma((int) spendingPattern.getSum())).append("원"));
            }
        }
        setProgressBar();
        setCircleProgressbar(normal_percentage, waste_percentage, invest_percentage);
    }

    private void setCircleProgressbar(int normal_percentage, int waste_percentage, int invest_percentage) {
        Log.d(TAG, "setCircleProgressbar: called!!"+normal_percentage);

        ObjectAnimator progressAnim2 = ObjectAnimator.ofInt(pb_circle_normal, "progress", 0, normal_percentage);
        progressAnim2.setDuration(500);
        progressAnim2.setInterpolator(new LinearInterpolator());
        progressAnim2.start();

        ObjectAnimator progressAnim3 = ObjectAnimator.ofInt(pb_circle_waste, "progress", 0, waste_percentage);
        progressAnim3.setDuration(500);
        progressAnim3.setInterpolator(new LinearInterpolator());
        progressAnim3.start();

        ObjectAnimator progressAnim4 = ObjectAnimator.ofInt(pb_circle_invest, "progress", 0, invest_percentage);
        progressAnim4.setDuration(500);
        progressAnim4.setInterpolator(new LinearInterpolator());
        progressAnim4.start();
    }

    @Override
    public void onThisMonthTransactionByPatternLoadFailed(String message) {

    }

}

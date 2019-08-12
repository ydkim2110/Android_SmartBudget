package com.example.smartbudget.Ui.Home;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.smartbudget.Database.Interface.ILastFewDaysTransactionsLoadListener;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsByPatternLoadListener;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsLoadListener;
import com.example.smartbudget.Database.Model.SpendingByPattern;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.Database.TransactionRoom.TransactionItem;
import com.example.smartbudget.Interface.IDateChangeListener;
import com.example.smartbudget.Interface.INSVScrollChangeListener;
import com.example.smartbudget.Model.EventBus.AddTransactionEvent;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Home.Category.ExpenseByCategoryActivity;
import com.example.smartbudget.Ui.Home.Overview.OverviewActivity;
import com.example.smartbudget.Ui.Home.Spending.SpendingActivity;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.DateHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IDateChangeListener,
        IThisMonthTransactionsByPatternLoadListener,
        IThisMonthTransactionsLoadListener, ILastFewDaysTransactionsLoadListener {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private static HomeFragment instance;

    public static HomeFragment getInstance() {
        return instance == null ? new HomeFragment() : instance;
    }

    public HomeFragment() {
    }

    private INSVScrollChangeListener mINSVScrollChangeListener;

    @BindView(R.id.rv_transaction)
    RecyclerView rv_transaction;
    @BindView(R.id.tv_weekday)
    TextView tv_weekday;
    @BindView(R.id.tv_used_expense)
    TextView tv_used_expense;
    @BindView(R.id.tv_total_expense)
    TextView tv_total_expense;
    @BindView(R.id.pb_expense_by_category)
    ProgressBar pb_expense_by_category;
    @BindView(R.id.tv_percentage)
    TextView tv_percentage;

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

    @BindView(R.id.home_overview_container)
    ConstraintLayout home_overview_container;
    @BindView(R.id.home_expense_by_category_container)
    ConstraintLayout home_expense_by_category_container;
    @BindView(R.id.refresh_home_fragment)
    SwipeRefreshLayout refresh_home_fragment;
    @BindView(R.id.home_spending_pattern_container)
    ConstraintLayout home_spending_pattern_container;

    Unbinder mUnbinder;

    private WeekTransactionAdapter mAdapter;

    private HashMap<String, List<TransactionItem>> groupedHashMap;

    private int total = 0;
    private int normal_percentage = 0;
    private int waste_percentage = 0;
    private int invest_percentage = 0;
    private String currentDate = "";
    private Date startDate;
    private Date endDate;
    private String zeroMoney;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mINSVScrollChangeListener = (INSVScrollChangeListener) context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        zeroMoney = getActivity().getResources().getString(R.string.zero_money);

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        initView(view);

        rv_transaction.setHasFixedSize(true);
        rv_transaction.setLayoutManager(new LinearLayoutManager(getContext()));

        currentDate = Common.dateFormat.format(new Date());
        loadFromDatabase(currentDate);

        return view;
    }

    private void setProgressBar(int usedExpense) {
        Log.d(TAG, "setProgressBar: called!!");
        int maxValue = 1400000;

        tv_used_expense.setText(new StringBuilder(Common.changeNumberToComma(usedExpense)).append("원"));
        tv_total_expense.setText(new StringBuilder(Common.changeNumberToComma(maxValue)).append("원"));
        tv_percentage.setText(new StringBuilder(Common.calcPercentageDownToTwo(usedExpense, maxValue)).append("%"));

        pb_expense_by_category.setMax(maxValue);
        ObjectAnimator progressAnim = ObjectAnimator.ofInt(pb_expense_by_category, "progress", 0, usedExpense);
        progressAnim.setDuration(1000);
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
            loadFromDatabase(currentDate);
        }
    }

    private void loadData() {
        Log.d(TAG, "loadData: called!!");
        if (refresh_home_fragment.isRefreshing()) {
            refresh_home_fragment.setRefreshing(false);
        }

        mAdapter = new WeekTransactionAdapter(getContext(), groupedHashMap);
        rv_transaction.setAdapter(mAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initView(View view) {
        Log.d(TAG, "initView: called!!");

        nsv_container.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            mINSVScrollChangeListener.onNSVScrollChangeListener(scrollY > oldScrollY);
        });

        home_overview_container.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), OverviewActivity.class);
            intent.putExtra(OverviewActivity.EXTRA_PASS_DATE, currentDate);
            getContext().startActivity(intent);
        });
        home_expense_by_category_container.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ExpenseByCategoryActivity.class);
            intent.putExtra("passed_date", currentDate);
            getContext().startActivity(intent);
        });
        home_spending_pattern_container.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SpendingActivity.class);
            intent.putExtra("passed_date", currentDate);
            getContext().startActivity(intent);
        });
        refresh_home_fragment.setOnRefreshListener(() -> {
            loadFromDatabase(currentDate);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private HashMap<String, List<TransactionItem>> groupDataIntoHashMap(List<TransactionItem> transactionItemList) {

        HashMap<String, List<TransactionItem>> groupedHashMap = new HashMap<>();

        if (transactionItemList != null) {
            for (TransactionItem dataModel : transactionItemList) {
                String hashMapkey = Common.dateFormat.format(DateHelper.changeStringToDate(dataModel.getDate()));

                if (groupedHashMap.containsKey(hashMapkey)) {
                    groupedHashMap.get(hashMapkey).add(dataModel);
                } else {
                    List<TransactionItem> list = new ArrayList<>();
                    list.add(dataModel);
                    groupedHashMap.put(hashMapkey, list);
                }
            }
        }

        return groupedHashMap;
    }

    @Override
    public void onThisMonthTransactionsByPatternLoadSuccess(List<SpendingByPattern> spendingPatterns) {
        Log.d(TAG, "onThisMonthTransactionsByPatternLoadSuccess: called!");
        total = 0;
        normal_percentage = 0;
        waste_percentage = 0;
        invest_percentage = 0;

        tv_normal.setText(new StringBuilder("Normal\n").append("0.0%"));
        tv_normal_sum.setText(zeroMoney);
        tv_waste.setText(new StringBuilder("Waste\n").append("0.0%"));
        tv_waste_sum.setText(zeroMoney);
        tv_invest.setText(new StringBuilder("Invest\n").append("0.0%"));
        tv_invest_sum.setText(zeroMoney);

        for (SpendingByPattern spendingPattern : spendingPatterns) {
            {
                total += (int) spendingPattern.getSum();
            }
        }

        tv_total_spending.setText(new StringBuilder(Common.changeNumberToComma(total)).append("원"));

        for (SpendingByPattern spendingPattern : spendingPatterns) {
            if (spendingPattern.getPattern().equals("Normal") && total != 0) {
                normal_percentage = (int) (spendingPattern.getSum() / total * 100);
                tv_normal.setText(new StringBuilder("Normal\n").append(Common.calcPercentageDownToOne((int) spendingPattern.getSum(), total)).append("%"));
                tv_normal_sum.setText(new StringBuilder(Common.changeNumberToComma((int) spendingPattern.getSum())).append("원"));
            } else if (spendingPattern.getPattern().equals("Waste") && total != 0) {
                waste_percentage = (int) (spendingPattern.getSum() / total * 100);
                tv_waste.setText(new StringBuilder("Waste\n").append(Common.calcPercentageDownToOne((int) spendingPattern.getSum(), total)).append("%"));
                tv_waste_sum.setText(new StringBuilder(Common.changeNumberToComma((int) spendingPattern.getSum())).append("원"));
            } else if (spendingPattern.getPattern().equals("Invest") && total != 0) {
                invest_percentage = (int) (spendingPattern.getSum() / total * 100);
                tv_invest.setText(new StringBuilder("Invest\n").append(Common.calcPercentageDownToOne((int) spendingPattern.getSum(), total)).append("%"));
                tv_invest_sum.setText(new StringBuilder(Common.changeNumberToComma((int) spendingPattern.getSum())).append("원"));
            }
        }
        setCircleProgressbar(normal_percentage, waste_percentage, invest_percentage);
    }

    @Override
    public void onThisMonthTransactionsByPatternLoadFailed(String message) {
        Log.d(TAG, "onThisMonthTransactionsByPatternLoadFailed: called!!");
        Toast.makeText(getContext(), "[Failed]", Toast.LENGTH_SHORT).show();
    }

    private void setCircleProgressbar(int normal_percentage, int waste_percentage, int invest_percentage) {
        Log.d(TAG, "setCircleProgressbar: called!!" + normal_percentage);

        ObjectAnimator progressAnim2 = ObjectAnimator.ofInt(pb_circle_normal, "progress", 0, normal_percentage);
        progressAnim2.setDuration(1000);
        progressAnim2.setInterpolator(new LinearInterpolator());
        progressAnim2.start();

        ObjectAnimator progressAnim3 = ObjectAnimator.ofInt(pb_circle_waste, "progress", 0, waste_percentage);
        progressAnim3.setDuration(1000);
        progressAnim3.setInterpolator(new LinearInterpolator());
        progressAnim3.start();

        ObjectAnimator progressAnim4 = ObjectAnimator.ofInt(pb_circle_invest, "progress", 0, invest_percentage);
        progressAnim4.setDuration(1000);
        progressAnim4.setInterpolator(new LinearInterpolator());
        progressAnim4.start();
    }

    @Override
    public void onDateChanged(String date) {
        Log.d(TAG, "onDateChanged: "+date);
        currentDate = date;
        loadFromDatabase(currentDate);
    }

    private void loadFromDatabase(String date) {
        Log.d(TAG, "loadFromDatabase: called!!");

        DBTransactionUtils.getThisMonthTransactions(MainActivity.db, date, this);

        // Expense By Pattern
        DBTransactionUtils.getThisMonthTransactionByPattern(MainActivity.db, date, this);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(Common.stringToDate(date));

        Log.d(TAG, "loadFromDatabase: compareTo: "+cal1.compareTo(cal2));


        int todayYear = cal1.get(Calendar.YEAR);
        int todayMonth = cal1.get(Calendar.MONTH)+1;
        int currentYear = cal2.get(Calendar.YEAR);
        int currentMonth = cal2.get(Calendar.MONTH)+1;

        if (cal1.compareTo(cal2) == -1) {
            endDate = Common.getMaximumDate(cal2.getTime());
            cal2.add(Calendar.DAY_OF_MONTH, 0);
            startDate = cal2.getTime();
        }
        else {
            if (currentYear == todayYear && currentMonth == todayMonth) {
                endDate = cal1.getTime();
                cal1.add(Calendar.DAY_OF_MONTH, -6);
                startDate = cal1.getTime();
            } else {
                endDate = Common.getMaximumDate(cal2.getTime());
                cal2.add(Calendar.DAY_OF_MONTH, -6);
                cal2.add(Calendar.MONTH, 1);
                startDate = cal2.getTime();
            }
        }

        tv_weekday.setText(new StringBuilder("(")
                .append(Common.monthdayFormat.format(startDate))
                .append(" ~ ")
                .append(Common.monthdayFormat.format(endDate))
                .append(")"));

        DBTransactionUtils.getLastFewDaysTransactions(MainActivity.db, Common.dateFormat.format(startDate),
                Common.dateFormat.format(endDate), this);
    }

    @Override
    public void onThisMonthTransactionsLoadSuccess(List<TransactionItem> transactionItemList) {
        int expense = 0;
        int income = 0;
        if (transactionItemList != null) {
            Common.TRANSACTION_LIST = transactionItemList;

            for (TransactionItem model : transactionItemList) {
                if (model.getType().equals("Income")) {
                    income += model.getAmount();
                } else if (model.getType().equals("Expense")) {
                    expense += model.getAmount();
                }
            }
        }
        Common.animateTextView(1000, 0, income, tv_income_total);
        Common.animateTextView(1000, 0, expense, tv_expense_total);
        Common.animateTextView(1000, 0, (income - expense), tv_balance);

        setProgressBar(expense);
    }

    @Override
    public void onThisMonthTransactionsLoadFailed(String message) {
        Log.d(TAG, "onThisMonthTransactionsLoadFailed: called!!");
        Toast.makeText(getContext(), "Failed!!", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onLastFewDaysTransactionsLoadSuccess(List<TransactionItem> transactionItemList) {
        Log.d(TAG, "onLastFewDaysTransactionsLoadSuccess: called!!");
        if (transactionItemList == null) {
            rv_transaction.setVisibility(View.GONE);
            ll_no_items.setVisibility(View.VISIBLE);
        } else {
            rv_transaction.setVisibility(View.VISIBLE);
            ll_no_items.setVisibility(View.GONE);
        }

        groupedHashMap = groupDataIntoHashMap(transactionItemList);

        loadData();
    }

    @Override
    public void onLastFewDaysTransactionsFailed(String message) {
        Log.d(TAG, "onLastFewDaysTransactionsFailed: called!!");
        Toast.makeText(getContext(), "Failed!!", Toast.LENGTH_SHORT).show();
    }
}

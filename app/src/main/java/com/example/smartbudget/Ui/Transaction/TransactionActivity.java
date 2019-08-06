package com.example.smartbudget.Ui.Transaction;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Interface.IThisMonthTransactionLoadListener;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Home.WeekTransactionAdapter;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Ui.Transaction.Add.AddTransactionActivity;
import com.example.smartbudget.Ui.Transaction.Transfer.TransferActivity;
import com.example.smartbudget.Utils.Common;
import com.example.smartbudget.Utils.DateHelper;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionActivity extends AppCompatActivity implements IThisMonthTransactionLoadListener {

    private static final String TAG = TransactionActivity.class.getSimpleName();

    @BindView(R.id.app_bar)
    AppBarLayout app_bar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.fab_sub1)
    FloatingActionButton fab_sub1;
    @BindView(R.id.fab_sub2)
    FloatingActionButton fab_sub2;
    @BindView(R.id.ll_sub1)
    LinearLayout ll_sub1;
    @BindView(R.id.ll_sub2)
    LinearLayout ll_sub2;
    @BindView(R.id.tv_fab_sub1)
    TextView tv_fab_sub1;
    @BindView(R.id.tv_fab_sub2)
    TextView tv_fab_sub2;

    @BindView(R.id.compactcalendar_view)
    CompactCalendarView compactcalendar_view;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.date_picker_text_view)
    TextView date_picker_text_view;
    @BindView(R.id.date_picker_arrow)
    ImageView date_picker_arrow;
    @BindView(R.id.date_picker_button)
    RelativeLayout date_picker_button;
    @BindView(R.id.rv_transaction)
    RecyclerView rv_transaction;
    @BindView(R.id.tv_no_item)
    TextView tv_no_item;
    @BindView(R.id.tv_total_income)
    TextView tv_total_income;
    @BindView(R.id.tv_total_expense)
    TextView tv_total_expense;

    private boolean isExpanded = false;
    private String currentDate;

    private HashMap<String, List<TransactionModel>> groupedHashMap;

    private Animation fabOpen;
    private Animation fabClose;
    private boolean isFabOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Log.d(TAG, "onCreate: started!!");

        initView();

        currentDate = Common.dateFormat.format(new Date());

    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.toolbar_title_transaction));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);

        compactcalendar_view.setLocale(TimeZone.getDefault(), Locale.KOREA);
        compactcalendar_view.setShouldDrawDaysHeader(true);
        compactcalendar_view.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubTitle(Common.yearmonthDateFormate.format(dateClicked));
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setSubTitle(Common.yearmonthDateFormate.format(firstDayOfNewMonth));
                loadData(firstDayOfNewMonth);
                currentDate = Common.dateFormat.format(firstDayOfNewMonth);
            }
        });

        setCurrentDate(new Date());

        date_picker_button.setOnClickListener(v -> {
            float rotation = isExpanded ? 0 : 180;
            ViewCompat.animate(date_picker_arrow).rotation(rotation).start();

            isExpanded = !isExpanded;
            app_bar.setExpanded(isExpanded, true);
        });

        fab.setOnClickListener(v -> {
            toggleFab();
        });

        fab_sub1.setOnClickListener(v -> {
            toggleFab();
            startActivity(new Intent(TransactionActivity.this, AddTransactionActivity.class));
        });

        fab_sub2.setOnClickListener(v -> {
            toggleFab();
            startActivity(new Intent(TransactionActivity.this, TransferActivity.class));
        });

        rv_transaction.setHasFixedSize(true);
        rv_transaction.setLayoutManager(new LinearLayoutManager(this));
    }

    private void toggleFab() {
        Log.d(TAG, "toggleFab: called!!");
        if (isFabOpen) {
            fab.setImageResource(R.drawable.ic_add_white_24dp);
            fab_sub1.startAnimation(fabClose);
            fab_sub2.startAnimation(fabClose);
            ll_sub1.setVisibility(View.INVISIBLE);
            ll_sub2.setVisibility(View.INVISIBLE);
            isFabOpen = false;
        } else {
            fab.setImageResource(R.drawable.ic_close_white_24dp);
            fab_sub1.startAnimation(fabOpen);
            fab_sub2.startAnimation(fabOpen);
            ll_sub1.setVisibility(View.VISIBLE);
            ll_sub2.setVisibility(View.VISIBLE);
            isFabOpen = true;
        }
    }

    private void loadData(Date date) {
        DatabaseUtils.getThisMonthTransaction(MainActivity.mDBHelper, Common.dateFormat.format(date), this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_transaction_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.toolbar_transaction_calendar) {
            Intent intent = new Intent(this, CalendarActivity.class);
            intent.putExtra("date", currentDate);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setCurrentDate(Date date) {
        Log.d(TAG, "setCurrentDate: called!!");
        setSubTitle(Common.yearmonthDateFormate.format(date));
        if (compactcalendar_view != null) {
            compactcalendar_view.setCurrentDate(date);
        }
        loadData(date);
    }

    @Override
    public void setTitle(CharSequence passedTitle) {
        if (title != null) {
            title.setText(passedTitle);
        }
    }

    private void setSubTitle(String subtitle) {
        Log.d(TAG, "setSubTitle: called!!");
        if (date_picker_text_view != null) {
            date_picker_text_view.setText(subtitle);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTransactionLoadSuccess(List<TransactionModel> transactionList) {
        if (transactionList.size() < 1) {
            rv_transaction.setVisibility(View.INVISIBLE);
            tv_no_item.setVisibility(View.VISIBLE);
        } else {
            rv_transaction.setVisibility(View.VISIBLE);
            tv_no_item.setVisibility(View.INVISIBLE);
        }

        int totalIncome = 0;
        int totalExpense = 0;

        if (transactionList != null) {
            for (TransactionModel model : transactionList) {
                if (model.getTransaction_type().equals("Income")) {
                    totalIncome = (int) (totalIncome + model.getTransaction_amount());
                    Log.d(TAG, "onTransactionLoadSuccess: income: " + totalIncome);
                } else if (model.getTransaction_type().equals("Expense")) {
                    totalExpense = (int) (totalExpense + model.getTransaction_amount());
                    Log.d(TAG, "onTransactionLoadSuccess: expense: " + totalExpense);
                }
            }
        }

        Log.d(TAG, "onTransactionLoadSuccess: Total expense: " + totalExpense);

        Common.animateTextView(0, totalIncome, tv_total_income);
        Common.animateTextView(0, totalExpense, tv_total_expense);

        groupedHashMap = groupDataIntoHashMap(transactionList);

        WeekTransactionAdapter adapter = new WeekTransactionAdapter(this, groupedHashMap);
        rv_transaction.setAdapter(adapter);
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
    public void onTransactionDeleteSuccess(boolean isSuccess) {

    }
}

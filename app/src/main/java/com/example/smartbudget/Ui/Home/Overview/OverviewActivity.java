package com.example.smartbudget.Ui.Home.Overview;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.Database.TransactionRoom.TransactionItem;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsByCategoryLoadListener;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewActivity extends AppCompatActivity implements IThisMonthTransactionsByCategoryLoadListener {

    private static final String TAG = OverviewActivity.class.getSimpleName();

    public static final String EXTRA_PASSED_DATE = "PASSED_DATE";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_spending)
    RecyclerView rv_spending;
    @BindView(R.id.pie_chart_overview)
    PieChart pie_chart_overview;

    @BindView(R.id.tv_income)
    TextView tv_income;
    @BindView(R.id.tv_expense)
    TextView tv_expense;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.pb_income)
    ProgressBar pb_income;
    @BindView(R.id.pb_expense)
    ProgressBar pb_expense;
    @BindView(R.id.tv_no_transactions)
    TextView tv_no_transactions;

    private String passed_date;
    private int total;

    private String moneyUnit;
    private String typeIncome;
    private String typeExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Log.d(TAG, "onCreate: started!!");
        ButterKnife.bind(this);

        moneyUnit = getResources().getString(R.string.money_unit);
        typeIncome = getResources().getString(R.string.type_income);
        typeExpense = getResources().getString(R.string.type_expense);

        if (getIntent() != null) {
            passed_date = getIntent().getStringExtra(EXTRA_PASSED_DATE);
            int expense = 0;
            int income = 0;

            for (TransactionItem model : Common.TRANSACTION_LIST) {
                if (model.getType().equals(typeIncome)) {
                    income += model.getAmount();
                } else if (model.getType().equals(typeExpense)) {
                    expense += model.getAmount();
                }
            }

            Common.animateTextView(1500, 0, income, moneyUnit, tv_income);
            Common.animateTextView(1500, 0, expense, moneyUnit, tv_expense);
            Common.animateTextView(1500, 0, (income - expense), moneyUnit, tv_balance);

            setProgressBar(income, expense);
        }

        initView();

        DBTransactionUtils.getThisMonthTransactionByCategory(MainActivity.db, passed_date, this);
    }

    private void setProgressBar(int income, int expense) {
        Log.d(TAG, "setProgressBar: called!!");

        if (income >= expense) {
            pb_income.setMax(income);
            pb_expense.setMax(income);
        } else {
            pb_income.setMax(expense);
            pb_expense.setMax(expense);
        }

        ObjectAnimator progressAnim = ObjectAnimator.ofInt(pb_income, "progress", 0, income);
        progressAnim.setDuration(1500);
        progressAnim.setInterpolator(new LinearInterpolator());
        progressAnim.start();

        ObjectAnimator progressAnim2 = ObjectAnimator.ofInt(pb_expense, "progress", 0, expense);
        progressAnim2.setDuration(1500);
        progressAnim2.setInterpolator(new LinearInterpolator());
        progressAnim2.start();
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_title_overview));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);


        rv_spending.setHasFixedSize(true);
        rv_spending.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());

        rv_spending.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onThisMonthTransactionByCategoryLoadSuccess(List<ExpenseByCategory> expenseByCategoryList) {
        Log.d(TAG, "onThisMonthTransactionByCategoryLoadSuccess: called!!");
        if (expenseByCategoryList == null || expenseByCategoryList.size() < 1) {
            tv_no_transactions.setVisibility(View.VISIBLE);
            pie_chart_overview.setVisibility(View.GONE);
        } else  {
            tv_no_transactions.setVisibility(View.GONE);
            pie_chart_overview.setVisibility(View.VISIBLE);

            Collections.sort(expenseByCategoryList, (o1, o2) -> {
                if (o1.getSumByCategory() > o2.getSumByCategory())
                    return -1;
                else if (o1.getSumByCategory() < o2.getSumByCategory())
                    return 1;
                else
                    return 0;
            });
            total = expenseByCategoryList.stream().mapToInt(ExpenseByCategory::getSumByCategory).sum();

            ArrayList<PieEntry> pieEntries = new ArrayList<>();
            ArrayList<Integer> pieColors = new ArrayList<>();

            Category category;
            Drawable drawable;
            for(ExpenseByCategory expenseByCategory : expenseByCategoryList) {
                category = Common.getExpenseCategory(expenseByCategory.getCategoryId());
                drawable = getDrawable(category.getIconResourceID());
                drawable.setTint(Color.parseColor("#FFFFFF"));
                pieColors.add(category.getIconColor());
                pieEntries.add(new PieEntry(expenseByCategory.getSumByCategory(), drawable));
            }

            PieDataSet dataSet = new PieDataSet(pieEntries, "");
            dataSet.setDrawValues(false);
            dataSet.setSliceSpace(3);
            dataSet.setSelectionShift(5);
            dataSet.setValueTextSize(12);
            dataSet.setColors(pieColors);

            //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            //dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

            // When valuePosition is OutsideSlice, indicates offset as percentage out of the slice size
            // dataSet.setValueLinePart1OffsetPercentage(100f);

            PieData pieData = new PieData(dataSet);
            pie_chart_overview.setData(pieData);
            pie_chart_overview.getLegend().setEnabled(false);
            pie_chart_overview.getDescription().setEnabled(false);

            pie_chart_overview.setUsePercentValues(true);
            pie_chart_overview.setDrawHoleEnabled(true);

            pie_chart_overview.setTransparentCircleColor(Color.WHITE);
            pie_chart_overview.setTransparentCircleAlpha(110);

            pie_chart_overview.setHoleRadius(70f);
            pie_chart_overview.setTransparentCircleRadius(55f);

            pie_chart_overview.setDrawCenterText(true);

            pie_chart_overview.setRotationAngle(0);
            pie_chart_overview.setRotationEnabled(true);

            SpannableStringBuilder ssb = new SpannableStringBuilder(passed_date).append("\n").append(Common.changeNumberToComma(total)).append(moneyUnit);
            ssb.setSpan(new StyleSpan(Typeface.BOLD), 10, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new ForegroundColorSpan(Color.RED), 10, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new AbsoluteSizeSpan(50), 10, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            pie_chart_overview.setCenterText(ssb);

            pie_chart_overview.animateY(1500, Easing.EaseInOutQuad);

            pie_chart_overview.invalidate();

            OverviewCategoryAdapter adapter = new OverviewCategoryAdapter(OverviewActivity.this, expenseByCategoryList, passed_date);
            rv_spending.setAdapter(adapter);
        }
    }

    @Override
    public void onThisMonthTransactionByCategoryLoadFailed(String message) {
        Log.d(TAG, "onThisMonthTransactionByCategoryLoadFailed: called!!");
    }

}

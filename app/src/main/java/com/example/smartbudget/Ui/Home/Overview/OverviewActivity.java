package com.example.smartbudget.Ui.Home.Overview;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewActivity extends AppCompatActivity implements IThisMonthTransactionsByCategoryLoadListener {

    private static final String TAG = OverviewActivity.class.getSimpleName();

    public static final String EXTRA_PASS_DATE = "pass_date";
    public static final String EXTRA_TRANSACTION_LIST = "transaction_list";

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

    private String passed_date;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Log.d(TAG, "onCreate: started!!");
        ButterKnife.bind(this);

        if (getIntent() != null) {
            passed_date = getIntent().getStringExtra(EXTRA_PASS_DATE);
            int expense = 0;
            int income = 0;

            for (TransactionItem model : Common.TRANSACTION_LIST) {
                if (model.getType().equals("Income")) {
                    income += model.getAmount();
                } else if (model.getType().equals("Expense")) {
                    expense += model.getAmount();
                }
            }

            Common.animateTextView(1500, 0, income, tv_income);
            Common.animateTextView(1500, 0, expense, tv_expense);
            Common.animateTextView(1500, 0, (income - expense), tv_balance);

            setProgressBar(income, expense);
        }

        initView();

        //setUpPieGraph();


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
        if (expenseByCategoryList != null) {
            Collections.sort(expenseByCategoryList, new Comparator<ExpenseByCategory>() {
                @Override
                public int compare(ExpenseByCategory o1, ExpenseByCategory o2) {
                    if (o1.getSumByCategory() > o2.getSumByCategory())
                        return -1;
                    else if (o1.getSumByCategory() < o2.getSumByCategory())
                        return 1;
                    else
                        return 0;
                }
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

//            ArrayList<Float> expenseByCategory = new ArrayList<>();
//
//            for (ExpenseByCategory data : expenseByCategoryList) {
//                expenseByCategory.add((float) data.getSumByCategory());
//            }
//
//            yData = expenseByCategory.toArray(new Float[expenseByCategory.size()]);
//
//
//
//            for (int i = 0; i < yData.length; i++) {
//                pieEntries.add(new PieEntry(yData[i], i));
//            }

            PieDataSet dataSet = new PieDataSet(pieEntries, "");
            dataSet.setDrawValues(false);
            dataSet.setSliceSpace(3);
            dataSet.setSelectionShift(5);
            dataSet.setValueTextSize(12);

            //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            //dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

            // When valuePosition is OutsideSlice, indicates offset as percentage out of the slice size
            // dataSet.setValueLinePart1OffsetPercentage(100f);


            //add colors to dataset
            ArrayList<Integer> colors = new ArrayList<>();

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);
            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(pieColors);

            //create pie data object
            PieData pieData = new PieData(dataSet);
            pie_chart_overview.setData(pieData);
            pie_chart_overview.getLegend().setEnabled(false);
            pie_chart_overview.getDescription().setEnabled(false);

            pie_chart_overview.setUsePercentValues(true);
            pie_chart_overview.setDrawHoleEnabled(true);

            pie_chart_overview.setTransparentCircleColor(Color.WHITE);
            pie_chart_overview.setTransparentCircleAlpha(110);

            pie_chart_overview.setHoleRadius(58f);
            pie_chart_overview.setTransparentCircleRadius(61f);

            pie_chart_overview.setDrawCenterText(true);

            pie_chart_overview.setRotationAngle(0);
            pie_chart_overview.setRotationEnabled(true);

            StringBuilder sb = new StringBuilder(passed_date).append("\n").append(Common.changeNumberToComma(total)).append("원");

            pie_chart_overview.setCenterText(sb);

            pie_chart_overview.animateY(1500, Easing.EaseInOutQuad);

            pie_chart_overview.invalidate();

//            pieDataSet.setDrawValues(false);
//            pieDataSet.setColors(pieColors);
//            pieDataSet.setSliceSpace(2f);
//
//            PieData data = new PieData(pieDataSet);
//            pie_chart_overview.setData(data);
//            pie_chart_overview.setTouchEnabled(false);
//            pie_chart_overview.getLegend().setEnabled(false);
//            pie_chart_overview.getDescription().setEnabled(false);
//
//            pie_chart_overview.setDrawHoleEnabled(true);
//            //  pie_chart_overview.setHoleColor(ContextCompat.getColor(this, R.color.khaki));
//            pie_chart_overview.setHoleRadius(55f);
//            pie_chart_overview.setTransparentCircleRadius(55f);
//            pie_chart_overview.setDrawCenterText(true);
//            pie_chart_overview.setRotationAngle(270);
//            pie_chart_overview.setRotationEnabled(false);
//            pie_chart_overview.setHighlightPerTapEnabled(true);
//
//            pie_chart_overview.invalidate();

            SpendingAdapter adapter = new SpendingAdapter(OverviewActivity.this, expenseByCategoryList, passed_date);
            rv_spending.setAdapter(adapter);
        }
    }

    @Override
    public void onThisMonthTransactionByCategoryLoadFailed(String message) {

    }
}

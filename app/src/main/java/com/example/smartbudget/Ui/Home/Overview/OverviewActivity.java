package com.example.smartbudget.Ui.Home.Overview;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Interface.IThisMonthTransactionByCategoryLoadListener;
import com.example.smartbudget.Model.DefaultCategories;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Budget.BudgetAdapter;
import com.example.smartbudget.Ui.Home.Category.ExpenseByCategoryActivity;
import com.example.smartbudget.Ui.Main.MainActivity;
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
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewActivity extends AppCompatActivity implements IThisMonthTransactionByCategoryLoadListener {

    private static final String TAG = OverviewActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_spending)
    RecyclerView rv_spending;
    @BindView(R.id.pie_chart_overview)
    PieChart pie_chart_overview;

    private Float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};

    private String[] xData = {"Mitch", "Jessica", "Mohammad", "Kelsey", "Sam", "Robert", "Ashley"};

    private float thisMonthIncome;
    private float thisMonthExpenses;

    private String passed_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Log.d(TAG, "onCreate: stareted!!");

        if (getIntent() != null) {
            passed_date = getIntent().getStringExtra("passed_date");
        }

        initView();

        setUpPieGraph();

        DatabaseUtils.getThisMonthTransactionByCategory(MainActivity.mDBHelper, passed_date, this);
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);

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

    private void setUpPieGraph() {
        Log.d(TAG, "setUpPieGraph: called!!");

        pie_chart_overview.setUsePercentValues(true);
        //mPieChart.setDescription("");
        pie_chart_overview.setDrawHoleEnabled(true);

        pie_chart_overview.setTransparentCircleColor(Color.WHITE);
        pie_chart_overview.setTransparentCircleAlpha(110);

        pie_chart_overview.setHoleRadius(58f);
        pie_chart_overview.setTransparentCircleRadius(61f);

        pie_chart_overview.setDrawCenterText(true);

        pie_chart_overview.setRotationAngle(0);
        pie_chart_overview.setRotationEnabled(true);

        pie_chart_overview.setCenterText("June");

        addPieData();

        pie_chart_overview.animateY(1500, Easing.EaseInOutQuad);

        Legend l = pie_chart_overview.getLegend();
        l.setEnabled(false);

        pie_chart_overview.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null) {
                    return;

                }

            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void addPieData() {
        Log.d(TAG, "addPieData: called!!");

        ArrayList<PieEntry> yVals = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yVals.add(new PieEntry(yData[i], i));
        }

        ArrayList<String> xVals = new ArrayList<>();

        for (int i = 0; i < xData.length; i++) {
            xVals.add(xData[i]);
        }

        //create the data set
        PieDataSet dataSet = new PieDataSet(yVals, "Expenses");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        dataSet.setValueTextSize(12);

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

        dataSet.setColors(colors);

        //create pie data object
        PieData pieData = new PieData(dataSet);
        pie_chart_overview.setData(pieData);
        pie_chart_overview.invalidate();
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

    @Override
    public void onThisMonthTransactionByCategoryLoadSuccess(List<ExpenseByCategory> expenseByCategoryList) {
        if (expenseByCategoryList != null) {
            SpendingAdapter adapter = new SpendingAdapter(OverviewActivity.this, expenseByCategoryList);
            rv_spending.setAdapter(adapter);
        }
    }

    @Override
    public void onThisMonthTransactionByCategoryLoadFailed(String message) {

    }
}

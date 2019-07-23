package com.example.smartbudget.Ui.Home.Overview;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.CategoryModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Ui.Main.Spending;
import com.example.smartbudget.Ui.Transaction.Add.ICategoryLoadListener;
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
import java.util.List;

public class OverviewActivity extends AppCompatActivity implements ICategoryLoadListener {

    private static final String TAG = OverviewActivity.class.getSimpleName();

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SpendingAdapter adapter;

    private PieChart mPieChart;
    private Float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};;
    private String[] xData = {"Mitch", "Jessica" , "Mohammad" , "Kelsey", "Sam", "Robert", "Ashley"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Overview");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPieChart = findViewById(R.id.overview_spending_pie_chart);

        setUpPieGraph();

        recyclerView = findViewById(R.id.spending_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);

        List<Spending> list = new ArrayList<>();

        DatabaseUtils.getAllCategory(MainActivity.mDBHelper, this);

        list.add(new Spending("42%", "Food", "320,000원"));
        list.add(new Spending("42%", "Transport", "320,000원"));
        list.add(new Spending("42%", "Clothing", "320,000원"));
        list.add(new Spending("42%", "Entertainment", "320,000원"));
        list.add(new Spending("42%", "Household", "320,000원"));
        list.add(new Spending("42%", "Bills", "320,000원"));
        list.add(new Spending("42%", "Other Expenses", "320,000원"));

    }

    private void setUpPieGraph() {
        Log.d(TAG, "setUpPieGraph: called!!");

        mPieChart.setUsePercentValues(true);
        //mPieChart.setDescription("");
        mPieChart.setDrawHoleEnabled(true);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        mPieChart.setRotationEnabled(true);

        mPieChart.setCenterText("June");

        addData();
        
        mPieChart.animateY(1500, Easing.EaseInOutQuad);
        
        Legend l = mPieChart.getLegend();
        l.setEnabled(false);
        
        mPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
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

    private void addData() {
        Log.d(TAG, "addData: called!!");

        ArrayList<PieEntry> yVals = new ArrayList<>();

        for (int i=0; i<yData.length; i++) {
            yVals.add(new PieEntry(yData[i], i));
        }

        ArrayList<String> xVals = new ArrayList<>();

        for (int i=0; i<xData.length; i++) {
            xVals.add(xData[i]);
        }

        //create the data set
        PieDataSet dataSet = new PieDataSet(yVals, "Expenses");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        dataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c: ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c: ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c: ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c: ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        
        dataSet.setColors(colors);

        //create pie data object
        PieData pieData = new PieData(dataSet);
        mPieChart.setData(pieData);
        mPieChart.invalidate();
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
    public void onCategoryLoadSuccess(List<CategoryModel> categoryList) {
        adapter = new SpendingAdapter(categoryList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCategoryLoadFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

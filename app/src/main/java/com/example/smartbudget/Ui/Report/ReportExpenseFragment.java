package com.example.smartbudget.Ui.Report;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.Database.Interface.ISumTransactionByDateLoadListener;
import com.example.smartbudget.Database.Model.SumTransactionByDate;
import com.example.smartbudget.Database.TransactionRoom.DBTransactionUtils;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportExpenseFragment extends Fragment implements ISumTransactionByDateLoadListener {

    private static final String TAG = ReportExpenseFragment.class.getSimpleName();

    private static ReportExpenseFragment instance;

    public static ReportExpenseFragment getInstance() {
        if (instance == null) {
            instance = new ReportExpenseFragment();
        }
        return instance;
    }

    public ReportExpenseFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.line_chart_report)
    LineChart line_chart_report;
    @BindView(R.id.tab_sub_report)
    TabLayout tab_sub_report;
    @BindView(R.id.vp_sub_report)
    ViewPager vp_sub_report;

    Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_expense, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        ReportSubPagerAdapter adapter = new ReportSubPagerAdapter(getChildFragmentManager(), getContext());

        vp_sub_report.setAdapter(adapter);

        tab_sub_report.setupWithViewPager(vp_sub_report);

        loadData();



        return view;
    }

    private void loadData() {
        Log.d(TAG, "loadData: called!!");
        DBTransactionUtils.getTransactionsByDate(MainActivity.db, Common.dateFormat.format(new Date()), this);
    }

    @Override
    public void onSumTransactionByDateLoadSuccess(List<SumTransactionByDate> sumTransactionByDateList) {
        Log.d(TAG, "onTransactionsLoadSuccess: called!!");

        for (SumTransactionByDate data : sumTransactionByDateList) {
            Log.d(TAG, "onTransactionsLoadSuccess: "+data.getDate()+" / "+data.getTotalAmount());
        }

        setUpGraph(sumTransactionByDateList);

    }

    @Override
    public void onSumTransactionByDateLoadFailed(String message) {

    }

    private void setUpGraph(List<SumTransactionByDate> sumTransactionByDateList) {
        Log.d(TAG, "setUpGraph: called!!");
        line_chart_report.getDescription().setEnabled(false);

        line_chart_report.setTouchEnabled(false);

        line_chart_report.setDrawGridBackground(false);

        ArrayList<Entry> thisMonthValue = new ArrayList<>();
        ArrayList<Entry> previousMonthValue = new ArrayList<>();
        ArrayList<String> xValue = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1);

        float thisMonthTotal = 0f;
        float previousMonthTotal = 0f;
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < maxDay; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i+1);
            String date = Common.dateFormat.format(cal.getTime());
            xValue.add(sdf.format(cal.getTime()));
            for (int j = 0; j < sumTransactionByDateList.size(); j++) {
                if (date.equals(sumTransactionByDateList.get(j).getDate())) {
                    thisMonthTotal += (float) sumTransactionByDateList.get(j).getTotalAmount();
                    previousMonthTotal += (float) sumTransactionByDateList.get(j).getTotalAmount()-30000;
                }
            }
            thisMonthValue.add(new Entry(i, thisMonthTotal));
            previousMonthValue.add(new Entry(i, previousMonthTotal));
        }

        line_chart_report.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValue));

        LineDataSet set1;
        LineDataSet set2;

        XAxis xAxis = line_chart_report.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setLabelCount(5, true);
        xAxis.setTextSize(12f);
        xAxis.enableAxisLineDashedLine(10f, 10f, 10f);

        YAxis yAxisLeft = line_chart_report.getAxisLeft();
        yAxisLeft.setDrawLabels(false);
        yAxisLeft.setDrawAxisLine(false);
        yAxisLeft.setDrawGridLines(false);

        YAxis yAxisRight = line_chart_report.getAxisRight();
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);

        set1 = new LineDataSet(thisMonthValue, "This Month");
        set2 = new LineDataSet(previousMonthValue, "Last Month");

        set1.setDrawFilled(false); // 바탕색
        set2.setDrawFilled(false); // 바탕색

        set1.setDrawValues(false);
        set2.setDrawValues(false);

        set1.setLineWidth(3f);
        set1.setCircleRadius(6f);
        set1.setDrawCircles(false);

        set2.setLineWidth(3f);
        set2.setCircleRadius(6f);
        set2.setDrawCircles(false);


        Description description = new Description();
        description.setEnabled(false);
        set1.setCircleColor(Color.RED);
        set1.setColor(Color.RED);
        set2.setCircleColor(Color.BLUE);
        set2.setColor(Color.BLUE);

        Legend legend = line_chart_report.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(14f);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        LineData data = new LineData(dataSets);
        line_chart_report.animateX(1500);
        line_chart_report.setData(data);
    }

}

package com.example.smartbudget.Ui.Report;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.smartbudget.Database.AccountRoom.AccountItem;
import com.example.smartbudget.Database.AccountRoom.DBAccountUtils;
import com.example.smartbudget.Database.Interface.IAccountsLoadListener;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.Utils.Common;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportActivity extends AppCompatActivity implements IAccountsLoadListener {

    private static final String TAG = ReportActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_sub_report)
    TabLayout tab_sub_report;
    @BindView(R.id.vp_sub_report)
    ViewPager vp_sub_report;

    @BindView(R.id.pie_chart_asset_report)
    PieChart pie_chart_asset_report;
    @BindView(R.id.pie_chart_debt_report)
    PieChart pie_chart_debt_report;

    @BindView(R.id.tv_total_asset)
    TextView tv_total_asset;
    @BindView(R.id.tv_total_debt)
    TextView tv_total_debt;

    @BindView(R.id.rv_report_asset)
    RecyclerView rv_report_asset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Log.d(TAG, "onCreate: started!!");

        initView();

        loadData();
    }

    private void loadData() {
        Log.d(TAG, "loadData: called!!");
        DBAccountUtils.getSumAccountsByHigCategory(MainActivity.db, this);
    }

    private void initView() {
        Log.d(TAG, "initView: called!!");
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.toolbar_title_report));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);

        rv_report_asset.setHasFixedSize(true);
        rv_report_asset.setLayoutManager(new LinearLayoutManager(this));

        ReportSubPagerAdapter adapter = new ReportSubPagerAdapter(getSupportFragmentManager(), this);

        vp_sub_report.setAdapter(adapter);

        tab_sub_report.setupWithViewPager(vp_sub_report);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAccountsLoadSuccess(List<AccountItem> accountItemList) {
        Log.d(TAG, "onAccountsLoadSuccess: called!!");

        setUpGraph(accountItemList);
        ReportAssetListAdapter adapter = new ReportAssetListAdapter(this, accountItemList);
        rv_report_asset.setAdapter(adapter);
    }

    private void setUpGraph(List<AccountItem> accountItemList) {
        Log.d(TAG, "setUpGraph: called!");

        ArrayList<PieEntry> assetEntries = new ArrayList<>();
        ArrayList<PieEntry> debtEntries = new ArrayList<>();

        int totalAsset = 0;
        int totalDebt = 0;

        for (AccountItem data : accountItemList) {
            if (data.getType().equals("asset")) {
                totalAsset += data.getAmount();
                assetEntries.add(new PieEntry((float) data.getAmount(), Common.getAccountTitleById(this, data.getHighCategory())));
            }
            else if (data.getType().equals("debt")) {
                totalDebt += data.getAmount();
                debtEntries.add(new PieEntry((float) data.getAmount(), Common.getAccountTitleById(this, data.getHighCategory())));
            }
        }

        Common.animateTextView(1500, 0, totalAsset, getResources().getString(R.string.money_unit), tv_total_asset);
        Common.animateTextView(1500, 0, totalDebt, getResources().getString(R.string.money_unit), tv_total_debt);

        PieDataSet assetDataSet = new PieDataSet(assetEntries, "");
        assetDataSet.setDrawValues(false);
        assetDataSet.setSliceSpace(3);
        assetDataSet.setSelectionShift(5);
        assetDataSet.setValueTextSize(12);
        assetDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        assetDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        assetDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData assetPieData = new PieData(assetDataSet);
        pie_chart_asset_report.setData(assetPieData);
        pie_chart_asset_report.getLegend().setEnabled(false);
        pie_chart_asset_report.getDescription().setEnabled(false);

        pie_chart_asset_report.setUsePercentValues(true);
        pie_chart_asset_report.setDrawHoleEnabled(true);

        pie_chart_asset_report.setExtraOffsets(20f, 5f, 20f, 5f);

        pie_chart_asset_report.setTransparentCircleColor(Color.WHITE);
        pie_chart_asset_report.setTransparentCircleAlpha(110);

        pie_chart_asset_report.setHoleRadius(70f);
        pie_chart_asset_report.setTransparentCircleRadius(55f);

        pie_chart_asset_report.setDrawCenterText(true);

        pie_chart_asset_report.setAlpha(0.8f);

        pie_chart_asset_report.setRotationAngle(0);
        pie_chart_asset_report.setRotationEnabled(true);

        pie_chart_asset_report.setEntryLabelColor(Color.BLACK);

        pie_chart_asset_report.animateY(1500, Easing.EaseInOutQuad);

        pie_chart_asset_report.invalidate();

        PieDataSet debtDataSet = new PieDataSet(debtEntries, "");
        debtDataSet.setDrawValues(false);
        debtDataSet.setSliceSpace(3);
        debtDataSet.setSelectionShift(5);
        debtDataSet.setValueTextSize(12);
        debtDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        debtDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        debtDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData debtPieData = new PieData(debtDataSet);
        pie_chart_debt_report.setData(debtPieData);
        pie_chart_debt_report.getLegend().setEnabled(false);
        pie_chart_debt_report.getDescription().setEnabled(false);

        pie_chart_debt_report.setUsePercentValues(true);
        pie_chart_debt_report.setDrawHoleEnabled(true);

        pie_chart_debt_report.setExtraOffsets(20f, 5f, 20f, 5f);

        pie_chart_debt_report.setTransparentCircleColor(Color.WHITE);
        pie_chart_debt_report.setTransparentCircleAlpha(110);

        pie_chart_debt_report.setHoleRadius(70f);
        pie_chart_debt_report.setTransparentCircleRadius(55f);

        pie_chart_debt_report.setDrawCenterText(true);

        pie_chart_debt_report.setAlpha(0.8f);

        pie_chart_debt_report.setRotationAngle(0);
        pie_chart_debt_report.setRotationEnabled(true);

        pie_chart_debt_report.setEntryLabelColor(Color.BLACK);

        pie_chart_debt_report.animateY(1500, Easing.EaseInOutQuad);

        pie_chart_debt_report.invalidate();
    }

    @Override
    public void onAccountsLoadFailed(String message) {
        Log.d(TAG, "onAccountsLoadFailed: called!!");
    }
}

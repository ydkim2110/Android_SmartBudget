package com.example.smartbudget.Overview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.smartbudget.R;
import com.example.smartbudget.Main.Spending;

import java.util.ArrayList;
import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SpendingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Overview");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.spending_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);

        List<Spending> list = new ArrayList<>();
        list.add(new Spending("42%", "교통", "320,000원"));
        list.add(new Spending("42%", "교통", "320,000원"));
        list.add(new Spending("42%", "교통", "320,000원"));
        list.add(new Spending("42%", "교통", "320,000원"));
        list.add(new Spending("42%", "교통", "320,000원"));
        list.add(new Spending("42%", "교통", "320,000원"));
        list.add(new Spending("42%", "교통", "320,000원"));
        list.add(new Spending("42%", "교통", "320,000원"));
        adapter = new SpendingAdapter(list);
        recyclerView.setAdapter(adapter);
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
}

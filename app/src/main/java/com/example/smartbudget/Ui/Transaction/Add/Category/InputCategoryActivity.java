package com.example.smartbudget.Ui.Transaction.Add.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.smartbudget.R;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputCategoryActivity extends AppCompatActivity {

    private static final String TAG = InputCategoryActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_input_category)
    TabLayout tab_input_category;
    @BindView(R.id.vp_input_category)
    ViewPager vp_input_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_category);
        Log.d(TAG, "onCreate: started!!");
        ButterKnife.bind(this);

        initView();

        CategoryPagerAdapter adapter = new CategoryPagerAdapter(getSupportFragmentManager(), this);
        vp_input_category.setAdapter(adapter);
        tab_input_category.setupWithViewPager(vp_input_category);

    }

    private void initView() {
        Log.d(TAG, "initView: called!!");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.toolbar_title_add_category));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}

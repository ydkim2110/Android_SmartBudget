package com.example.smartbudget.Travel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.smartbudget.R;

public class TravelFullListActivity extends AppCompatActivity {

    private static final String TAG = TravelFullListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_full_list);
        Log.d(TAG, "onCreate: started!!");
    }


}

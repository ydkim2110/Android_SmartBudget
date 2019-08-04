package com.example.smartbudget.Ui.Budget;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunningFragment extends Fragment {

    private static final String TAG = RunningFragment.class.getSimpleName();

    private static RunningFragment instance;

    public static RunningFragment getInstance() {
        return instance == null ? new RunningFragment() : instance;
    }

    public RunningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called!!");
        return inflater.inflate(R.layout.fragment_running, container, false);
    }

}

package com.example.smartbudget.Report;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class YearlyFragment extends Fragment {

    public static YearlyFragment newInstance() {
        YearlyFragment fragment = new YearlyFragment();
        return fragment;
    }

    public YearlyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yearly, container, false);
    }

}
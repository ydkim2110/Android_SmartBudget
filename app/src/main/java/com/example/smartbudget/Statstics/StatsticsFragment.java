package com.example.smartbudget.Statstics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsticsFragment extends Fragment {


    public StatsticsFragment() {
        // Required empty public constructor
    }

    public static StatsticsFragment newInstance() {
        StatsticsFragment fragment = new StatsticsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statstics, container, false);
    }

}

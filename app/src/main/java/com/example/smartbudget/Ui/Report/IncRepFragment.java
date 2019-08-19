package com.example.smartbudget.Ui.Report;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncRepFragment extends Fragment {

    private static final String TAG = IncRepFragment.class.getSimpleName();

    private static IncRepFragment instance;

    public static IncRepFragment getInstance() {
        if (instance == null) {
            instance = new IncRepFragment();
        }
        return instance;
    }

    public IncRepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inc_rep, container, false);
    }

}

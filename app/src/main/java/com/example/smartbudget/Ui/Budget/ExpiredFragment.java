package com.example.smartbudget.Ui.Budget;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpiredFragment extends Fragment {

    private static ExpiredFragment instance;

    public static ExpiredFragment getInstance() {
        return instance == null ? new ExpiredFragment() : instance;
    }


    public ExpiredFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expired, container, false);
    }

}

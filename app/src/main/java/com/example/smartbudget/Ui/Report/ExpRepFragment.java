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
public class ExpRepFragment extends Fragment {

    private static final String TAG = ExpRepFragment.class.getSimpleName();

    private static ExpRepFragment instance;

    public static ExpRepFragment getInstance() {
        if (instance == null) {
            instance = new ExpRepFragment();
        }
        return instance;
    }

    public ExpRepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exp_rep, container, false);
    }

}

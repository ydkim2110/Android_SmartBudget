package com.example.smartbudget.Travel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TravelFragment extends Fragment {

    private static TravelFragment instance;

    public static TravelFragment getInstance() {
        if (instance == null) {
            instance = new TravelFragment();
        }
        return instance;
    }

    public TravelFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_travel, container, false);
    }

}

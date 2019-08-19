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
public class ReportAssetFragment extends Fragment {

    private static ReportAssetFragment instance;

    public static ReportAssetFragment getInstance() {
        if (instance == null){
            instance = new ReportAssetFragment();
        }
        return instance;
    }

    public ReportAssetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_asset, container, false);
    }

}

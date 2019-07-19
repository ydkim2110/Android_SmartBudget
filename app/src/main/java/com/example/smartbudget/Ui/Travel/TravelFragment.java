package com.example.smartbudget.Ui.Travel;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.smartbudget.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    Unbinder mUnbinder;

    private RecyclerView mRecyclerView;

    @BindView(R.id.txt_view_more)
    TextView txt_view_more;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_travel, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mRecyclerView = view.findViewById(R.id.travel_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), new LinearLayoutManager(getContext()).getOrientation()));

        List<Travel> travelList = new ArrayList<>();
        travelList.add(new Travel("유럽여행", "유럽", "2019-06-04", "2019-06-12", 4000000d));
        travelList.add(new Travel("일본 오사카", "일본", "2019-03-05", "2019-06-25", 2000000d));
        travelList.add(new Travel("코타키나발루", "말레이시아", "2019-05-05", "2019-05-15", 4200000d));
        travelList.add(new Travel("발리 여름휴가", "인도네시아", "2019-02-12", "2019-02-16", 3400000d));
        travelList.add(new Travel("하외이", "미국", "2019-05-20", "2019-05-25", 1200000d));

        TravelListAdapter adapter = new TravelListAdapter(getContext(), travelList);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

}

package com.example.smartbudget.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbudget.R;
import com.example.smartbudget.overview.OverviewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    private RecyclerView transactionRecyclerview;
    private TransactionAdapter transactionAdapter;

    private ConstraintLayout clickableArea;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        ConstraintLayout constraintLayout = view.findViewById(R.id.home_overview_container);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), OverviewActivity.class));
            }
        });

        transactionRecyclerview = view.findViewById(R.id.transaction_recyclerview);
        transactionRecyclerview.setHasFixedSize(true);
        transactionRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        List<Transaction> transactionList = new ArrayList<>();
        List<ListItem> consolidatedList = new ArrayList<>();

        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-02"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-02"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-04"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-04"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-04"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-05"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-05"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-12"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-12"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-15"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-15"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-15"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-18"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-18"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-19"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-19"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-22"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-22"));
        transactionList.add(new Transaction("교통", "택시", 12000, "2019-05-22"));

        HashMap<String, List<Transaction>> groupedHashMap = groupDataIntoHashMap(transactionList);

        for (String date : groupedHashMap.keySet()) {
            DateItem dateItem = new DateItem();
            dateItem.setDate(date);
            consolidatedList.add(dateItem);

            for (Transaction transaction : groupedHashMap.get(date)) {
                TransactionItem transactionItem = new TransactionItem();
                transactionItem.setTransaction(transaction);
                consolidatedList.add(transactionItem);
            }
        }

        transactionAdapter = new TransactionAdapter(consolidatedList);
        transactionRecyclerview.setAdapter(transactionAdapter);
        transactionAdapter.notifyDataSetChanged();

        return view;
    }

    private HashMap<String, List<Transaction>> groupDataIntoHashMap(List<Transaction> listOfTransaction) {

        HashMap<String, List<Transaction>> groupedHashMap = new HashMap<>();

        for (Transaction dataModel : listOfTransaction) {

            String hashMapkey = dataModel.getDate();

            if (groupedHashMap.containsKey(hashMapkey)) {
                // The key is already in the HashMap; add the pojo object
                // against the existing key.
                groupedHashMap.get(hashMapkey).add(dataModel);
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                List<Transaction> list = new ArrayList<>();
                list.add(dataModel);
                groupedHashMap.put(hashMapkey, list);
            }
        }

        return groupedHashMap;

    }

}

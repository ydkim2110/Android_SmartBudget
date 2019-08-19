package com.example.smartbudget.Ui.Transaction.Add.Category;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.DefaultCategories;
import com.example.smartbudget.Model.SubCategory;
import com.example.smartbudget.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {

    private static final String TAG = ExpenseFragment.class.getSimpleName();

    private static ExpenseFragment instance;

    public static ExpenseFragment getInstance() {
        return instance == null ? new ExpenseFragment() : instance;
    }

    public ExpenseFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv_expense_categories)
    RecyclerView rv_expense_categories;

    private HashMap<String, List<SubCategory>> groupedHashMap;

    private List<CategoryExpenseAdapter.Item> data;

    Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        rv_expense_categories.setHasFixedSize(true);
        rv_expense_categories.setLayoutManager(new LinearLayoutManager(getContext()));

        groupedHashMap = groupDataIntoHashMap(Arrays.asList(DefaultCategories.getDefaultSubExpenseCategories()));

        List<Category> categoryList = Arrays.asList(DefaultCategories.getDefaultExpenseCategories());

        data = new ArrayList<>();
//        CategoryExpenseAdapter.Item noneExpandableData = new CategoryExpenseAdapter.Item();

        for (Category category : categoryList) {
            //if (category.getCategoryID().equals(":food&drink")) {
                CategoryExpenseAdapter.Item noneExpandableData = new CategoryExpenseAdapter.Item(CategoryExpenseAdapter.HEADER, category);
                noneExpandableData.invisibleChildren = new ArrayList<>();
                if (groupedHashMap.get(category.getCategoryID()) != null) {
                    for (SubCategory subCategory : groupedHashMap.get(category.getCategoryID())) {
                        if (category.getCategoryID().equals(subCategory.getCategoryId())) {
                            Log.d(TAG, "onCreateView: first sub called!! "+subCategory.getId());
                            noneExpandableData.invisibleChildren.add(new CategoryExpenseAdapter.Item( CategoryExpenseAdapter.CHILD, subCategory));
                        }
                    }
                }
                data.add(noneExpandableData);
            //}
//            else {
//                data.add(new CategoryExpenseAdapter.Item(CategoryExpenseAdapter.HEADER, category));
//                if (groupedHashMap.get(category.getCategoryID()) != null) {
//                    for (SubCategory subCategory : groupedHashMap.get(category.getCategoryID())) {
//                        if (category.getCategoryID().equals(subCategory.getCategoryId())) {
//                            Log.d(TAG, "onCreateView: first sub called!! "+subCategory.getId());
//                            data.add(new CategoryExpenseAdapter.Item(CategoryExpenseAdapter.CHILD, subCategory));
//                        }
//                    }
//                }
//            }
        }

        CategoryExpenseAdapter adapter = new CategoryExpenseAdapter(getContext(), data);
        rv_expense_categories.setAdapter(adapter);

        return view;
    }

    private HashMap<String, List<SubCategory>> groupDataIntoHashMap(List<SubCategory> subCategoryList) {

        HashMap<String, List<SubCategory>> groupedHashMap = new HashMap<>();

        if (subCategoryList != null) {
            for (SubCategory subCategory : subCategoryList) {

                String hashMapkey = subCategory.getCategoryId();

                if (groupedHashMap.containsKey(hashMapkey)) {
                    groupedHashMap.get(hashMapkey).add(subCategory);
                } else {
                    List<SubCategory> list = new ArrayList<>();
                    list.add(subCategory);
                    groupedHashMap.put(hashMapkey, list);
                }
            }
        }

        return groupedHashMap;
    }

}

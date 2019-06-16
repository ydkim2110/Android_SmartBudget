package com.example.smartbudget.AddTransaction.Category;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.smartbudget.Database.DBHelper;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.R;
import com.example.smartbudget.AddTransaction.ICategoryLoadListener;

import java.util.List;

public class CategoryDialogFragment extends DialogFragment
        implements ICategoryLoadListener, CategoryDialogAdapter.UpdateButtonListener {

    private static final String TAG = CategoryDialogFragment.class.getSimpleName();

    private String selectedCategoryName = "";

    @Override
    public void onUpdate(boolean status, String categoryName) {
        if (status) {
            saveBtn.setEnabled(true);
            selectedCategoryName = categoryName;
        }
    }

    public interface OnDialogSendListener {
        void sendResult(String result);
    }
    public OnDialogSendListener mOnDialogSendListener;

    public static CategoryDialogFragment newInstance() {
        return new CategoryDialogFragment();
    }

    private Button saveBtn;
    private Button cancelBtn;

    private RecyclerView mCategoryRecyclerView;
    private DBHelper mDBHelper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnDialogSendListener = (OnDialogSendListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogTheme);

        mDBHelper = new DBHelper(getContext());
        DatabaseUtils.getAllCategory(mDBHelper, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_dialog, container, false);

        saveBtn = view.findViewById(R.id.save_btn);
        cancelBtn = view.findViewById(R.id.cancel_btn);
        saveBtn.setEnabled(false);

        mCategoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        mCategoryRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        mCategoryRecyclerView.setLayoutManager(layoutManager);

        handleClickButton();

        return view;
    }

    private void handleClickButton() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: cancel btn click!!");
                getDialog().dismiss();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: save btn click!!");
                // todo: send result to activity
                mOnDialogSendListener.sendResult(selectedCategoryName);
                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onCategoryLoadSuccess(List<Category> categoryList) {
        CategoryDialogAdapter categoryAdapter = new CategoryDialogAdapter(getContext(), categoryList, this);
        mCategoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

}

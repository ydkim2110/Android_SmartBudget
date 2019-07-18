package com.example.smartbudget.Ui.Transaction.Add.Category;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Model.CategoryModel;
import com.example.smartbudget.Ui.Main.MainActivity;
import com.example.smartbudget.R;
import com.example.smartbudget.Ui.Transaction.Add.ICategoryLoadListener;

import java.util.List;

public class CategoryDialogFragment extends DialogFragment
        implements ICategoryLoadListener, CategoryDialogAdapter.UpdateButtonListener {

    private static final String TAG = CategoryDialogFragment.class.getSimpleName();

    private CategoryModel mCategoryModel;

    @Override
    public void onUpdate(boolean status, CategoryModel categoryModel) {
        if (status) {
            saveBtn.setEnabled(true);
            mCategoryModel = categoryModel;
        }
    }

    public interface OnDialogSendListener {
        void sendResult(CategoryModel categoryModel);
    }
    public OnDialogSendListener mOnDialogSendListener;

    public static CategoryDialogFragment newInstance() {
        return new CategoryDialogFragment();
    }

    private Button saveBtn;
    private Button cancelBtn;

    private RecyclerView mCategoryRecyclerView;

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

        DatabaseUtils.getAllCategory(MainActivity.mDBHelper, this);
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
                mOnDialogSendListener.sendResult(mCategoryModel);
                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onCategoryLoadSuccess(List<CategoryModel> categoryList) {
        CategoryDialogAdapter categoryAdapter = new CategoryDialogAdapter(getContext(), categoryList, this);
        mCategoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

}

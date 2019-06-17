package com.example.smartbudget.AddTransaction.Category;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartbudget.R;
import com.example.smartbudget.Utils.IRecyclerItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryDialogAdapter extends RecyclerView.Adapter<CategoryDialogAdapter.ViewHolder> {

    private static final String TAG = CategoryDialogAdapter.class.getSimpleName();

    public interface UpdateButtonListener {
        void onUpdate(boolean status, String categoryName);
    }

    public UpdateButtonListener mUpdateButtonListener;

    private Context mContext;
    private List<Category> mCategoryList;
    private List<ImageView> mImageViewList;


    public CategoryDialogAdapter(Context context, List<Category> categoryList, UpdateButtonListener listener) {
        this.mContext = context;
        this.mCategoryList = categoryList;
        mImageViewList = new ArrayList<>();
        this.mUpdateButtonListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dialog_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.categoryImage.setImageResource(mCategoryList.get(i).getIcon());
        viewHolder.categoryName.setText(mCategoryList.get(i).getName());

        if (!mImageViewList.contains(viewHolder.categoryImage)) {
            mImageViewList.add(viewHolder.categoryImage);
        }

        viewHolder.setIRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int position) {
                for (ImageView imageView : mImageViewList) {
                    imageView.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack));
                }
                viewHolder.categoryImage.setColorFilter(ContextCompat.getColor(mContext, R.color.colorRevenue));

                mUpdateButtonListener.onUpdate(true, mCategoryList.get(i).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView categoryImage;
        TextView categoryName;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener IRecyclerItemSelectedListener) {
            this.mIRecyclerItemSelectedListener = IRecyclerItemSelectedListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.category_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelectedListener(v, getAdapterPosition());
        }
    }

}
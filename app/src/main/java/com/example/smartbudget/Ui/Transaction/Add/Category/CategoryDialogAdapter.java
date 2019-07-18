package com.example.smartbudget.Ui.Transaction.Add.Category;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartbudget.Model.CategoryModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryDialogAdapter extends RecyclerView.Adapter<CategoryDialogAdapter.ViewHolder> {

    private static final String TAG = CategoryDialogAdapter.class.getSimpleName();

    public interface UpdateButtonListener {
        void onUpdate(boolean status, CategoryModel categoryModel);
    }

    public UpdateButtonListener mUpdateButtonListener;

    private Context mContext;
    private List<CategoryModel> mCategoryList;
    private List<ImageView> mImageViewList;


    public CategoryDialogAdapter(Context context, List<CategoryModel> categoryList, UpdateButtonListener listener) {
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
        viewHolder.categoryImage.setImageResource(mCategoryList.get(i).getCategory_icon());
        viewHolder.categoryName.setText(mCategoryList.get(i).getCategory_name());

        if (!mImageViewList.contains(viewHolder.categoryImage)) {
            mImageViewList.add(viewHolder.categoryImage);
        }

        viewHolder.setIRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
                for (ImageView imageView : mImageViewList) {
                    imageView.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBlack));
                }
                viewHolder.categoryImage.setColorFilter(ContextCompat.getColor(mContext, R.color.colorRevenue));

                mUpdateButtonListener.onUpdate(true, mCategoryList.get(i));
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
            mIRecyclerItemSelectedListener.onItemSelected(v, getAdapterPosition());
        }
    }

}

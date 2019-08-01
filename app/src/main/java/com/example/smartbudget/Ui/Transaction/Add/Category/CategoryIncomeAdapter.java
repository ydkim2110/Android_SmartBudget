package com.example.smartbudget.Ui.Transaction.Add.Category;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;
import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.EventBus.CategorySelectedEvent;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryIncomeAdapter extends RecyclerView.Adapter<CategoryIncomeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Category> mCategoryList;

    public CategoryIncomeAdapter(Context context, List<Category> categoryList) {
        this.mContext = context;
        this.mCategoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.iv_category_icon.setImageResource(mCategoryList.get(position).getIconResourceID());
        holder.iv_category_icon.setColorFilter(mCategoryList.get(position).getIconColor());
        holder.tv_name.setText(mCategoryList.get(position).getCategoryVisibleName(mContext));
        holder.btn_expand_toggle.setVisibility(View.INVISIBLE);

        holder.setIRecyclerItemSelectedListener((v, i) -> {
            EventBus.getDefault().postSticky(new CategorySelectedEvent(true, Common.TYPE_CATEGORY, Common.TYPE_INCOME_TRANSACTION, mCategoryList.get(i)));
            ((Activity) mContext).finish();
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList != null ? mCategoryList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_category_icon)
        ImageView iv_category_icon;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.btn_expand_toggle)
        ImageView btn_expand_toggle;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener IRecyclerItemSelectedListener) {
            mIRecyclerItemSelectedListener = IRecyclerItemSelectedListener;
        }

        Unbinder unbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelected(v, getAdapterPosition());
        }
    }
}

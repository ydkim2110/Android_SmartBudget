package com.example.smartbudget.Ui.Transaction.Add.Category;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Model.Category;
import com.example.smartbudget.Model.SubCategory;
import com.example.smartbudget.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = CategoryAdapter.class.getSimpleName();

    public static final int CHILD = 0;
    public static final int HEADER = 1;

    private Context context;
    private List<Item> data;

    public CategoryAdapter(Context context, List<Item> data) {
        this.context =context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        switch (viewType) {
            case HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
                return new CategoryViewHolder(view);
            case CHILD:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_category, parent, false);
                return new SubCategoryViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Item item = data.get(position);
        Log.d(TAG, "onBindViewHolder: item.type: "+item.type);
            switch (item.type) {
                case HEADER:
                    final CategoryViewHolder itemController = (CategoryViewHolder) holder;
                    itemController.refferalItem = item;

                    itemController.tv_name.setText(item.category.getCategoryVisibleName(context));
                    itemController.iv_category_icon.setImageResource(item.category.getIconResourceID());
                    itemController.iv_category_icon.setColorFilter(item.category.getIconColor());

                    if (item.invisibleChildren == null) {
                        itemController.btn_expand_toggle.setImageResource(R.drawable.ic_remove_circle_outline_black_24dp);
                    } else {
                        itemController.btn_expand_toggle.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
                    }

                    itemController.btn_expand_toggle.setOnClickListener(v -> {
                        if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.ic_remove_circle_outline_black_24dp);
                            item.invisibleChildren = null;
                        }
                    });
                    break;
                case CHILD:
                    final SubCategoryViewHolder subController = (SubCategoryViewHolder) holder;
                    subController.refferalItem = item;

                    Log.d(TAG, "onBindViewHolder: sub: ");
                    subController.tv_name.setText(item.subCategory.getCategoryVisibleName(context));
                    subController.iv_category_icon.setImageResource(item.subCategory.getIconResourceID());
                    subController.iv_category_icon.setColorFilter(item.subCategory.getIconColor());
                    break;
            }

    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_category_icon)
        ImageView iv_category_icon;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.btn_expand_toggle)
        ImageView btn_expand_toggle;

        Item refferalItem;

        Unbinder unbinder;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    public class SubCategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_category_icon)
        ImageView iv_category_icon;
        @BindView(R.id.tv_name)
        TextView tv_name;

        Item refferalItem;

        Unbinder unbinder;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    public static class Item {
        public int type;
        public Category category;
        public SubCategory subCategory;
        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, Category category) {
            this.type = type;
            this.category = category;
        }

        public Item(int type, SubCategory subCategory) {
            this.type = type;
            this.subCategory = subCategory;
        }
    }
}

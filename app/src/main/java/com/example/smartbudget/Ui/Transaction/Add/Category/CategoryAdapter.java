package com.example.smartbudget.Ui.Transaction.Add.Category;

import android.content.Context;
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
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private Context context;
    private List<CategoryListItem> consolidatedList = new ArrayList<>();

    private List<Item> data;

    public CategoryAdapter(List<Item> data) {
        this.data = data;
    }

    public CategoryAdapter(Context context, List<CategoryListItem> categoryList) {
        this.context = context;
        this.consolidatedList = categoryList;
    }

    @Override
    public int getItemViewType(int position) {
        //return consolidatedList.get(position).getType();
        return data.get(position).type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder viewHolder = null;
//        switch (viewType) {
//            case CategoryListItem.TYPE_CATEGORY:
//                View view = LayoutInflater.from(context)
//                        .inflate(R.layout.item_category, parent, false);
//                viewHolder =  new ViewHolder(view);
//                break;
//            case CategoryListItem.TYPE_SUB_CATEGORY:
//                View view1 = LayoutInflater.from(context)
//                        .inflate(R.layout.item_sub_category, parent, false);
//                viewHolder =  new ViewHolder(view1);
//                break;
//        }
//        return viewHolder;

        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        switch (viewType) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_category, parent, false);
                CategoryViewHolder header = new CategoryViewHolder(view);
                return header;
            case CHILD:
                TextView itemTextView = new TextView(context);
                itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
                itemTextView.setTextColor(0x88000000);
                itemTextView.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                return new RecyclerView.ViewHolder(itemTextView) {
                };
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

//        switch (holder.getItemViewType()) {
//            case CategoryListItem.TYPE_CATEGORY:
//                CategoryItem categoryItem = (CategoryItem) consolidatedList.get(position);
//                ViewHolder categoryHolder = (ViewHolder) holder;
//                categoryHolder.iv_category_icon.setImageResource(categoryItem.getCategory().getIconResourceID());
//                categoryHolder.iv_category_icon.setColorFilter(categoryItem.getCategory().getIconColor());
//                categoryHolder.tv_name.setText(categoryItem.getCategory().getCategoryVisibleName(context));
//                break;
//            case CategoryListItem.TYPE_SUB_CATEGORY:
//                SubCategoryItem subCategoryItem = (SubCategoryItem) consolidatedList.get(position);
//                ViewHolder subCategoryHolder = (ViewHolder) holder;
//                subCategoryHolder.iv_category_icon.setImageResource(subCategoryItem.getSubCategory().getIconResourceID());
//                subCategoryHolder.iv_category_icon.setColorFilter(subCategoryItem.getSubCategory().getIconColor());
//                subCategoryHolder.tv_name.setText(subCategoryItem.getSubCategory().getCategoryVisibleName(context));
//                break;
//        }

        final Item item = data.get(position);
        switch (item.type) {
            case HEADER:
                final CategoryViewHolder itemController = (CategoryViewHolder) holder;
                itemController.refferalItem = item;
                itemController.tv_name.setText(item.category.getCategoryID());
                itemController.iv_category_icon.setImageResource(item.category.getIconResourceID());
                itemController.iv_category_icon.setColorFilter(item.category.getIconColor());
                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.ic_remove_circle_outline_black_24dp);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
                }
                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                    }
                });
                break;
            case CHILD:
                TextView itemTextView = (TextView) holder.itemView;
                itemTextView.setText(data.get(position).subCategory.getId());
                break;
        }
    }

    @Override
    public int getItemCount() {
        //return consolidatedList != null ? consolidatedList.size() : 0;
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

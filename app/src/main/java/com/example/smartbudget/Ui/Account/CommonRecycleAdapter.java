package com.example.smartbudget.Ui.Account;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CommonRecycleAdapter extends RecyclerView.Adapter<CommonRecycleAdapter.ViewHolder> {

    private List<String> itemsData;

    public CommonRecycleAdapter(List<String> itemsData) {
        this.itemsData = itemsData;
    }

    @Override
    public CommonRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_common_items, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.text.setText(itemsData.get(position));

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        TextView text;

        Unbinder mUnbinder;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mUnbinder = ButterKnife.bind(this, itemLayoutView);
        }
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

}

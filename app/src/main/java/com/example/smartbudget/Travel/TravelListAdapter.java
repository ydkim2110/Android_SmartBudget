package com.example.smartbudget.Travel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder> {

    private Context mContext;
    private List<Travel> mTravelList;

    public TravelListAdapter(Context mContext, List<Travel> mTravelList) {
        this.mContext = mContext;
        this.mTravelList = mTravelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.travel_list_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.travel_list_cover_photo.setImageResource(R.drawable.piggy_bank);
        viewHolder.travel_list_name.setText(mTravelList.get(i).getName());
        viewHolder.travel_list_period.setText(mTravelList.get(i).getStart_date()+" ~ "+mTravelList.get(i).getEnd_date());
        viewHolder.travel_list_amount.setText(Common.changeNumberToComma((int) mTravelList.get(i).getAmount()) +"원");

        viewHolder.setIRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position) {
                Intent intent = new Intent(mContext, TravelDetailActivity.class);
                intent.putExtra("travel_list", mTravelList.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTravelList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView travel_list_cover_photo;
        private TextView travel_list_name;
        private TextView travel_list_period;
        private TextView travel_list_amount;

        private IRecyclerItemSelectedListener mIRecyclerItemSelectedListener;

        public void setIRecyclerItemSelectedListener(IRecyclerItemSelectedListener mIRecyclerItemSelectedListener) {
            this.mIRecyclerItemSelectedListener = mIRecyclerItemSelectedListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            travel_list_cover_photo = itemView.findViewById(R.id.travel_list_cover_photo);
            travel_list_name = itemView.findViewById(R.id.travel_list_name);
            travel_list_period = itemView.findViewById(R.id.travel_list_period);
            travel_list_amount = itemView.findViewById(R.id.travel_list_amount);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mIRecyclerItemSelectedListener.onItemSelected(v, getAdapterPosition());
        }
    }
}

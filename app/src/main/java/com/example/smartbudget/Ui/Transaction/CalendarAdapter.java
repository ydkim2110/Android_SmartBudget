package com.example.smartbudget.Ui.Transaction;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbudget.Interface.IRecyclerItemSelectedListener;
import com.example.smartbudget.Model.TransactionModel;
import com.example.smartbudget.R;
import com.example.smartbudget.Utils.Common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyViewHolder> {

    private static final String TAG = CalendarAdapter.class.getSimpleName();

    private Context mContext;
    private List<Date> mDateList;
    private Calendar mCalendar;
    ;
    private List<TransactionModel> mTransactionList;

    public CalendarAdapter(Context context, List<Date> dateList, Calendar calendar, List<TransactionModel> transactionList) {
        mContext = context;
        mDateList = dateList;
        mCalendar = calendar;
        mTransactionList = transactionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_calendar_day, parent, false);

        view.post(() -> {
            GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            params.height = parent.getMeasuredHeight() / 5;

            view.setLayoutParams(params);

        });

        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Date monthDate = mDateList.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);

        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);

        int currentYear = mCalendar.get(Calendar.YEAR);
        int currentMonth = mCalendar.get(Calendar.MONTH) + 1;
        int currentDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        int todayYear = Calendar.getInstance().get(Calendar.YEAR);
        int todayMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int todayDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        if (position % 7 == 0) {
            holder.tv_calendar_day.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        } else if (position % 7 == 6) {
            holder.tv_calendar_day.setTextColor(mContext.getResources().getColor(android.R.color.holo_blue_dark));
        }

        if (displayMonth == currentMonth && displayYear == currentYear) {

        } else {
            holder.tv_calendar_day.setTextColor(mContext.getResources().getColor(android.R.color.darker_gray));
        }

        if (displayYear == todayYear && displayMonth == todayMonth && displayDay == todayDay) {
            holder.tv_calendar_day.setBackgroundResource(R.drawable.shape_oval);
            holder.tv_calendar_day.setTextColor(mContext.getResources().getColor(android.R.color.white));
        }

        holder.tv_calendar_day.setText("" + dayNo);

        Calendar eventCalendar = Calendar.getInstance();
        ArrayList<Integer> expenseTotal = new ArrayList<>();
        ArrayList<Integer> incomeTotal = new ArrayList<>();

        for (int i = 0; i < mTransactionList.size(); i++) {
            eventCalendar.setTime(convertStringToDate(mTransactionList.get(i).getDate()));
            if (dayNo == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                    && displayYear == eventCalendar.get(Calendar.YEAR)) {
                if (mTransactionList.get(i).getType().equals("Expense")) {
                    expenseTotal.add((int) mTransactionList.get(i).getAmount());
                    if (expenseTotal.stream().mapToInt(n -> n).sum() > 1000000) {
                        holder.tv_expense.setTextSize(10);
                    } else if (expenseTotal.stream().mapToInt(n -> n).sum() > 10000000) {
                        holder.tv_expense.setTextSize(8);
                    }
                    holder.tv_expense.setText(Common.changeNumberToComma(expenseTotal.stream().mapToInt(n -> n).sum()) + "원");
                }
                else if (mTransactionList.get(i).getType().equals("Income")) {
                    incomeTotal.add((int) mTransactionList.get(i).getAmount());
                    if (incomeTotal.stream().mapToInt(n -> n).sum() > 1000000) {
                        holder.tv_income.setTextSize(10);
                    } else if (incomeTotal.stream().mapToInt(n -> n).sum() > 10000000) {
                        holder.tv_income.setTextSize(8);
                    }
                    holder.tv_income.setText(Common.changeNumberToComma(incomeTotal.stream().mapToInt(n -> n).sum()) + "원");
                }
            }
        }

        holder.setIRecyclerClickListener((view, i) -> {
            Toast.makeText(mContext, "Selected Day: " + mDateList.get(i), Toast.LENGTH_SHORT).show();
        });

    }

    private Date convertStringToDate(String eventDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date date = null;

        try {
            date = dateFormat.parse(eventDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    @Override
    public int getItemCount() {
        return mDateList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.container)
        ConstraintLayout container;
        @BindView(R.id.tv_calendar_day)
        TextView tv_calendar_day;
        @BindView(R.id.tv_income)
        TextView tv_income;
        @BindView(R.id.tv_expense)
        TextView tv_expense;

        private IRecyclerItemSelectedListener mIRecyclerClickListener;

        public void setIRecyclerClickListener(IRecyclerItemSelectedListener IRecyclerClickListener) {
            mIRecyclerClickListener = IRecyclerClickListener;
        }

        Unbinder mUnbinder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mUnbinder = ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mIRecyclerClickListener.onItemSelected(v, getAdapterPosition());
        }
    }

}

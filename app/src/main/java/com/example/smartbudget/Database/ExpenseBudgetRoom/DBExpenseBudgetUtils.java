package com.example.smartbudget.Database.ExpenseBudgetRoom;

import android.os.AsyncTask;

import com.example.smartbudget.Database.BudgetDatabase;
import com.example.smartbudget.Database.Interface.IExpenseBudgetUpdateListener;
import com.example.smartbudget.Interface.IExpenseBudgetLoadListener;

import java.util.List;

public class DBExpenseBudgetUtils {

    public static void getAllExpenseBudget(BudgetDatabase db, IExpenseBudgetLoadListener listener) {
        GetAllExpenseBudgetAsync task = new GetAllExpenseBudgetAsync(db, listener);
        task.execute();
    }
    public static void updateExpenseBudgetAmount(BudgetDatabase db, IExpenseBudgetUpdateListener listener, ExpenseBudgetItem... expenseBudgetItem) {
        UpdateExpenseBudgetAmount task = new UpdateExpenseBudgetAmount(db, listener);
        task.execute(expenseBudgetItem);
    }

    private static class GetAllExpenseBudgetAsync extends AsyncTask<Void, Void, List<ExpenseBudgetItem>> {

        BudgetDatabase mBudgetDatabase;
        IExpenseBudgetLoadListener mListener;

        public GetAllExpenseBudgetAsync(BudgetDatabase mBudgetDatabase, IExpenseBudgetLoadListener mListener) {
            this.mBudgetDatabase = mBudgetDatabase;
            this.mListener = mListener;
        }

        @Override
        protected List<ExpenseBudgetItem> doInBackground(Void... voids) {
            return mBudgetDatabase.expenseBudgetDAO().getExpenseBudgetList();
        }

        @Override
        protected void onPostExecute(List<ExpenseBudgetItem> expenseBudgetItemList) {
            super.onPostExecute(expenseBudgetItemList);
            mListener.onExpenseBudgetLoadSuccess(expenseBudgetItemList);
        }
    }

    private static class UpdateExpenseBudgetAmount extends AsyncTask<ExpenseBudgetItem, Void, Boolean> {

        BudgetDatabase mBudgetDatabase;
        IExpenseBudgetUpdateListener mListener;


        public UpdateExpenseBudgetAmount(BudgetDatabase budgetDatabase, IExpenseBudgetUpdateListener listener) {
            mBudgetDatabase = budgetDatabase;
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(ExpenseBudgetItem... expenseBudgetItems) {
            mBudgetDatabase.expenseBudgetDAO().updateExpenseBudget(expenseBudgetItems[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mListener.onExpenseBudgetUpdateSuccess(aBoolean);
        }
    }
}

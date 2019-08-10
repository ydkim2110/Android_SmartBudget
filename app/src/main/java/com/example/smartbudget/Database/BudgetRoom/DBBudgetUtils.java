package com.example.smartbudget.Database.BudgetRoom;

import android.database.Cursor;
import android.os.AsyncTask;

import com.example.smartbudget.Database.BudgetDatabase;
import com.example.smartbudget.Database.DBContract;
import com.example.smartbudget.Database.DBHelper;
import com.example.smartbudget.Interface.IBudgetLoadListener;
import com.example.smartbudget.Model.BudgetModel;
import com.example.smartbudget.Interface.IDBInsertListener;

import java.util.ArrayList;
import java.util.List;

public class DBBudgetUtils {
    private static final String TAG = DBBudgetUtils.class.getSimpleName();

    public static void insertBudgetAsync(BudgetDatabase db, IDBInsertListener listener, BudgetItem... budgetItems) {
        InsertBudgetAsync task = new InsertBudgetAsync(db, listener);
        task.execute(budgetItems);
    }

    public static void getRunningBudgets(BudgetDatabase db, String date, IBudgetLoadListener listener) {
        GetRunningBudgetsAsync task = new GetRunningBudgetsAsync(db, date, listener);
        task.execute();
    }

    public static void getExpiredBudgets(BudgetDatabase db, String date, IBudgetLoadListener listener) {
        GetExpiredBudgetsAsync task = new GetExpiredBudgetsAsync(db, date, listener);
        task.execute();
    }

    /*
    ============================================================================
    ASYNC TASK DEFINE
    ============================================================================
     */
    private static class InsertBudgetAsync extends AsyncTask<BudgetItem, Void, Boolean> {

        BudgetDatabase db;
        IDBInsertListener mListener;

        public InsertBudgetAsync(BudgetDatabase db, IDBInsertListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(BudgetItem... budgetItems) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean isInserted) {
            super.onPostExecute(isInserted);
            mListener.onDBInsertSuccess(isInserted);
        }
    }

    private static class GetRunningBudgetsAsync extends AsyncTask<Void, Void, List<BudgetItem>> {

        BudgetDatabase mBudgetDatabase;
        String date;
        IBudgetLoadListener mListener;

        public GetRunningBudgetsAsync(BudgetDatabase budgetDatabase, String date, IBudgetLoadListener listener) {
            mBudgetDatabase = budgetDatabase;
            this.date = date;
            mListener = listener;
        }

        @Override
        protected List<BudgetItem> doInBackground(Void... voids) {
            return mBudgetDatabase.budgetDAO().getRunningBudgets(date);
        }

        @Override
        protected void onPostExecute(List<BudgetItem> budgetItemList) {
            super.onPostExecute(budgetItemList);
            mListener.onBudgetLoadSuccess(budgetItemList);
        }
    }

    private static class GetExpiredBudgetsAsync extends AsyncTask<Void, Void, List<BudgetItem>> {

        BudgetDatabase mBudgetDatabase;
        String date;
        IBudgetLoadListener mListener;

        public GetExpiredBudgetsAsync(BudgetDatabase budgetDatabase, String date, IBudgetLoadListener listener) {
            mBudgetDatabase = budgetDatabase;
            this.date = date;
            mListener = listener;
        }

        @Override
        protected List<BudgetItem> doInBackground(Void... voids) {
            return mBudgetDatabase.budgetDAO().getExpiredBudgets(date);
        }

        @Override
        protected void onPostExecute(List<BudgetItem> budgetItemList) {
            super.onPostExecute(budgetItemList);
            mListener.onBudgetLoadSuccess(budgetItemList);
        }
    }
}

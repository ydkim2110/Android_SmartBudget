package com.example.smartbudget.Database.BudgetRoom;

import android.os.AsyncTask;
import android.util.Log;

import com.example.smartbudget.Database.BudgetDatabase;
import com.example.smartbudget.Database.Interface.IBudgetDeleteListener;
import com.example.smartbudget.Database.Model.SumBudget;
import com.example.smartbudget.Interface.IBudgetLoadListener;
import com.example.smartbudget.Database.Interface.IBudgetInsertListener;

import java.util.List;

public class DBBudgetUtils {
    private static final String TAG = DBBudgetUtils.class.getSimpleName();

    public static void insertBudget(BudgetDatabase db, IBudgetInsertListener listener, BudgetItem... budgetItems) {
        InsertBudgetAsync task = new InsertBudgetAsync(db, listener);
        task.execute(budgetItems);
    }

    public static void deleteBudget(BudgetDatabase db, IBudgetDeleteListener listener, BudgetItem... budgetItems) {
        DeleteBudgetAsync task = new DeleteBudgetAsync(db, listener);
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

        BudgetDatabase mBudgetDatabase;
        IBudgetInsertListener mListener;

        public InsertBudgetAsync(BudgetDatabase budgetDatabase, IBudgetInsertListener listener) {
            this.mBudgetDatabase = budgetDatabase;
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(BudgetItem... budgetItems) {
            long result = mBudgetDatabase.budgetDAO().insertBudget(budgetItems[0]);
            Log.d(TAG, "doInBackground: result: "+result);
            return result != -1;
        }

        @Override
        protected void onPostExecute(Boolean isInserted) {
            super.onPostExecute(isInserted);
            mListener.onBudgetInsertSuccess(isInserted);
        }
    }

    private static class DeleteBudgetAsync extends AsyncTask<BudgetItem, Void, Boolean> {

        BudgetDatabase mBudgetDatabase;
        IBudgetDeleteListener mListener;

        public DeleteBudgetAsync(BudgetDatabase budgetDatabase, IBudgetDeleteListener listener) {
            this.mBudgetDatabase = budgetDatabase;
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(BudgetItem... budgetItems) {
            long result = mBudgetDatabase.budgetDAO().deleteBudget(budgetItems[0]);
            Log.d(TAG, "doInBackground: result: "+result);
            return result == 1;
        }

        @Override
        protected void onPostExecute(Boolean isInserted) {
            super.onPostExecute(isInserted);
            mListener.onBudgetDeleteSuccess(isInserted);
        }
    }

    private static class GetRunningBudgetsAsync extends AsyncTask<Void, Void, List<SumBudget>> {

        BudgetDatabase mBudgetDatabase;
        String date;
        IBudgetLoadListener mListener;

        public GetRunningBudgetsAsync(BudgetDatabase budgetDatabase, String date, IBudgetLoadListener listener) {
            mBudgetDatabase = budgetDatabase;
            this.date = date;
            mListener = listener;
        }

        @Override
        protected List<SumBudget> doInBackground(Void... voids) {
            return mBudgetDatabase.budgetDAO().getRunningBudgets(date);
        }

        @Override
        protected void onPostExecute(List<SumBudget> budgetItemList) {
            super.onPostExecute(budgetItemList);
            mListener.onBudgetLoadSuccess(budgetItemList);
        }
    }

    private static class GetExpiredBudgetsAsync extends AsyncTask<Void, Void, List<SumBudget>> {

        BudgetDatabase mBudgetDatabase;
        String date;
        IBudgetLoadListener mListener;

        public GetExpiredBudgetsAsync(BudgetDatabase budgetDatabase, String date, IBudgetLoadListener listener) {
            mBudgetDatabase = budgetDatabase;
            this.date = date;
            mListener = listener;
        }

        @Override
        protected List<SumBudget> doInBackground(Void... voids) {
            return mBudgetDatabase.budgetDAO().getExpiredBudgets(date);
        }

        @Override
        protected void onPostExecute(List<SumBudget> budgetItemList) {
            super.onPostExecute(budgetItemList);
            mListener.onBudgetLoadSuccess(budgetItemList);
        }
    }
}

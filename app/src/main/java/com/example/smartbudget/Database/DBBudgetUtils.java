package com.example.smartbudget.Database;

import android.database.Cursor;
import android.os.AsyncTask;

import com.example.smartbudget.Interface.IBudgetLoadListener;
import com.example.smartbudget.Model.BudgetModel;
import com.example.smartbudget.Interface.IDBInsertListener;

import java.util.ArrayList;
import java.util.List;

public class DBBudgetUtils {
    private static final String TAG = DBBudgetUtils.class.getSimpleName();

    public static void insertBudgetAsync(DBHelper db, IDBInsertListener listener, BudgetModel... budgetModels) {
        InsertBudgetAsync task = new InsertBudgetAsync(db, listener);
        task.execute(budgetModels);
    }

    public static void getRunningBudgets(DBHelper db, IBudgetLoadListener listener) {
        GetRunningBudgetsAsync task = new GetRunningBudgetsAsync(db, listener);
        task.execute();
    }

    public static void getExpiredBudgets(DBHelper db, IBudgetLoadListener listener) {
        GetExpiredBudgetsAsync task = new GetExpiredBudgetsAsync(db, listener);
        task.execute();
    }

    /*
    ============================================================================
    ASYNC TASK DEFINE
    ============================================================================
     */
    private static class InsertBudgetAsync extends AsyncTask<BudgetModel, Void, Boolean> {

        DBHelper db;
        IDBInsertListener mListener;

        public InsertBudgetAsync(DBHelper db, IDBInsertListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(BudgetModel... budgetModels) {
            return db.insertBudget(budgetModels[0]);
        }

        @Override
        protected void onPostExecute(Boolean isInserted) {
            super.onPostExecute(isInserted);
            mListener.onDBInsertSuccess(isInserted);
        }
    }

    private static class GetRunningBudgetsAsync extends AsyncTask<Void, Void, List<BudgetModel>> {

        DBHelper db;
        IBudgetLoadListener mListener;

        public GetRunningBudgetsAsync(DBHelper db, IBudgetLoadListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected List<BudgetModel> doInBackground(Void... voids) {
            Cursor cursor = db.getRunningBudgets();
            List<BudgetModel> budgetList = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    do {
                        BudgetModel budget = new BudgetModel();

                        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Budget._ID));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Budget.COL_DESCRIPTION));
                        String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Budget.COL_AMOUNT));
                        String startDate = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Budget.COL_START_DATE));
                        String endDate = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Budget.COL_END_DATE));
                        String accountId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Budget.COL_ACCOUNT_ID));

                        budget.setId((int) id);
                        budget.setDescription(description);
                        budget.setAmount(Double.parseDouble(amount));
                        budget.setStartDate(startDate);
                        budget.setEndDate(endDate);
                        budget.setAccountId(Integer.parseInt(accountId));

                        budgetList.add(budget);
                    }
                    while (cursor.moveToNext());
                } finally {
                    cursor.close();
                }
                return budgetList;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<BudgetModel> budgetModelList) {
            super.onPostExecute(budgetModelList);
            mListener.onBudgetLoadSuccess(budgetModelList);
        }
    }

    private static class GetExpiredBudgetsAsync extends AsyncTask<Void, Void, List<BudgetModel>> {

        DBHelper db;
        IBudgetLoadListener mListener;

        public GetExpiredBudgetsAsync(DBHelper db, IBudgetLoadListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected List<BudgetModel> doInBackground(Void... voids) {
            Cursor cursor = db.getExpiredBudgets();
            List<BudgetModel> budgetList = new ArrayList<>();
            if (cursor != null && cursor.getCount() > 0) {
                try {
                    do {
                        BudgetModel budget = new BudgetModel();

                        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DBContract.Budget._ID));
                        String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Budget.COL_DESCRIPTION));
                        String amount = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Budget.COL_AMOUNT));
                        String startDate = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Budget.COL_START_DATE));
                        String endDate = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Budget.COL_END_DATE));
                        String accountId = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.Budget.COL_ACCOUNT_ID));

                        budget.setId((int) id);
                        budget.setDescription(description);
                        budget.setAmount(Double.parseDouble(amount));
                        budget.setStartDate(startDate);
                        budget.setEndDate(endDate);
                        budget.setAccountId(Integer.parseInt(accountId));

                        budgetList.add(budget);
                    }
                    while (cursor.moveToNext());
                } finally {
                    cursor.close();
                }
                return budgetList;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<BudgetModel> budgetModelList) {
            super.onPostExecute(budgetModelList);
            mListener.onBudgetLoadSuccess(budgetModelList);
        }
    }
}

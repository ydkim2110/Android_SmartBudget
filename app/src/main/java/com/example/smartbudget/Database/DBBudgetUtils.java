package com.example.smartbudget.Database;

import android.os.AsyncTask;

import com.example.smartbudget.Model.BudgetModel;
import com.example.smartbudget.Interface.IAccountInsertListener;

public class DBBudgetUtils {
    private static final String TAG = DBBudgetUtils.class.getSimpleName();

    public static void insertBudgetAsync(DBHelper db, IAccountInsertListener listener, BudgetModel... budgetModels) {
        InsertBudgetAsync task = new InsertBudgetAsync(db, listener);
        task.execute(budgetModels);
    }

    /*
    ============================================================================
    ASYNC TASK DEFINE
    ============================================================================
     */
    private static class InsertBudgetAsync extends AsyncTask<BudgetModel, Void, Boolean> {

        DBHelper db;
        IAccountInsertListener mListener;

        public InsertBudgetAsync(DBHelper db, IAccountInsertListener listener) {
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
            mListener.onAccountInsertSuccess(isInserted);
        }
    }
}

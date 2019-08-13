package com.example.smartbudget.Database.TransactionRoom;

import android.os.AsyncTask;
import android.util.Log;

import com.example.smartbudget.Database.DBHelper;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Interface.ILastFewDaysTransactionsLoadListener;
import com.example.smartbudget.Database.Interface.ISumAmountByBudgetListener;
import com.example.smartbudget.Database.Interface.IThisMonthSumTransactionBySubCategoryListener;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsLoadListener;
import com.example.smartbudget.Database.Interface.ITransactionsLoadListener;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsByPatternLoadListener;
import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Database.Model.SpendingByPattern;
import com.example.smartbudget.Database.BudgetDatabase;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsByCategoryLoadListener;
import com.example.smartbudget.Database.Model.SumTransactionBySubCategory;
import com.example.smartbudget.Interface.IThisMonthTransactionLoadListener;
import com.example.smartbudget.Interface.ITransactionInsertListener;
import com.example.smartbudget.Interface.ITransactionUpdateListener;
import com.example.smartbudget.Model.TransactionModel;


import java.util.List;

public class DBTransactionUtils {

    public static void insertTransactionAsync(BudgetDatabase db, ITransactionInsertListener listener, TransactionItem... transactionItems) {
        InsertTransactionAsync task = new InsertTransactionAsync(db, listener);
        task.execute(transactionItems);
    }

    public static void getAllTransactions(BudgetDatabase db, ITransactionsLoadListener listener) {
        GetAllTransactionAsync task = new GetAllTransactionAsync(db, listener);
        task.execute();
    }

    public static void getThisMonthTransactions(BudgetDatabase db, String date, IThisMonthTransactionsLoadListener listener) {
        GetThisMonthTransactionAsync task = new GetThisMonthTransactionAsync(db, date, listener);
        task.execute();
    }

    public static void getThisMonthTransactionsByAccount(BudgetDatabase db, String date, int accountId, IThisMonthTransactionsLoadListener listener) {
        GetThisMonthTransactionsByAccountAsync task = new GetThisMonthTransactionsByAccountAsync(db, date, accountId, listener);
        task.execute();
    }

    public static void getThisMonthTransactionByPattern(BudgetDatabase db, String date, IThisMonthTransactionsByPatternLoadListener listener) {
        GetThisMonthTransactionByPatternAsync task = new GetThisMonthTransactionByPatternAsync(db, date, listener);
        task.execute();
    }

    public static void getLastFewDaysTransactions(BudgetDatabase db, String startDate, String endDate, ILastFewDaysTransactionsLoadListener listener) {
        GetLastFewDaysTransactionsAsync task = new GetLastFewDaysTransactionsAsync(db, startDate, endDate, listener);
        task.execute();
    }

    public static void getThisMonthTransactionByCategory(BudgetDatabase db, String date, IThisMonthTransactionsByCategoryLoadListener listener) {
        GetThisMonthTransactionByCategoryAsync task = new GetThisMonthTransactionByCategoryAsync(db, date, listener);
        task.execute();
    }

    public static void getThisMonthTransactionListByPattern(BudgetDatabase db, String date, String pattern, IThisMonthTransactionsLoadListener listener) {
        GetThisMonthTransactionListByPatternAsync task = new GetThisMonthTransactionListByPatternAsync(db, date, pattern, listener);
        task.execute();
    }

    public static void getThisMonthTransactionListByCategory(BudgetDatabase db, String date, String categoryId, IThisMonthTransactionsLoadListener listener) {
        GetThisMonthTransactionListByCategoryAsync task = new GetThisMonthTransactionListByCategoryAsync(db, date, categoryId, listener);
        task.execute();
    }

    public static void getThisMonthSumTransactionBySubCategory(BudgetDatabase db, String date, String categoryId, IThisMonthSumTransactionBySubCategoryListener listener) {
        GetThisMonthSumTransactionBySubCategoryAsync task = new GetThisMonthSumTransactionBySubCategoryAsync(db, date, categoryId, listener);
        task.execute();
    }

    public static void getSumAmountByBudget(BudgetDatabase db, String startDate, String endDate, String type, int accountId, ISumAmountByBudgetListener listener) {
        GetSumAmountByBudget task = new GetSumAmountByBudget(db, startDate, endDate, type, accountId, listener);
        task.execute();
    }

    public static void updateTransactionAsync(BudgetDatabase db, ITransactionUpdateListener listener, TransactionItem... transactionItems) {
        UpdateTransactionAsync task = new UpdateTransactionAsync(db, listener);
        task.execute(transactionItems);
    }

    /*
    ============================================================================
    ASYNC TASK DEFINE
    ============================================================================
     */
    private static class InsertTransactionAsync extends AsyncTask<TransactionItem, Void, Boolean> {

        BudgetDatabase mBudgetDatabase;
        ITransactionInsertListener mListener;

        public InsertTransactionAsync(BudgetDatabase mBudgetDatabase, ITransactionInsertListener mListener) {
            this.mBudgetDatabase = mBudgetDatabase;
            this.mListener = mListener;
        }

        @Override
        protected Boolean doInBackground(TransactionItem... transactionItems) {
            mBudgetDatabase.transactionDAO().insertTransactionUpdateAccount(transactionItems[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isInserted) {
            super.onPostExecute(isInserted);
            mListener.onTransactionInsertSuccess(isInserted);
        }
    }

    private static class GetAllTransactionAsync extends AsyncTask<Void, Void, List<TransactionItem>> {

        BudgetDatabase mBudgetDatabase;
        ITransactionsLoadListener mListener;

        public GetAllTransactionAsync(BudgetDatabase budgetDatabase, ITransactionsLoadListener listener) {
            mBudgetDatabase = budgetDatabase;
            mListener = listener;
        }

        @Override
        protected List<TransactionItem> doInBackground(Void... voids) {
            return mBudgetDatabase.transactionDAO().getAllTransactions();
        }

        @Override
        protected void onPostExecute(List<TransactionItem> transactionItemList) {
            super.onPostExecute(transactionItemList);
            mListener.onTransactionsLoadSuccess(transactionItemList);
        }
    }

    private static class GetThisMonthTransactionAsync extends AsyncTask<Void, Void, List<TransactionItem>> {

        BudgetDatabase mBudgetDatabase;
        String date;
        IThisMonthTransactionsLoadListener mListener;

        public GetThisMonthTransactionAsync(BudgetDatabase budgetDatabase, String date, IThisMonthTransactionsLoadListener listener) {
            mBudgetDatabase = budgetDatabase;
            this.date = date;
            mListener = listener;
        }

        @Override
        protected List<TransactionItem> doInBackground(Void... voids) {
            return mBudgetDatabase.transactionDAO().getThisMonthTransactions(date);
        }

        @Override
        protected void onPostExecute(List<TransactionItem> transactionItemList) {
            super.onPostExecute(transactionItemList);
            mListener.onThisMonthTransactionsLoadSuccess(transactionItemList);
        }
    }

    private static class GetThisMonthTransactionByPatternAsync extends AsyncTask<Void, Void, List<SpendingByPattern>> {

        BudgetDatabase mBudgetDatabase;
        String date;
        private IThisMonthTransactionsByPatternLoadListener mListener;

        public GetThisMonthTransactionByPatternAsync(BudgetDatabase budgetDatabase, String date, IThisMonthTransactionsByPatternLoadListener listener) {
            mBudgetDatabase = budgetDatabase;
            this.date = date;
            mListener = listener;
        }

        @Override
        protected List<SpendingByPattern> doInBackground(Void... voids) {
            return mBudgetDatabase.transactionDAO().getThisMonthTransactionsByPattern(date);
        }

        @Override
        protected void onPostExecute(List<SpendingByPattern> spendingPatterns) {
            super.onPostExecute(spendingPatterns);
            mListener.onThisMonthTransactionsByPatternLoadSuccess(spendingPatterns);
        }
    }

    private static class GetThisMonthTransactionsByAccountAsync extends AsyncTask<Void, Void, List<TransactionItem>> {

        BudgetDatabase mBudgetDatabase;
        String date;
        int accountId;
        private IThisMonthTransactionsLoadListener mListener;

        public GetThisMonthTransactionsByAccountAsync(BudgetDatabase mBudgetDatabase, String date, int accountId, IThisMonthTransactionsLoadListener mListener) {
            this.mBudgetDatabase = mBudgetDatabase;
            this.date = date;
            this.accountId = accountId;
            this.mListener = mListener;
        }

        @Override
        protected List<TransactionItem> doInBackground(Void... voids) {
            return mBudgetDatabase.transactionDAO().getThisMonthTransactionsByAccount(date, accountId);
        }

        @Override
        protected void onPostExecute(List<TransactionItem> transactionItemList) {
            super.onPostExecute(transactionItemList);
            mListener.onThisMonthTransactionsLoadSuccess(transactionItemList);
        }
    }

    private static class GetLastFewDaysTransactionsAsync extends AsyncTask<Void, Void, List<TransactionItem>> {

        BudgetDatabase mBudgetDatabase;
        String startDate;
        String endDate;
        ILastFewDaysTransactionsLoadListener mListener;

        public GetLastFewDaysTransactionsAsync(BudgetDatabase budgetDatabase, String startDate, String endDate, ILastFewDaysTransactionsLoadListener listener) {
            mBudgetDatabase = budgetDatabase;
            this.startDate = startDate;
            this.endDate = endDate;
            mListener = listener;
        }

        @Override
        protected List<TransactionItem> doInBackground(Void... voids) {
            return mBudgetDatabase.transactionDAO().getLastFewDaysTransactions(startDate, endDate);
        }

        @Override
        protected void onPostExecute(List<TransactionItem> transactionItemList) {
            super.onPostExecute(transactionItemList);
            mListener.onLastFewDaysTransactionsLoadSuccess(transactionItemList);
        }
    }

    private static class GetThisMonthTransactionByCategoryAsync extends AsyncTask<Void, Void, List<ExpenseByCategory>> {

        BudgetDatabase mBudgetDatabase;
        String date;
        IThisMonthTransactionsByCategoryLoadListener mListener;

        public GetThisMonthTransactionByCategoryAsync(BudgetDatabase budgetDatabase, String date, IThisMonthTransactionsByCategoryLoadListener listener) {
            mBudgetDatabase = budgetDatabase;
            this.date = date;
            mListener = listener;
        }

        @Override
        protected List<ExpenseByCategory> doInBackground(Void... voids) {
            return mBudgetDatabase.transactionDAO().getThisMonthTransactionsByCategory(date);
        }

        @Override
        protected void onPostExecute(List<ExpenseByCategory> expenseByCategoryList) {
            super.onPostExecute(expenseByCategoryList);
            mListener.onThisMonthTransactionByCategoryLoadSuccess(expenseByCategoryList);
        }
    }

    private static class GetThisMonthTransactionListByPatternAsync extends AsyncTask<Void, Void, List<TransactionItem>> {

        BudgetDatabase mBudgetDatabase;
        String date;
        String pattern;
        IThisMonthTransactionsLoadListener mListener;

        public GetThisMonthTransactionListByPatternAsync(BudgetDatabase budgetDatabase, String date, String pattern, IThisMonthTransactionsLoadListener listener) {
            mBudgetDatabase = budgetDatabase;
            this.date = date;
            this.pattern = pattern;
            mListener = listener;
        }

        @Override
        protected List<TransactionItem> doInBackground(Void... voids) {
            return mBudgetDatabase.transactionDAO().getThisMonthTransactionListByPattern(date, pattern);
        }

        @Override
        protected void onPostExecute(List<TransactionItem> transactionItemList) {
            super.onPostExecute(transactionItemList);
            mListener.onThisMonthTransactionsLoadSuccess(transactionItemList);
        }
    }

    private static class GetThisMonthTransactionListByCategoryAsync extends AsyncTask<Void, Void, List<TransactionItem>> {

        BudgetDatabase mBudgetDatabase;
        String date;
        String categoryId;
        IThisMonthTransactionsLoadListener mListener;

        public GetThisMonthTransactionListByCategoryAsync(BudgetDatabase budgetDatabase, String date, String categoryId, IThisMonthTransactionsLoadListener listener) {
            mBudgetDatabase = budgetDatabase;
            this.date = date;
            this.categoryId = categoryId;
            mListener = listener;
        }

        @Override
        protected List<TransactionItem> doInBackground(Void... voids) {
            return mBudgetDatabase.transactionDAO().getThisMonthTransactionListByCategory(date, categoryId);
        }

        @Override
        protected void onPostExecute(List<TransactionItem> transactionItemList) {
            super.onPostExecute(transactionItemList);
            mListener.onThisMonthTransactionsLoadSuccess(transactionItemList);
        }
    }

    private static class GetThisMonthSumTransactionBySubCategoryAsync extends AsyncTask<Void, Void, List<SumTransactionBySubCategory>> {

        BudgetDatabase mBudgetDatabase;
        String date;
        String categoryId;
        IThisMonthSumTransactionBySubCategoryListener mListener;

        public GetThisMonthSumTransactionBySubCategoryAsync(BudgetDatabase budgetDatabase, String date, String categoryId, IThisMonthSumTransactionBySubCategoryListener listener) {
            mBudgetDatabase = budgetDatabase;
            this.date = date;
            this.categoryId = categoryId;
            mListener = listener;
        }

        @Override
        protected List<SumTransactionBySubCategory> doInBackground(Void... voids) {
            return mBudgetDatabase.transactionDAO().getThisMonthSumTransactionBySubCategory(date, categoryId);
        }

        @Override
        protected void onPostExecute(List<SumTransactionBySubCategory> sumTransactionBySubCategoryList) {
            super.onPostExecute(sumTransactionBySubCategoryList);
            mListener.onThisMonthSumTransactionBySubCategorySuccess(sumTransactionBySubCategoryList);
        }
    }

    private static class GetSumAmountByBudget extends AsyncTask<Void, Void, Double> {

        BudgetDatabase mBudgetDatabase;
        String startDate;
        String endDate;
        String type;
        int accountId;
        ISumAmountByBudgetListener mListener;

        public GetSumAmountByBudget(BudgetDatabase budgetDatabase, String startDate, String endDate, String type, int accountId, ISumAmountByBudgetListener listener) {
            mBudgetDatabase = budgetDatabase;
            this.startDate = startDate;
            this.endDate = endDate;
            this.type = type;
            this.accountId = accountId;
            mListener = listener;
        }

        @Override
        protected Double doInBackground(Void... voids) {
            return mBudgetDatabase.transactionDAO().sumAmountByBudget(startDate, endDate, type, accountId);
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            mListener.onSumAmountByBudgeSuccess(aDouble);
        }
    }

    public static class UpdateTransactionAsync extends AsyncTask<TransactionItem, Void, Boolean> {

        BudgetDatabase db;
        ITransactionUpdateListener mListener;

        public UpdateTransactionAsync(BudgetDatabase db, ITransactionUpdateListener mListener) {
            this.db = db;
            this.mListener = mListener;
        }

        @Override
        protected Boolean doInBackground(TransactionItem... transactionItems) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mListener.onTransactionUpdateSuccess(aBoolean);
        }
    }
}

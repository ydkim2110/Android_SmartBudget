package com.example.smartbudget.Database.TransactionRoom;

import android.os.AsyncTask;

import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Interface.ILastFewDaysTransactionsLoadListener;
import com.example.smartbudget.Database.Interface.ISumAmountByBudgetListener;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsLoadListener;
import com.example.smartbudget.Database.Interface.ITransactionsLoadListener;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsByPatternLoadListener;
import com.example.smartbudget.Database.Model.ExpenseByCategory;
import com.example.smartbudget.Database.Model.SpendingByPattern;
import com.example.smartbudget.Database.BudgetDatabase;
import com.example.smartbudget.Database.Interface.IThisMonthTransactionsByCategoryLoadListener;
import com.example.smartbudget.Interface.IThisMonthTransactionLoadListener;
import com.example.smartbudget.Ui.Home.Spending.InvestFragment;


import java.util.List;

public class DBTransactionUtils {

    public static void getAllTransactions(BudgetDatabase db, ITransactionsLoadListener listener) {
        GetAllTransactionAsync task = new GetAllTransactionAsync(db, listener);
        task.execute();
    }

    public static void getThisMonthTransactions(BudgetDatabase db, String date, IThisMonthTransactionsLoadListener listener) {
        GetThisMonthTransactionAsync task = new GetThisMonthTransactionAsync(db, date, listener);
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
        GetThisMonthTransactionListByPattern task = new GetThisMonthTransactionListByPattern(db, date, pattern, listener);
        task.execute();
    }

    public static void getSumAmountByBudget(BudgetDatabase db, String startDate, String endDate, String type, int accountId, ISumAmountByBudgetListener listener) {
        GetSumAmountByBudget task = new GetSumAmountByBudget(db, startDate, endDate, type, accountId, listener);
        task.execute();
    }

    /*
    ============================================================================
    ASYNC TASK DEFINE
    ============================================================================
     */

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

    private static class GetThisMonthTransactionListByPattern extends AsyncTask<Void, Void, List<TransactionItem>> {

        BudgetDatabase mBudgetDatabase;
        String date;
        String pattern;
        IThisMonthTransactionsLoadListener mListener;

        public GetThisMonthTransactionListByPattern(BudgetDatabase budgetDatabase, String date, String pattern, IThisMonthTransactionsLoadListener listener) {
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
}

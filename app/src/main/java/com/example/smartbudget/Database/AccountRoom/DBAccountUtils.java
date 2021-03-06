package com.example.smartbudget.Database.AccountRoom;

import android.os.AsyncTask;
import android.util.Log;

import com.example.smartbudget.Database.BudgetDatabase;
import com.example.smartbudget.Database.DBHelper;
import com.example.smartbudget.Database.DatabaseUtils;
import com.example.smartbudget.Database.Interface.IAccountDeleteListener;
import com.example.smartbudget.Database.Interface.IAccountInsertListener;
import com.example.smartbudget.Database.Interface.IAccountLoadListener;
import com.example.smartbudget.Database.Interface.IAccountUpdateListener;
import com.example.smartbudget.Database.Interface.IAccountsLoadListener;
import com.example.smartbudget.Database.Interface.IBudgetInsertListener;
import com.example.smartbudget.Database.Interface.IDBUpdateListener;
import com.example.smartbudget.Interface.IUpdateAccountListener;
import com.example.smartbudget.Model.AccountModel;

import java.util.List;

public class DBAccountUtils {

    public static void getAccount(BudgetDatabase db, int id, IAccountLoadListener listener) {
        GetAccountAsync task = new GetAccountAsync(db, id, listener);
        task.execute();
    }

    public static void getAllAccounts(BudgetDatabase db, IAccountsLoadListener listener) {
        GetAllAccountsAsync task = new GetAllAccountsAsync(db, listener);
        task.execute();
    }

    public static void getAccountsByType(BudgetDatabase db, String type, IAccountsLoadListener listener) {
        GetAccountsByTypeAsync task = new GetAccountsByTypeAsync(db, type, listener);
        task.execute();
    }

    public static void getSumAccountsByType(BudgetDatabase db, IAccountsLoadListener listener) {
        GetSumAccountsByTypeAsync task = new GetSumAccountsByTypeAsync(db, listener);
        task.execute();
    }

    public static void getSumAccountsByHigCategory(BudgetDatabase db, IAccountsLoadListener listener) {
        GetSumAccountsByHighCategoryAsync task = new GetSumAccountsByHighCategoryAsync(db, listener);
        task.execute();
    }

    public static void insertAccountAsync(BudgetDatabase db, IAccountInsertListener listener, AccountItem... accountItems) {
        InsertAccountAsync task = new InsertAccountAsync(db, listener);
        task.execute(accountItems);
    }

    public static void updateAccountAsync(BudgetDatabase db, IAccountUpdateListener listener, AccountItem... accountItems) {
        UpdateAccountAsync task = new UpdateAccountAsync(db, listener);
        task.execute(accountItems);
    }

    public static void updateAccountsByTransfer(BudgetDatabase db, double amount, int fromId, int toId, IUpdateAccountListener listener) {
        UpdateAccountsByTransferAsync task = new UpdateAccountsByTransferAsync(db, amount, fromId, toId, listener);
        task.execute();
    }

    public static void deleteAccountAsync(BudgetDatabase db, IAccountDeleteListener listener, AccountItem... accountItems) {
        DeleteAccountAsync task = new DeleteAccountAsync(db, listener);
        task.execute(accountItems);
    }

    /*
    ============================================================================
    ASYNC TASK DEFINE
    ============================================================================
     */
    private static class GetAccountAsync extends AsyncTask<Void, Void, AccountItem> {

        BudgetDatabase db;
        int id;
        IAccountLoadListener mListener;

        public GetAccountAsync(BudgetDatabase db, int id, IAccountLoadListener listener) {
            this.db = db;
            this.id = id;
            mListener = listener;
        }

        @Override
        protected AccountItem doInBackground(Void... voids) {
            return db.accountDAO().getAccount(id);
        }

        @Override
        protected void onPostExecute(AccountItem accountItem) {
            super.onPostExecute(accountItem);
            mListener.onAccountLoadSuccess(accountItem);
        }
    }

    private static class GetAllAccountsAsync extends AsyncTask<Void, Void, List<AccountItem>> {

        BudgetDatabase db;
        IAccountsLoadListener mListener;

        public GetAllAccountsAsync(BudgetDatabase db, IAccountsLoadListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected List<AccountItem> doInBackground(Void... voids) {
            return db.accountDAO().getAllAccounts();
        }


        @Override
        protected void onPostExecute(List<AccountItem> accountItemList) {
            super.onPostExecute(accountItemList);
            mListener.onAccountsLoadSuccess(accountItemList);
        }
    }

    private static class GetAccountsByTypeAsync extends AsyncTask<Void, Void, List<AccountItem>> {

        BudgetDatabase db;
        String type;
        IAccountsLoadListener mListener;

        public GetAccountsByTypeAsync(BudgetDatabase db, String type, IAccountsLoadListener listener) {
            this.db = db;
            this.type = type;
            mListener = listener;
        }

        @Override
        protected List<AccountItem> doInBackground(Void... voids) {
            return db.accountDAO().getAccountsByType(type);
        }

        @Override
        protected void onPostExecute(List<AccountItem> accountItemList) {
            super.onPostExecute(accountItemList);
            mListener.onAccountsLoadSuccess(accountItemList);
        }
    }

    private static class GetSumAccountsByTypeAsync extends AsyncTask<Void, Void, List<AccountItem>> {

        BudgetDatabase db;
        IAccountsLoadListener mListener;

        public GetSumAccountsByTypeAsync(BudgetDatabase db, IAccountsLoadListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected List<AccountItem> doInBackground(Void... voids) {
            return db.accountDAO().getSumAccountsByType();
        }

        @Override
        protected void onPostExecute(List<AccountItem> accountItemList) {
            super.onPostExecute(accountItemList);
            mListener.onAccountsLoadSuccess(accountItemList);
        }
    }

    private static class GetSumAccountsByHighCategoryAsync extends AsyncTask<Void, Void, List<AccountItem>> {

        BudgetDatabase db;
        IAccountsLoadListener mListener;

        public GetSumAccountsByHighCategoryAsync(BudgetDatabase db, IAccountsLoadListener listener) {
            this.db = db;
            mListener = listener;
        }

        @Override
        protected List<AccountItem> doInBackground(Void... voids) {
            return db.accountDAO().getSumAccountsByHighCategory();
        }

        @Override
        protected void onPostExecute(List<AccountItem> accountItemList) {
            super.onPostExecute(accountItemList);
            mListener.onAccountsLoadSuccess(accountItemList);
        }
    }

    private static class InsertAccountAsync extends AsyncTask<AccountItem, Void, Boolean> {

        BudgetDatabase mBudgetDatabase;
        IAccountInsertListener mListener;

        public InsertAccountAsync(BudgetDatabase budgetDatabase, IAccountInsertListener listener) {
            this.mBudgetDatabase = budgetDatabase;
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(AccountItem... accountItems) {
            mBudgetDatabase.accountDAO().insertAccount(accountItems[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isInserted) {
            super.onPostExecute(isInserted);
            mListener.onAccountInsertSuccess(isInserted);
        }
    }

    public static class UpdateAccountAsync extends AsyncTask<AccountItem, Void, Boolean> {

        BudgetDatabase mBudgetDatabase;
        IAccountUpdateListener mListener;

        public UpdateAccountAsync(BudgetDatabase budgetDatabase, IAccountUpdateListener listener) {
            this.mBudgetDatabase = budgetDatabase;
            this.mListener = listener;
        }

        @Override
        protected Boolean doInBackground(AccountItem... accountItems) {
            mBudgetDatabase.accountDAO().updateAccount(accountItems[0]);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isUpdated) {
            super.onPostExecute(isUpdated);
            mListener.onAccountUpdateSuccess(isUpdated);
        }
    }

    private static class UpdateAccountsByTransferAsync extends AsyncTask<Void, Void, Boolean> {

        BudgetDatabase mBudgetDatabase;
        IUpdateAccountListener mListener;
        double amount;
        int fromId;
        int toId;

        public UpdateAccountsByTransferAsync(BudgetDatabase budgetDatabase, double amount, int fromId, int toId, IUpdateAccountListener listener) {
            this.mBudgetDatabase = budgetDatabase;
            this.amount = amount;
            this.fromId = fromId;
            this.toId = toId;
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            mBudgetDatabase.accountDAO().updateAccountByTransfer(amount, fromId, toId);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mListener.onUpdateAccountSuccess(aBoolean);
        }
    }


    private static class DeleteAccountAsync extends AsyncTask<AccountItem, Void, Boolean> {

        BudgetDatabase mBudgetDatabase;
        IAccountDeleteListener mListener;

        public DeleteAccountAsync(BudgetDatabase budgetDatabase, IAccountDeleteListener listener) {
            mBudgetDatabase = budgetDatabase;
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(AccountItem... accountItems) {
            try {
                long result = mBudgetDatabase.accountDAO().deleteAccount(accountItems[0]);
                return result == 1;
            } catch (Exception e) {

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mListener.onAccountDeleteSuccess(aBoolean);
        }
    }
}

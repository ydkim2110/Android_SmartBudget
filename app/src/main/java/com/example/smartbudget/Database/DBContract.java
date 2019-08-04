package com.example.smartbudget.Database;

import android.provider.BaseColumns;

public class DBContract {

    private DBContract() {
    }

    public static class Account implements BaseColumns {
        public static final String TABLE_NAME = "account_table";
        public static final String COL_NAME = "name";
        public static final String COL_DESCRIPTION = "description";
        public static final String COL_AMOUNT = "amount";
        public static final String COL_HIGH_CATEGORY = "high_category";
        public static final String COL_TYPE = "type";
        public static final String COL_CREATE_AT = "create_at";
        public static final String COL_CURRENCY = "currency";
    }

    public static class Category implements BaseColumns {
        public static final String TABLE_NAME = "category_table";
        public static final String COL_NAME = "category_name";
        public static final String COL_ICON = "category_icon";
    }

    public static class SubCategory implements BaseColumns {
        public static final String TABLE_NAME = "subcategory_table";
        public static final String COL_NAME = "subcategory_name";
        public static final String COL_ICON = "subcategory_icon";
        public static final String COL_CATEGORY_ID = "category_id";
    }

    public static class Budget implements BaseColumns {
        public static final String TABLE_NAME = "budget_table";
        public static final String COL_NAME = "category_name";
    }

    public static class Transaction implements BaseColumns {
        public static final String TABLE_NAME = "transaction_table";
        public static final String COL_NOTE = "transaction_note";
        public static final String COL_AMOUNT = "transaction_amount";
        public static final String COL_TYPE = "transaction_type";
        public static final String COL_PATTERN = "transaction_pattern";
        public static final String COL_DATE = "transaction_date";
        public static final String COL_CATEGORY_ID = "category_id";
        public static final String COL_SUB_CATEGORY_ID = "sub_category_id";
        public static final String COL_ACCOUNT_ID = "account_id";
        public static final String COL_TO_ACCOUNT = "to_account";
    }
}

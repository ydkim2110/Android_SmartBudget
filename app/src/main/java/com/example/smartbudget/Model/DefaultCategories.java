package com.example.smartbudget.Model;

import android.graphics.Color;

import com.example.smartbudget.R;

public class DefaultCategories {

    private static Category[] expenseCategories = new Category[] {
            new Category(":food&drink", "식사/음료", R.drawable.category_food, Color.parseColor("#c2185b")),
            new Category(":utility", "주거", R.drawable.category_home, Color.parseColor("#0288d1")),
            new Category(":communication", "통신", R.drawable.ic_phone_android_black_24dp, Color.parseColor("#455a64")),
            new Category(":homeneeds", "생활용품", R.drawable.category_shopping, Color.parseColor("#00796b")),
            new Category(":clothing&beauty", "의복/미용", R.drawable.category_clothing, Color.parseColor("#d32f2f")),
            new Category(":education", "교육", R.drawable.category_gas_station, Color.parseColor("#7b1fa2")),
            new Category(":entertainment", "엔터테인먼트", R.drawable.category_gaming, Color.parseColor("#512da8")),
            new Category(":healthcare", "건강", R.drawable.category_pharmacy, Color.parseColor("#afb42b")),
            new Category(":gifts", "선물/기부", R.drawable.category_gift, Color.parseColor("#303f9f")),
            new Category(":transportation", "교통", R.drawable.category_transport, Color.parseColor("#ffa000")),
            new Category(":travel", "여행", R.drawable.category_holidays, Color.parseColor("#1976d2")),
            new Category(":taxes", "세금", R.drawable.category_default, Color.parseColor("#0097a7")),
            new Category(":other_expense", "기타비용", R.drawable.category_default, Color.parseColor("#689f38")),
    };

    private static SubCategory[] expenseSubCategories = new SubCategory[] {
            new SubCategory(":breakfast", "아침", R.drawable.category_food, Color.parseColor("#c2185b"), ":food&drink"),
            new SubCategory(":lunch", "점심", R.drawable.category_food, Color.parseColor("#c2185b"), ":food&drink"),
            new SubCategory(":dinner", "저녁", R.drawable.category_food, Color.parseColor("#c2185b"), ":food&drink"),
            new SubCategory(":grocery", "식료품", R.drawable.category_food, Color.parseColor("#c2185b"), ":food&drink"),
            new SubCategory(":snack", "간식", R.drawable.category_food, Color.parseColor("#c2185b"), ":food&drink"),
            new SubCategory(":coffee&beberage", "커피/음료", R.drawable.category_food, Color.parseColor("#c2185b"), ":food&drink"),
            new SubCategory(":other", "기타", R.drawable.category_food, Color.parseColor("#c2185b"), ":food&drink"),
            new SubCategory(":maintenance_charge", "관리비", R.drawable.category_home, Color.parseColor("#0288d1"), ":utility"),
            new SubCategory(":electricity_bill", "전기요금", R.drawable.category_home, Color.parseColor("#0288d1"), ":utility"),
            new SubCategory(":water_bill", "수도요금", R.drawable.category_home, Color.parseColor("#0288d1"), ":utility"),
            new SubCategory(":gas_bill", "가스요금", R.drawable.category_home, Color.parseColor("#0288d1"), ":utility"),
            new SubCategory(":mobile", "모바일", R.drawable.ic_phone_android_black_24dp, Color.parseColor("#455a64"), ":communication"),
            new SubCategory(":internet&tv", "인터넷&TV", R.drawable.ic_phone_android_black_24dp, Color.parseColor("#455a64"), ":communication"),
            new SubCategory(":other", "기타", R.drawable.ic_phone_android_black_24dp, Color.parseColor("#455a64"), ":communication"),
            new SubCategory(":furniture&appliances", "가구/가전", R.drawable.category_shopping, Color.parseColor("#00796b"), ":homeneeds"),
            new SubCategory(":kitchen_products", "주방용품", R.drawable.category_shopping, Color.parseColor("#00796b"), ":homeneeds"),
            new SubCategory(":washroom_products", "욕실용품", R.drawable.category_shopping, Color.parseColor("#00796b"), ":homeneeds"),
            new SubCategory(":miscellaneous", "잡화", R.drawable.category_shopping, Color.parseColor("#00796b"), ":homeneeds"),
            new SubCategory(":clothes", "의복", R.drawable.category_clothing, Color.parseColor("#d32f2f"), ":clothing&beauty"),
            new SubCategory(":accessory", "액세서리", R.drawable.category_clothing, Color.parseColor("#d32f2f"), ":clothing&beauty"),
            new SubCategory(":shoes", "신발", R.drawable.category_clothing, Color.parseColor("#d32f2f"), ":clothing&beauty"),
            new SubCategory(":hair", "미용", R.drawable.category_clothing, Color.parseColor("#d32f2f"), ":clothing&beauty"),
            new SubCategory(":beauty", "뷰티", R.drawable.category_clothing, Color.parseColor("#d32f2f"), ":clothing&beauty"),
            new SubCategory(":laundry", "세탁/수선", R.drawable.category_clothing, Color.parseColor("#d32f2f"), ":clothing&beauty"),
            new SubCategory(":other", "기타", R.drawable.category_clothing, Color.parseColor("#d32f2f"), ":clothing&beauty"),
            new SubCategory(":academy", "학원", R.drawable.category_gas_station, Color.parseColor("#7b1fa2"), ":education"),
            new SubCategory(":book", "도서", R.drawable.category_gas_station, Color.parseColor("#7b1fa2"), ":education"),
            new SubCategory(":lecture", "강의", R.drawable.category_gas_station, Color.parseColor("#7b1fa2"), ":education"),
            new SubCategory(":other", "기타", R.drawable.category_gas_station, Color.parseColor("#7b1fa2"), ":education"),
            new SubCategory(":movie", "영화", R.drawable.category_gaming, Color.parseColor("#512da8"), ":entertainment"),
            new SubCategory(":game", "게임", R.drawable.category_gaming, Color.parseColor("#512da8"), ":entertainment"),
            new SubCategory(":performance", "공연", R.drawable.category_gaming, Color.parseColor("#512da8"), ":entertainment"),
            new SubCategory(":other", "기타", R.drawable.category_gaming, Color.parseColor("#512da8"), ":entertainment"),
            new SubCategory(":hospital", "병원", R.drawable.category_pharmacy, Color.parseColor("#afb42b"), ":healthcare"),
            new SubCategory(":drug", "약", R.drawable.category_pharmacy, Color.parseColor("#afb42b"), ":healthcare"),
            new SubCategory(":sports", "스포츠", R.drawable.category_pharmacy, Color.parseColor("#afb42b"), ":healthcare"),
            new SubCategory(":other", "기타", R.drawable.category_pharmacy, Color.parseColor("#afb42b"), ":healthcare"),
            new SubCategory(":gift", "선물", R.drawable.category_gift, Color.parseColor("#303f9f"), ":gifts"),
            new SubCategory(":marriage", "결혼", R.drawable.category_gift, Color.parseColor("#303f9f"), ":gifts"),
            new SubCategory(":funeral", "장례식", R.drawable.category_gift, Color.parseColor("#303f9f"), ":gifts"),
            new SubCategory(":donation", "기부", R.drawable.category_gift, Color.parseColor("#1976d2"), ":gifts"),
            new SubCategory(":other", "기타", R.drawable.category_gift, Color.parseColor("#303f9f"), ":gifts"),
            new SubCategory(":bus", "버스", R.drawable.category_transport, Color.parseColor("#ffa000"), ":transportation"),
            new SubCategory(":taxi", "택시", R.drawable.category_transport, Color.parseColor("#ffa000"), ":transportation"),
            new SubCategory(":metro", "지하철", R.drawable.category_transport, Color.parseColor("#ffa000"), ":transportation"),
            new SubCategory(":other", "기타", R.drawable.category_transport, Color.parseColor("#ffa000"), ":transportation"),
    };

    private static Category[] incomeCategories = new Category[] {
            new Category(":salary", "월급", R.drawable.category_food, Color.parseColor("#c2185b")),
            new Category(":interest&dividend", "이자/배당", R.drawable.category_home, Color.parseColor("#0288d1")),
            new Category(":bonus", "보너스", R.drawable.ic_phone_android_black_24dp, Color.parseColor("#455a64")),
            new Category(":sale", "판매", R.drawable.category_clothing, Color.parseColor("#d32f2f")),
            new Category(":refund", "환급", R.drawable.category_gas_station, Color.parseColor("#7b1fa2")),
            new Category(":other_income", "기타수입", R.drawable.category_pharmacy, Color.parseColor("#afb42b")),
    };

    public static Category[] getDefaultExpenseCategories() {
        return  expenseCategories;
    }
    public static Category[] getDefaultIncomeCategories() {
        return incomeCategories;
    }
    public static SubCategory[] getDefaultSubExpenseCategories() {
        return expenseSubCategories;
    }

}

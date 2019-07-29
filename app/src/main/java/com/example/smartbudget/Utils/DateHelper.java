package com.example.smartbudget.Utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DateHelper {

    private static LocalDate today = LocalDate.now();
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static Date getWeekStartDate() {
        LocalDate monday = today;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        return Date.from(monday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getWeekEndDate() {
        LocalDate sunday = today;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.plusDays(1);
        }
        return Date.from(sunday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date changeStringToDate(String date) {
        Date tempDate = null;
        try {
            tempDate = dateTimeFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            tempDate = new Date();
        }
        return tempDate;
    }

    public static String changeDateToString(Date date) {
        return dateTimeFormat.format(date);
    }

}

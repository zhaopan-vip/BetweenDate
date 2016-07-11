package com.qdota.betweendate;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by zhaopan on 16/7/11.
 */

public class Utility {
    public static class BetweenDate {
        public int year;
        public int month;
        public int day;
    }
    public static BetweenDate calcBetweenDate(GregorianCalendar c1, GregorianCalendar c2) {
        GregorianCalendar date1 = c1;
        GregorianCalendar date2 = c2;
        // keep date1 <= date2
        if (c1.after(c2)) {
            date1 = c2;
            date2 = c1;
        }
        int year1 = date1.get(Calendar.YEAR);
        int month1 = date1.get(Calendar.MONTH);
        int day1 = date1.get(Calendar.DAY_OF_MONTH);
        int year2 = date2.get(Calendar.YEAR);
        int month2 = date2.get(Calendar.MONTH);
        int day2 = date2.get(Calendar.DAY_OF_MONTH);

        BetweenDate bd = new BetweenDate();
        bd.year = year2 - year1;
        bd.month = month2 - month1;
        bd.day = day2 - day1;
        if (bd.month < 0) {
            bd.year -= 1;
            bd.month += 12;
        }
        if (bd.day < 0) {
            // eg. 2015.11.30 - 2015.12.17
            bd.month -= 1;
            if (bd.month < 0) {
                // eg. 2015.1.31 - 2016.1.10
                bd.year -= 1;
                bd.month += 12;
            }
            // calc the day from (month2-1)/day1 to (month2)/day2

            // notice that dayFrom may be invalid date in monthFrom, we need check
            GregorianCalendar checkDate = new GregorianCalendar(year2, month2, 1);
            checkDate.setTimeInMillis(checkDate.getTimeInMillis() - 24 * 3600 * 1000);
            int checkDay = checkDate.get(Calendar.DAY_OF_MONTH);
            // so we can calculate days by day1 to checkDay & 1 - day2
            bd.day = (checkDay > day1? (checkDay - day1): 0) + day2;
        }
        return bd;
    }
}

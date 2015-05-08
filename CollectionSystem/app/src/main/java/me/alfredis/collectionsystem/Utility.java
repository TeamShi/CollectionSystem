package me.alfredis.collectionsystem;

import java.util.Calendar;

/**
 * Created by Alfred on 15/5/8.
 */
public class Utility {
    public static String formatCalendarDateString(Calendar c) {
        return c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
    }
}

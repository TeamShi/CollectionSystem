package me.alfredis.collectionsystem;

import java.util.Calendar;

import me.alfredis.collectionsystem.datastructure.Hole;

/**
 * Created by Alfred on 15/5/8.
 */
public class Utility {
    public static String formatCalendarDateString(Calendar c) {
        return c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
    }

    public static int getProjectStageIndex(Hole.ProjectStageType ps) {
        switch (ps) {
            case I:
                return 0;
            case II:
                return 1;
            case III:
                return 2;
            case IV:
                return 3;
            default:
                return -1;
        }
    }

    public static int getProjectIdPart3Index(String projectPart3) {
        return Integer.valueOf(projectPart3) - 1;
    }
}

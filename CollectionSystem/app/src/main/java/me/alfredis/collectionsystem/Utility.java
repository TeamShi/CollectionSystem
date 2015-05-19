package me.alfredis.collectionsystem;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.alfredis.collectionsystem.datastructure.Hole;

/**
 * Created by Alfred on 15/5/8.
 */
public class Utility {

    public static String formatCalendarDateString(Calendar calendar ,String simpleDateFormat) {
        SimpleDateFormat fmt = new SimpleDateFormat(simpleDateFormat);// example "yyyy年MM月dd日"
        String dateStr = fmt.format(calendar.getTime());
        return dateStr;
    }

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

    public static int getHoleIdPart1Index(Hole.HoleIdPart1Type holeIdPart1) {
        switch (holeIdPart1) {
            case JC:
                return 0;
            case JZ:
                return 1;
            default:
                return -1;
        }
    }

    public static int getArticleIndex(Hole.ArticleType articleType) {
        switch (articleType) {
            case K:
                return 0;
            case DK:
                return 1;
            case AK:
                return 2;
            case ACK:
                return 3;
            case CDK:
                return 4;
            default:
                return -1;
        }
    }

    public static String[] convert2Array(String string) {
        String[] row = string.split(",");
        for (int j = 0, colLen = row.length; j < colLen; j++) {
            System.out.println(row[j]);
            if (row[j].equals("null")) {
                row[j] = "";
            }
        }
        return row;
    }

    public static String formatTimeString(Calendar c) {

    }

}

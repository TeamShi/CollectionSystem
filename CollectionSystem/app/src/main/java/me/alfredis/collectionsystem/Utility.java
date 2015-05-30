package me.alfredis.collectionsystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public static Calendar getCalendarFromDateString(String dateString,String simpleDateFormat) {
        SimpleDateFormat fmt = new SimpleDateFormat(simpleDateFormat);
        Calendar  c = Calendar.getInstance();
        Date date = null;
        try {
           date =  fmt.parse(dateString);
            c.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  c;
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
//            System.out.println(row[j]);
            if (row[j].equals("null")) {
                row[j] = "";
            }
        }
        return row;
    }

    public static String formatTimeString(Calendar c) {
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        if (minute < 10) {
            return hour + ":0" + minute;
        } else {
            return hour + ":" + minute;
        }
    }

    public static void copyFile(InputStream is, File dest) throws IOException {
        FileOutputStream fos = new FileOutputStream(dest);
        byte[] buffer = new byte[1024];
        int count = 0;
        while (true) {
            count++;
            int len = is.read(buffer);
            if (len == -1) {
                break;
            }
            fos.write(buffer, 0, len);
        }
        is.close();
        fos.close();
    }

    public static String calculateTimeSpan(Calendar c1, Calendar c2) {
        int hour1 = c1.get(Calendar.HOUR_OF_DAY);
        int hour2 = c2.get(Calendar.HOUR_OF_DAY);
        int minute1 = c1.get(Calendar.MINUTE);
        int minute2 = c2.get(Calendar.MINUTE);

        int answerHour = 0;
        int answerMinute = 0;

        if (minute2 < minute1) {
            answerMinute = minute2 + 60 - minute1;
            answerHour = hour2 - hour1;
        } else {
            answerHour = hour2 - hour1;
            answerMinute = minute2 - minute1;
        }

        if (answerMinute < 10) {
            return answerHour + ":0" + answerMinute;
        } else {
            return answerHour + ":" + answerMinute;
        }
    }

}

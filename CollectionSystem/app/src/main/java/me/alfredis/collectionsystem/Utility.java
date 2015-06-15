package me.alfredis.collectionsystem;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
            answerHour = hour2 - hour1 - 1;
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

    /**
     * 递归压缩文件夹
     *
     * @param srcRootDir 压缩文件夹根目录的子路径
     * @param file       当前递归压缩的文件或目录对象
     * @param zos        压缩文件存储对象
     * @throws Exception
     */
    private static void zip(String srcRootDir, File file, ZipOutputStream zos) throws Exception {
        if (file == null) {
            return;
        }

        //如果是文件，则直接压缩该文件
        if (file.isFile()) {
            int count, bufferLen = 1024;
            byte data[] = new byte[bufferLen];

            //获取文件相对于压缩文件夹根目录的子路径
            String subPath = file.getAbsolutePath();
            int index = subPath.indexOf(srcRootDir);
            if (index != -1) {
                subPath = subPath.substring(srcRootDir.length() + File.separator.length());
            }
            ZipEntry entry = new ZipEntry(subPath);
            zos.putNextEntry(entry);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            while ((count = bis.read(data, 0, bufferLen)) != -1) {
                zos.write(data, 0, count);
            }
            bis.close();
            zos.closeEntry();
        } else {
            //如果是目录，则压缩整个目录
            //压缩目录中的文件或子目录
            File[] childFileList = file.listFiles();
            for (int n = 0; n < childFileList.length; n++) {

                childFileList[n].getAbsolutePath().indexOf(file.getAbsolutePath());
                zip(srcRootDir, childFileList[n], zos);
            }
        }
    }

    /**
     * 对文件或文件目录进行压缩
     *
     * @param srcPath     要压缩的源文件路径。如果压缩一个文件，则为该文件的全路径；如果压缩一个目录，则为该目录的顶层目录路径
     * @param zipPath     压缩文件保存的路径。注意：zipPath不能是srcPath路径下的子文件夹
     * @param zipFileName 压缩文件名
     * @throws Exception
     */
    public static void zip(String srcPath, String zipPath, String zipFileName) throws Exception {
        CheckedOutputStream cos = null;
        ZipOutputStream zos = null;
        try {
            File srcFile = new File(srcPath);

            //判断压缩文件保存的路径是否存在，如果不存在，则创建目录
            File zipDir = new File(zipPath);
            if (!zipDir.exists() || !zipDir.isDirectory()) {
                zipDir.mkdirs();
            }

            //创建压缩文件保存的文件对象
            String zipFilePath = zipPath + File.separator + zipFileName;
            File zipFile = new File(zipFilePath);
            if (zipFile.exists()) {
                //检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
                SecurityManager securityManager = new SecurityManager();
                securityManager.checkDelete(zipFilePath);
                //删除已存在的目标文件
                zipFile.delete();
            }

            cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
            zos = new ZipOutputStream(cos);

            //如果只是压缩一个文件，则需要截取该文件的父目录
            String srcRootDir = srcPath;
            if (srcFile.isFile()) {
                int index = srcPath.lastIndexOf(File.separator);
                if (index != -1) {
                    srcRootDir = srcPath.substring(0, index);
                }
            }
            //调用递归压缩方法进行目录或文件压缩
            zip(srcRootDir, srcFile, zos);
            zos.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public  static  Double getLastNumber(String input){
        String[] parts = input.split("(?=\\d+(\\.\\d{1,2})?$)", 2);
        double num = Double.valueOf(parts[1]);
        return num;
    }

    public static String decodeChar(char c) {
        switch (c) {
            case 'X':
                return "0";
            case 'A':
                return "1";
            case 'Z':
                return "2";
            case 'F':
                return "3";
            case 'D':
                return "4";
            case 'G':
                return "5";
            case 'H':
                return "6";
            case 'T':
                return "7";
            case 'R':
                return "8";
            case 'L':
                return "9";
            default:
                return "";
        }
    }

    public static long getExpiredDate(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(decodeChar(s.charAt(i)));
        }

        String temp = sb.toString();

        StringBuilder sb2 = new StringBuilder();

        for (int i = 0; i < sb.length(); i++) {
            sb2.append(temp.charAt(5));
            sb2.append(temp.charAt(8));
            sb2.append(temp.charAt(0));
            sb2.append(temp.charAt(6));
            sb2.append(temp.charAt(3));
            sb2.append(temp.charAt(1));
            sb2.append(temp.charAt(4));
            sb2.append(temp.charAt(7));
            sb2.append(temp.charAt(2));
            sb2.append(temp.charAt(9));
        }
        return Long.parseLong(sb.toString());
    }


    /**
     * 取得两个时间段的时间间隔
     * return t2 与t1的间隔天数
     * throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常
     */
    public static int getIntervalDays(Calendar start,Calendar end) throws ParseException{
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int betweenDays = 0;
//        Date d1 = format.parse(t1);
//        Date d2 = format.parse(t2);
//        Calendar c1 = Calendar.getInstance();
//        Calendar c2 = Calendar.getInstance();
//        c1.setTime(d1);
//        c2.setTime(d2);
//        // 保证第二个时间一定大于第一个时间
//        if(c1.after(c2)){
//            c1 = c2;
//            c2.setTime(d1);
//        }
        int betweenYears = end.get(Calendar.YEAR)-start.get(Calendar.YEAR);
        betweenDays = end.get(Calendar.DAY_OF_YEAR)-start.get(Calendar.DAY_OF_YEAR);
        for(int i=0;i<betweenYears;i++){
            int tmp=countDays(start.get(Calendar.YEAR));
            betweenDays+=countDays(start.get(Calendar.YEAR));
            start.set(Calendar.YEAR, (start.get(Calendar.YEAR) + 1));
        }
        return betweenDays;
    }
    public static int countDays(int year){
        int n=0;
        for (int i = 1; i <= 12; i++) {
            n += countDays(i,year);
        }
        return n;
    }


    public static int countDays(int month, int year){
        int count = -1;
        switch(month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                count = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                count = 30;
                break;
            case 2:
                if(year % 4 == 0)
                    count = 29;
                else
                    count = 28;
                if((year % 100 ==0) & (year % 400 != 0))
                    count = 28;
        }
        return count;
    }

}

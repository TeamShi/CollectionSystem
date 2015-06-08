package me.alfredis.collectionsystem.parser;

import android.content.res.AssetManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import me.alfredis.collectionsystem.datastructure.DSTRig;
import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.datastructure.SPTRig;

import static me.alfredis.collectionsystem.Utility.convert2Array;
import static me.alfredis.collectionsystem.Utility.formatCalendarDateString;

/**
 * Created by jishshi on 2015/5/10.
 */
public class HtmlParser {

    public static String BASIC_RIG_EVENT_TEMPLATE = "RigEventTable.html";
    public static String SPT_RIG_EVENT_TEMPLATE = "SPTRigEventTable.html";
    public static String DST_RIG_EVENT_TEMPLATE = "DSTRigEventTable.html";

    public static String TBODY_ID = "tableBody";

    public static boolean write(String outPath, String[][] data, InputStream inputStream) throws IOException {
        String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
        if (!fileType.equals("html")) {
            System.out.println("您的文档格式不正确(非html)！");
            return false;
        }

        //读模版文件
        Document doc = Jsoup.parse(inputStream, "UTF-8","./");
        Element tbody = doc.getElementById(TBODY_ID);

        // 循环写入行数据
        for (int i = 0, rows = data.length; i < rows; i++) {
            StringBuffer row = new StringBuffer();
            row.append("<tr>");
            // 循环写入列数据
            for (int j = 0, cols = data[i].length; j < cols; j++) {
                row.append("<td>");
                String text = data[i][j].equals("null") ? "" : data[i][j];
                row.append(text);
                row.append("</td>");
            }
            row.append("</tr>");
            tbody.append(row.toString());
        }

        FileWriter fileWriter = new FileWriter(outPath);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(doc.outerHtml());
        bufferedWriter.close();
        fileWriter.close();

        return true;

    }

    public static boolean parse(String dirPath, ArrayList<Hole> holes, AssetManager assetManager) {
        ArrayList<RigEvent> rigEvents = new ArrayList<RigEvent>();
        ArrayList<SPTRig> sptRigEvents = new ArrayList<SPTRig>();
        ArrayList<DSTRig> dstRigEvents = new ArrayList<DSTRig>();

        for (int i = 0, len = holes.size(); i < len; i++) {
            ArrayList<RigEvent> currRigEvents = holes.get(i).getRigList();
            for (int j = 0, size = currRigEvents.size(); j < size; j++) {
                RigEvent currRigEvent = rigEvents.get(j);
                if (currRigEvent instanceof SPTRig) {
                    sptRigEvents.add((SPTRig) currRigEvent);
                } else if (rigEvents.get(j) instanceof DSTRig) {
                    dstRigEvents.add((DSTRig) currRigEvent);
                } else {
                    rigEvents.add(currRigEvent);
                }
            }
        }


        //html output
        String[][] rigEventArray = convert(rigEvents);
        String[][] sptRigEventArray = convertSpt(sptRigEvents);
        String[][] dstRigEventArray = convertDst(dstRigEvents);

        try {
            write(dirPath + "/rigEvent.html", rigEventArray, assetManager.open(BASIC_RIG_EVENT_TEMPLATE));
            write(dirPath + "/sptRigEvent.html", sptRigEventArray, assetManager.open(SPT_RIG_EVENT_TEMPLATE));
            write(dirPath + "/dstRigEvent.html", dstRigEventArray, assetManager.open(DST_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean parseRig(String dirPath, ArrayList<RigEvent> rigEvents, AssetManager assetManager) {
        if(rigEvents == null) {
            return false;
        }

        ArrayList<RigEvent> list = new ArrayList<RigEvent>();
        for (int j = 0, size = rigEvents.size(); j < size; j++) {
            RigEvent currRigEvent = rigEvents.get(j);
            if (currRigEvent != null && !(currRigEvent instanceof SPTRig) && !(rigEvents.get(j) instanceof DSTRig)) {
                list.add(currRigEvent);
            }
        }

        String[][] rigEventArray = convert(list);

        try {
            write(dirPath + "rigEvent.html", rigEventArray, assetManager.open(BASIC_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean parseSptRig(String dirPath, ArrayList<RigEvent> rigEvents, AssetManager assetManager) {
        if(rigEvents == null) {
            return false;
        }

        ArrayList<SPTRig> list = new ArrayList<SPTRig>();
        for (int j = 0, size = rigEvents.size(); j < size; j++) {
            RigEvent currRigEvent = rigEvents.get(j);
            if (currRigEvent instanceof SPTRig) {
                list.add((SPTRig) currRigEvent);
            }
        }

        String[][] sptEventArray = convertSpt(list);

        try {
            write(dirPath + "sptRigEvent.html", sptEventArray, assetManager.open(SPT_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean parseDstRig(String dirPath, ArrayList<RigEvent> rigEvents, AssetManager assetManager) {
        if(rigEvents == null) {
            return false;
        }

        ArrayList<DSTRig> list = new ArrayList<DSTRig>();
        for (int j = 0, size = rigEvents.size(); j < size; j++) {
            RigEvent currRigEvent = rigEvents.get(j);
            if (currRigEvent instanceof DSTRig) {
                list.add((DSTRig) currRigEvent);
            }
        }

        String[][] dstEventArray = convertDst(list);

        try {
            write(dirPath + "dstRigEvent.html", dstEventArray, assetManager.open(DST_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static String[][] convertSpt(ArrayList<SPTRig> sptRigEvents) {
        int rows = sptRigEvents.size();
        String[][] resultData = new String[rows][];
        for (int i = 0; i < rows; i++) {
            SPTRig sptRigEvent = sptRigEvents.get(i);
            StringBuffer sb = new StringBuffer();
            sb.append(sptRigEvent.getClassPeopleCount()).append(",");
            sb.append(formatCalendarDateString(sptRigEvent.getDate(), "yyyy年MM月dd日")).append(",");
            sb.append(formatCalendarDateString(sptRigEvent.getStartTime(), "hh时mm分")).append(",");
            sb.append(formatCalendarDateString(sptRigEvent.getEndTime(), "hh时mm分")).append(",");
            sb.append(sptRigEvent.getPenetration1DepthFrom()).append(",");
            sb.append(sptRigEvent.getPenetration1DepthTo()).append(",");

            sb.append(sptRigEvent.getPenetration1DepthFrom()).append(",");
            sb.append(sptRigEvent.getPenetration1DepthTo()).append(",");
            sb.append(sptRigEvent.getPenetration1Count()).append(",");
            sb.append(sptRigEvent.getRig1DepthFrom()).append(",");
            sb.append(sptRigEvent.getRig1DepthTo()).append(",");

            sb.append(sptRigEvent.getPenetration2DepthFrom()).append(",");
            sb.append(sptRigEvent.getPenetration2DepthTo()).append(",");
            sb.append(sptRigEvent.getPenetration2Count()).append(",");
            sb.append(sptRigEvent.getRig2DepthFrom()).append(",");
            sb.append(sptRigEvent.getRig2DepthTo()).append(",");

            sb.append(sptRigEvent.getPenetration3DepthFrom()).append(",");
            sb.append(sptRigEvent.getPenetration3DepthTo()).append(",");
            sb.append(sptRigEvent.getPenetration3Count()).append(",");
            sb.append(sptRigEvent.getRig3DepthFrom()).append(",");
            sb.append(sptRigEvent.getRig3DepthTo()).append(",");

            sb.append(sptRigEvent.getGroundName()).append(",");
            sb.append(sptRigEvent.getGroundColor()).append(",");
            sb.append(sptRigEvent.getGroundSaturation()).append(",");
            sb.append(sptRigEvent.getCumulativeCount()).append(",");
            sb.append(sptRigEvent.getSpecialNote()).append(",");

            resultData[i] = convert2Array(sb.toString());
        }
        return resultData;
    }

    private static String[][] convertDst(ArrayList<DSTRig> dstRigEvents) {
        ArrayList<String> records = new ArrayList<>();
        for (int i = 0, len = dstRigEvents.size(); i < len; i++) {
            DSTRig dstRig = dstRigEvents.get(i);
            ArrayList<DSTRig.DynamicSoundingEvent> events = dstRig.getDynamicSoundingEvents();
            for (int j = 0, size = events.size(); j < size; j++) {
                DSTRig.DynamicSoundingEvent event = events.get(j);
                StringBuffer sb = new StringBuffer();
                sb.append(dstRig.getClassPeopleCount()).append(",");
                sb.append(formatCalendarDateString(dstRig.getDate(), "yyyy年MM月dd日")).append(",");
                sb.append(formatCalendarDateString(dstRig.getStartTime(), "hh时mm分")).append(",");
                sb.append(formatCalendarDateString(dstRig.getEndTime(), "hh时mm分")).append(",");
                sb.append(event.getTotalLength()).append(",");
                sb.append(event.getDigDepth()).append(",");
                sb.append(event.getPenetration()).append(",");
                sb.append(event.getHammeringCount()).append(",");
                sb.append(dstRig.getGroundName()).append(",");

                records.add(sb.toString());
            }
        }
        int rows = records.size();
        String[][] resultData = new String[rows][];
        for (int i = 0; i < rows; i++) {
            resultData[i] = convert2Array(records.get(i));
        }

        return resultData;
    }


    private static String[][] convert(ArrayList<RigEvent> rigEvents) {
        int rows = rigEvents.size();
        String[][] resultData = new String[rows][];
        for (int i = 0; i < rows; i++) {
            RigEvent rigEvent = rigEvents.get(i);
            StringBuffer sb = new StringBuffer();
            sb.append(rigEvent.getClassPeopleCount()).append(",");
            sb.append(formatCalendarDateString(rigEvent.getDate(), "yyyy年MM月dd日")).append(",");
            sb.append(formatCalendarDateString(rigEvent.getStartTime(), "hh时mm分")).append(",");
            sb.append(formatCalendarDateString(rigEvent.getEndTime(), "hh时mm分")).append(",");
            sb.append(rigEvent.getProjectName()).append(",");
            sb.append(rigEvent.getDrillPipeId()).append(",");
            sb.append(rigEvent.getDrillPipeLength()).append(",");
            sb.append(rigEvent.getCumulativeLength()).append(",");
            sb.append(rigEvent.getCoreBarreliDiameter()).append(",");
            sb.append(rigEvent.getCoreBarreliLength()).append(",");
            sb.append(rigEvent.getDrillType()).append(",");
            sb.append(rigEvent.getDrillDiameter()).append(",");
            sb.append(rigEvent.getDrillLength()).append(",");
            sb.append(rigEvent.getPenetrationDiameter()).append(",");
            sb.append(rigEvent.getPenetrationLength()).append(",");
            sb.append(rigEvent.getDynamicSoundingType()).append(",");
            sb.append(rigEvent.getSoundingDiameter()).append(",");
            sb.append(rigEvent.getSoundinglength()).append(",");
            sb.append(rigEvent.getDrillToolTotalLength()).append(",");
            sb.append(rigEvent.getDrillToolRemainLength()).append(",");
            sb.append(rigEvent.getRoundTripMeterage()).append(",");
            sb.append(rigEvent.getCumulativeMeterage()).append(",");
            sb.append(rigEvent.getRockCoreId()).append(",");
            sb.append(rigEvent.getRockCoreLength()).append(",");
            sb.append(rigEvent.getRockCoreRecovery()).append(",");
            sb.append(rigEvent.getGroundName()).append(",");
            sb.append(rigEvent.getStartDepth()).append(",");//起止深度
            sb.append(rigEvent.getGroundColor()).append(",");
            sb.append(rigEvent.getGroundDensity()).append(",");
            sb.append(rigEvent.getGroundSaturation()).append(",");
            sb.append(rigEvent.getGroundWeathering()).append(",");
            sb.append(rigEvent.getSpecialNote()).append(",");

            resultData[i] = convert2Array(sb.toString());
        }

        return resultData;
    }

    public static boolean parseToLocal(String outPath, ArrayList<Hole> holes, String srcTemplate) throws IOException {
        ArrayList<RigEvent> rigEvents = new ArrayList<RigEvent>();
        ArrayList<SPTRig> sptRigEvents = new ArrayList<SPTRig>();
        ArrayList<DSTRig> dstRigEvents = new ArrayList<DSTRig>();

        for (int i = 0, len = holes.size(); i < len; i++) {
            ArrayList<RigEvent> currRigEvents = holes.get(i).getRigList();
            rigEvents.addAll(currRigEvents);
            for (int j = 0, size = currRigEvents.size(); j < size; j++) {
                RigEvent currRigEvent = rigEvents.get(j);
                if (currRigEvent instanceof SPTRig) {
                    sptRigEvents.add((SPTRig) currRigEvent);
                } else if (rigEvents.get(j) instanceof DSTRig) {
                    dstRigEvents.add((DSTRig) currRigEvent);
                } else {
                    // do nothing
                }
            }
        }


        //html output
        String[][] rigEventArray = convert(rigEvents);
        String[][] sptRigEventArray = convertSpt(sptRigEvents);
        String[][] dstRigEventArray = convertDst(dstRigEvents);

        write(outPath + "rigEvent.html", rigEventArray, new FileInputStream(srcTemplate+"/"+BASIC_RIG_EVENT_TEMPLATE));
        write(outPath + "sptRigEvent.html", sptRigEventArray, new FileInputStream(srcTemplate+"/"+SPT_RIG_EVENT_TEMPLATE));
        write(outPath + "dstRigEvent.html", dstRigEventArray, new FileInputStream(srcTemplate + "/"+DST_RIG_EVENT_TEMPLATE));

        return true;

    }

}

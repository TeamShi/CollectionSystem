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
import me.alfredis.collectionsystem.datastructure.RigView;
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

        for(Hole hole:holes){
            //html output
            String[][] rigEventArray = convertRig(hole);
            String[][] sptRigEventArray = convertSpt(hole);
            String[][] dstRigEventArray = convertDst(hole);
            try {
                write(dirPath + "/rig_"+hole.getHoleId()+".html", rigEventArray, assetManager.open(BASIC_RIG_EVENT_TEMPLATE));
                write(dirPath + "/sptRig_"+hole.getHoleId()+".html", sptRigEventArray, assetManager.open(SPT_RIG_EVENT_TEMPLATE));
                write(dirPath + "/dstRig_"+hole.getHoleId()+".html", dstRigEventArray, assetManager.open(DST_RIG_EVENT_TEMPLATE));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    public static boolean parseRig(String dirPath, ArrayList<Hole> holes, AssetManager assetManager) {
        if(holes == null) {
            return false;
        }
        String[][] rigEventArray = null;
        for(Hole hole:holes){
            String[][] tempArray = convertRig(hole);
            System.arraycopy(tempArray,0,rigEventArray,rigEventArray.length,tempArray.length);
        }

        try {
            write(dirPath + "rigEvent.html", rigEventArray, assetManager.open(BASIC_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean parseSptRig(String dirPath, Hole hole, AssetManager assetManager) {
        if(hole == null) {
            return false;
        }


        String[][] sptEventArray = convertSpt(hole);

        try {
            write(dirPath + "sptRigEvent.html", sptEventArray, assetManager.open(SPT_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean parseDstRig(String dirPath, Hole hole, AssetManager assetManager) {
        if(hole == null) {
            return false;
        }

        String[][] dstEventArray = convertDst(hole);

        try {
            write(dirPath + "dstRigEvent.html", dstEventArray, assetManager.open(DST_RIG_EVENT_TEMPLATE));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static String[][] convertSpt(Hole hole) {
        ArrayList<SPTRig> sptRigEvents = new ArrayList<>();
        for(RigEvent rigEvent : hole.getRigList()){
            if(rigEvent instanceof  SPTRig) {
                sptRigEvents.add((SPTRig) rigEvent);
            }
        }
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

    private static String[][] convertDst(Hole hole) {
        ArrayList<DSTRig> dstRigEvents = new ArrayList<>();
        for(RigEvent rigEvent : hole.getRigList()){
            if(rigEvent instanceof  DSTRig) {
                dstRigEvents.add((DSTRig) rigEvent);
            }
        }
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


    private static String[][] convertRig(Hole hole) {
        ArrayList<RigEvent> rigEvents = hole.getRigList();
        int rows = rigEvents.size();
        String[][] resultData = new String[rows][];
        int groundNo = 1;
        for (int i = 0; i < rows; i++) {
            RigEvent rigEvent =  rigEvents.get(i);
            RigView rigView = new RigView(hole,rigEvent);
            StringBuffer sb = new StringBuffer();

            sb.append(rigView.getClassPeopleCount()).append(",");

            sb.append(rigView.getDate()).append(",");
            sb.append(rigView.getStartTime()).append(",");
            sb.append(rigView.getEndTime()).append(",");
            sb.append(rigView.getTimeInterval()).append(",");

            sb.append(rigView.getProjectName()).append(",");

            sb.append(rigView.getDrillPipeId()).append(",");
            sb.append(rigView.getDrillPipeLength()).append(",");
            sb.append(rigView.getCumulativeLength()).append(",");

            sb.append(rigView.getCoreBarreliDiameter()).append(",");
            sb.append(rigView.getCoreBarreliLength()).append(",");

            sb.append(rigView.getDrillType()).append(",");
            sb.append(rigView.getDrillDiameter()).append(",");
            sb.append(rigView.getDrillLength()).append(",");

            //进尺
            sb.append(rigView.getDrillToolTotalLength()).append(",");
            sb.append(rigView.getDrillToolRemainLength()).append(",");
            sb.append(rigView.getRoundTripMeterage()).append(",");
            sb.append(rigView.getCumulativeMeterage()).append(",");

            //护壁措施
            sb.append(rigView.getDadoType()).append(",");
            sb.append(rigView.getCasedId()).append(",");
            sb.append(rigView.getCasedDiameter()).append(",");
            sb.append(rigView.getCasedLength()).append(",");
            sb.append(rigView.getCasedTotalLength()).append(",");
            //孔内情况
            sb.append(rigView.getCasedSituation()).append(",");

            //岩心采取
            sb.append(rigView.getRockCoreId()).append(",");
            sb.append(rigView.getRockCoreLength()).append(",");
            sb.append(rigView.getRockCoreRecovery()).append(",");

            //土样
            sb.append(rigView.getEarthNo()).append(",");
            sb.append(rigView.getEarthDiameter()).append(",");
            sb.append(rigView.getEarthDepth()).append(",");
            sb.append(rigView.getEarthCount()).append(",");

            //水样
            sb.append(rigView.getWaterNo()).append(",");
            sb.append(rigView.getWaterDepth()).append(",");
            sb.append(rigView.getWaterCount()).append(",");

            //地层
            if(rigView.getRigType().equals("Normal")){
                sb.append(groundNo++).append(",");//编号, 四类普通钻,编号加1
            }else{
                sb.append("").append(",");
            }
            sb.append(rigView.getGroundDepth()).append(","); //底层深度 本次累计进尺
//            double prevGroundDepth = i > 0 ? rigEvents.get(i-1).getCumulativeMeterage(): 0;
//            double currGroundDepth = rigEvent.getCumulativeMeterage();
            sb.append(rigView.getGroundDepthDiff()).append(",");//层厚 本次累计进尺 -上次累计进尺
            sb.append(rigView.getGroundNote()).append(","); // 名称及岩性
            sb.append(rigView.getGroundClass()).append(","); //岩层等级

            //地下水 只填第一行
            if(i > 0 ) {
                sb.append("").append(",");
                sb.append("").append(",");
                sb.append("").append(",");
                sb.append("").append(",");
            } else {
                sb.append(rigView.getMeasureDatesInterval()).append(","); //观测时间 - 观测水位日期差 * 24
                sb.append(rigView.getInitialLevel()).append(",");
                sb.append(rigView.getStableLevel()).append(",");
                sb.append(rigView.getLevelChange()).append(",");
            }

            sb.append(rigView.getHoleNote()).append(",");

            resultData[i] = convert2Array(sb.toString());
        }

        return resultData;
    }

    public static boolean parseToLocal(String outPath, ArrayList<Hole> holes, String srcTemplate) throws IOException {
//        ArrayList<RigEvent> rigEvents = new ArrayList<RigEvent>();
//        ArrayList<SPTRig> sptRigEvents = new ArrayList<SPTRig>();
//        ArrayList<DSTRig> dstRigEvents = new ArrayList<DSTRig>();
//
//        for (int i = 0, len = holes.size(); i < len; i++) {
//            ArrayList<RigEvent> currRigEvents = holes.get(i).getRigList();
//            rigEvents.addAll(currRigEvents);
//            for (int j = 0, size = currRigEvents.size(); j < size; j++) {
//                RigEvent currRigEvent = currRigEvents.get(j);
//                if (currRigEvent instanceof SPTRig) {
//                    sptRigEvents.add((SPTRig) currRigEvent);
//                } else if (currRigEvent instanceof DSTRig) {
//                    dstRigEvents.add((DSTRig) currRigEvent);
//                } else {
//                    // do nothing
//                }
//            }
//        }


        //html output
        for(Hole hole:holes) {
            String[][] rigEventArray = convertRig(hole);
            String[][] sptRigEventArray = convertSpt(hole);
            String[][] dstRigEventArray = convertDst(hole);
            try {
                write(outPath + "/rig_"+hole.getHoleId()+".html", rigEventArray, new FileInputStream(srcTemplate+"/"+BASIC_RIG_EVENT_TEMPLATE));
                write(outPath + "/spt_"+hole.getHoleId()+".html", sptRigEventArray, new FileInputStream(srcTemplate+"/"+SPT_RIG_EVENT_TEMPLATE));
                write(outPath + "/dst_"+hole.getHoleId()+".html", dstRigEventArray, new FileInputStream(srcTemplate + "/"+DST_RIG_EVENT_TEMPLATE));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;

    }

}

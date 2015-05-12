package me.alfredis.collectionsystem.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import me.alfredis.collectionsystem.Utility;
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

    public static String BASIC_RIG_EVENT_TEMPLATE = "." +File.separator+"template"+File.separator+"RigEventTable.html";

    public static String TBODY_ID = "tableBody";


    public static boolean write(String outPath, String[][] data, String templatePath) throws IOException {
        String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
        if (!fileType.equals("html")) {
            System.out.println("您的文档格式不正确(非html)！");
            return false;
        }

        //读模版文件
        File input = new File(templatePath);
        Document doc = Jsoup.parse(input, "UTF-8");
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

        return true;

    }

    public static boolean parse(String dirPath,ArrayList<Hole> holes) throws IOException {
        ArrayList<RigEvent> rigEvents = new ArrayList<RigEvent>();
        ArrayList<SPTRig> sptRigEvents = new ArrayList<SPTRig>();
        ArrayList<DSTRig> dstRigEvents  = new ArrayList<DSTRig>();

        for (int i = 0, len = holes.size(); i < len; i++) {
            ArrayList<RigEvent> currRigEvents= holes.get(i).getRigLists();
            rigEvents.addAll(currRigEvents);
            for(int j= 0,size=currRigEvents.size();j<size;j++){
                RigEvent currRigEvent = rigEvents.get(0);
                if(currRigEvent instanceof SPTRig) {
                    sptRigEvents.add((SPTRig) currRigEvent);
                }else if(rigEvents.get(j) instanceof DSTRig){
                    dstRigEvents.add((DSTRig) currRigEvent);
                }else{
                    // do nothing
                }
            }
        }


        //html output
        String[][]  rigEventArray = convert(rigEvents);
        write(dirPath + "rigEvent.html", rigEventArray, BASIC_RIG_EVENT_TEMPLATE);

        return false;
    }

    private static String[][] convert(ArrayList<RigEvent> rigEvents) {
        int rows = rigEvents.size();
        String[][] rowDrillData = new String[rows][];
        for (int i = 0 ;i < rows; i++) {
            RigEvent rigEvent = rigEvents.get(i);
            StringBuffer sb = new StringBuffer("RigEvent{");
            sb.append(rigEvent.getId()).append(",");
            sb.append(formatCalendarDateString(rigEvent.getDate(), "yyyy年MM月dd日")).append(",");
            sb.append(formatCalendarDateString(rigEvent.getStartTime(),"MM月dd日")).append(",");
            sb.append(formatCalendarDateString(rigEvent.getEndTime(), "MM月dd日")).append(",");
            sb.append(rigEvent.getProjectName()).append(",");
            sb.append(rigEvent.getDrillPipeId()).append(",");
            sb.append(rigEvent.getDrillPipeLength()).append(",");
            sb.append(rigEvent.getCumulativeLength()).append(",");
            sb.append(rigEvent.getCoreBarreliDiameter()).append(",");
            sb.append(rigEvent.getCoreBarreliLength()).append(",");
            sb.append(rigEvent.getDrillType()).append(",");
            sb.append(rigEvent.getDrillDiameter()).append(",");
            sb.append(rigEvent.getDrillPipeLength()).append(",");
            sb.append(rigEvent.getPenetrationDiameter()).append(",");
            sb.append(rigEvent.getPenetrationLength()).append(",");
            sb.append(rigEvent.getDynamicSoundingType()).append(",");
            sb.append(rigEvent.getSoundingDiameter()).append(",");
            sb.append(rigEvent.getSoundingLength()).append(",");
            sb.append(rigEvent.getDrillToolTotalLength()).append(",");
            sb.append(rigEvent.getDrillToolRemainLength()).append(",");
            sb.append(rigEvent.getRoundTripMeterage()).append(",");
            sb.append(rigEvent.getCumulativeMeterage()).append(",");
            sb.append(rigEvent.getRockCoreId()).append(",");
            sb.append(rigEvent.getRockCoreLength()).append(",");
            sb.append(rigEvent.getRockCoreRecovery()).append(",");
            sb.append(rigEvent.getEndDepth() - rigEvent.getStartDepth()).append(",");// 计算深度差
            sb.append(rigEvent.getGroundName()).append(",");
            sb.append(rigEvent.getGroundColor()).append(",");
            sb.append(rigEvent.getGroundDensity()).append(",");
            sb.append(rigEvent.getGroundSaturation()).append(",");
            sb.append(rigEvent.getGroundWeathering()).append(",");
            sb.append(rigEvent.getNote()).append(",");

            rowDrillData[i] = convert2Array(sb.toString());
        }

        return rowDrillData;
    }

}

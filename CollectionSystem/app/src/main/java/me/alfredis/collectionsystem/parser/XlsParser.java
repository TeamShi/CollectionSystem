package me.alfredis.collectionsystem.parser;
/**
 * Created by jishshi on 2015/5/9.
 */

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import me.alfredis.collectionsystem.Utility;
import me.alfredis.collectionsystem.datastructure.DSTRig;
import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.datastructure.SPTRig;

import static me.alfredis.collectionsystem.Utility.convert2Array;
import static me.alfredis.collectionsystem.Utility.formatCalendarDateString;


/**
 * xls工具类
 *
 * @author hjn
 */
public class XlsParser {

    public static String[] HOLE_HEADER = new String[]{"勘察点名称", "工程名称", "阶 段", "冠 词", "里  程", "偏移量", "高  程", "经距X", "纬距Y", "位置描述", "记录者", "记录日期", "复核者", "复核日期", "附  注", "孔  深"};
    public static String[] RIGEVENT_HEADER = new String[]{"勘探点名称", "作业ID", "班次/人数", "日期", "开始时间", "结束时间", "作业项目", "钻杆编号", "钻杆长度", "累计长度", "岩心管直接", "岩心管长度", "钻头类型", "钻头直径", "钻头长度",
            "贯入器直径", "贯入器长度", "动探类型", "探头直径", "探头长度", "钻具总长", "钻杆余长", "回次进尺", "累积进尺", "岩芯编号", "岩芯长度", "岩芯采取旅", "岩土名称", "本钻起深度", "本钻止深度", "岩土颜色", "岩土稠密度", "岩土饱和度", "岩石风化程度", "岩土岩性"};
    public static String[] SPTEVENT_HEADER = new String[]{"勘探点名称", "作业ID", "贯入深度起", "贯入深度止", "第一击起深度", "第一击止深度", "第一击贯入击数", "钻进1深度起", "钻进1深度止",
            "第一击起深度", "第二击止深度", "第二击贯入击数", "钻进2深度起", "钻进2深度止", "第三击起深度", "第三击止深度", "第三击贯入击数", "钻进3深度起", "钻进3深度止", "岩土名称", "岩土颜色", "岩土饱和度", "累计击数", "其他特征"};
    public static String[] DSTEVENT_HEADER = new String[]{"勘探点名称", "作业ID", "岩土名称", "探杆总长", "入土深度", "贯入度", "锤击数", "密实度"};

    public static String HOLES_NAME = "钻孔记录";
    public static String RIGEVENT_NAME = "钻孔原始记录";
    public static String SPTEVENT_NAME = "标准贯入表";
    public static String DSTEVENT_NAME = "动力触探表";


    public static boolean write(String outPath, String[][] array, String sheetName) throws Exception {
        String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
        File file = new File(outPath);
        Workbook wb = null;
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook(fileInputStream);
            } else if (fileType.equals("xlsx")) {
                wb = new XSSFWorkbook(fileInputStream);
            } else {
                System.out.println("您的文档格式不正确！");
                return false;
            }
            fileInputStream.close();
        } else {
            if (fileType.equals("xls")) {
                wb = new HSSFWorkbook();
            } else if (fileType.equals("xlsx")) {
                wb = new XSSFWorkbook();
            } else {
                System.out.println("您的文档格式不正确！");
                return false;
            }
        }

        Sheet sheet1 = wb.createSheet(sheetName);
        // 循环写入行数据
        for (int i = 0, rows = array.length; i < rows; i++) {
            Row row = sheet1.createRow(i);
            // 循环写入列数据
            for (int j = 0, cols = array[i].length; j < cols; j++) {
                Cell cell = row.createCell(j);
                String text = array[i][j].equals("null") ? "" : array[i][j];
                cell.setCellValue(text);
            }
        }

        // 创建文件流
        OutputStream stream = new FileOutputStream(file, false);
        // 写入数据
        wb.write(stream);
        // 关闭文件流
        stream.close();
        return true;
    }

    public static ArrayList<Hole> parse(String dirPath) throws Exception {
        ArrayList<String[]> holes = read(dirPath, HOLES_NAME);
        ArrayList<String[]> rigs = read(dirPath, RIGEVENT_NAME);
        ArrayList<String[]> sptRigs = read(dirPath, SPTEVENT_NAME);
        ArrayList<String[]> dstRigs = read(dirPath, DSTEVENT_NAME);

        ArrayList<Hole> holeList = convert2Holes(holes);
        HashMap<String, ArrayList<RigEvent>> rigMap = convert2Rig(rigs);///<holeId,SPTRig list>
        HashMap<String, SPTRig> sptRigMap = convert2SptRig(sptRigs);//<eventId,SPTRig list>
        HashMap<String, DSTRig> dstRigMap = convert2DstRig(dstRigs);//<eventId,DSTRig list>

        Iterator entries = rigMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String holeId = (String) entry.getKey();
            ArrayList<RigEvent> rigEvents = (ArrayList<RigEvent>) entry.getValue();
            ArrayList<RigEvent> newRigEvents = new ArrayList<>();
            for (RigEvent currEvent : rigEvents) {
                String eventId = currEvent.getEventId();
                if ("标贯".equals(currEvent.getProjectName())) {
                    SPTRig rigEvent = new SPTRig(currEvent.getHoleId(),currEvent.getProjectName(),currEvent.getDrillPipeId(),currEvent.getDrillPipeLength(),currEvent.getCumulativeLength(),currEvent.getDrillToolTotalLength(),currEvent.getDrillToolRemainLength(),currEvent.getRoundTripMeterage(),currEvent.getCumulativeMeterage(),currEvent.getNote()) ;
                    SPTRig sptRig = sptRigMap.get(eventId);
                    rigEvent.setPenetrationFrom(sptRig.getPenetrationFrom());
                    rigEvent.setPenetrationTo(sptRig.getPenetrationTo());

                    rigEvent.setPenetration1DepthFrom(sptRig.getPenetration1DepthFrom());
                    rigEvent.setPenetration1DepthTo(sptRig.getPenetration1DepthTo());
                    rigEvent.setPenetration1Count(sptRig.getPenetration1Count());
                    rigEvent.setRig1DepthFrom(sptRig.getRig1DepthFrom());
                    rigEvent.setRig1DepthTo(sptRig.getPenetration1DepthFrom());

                    rigEvent.setPenetration2DepthFrom(sptRig.getPenetration2DepthFrom());
                    rigEvent.setPenetration2DepthTo(sptRig.getPenetration2DepthTo());
                    rigEvent.setPenetration2Count(sptRig.getPenetration2Count());
                    rigEvent.setRig2DepthFrom(sptRig.getRig2DepthFrom());
                    rigEvent.setRig2DepthTo(sptRig.getPenetration2DepthFrom());

                    rigEvent.setPenetration3DepthFrom(sptRig.getPenetration3DepthFrom());
                    rigEvent.setPenetration3DepthTo(sptRig.getPenetration3DepthTo());
                    rigEvent.setPenetration3Count(sptRig.getPenetration3Count());
                    rigEvent.setRig3DepthFrom(sptRig.getRig3DepthFrom());
                    rigEvent.setRig3DepthTo(sptRig.getPenetration3DepthFrom());

                    rigEvent.setGroundName(sptRig.getGroundName());
                    rigEvent.setGroundColor(sptRig.getGroundColor());
                    rigEvent.setGroundSaturation(sptRig.getGroundSaturation());
                    rigEvent.setCumulativeCount(sptRig.getCumulativeCount());
                    rigEvent.setNote(sptRig.getNote());

                    newRigEvents.add(rigEvent);
                } else if ("动探".equals(currEvent.getProjectName())) {
                    DSTRig rigEvent =new DSTRig(currEvent.getHoleId(),currEvent.getProjectName(),currEvent.getDrillPipeId(),currEvent.getDrillPipeLength(),currEvent.getCumulativeLength(),currEvent.getDrillToolTotalLength(),currEvent.getDrillToolRemainLength(),currEvent.getRoundTripMeterage(),currEvent.getCumulativeMeterage(),currEvent.getNote()) ;;
                    DSTRig dstRig = dstRigMap.get(eventId);

                    rigEvent.setGroundName(dstRig.getGroundName());
                    rigEvent.setDynamicSoundingEvents(dstRig.getDynamicSoundingEvents());

                    newRigEvents.add(rigEvent);
                } else {
                    newRigEvents.add(currEvent);
                }
            }
            rigMap.put(holeId, newRigEvents); //update map
        }

        for (Hole hole : holeList) {
            String holeId = hole.getHoleId();
            hole.setRigList(rigMap.get(holeId));
        }

        return holeList;
    }

    private static ArrayList<Hole> convert2Holes(ArrayList<String[]> holes) {
        ArrayList<Hole> list = new ArrayList<>();

        for (int i = 1, len = holes.size(); i < len; i++) {
            String[] values = holes.get(i);
            Hole hole = new Hole();
            //set holeId
            String[] holeIdParts = values[0].split("-");
            hole.setHoleIdPart1(Enum.valueOf(Hole.HoleIdPart1Type.class, holeIdParts[0]));
            hole.setHoleIdPart2Year(holeIdParts[1].substring(1));
            hole.setHoleIdPart3(holeIdParts[2]);
            hole.setHoleIdPart4(holeIdParts[3]);

            hole.setProjectName(values[1]);
            hole.setProjectStage(Enum.valueOf(Hole.ProjectStageType.class, values[2]));
            hole.setArticle(Enum.valueOf(Hole.ArticleType.class, values[3]));
            hole.setMileage(Double.parseDouble(values[4]));
            hole.setOffset(Double.parseDouble(values[5]));
            hole.setHoleElevation(Double.parseDouble(values[6]));
            hole.setLongitudeDistance(Double.parseDouble(values[7]));
            hole.setLatitudeDistance(Double.parseDouble(values[8]));

            // values[9] --> 位置描述
            hole.setRecorderName(values[10]);
            hole.setRecordDate(Utility.getCalendarFromDateString(values[11], "yyyy年MM月dd日"));

            hole.setReviewerName(values[10]);
            hole.setReviewDate(Utility.getCalendarFromDateString(values[11], "yyyy年MM月dd日"));
            hole.setNote(values[14]);
            hole.setActuralDepth(Double.parseDouble(values[15]));

            list.add(hole);
        }

        return list;
    }

    private static HashMap<String, ArrayList<RigEvent>> convert2Rig(ArrayList<String[]> rigRcords) {
        HashMap<String, ArrayList<RigEvent>> map = new HashMap<>();
        for (int i = 1, len = rigRcords.size(); i < len; i++) {
            String[] values = rigRcords.get(i);
            RigEvent rigEvent = new RigEvent();
            rigEvent.setHoleId(values[0]);

            rigEvent.setEventId(values[1]);

            rigEvent.setClassPeopleCount(values[2]);

            String date = values[3];
            rigEvent.setDate(Utility.getCalendarFromDateString(date, "yyyy年MM月dd日"));

            String startDate = date + values[4];
            rigEvent.setStartTime(Utility.getCalendarFromDateString(startDate, "yyyy年MM月dd日hh时mm分"));

            String endDate = date + values[5];
            rigEvent.setEndTime(Utility.getCalendarFromDateString(endDate, "yyyy年MM月dd日hh时mm分"));

            rigEvent.setProjectName(values[6]);
            rigEvent.setDrillPipeId(Integer.parseInt(values[7]));
            rigEvent.setDrillPipeLength(Double.parseDouble(values[8]));
            rigEvent.setCumulativeLength(Double.parseDouble(values[9]));
            rigEvent.setCoreBarreliDiameter(Double.parseDouble(values[10]));
            rigEvent.setCoreBarreliLength(Double.parseDouble(values[11]));
            rigEvent.setDrillType(values[12]);
            rigEvent.setDrillDiameter(Double.parseDouble(values[13]));
            rigEvent.setDrillLength(Double.parseDouble(values[14]));
            rigEvent.setPenetrationDiameter(Double.parseDouble(values[15]));
            rigEvent.setPenetrationLength(Double.parseDouble(values[16]));
            if (!values[17].equals("")) {
                rigEvent.setDynamicSoundingType(Enum.valueOf(RigEvent.DynamicSoundingType.class, values[17]));
            }
            rigEvent.setSoundingDiameter(Double.parseDouble(values[18]));
            rigEvent.setSoundinglength(Double.parseDouble(values[19]));
            rigEvent.setDrillToolTotalLength(Double.parseDouble(values[20]));
            rigEvent.setDrillToolRemainLength(Double.parseDouble(values[21]));
            rigEvent.setRoundTripMeterage(Double.parseDouble(values[22]));
            rigEvent.setCumulativeMeterage(Double.parseDouble(values[23]));
            rigEvent.setRockCoreId(values[24]);
            rigEvent.setRockCoreLength(Double.parseDouble(values[25]));
            rigEvent.setRockCoreRecovery(Double.parseDouble(values[26]));
            rigEvent.setGroundName(values[27]);
            rigEvent.setStartDepth(Double.parseDouble(values[28]));
            rigEvent.setEndDepth(Double.parseDouble(values[29]));
            rigEvent.setGroundColor(values[30]);
            rigEvent.setGroundDensity(values[31]);
            rigEvent.setGroundSaturation(values[32]);
            rigEvent.setGroundWeathering(values[33]);
            rigEvent.setNote(values[34]);

            String holeId = rigEvent.getHoleId();
            ArrayList<RigEvent> rigList = map.get(holeId);
            if (rigList == null) {
                rigList = new ArrayList<>();
            }
            rigList.add(rigEvent);
            map.put(holeId, rigList);

        }

        return map;
    }

    private static HashMap<String, SPTRig> convert2SptRig(ArrayList<String[]> sptRecords) {
        HashMap<String, SPTRig> map = new HashMap<String, SPTRig>();
        for (int i = 1, len = sptRecords.size(); i < len; i++) {
            String[] values = sptRecords.get(i);
            SPTRig sptEvent = new SPTRig();
            sptEvent.setHoleId(values[0]);
            sptEvent.setEventId(values[1]);

            sptEvent.setPenetrationFrom(Double.parseDouble(values[2]));
            sptEvent.setPenetrationTo(Double.parseDouble(values[3]));

            sptEvent.setPenetration1DepthFrom(Double.parseDouble(values[4]));
            sptEvent.setPenetration1DepthTo(Double.parseDouble(values[5]));
            sptEvent.setPenetration1Count(Integer.parseInt(values[6]));
            sptEvent.setRig1DepthFrom(Double.parseDouble(values[7]));
            sptEvent.setRig1DepthTo(Double.parseDouble(values[8]));

            sptEvent.setPenetration2DepthFrom(Double.parseDouble(values[9]));
            sptEvent.setPenetration2DepthTo(Double.parseDouble(values[10]));
            sptEvent.setPenetration2Count(Integer.parseInt(values[11]));
            sptEvent.setRig2DepthFrom(Double.parseDouble(values[12]));
            sptEvent.setRig2DepthTo(Double.parseDouble(values[13]));

            sptEvent.setPenetration3DepthFrom(Double.parseDouble(values[14]));
            sptEvent.setPenetration3DepthTo(Double.parseDouble(values[15]));
            sptEvent.setPenetration3Count(Integer.parseInt(values[16]));
            sptEvent.setRig3DepthFrom(Double.parseDouble(values[17]));
            sptEvent.setRig3DepthTo(Double.parseDouble(values[18]));

            sptEvent.setGroundName(values[19]);
            sptEvent.setGroundColor(values[20]);
            sptEvent.setGroundSaturation(values[21]);
            sptEvent.setCumulativeCount(Integer.parseInt(values[22]));
            sptEvent.setNote(values[23]);

            String eventId = sptEvent.getEventId();
            map.put(eventId, sptEvent);
        }

        return map;
    }

    private static HashMap<String, DSTRig> convert2DstRig(ArrayList<String[]> dstRecords) {
        HashMap<String, DSTRig> map = new HashMap<>();
        HashMap<String, ArrayList<DSTRig.DynamicSoundingEvent>> listMap = new HashMap<>(); //<eventid,list>

        for (int i = 1, len = dstRecords.size(); i < len; i++) {
            String[] values = dstRecords.get(i);
            DSTRig.DynamicSoundingEvent dynamicSoundingEvent = new DSTRig.DynamicSoundingEvent();

            dynamicSoundingEvent.setTotalLength(Double.parseDouble(values[3]));
            dynamicSoundingEvent.setDigDepth(Double.parseDouble(values[4]));
            dynamicSoundingEvent.setPenetration(Double.parseDouble(values[5]));
            dynamicSoundingEvent.setHammeringCount(Integer.parseInt(values[6]));
            dynamicSoundingEvent.setCompactness(values[7]);

            String eventId = values[1];
            ArrayList<DSTRig.DynamicSoundingEvent> dstRigList = listMap.get(eventId);
            if (dstRigList == null) {
                dstRigList = new ArrayList<>();
            }
            dstRigList.add(dynamicSoundingEvent);
            listMap.put(eventId, dstRigList);
        }

        for (int i = 1, len = dstRecords.size(); i < len; i++) {
            String[] values = dstRecords.get(i);
            DSTRig dstEvent = new DSTRig();
            String eventId = values[1];
            dstEvent.setHoleId(values[0]);
            dstEvent.setEventId(eventId);
            dstEvent.setGroundName(values[2]);
            dstEvent.setDynamicSoundingEvents(listMap.get(eventId));

            map.put(eventId,dstEvent);
        }

        return map;
    }

    private static ArrayList<String[]> read(String filePath, String sheetName) throws IOException {
        String fileType = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        InputStream stream = new FileInputStream(filePath);
        Workbook wb = null;
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(stream);
        } else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(stream);
        } else {
            System.out.println("您输入的excel格式不正确");
        }
        Sheet holesSheet = wb.getSheet(sheetName);
        ArrayList<String[]> holes = convertSheet(holesSheet);

        stream.close();

        return holes;
    }

    private static ArrayList<String[]> convertSheet(Sheet sheet) {
        ArrayList<String[]> records = new ArrayList<String[]>();
        for (Row row : sheet) {
            StringBuffer sb = new StringBuffer();
            for (Cell cell : row) {
                sb.append(cell.getStringCellValue() + ",");
            }
            records.add(sb.toString().split(","));
        }
        return records;
    }


    public static boolean parse(String path, ArrayList<Hole> holes) {

        ArrayList<RigEvent> rigEvents = new ArrayList<>();
        ArrayList<SPTRig> sptRigEvents = new ArrayList<>();
        ArrayList<DSTRig> dstRigEvents = new ArrayList<>();

        for (Hole hole : holes) {
            for (RigEvent rigEvent : hole.getRigList()) {
                rigEvent.setHoleId(hole.getHoleId());
                rigEvent.setEventId(String.valueOf(UUID.randomUUID()));
                if (rigEvent instanceof SPTRig) {
                    sptRigEvents.add((SPTRig) rigEvent);
                } else if (rigEvent instanceof DSTRig) {
                    dstRigEvents.add((DSTRig) rigEvent);
                } else {
                }
                rigEvents.add(rigEvent);
            }
        }


        String[][] holeArray = convertHoles(holes);
        String[][] rigArray = convertRig(rigEvents);
        String[][] sptRigArray = convertSpt(sptRigEvents);
        String[][] dstRigArray = convertDst(dstRigEvents);

        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            XlsParser.write(path, holeArray, HOLES_NAME);
            XlsParser.write(path, rigArray, RIGEVENT_NAME);
            XlsParser.write(path, sptRigArray, SPTEVENT_NAME);
            XlsParser.write(path, dstRigArray, DSTEVENT_NAME);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static String[][] convertHoles(ArrayList<Hole> holes) {
        ArrayList<String> records = new ArrayList<>();
        for (int i = 0, len = holes.size(); i < len; i++) {
            Hole hole = holes.get(i);
            StringBuffer sb = new StringBuffer();
            sb.append(hole.getHoleId()).append(",");
            sb.append(hole.getProjectName()).append(",");
            sb.append(hole.getProjectStage()).append(",");
            sb.append(hole.getArticle()).append(",");
            sb.append(hole.getMileage()).append(",");
            sb.append(hole.getOffset()).append(",");
            sb.append(hole.getHoleElevation()).append(",");
            sb.append(hole.getLongitudeDistance()).append(",");
            sb.append(hole.getLatitudeDistance()).append(",");
            sb.append("position placeholder").append(",");
            sb.append(hole.getRecorderName()).append(",");
            sb.append(Utility.formatCalendarDateString(hole.getRecordDate(), "yyyy年MM月dd日")).append(",");
            sb.append(hole.getReviewerName()).append(",");
            sb.append(Utility.formatCalendarDateString(hole.getReviewDate(), "yyyy年MM月dd日")).append(",");
            sb.append(hole.getNote()).append(",");
            sb.append(hole.getActuralDepth()).append(",");

            records.add(sb.toString());
        }
        int rows = records.size() + 1;
        String[][] resultData = new String[rows][];
        resultData[0] = HOLE_HEADER;
        for (int i = 1; i < rows; i++) {
            resultData[i] = convert2Array(records.get(i - 1));
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
                sb.append(dstRig.getHoleId()).append(",");
                sb.append(dstRig.getEventId()).append(","); // 作业ID
                sb.append(dstRig.getGroundName()).append(",");
                sb.append(event.getTotalLength()).append(",");
                sb.append(event.getDigDepth()).append(",");
                sb.append(event.getPenetration()).append(",");
                sb.append(event.getHammeringCount()).append(",");
                sb.append(event.getCompactness()).append(",");

                records.add(sb.toString());
            }
        }
        int rows = records.size() + 1;
        String[][] resultData = new String[rows][];
        resultData[0] = DSTEVENT_HEADER;
        for (int i = 1; i < rows; i++) {
            resultData[i] = convert2Array(records.get(i - 1));
        }

        return resultData;
    }

    private static String[][] convertSpt(ArrayList<SPTRig> sptRigEvents) {
        int rows = sptRigEvents.size() + 1;
        String[][] resultData = new String[rows][];
        resultData[0] = SPTEVENT_HEADER;
        for (int i = 1; i < rows; i++) {
            SPTRig sptRigEvent = sptRigEvents.get(i - 1);
            StringBuffer sb = new StringBuffer();
            sb.append(sptRigEvent.getHoleId()).append(",");
            sb.append(sptRigEvent.getEventId()).append(",");

            sb.append(sptRigEvent.getPenetrationFrom()).append(",");
            sb.append(sptRigEvent.getPenetrationTo()).append(",");

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
            sb.append(sptRigEvent.getNote()).append(",");

            resultData[i] = convert2Array(sb.toString());
        }
        return resultData;
    }

    private static String[][] convertRig(ArrayList<RigEvent> rigEvents) {
        int rows = rigEvents.size() + 1;
        String[][] resultData = new String[rows][];
        resultData[0] = RIGEVENT_HEADER;
        for (int i = 1; i < rows; i++) {
            RigEvent rigEvent = rigEvents.get(i - 1);
            StringBuffer sb = new StringBuffer();
            sb.append(rigEvent.getHoleId()).append(",");
            sb.append(rigEvent.getEventId()).append(",");
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
            sb.append(rigEvent.getStartDepth()).append(",");
            sb.append(rigEvent.getEndDepth()).append(",");
            sb.append(rigEvent.getGroundColor()).append(",");
            sb.append(rigEvent.getGroundDensity()).append(",");
            sb.append(rigEvent.getGroundSaturation()).append(",");
            sb.append(rigEvent.getGroundWeathering()).append(",");
            sb.append(rigEvent.getNote()).append(",");

            resultData[i] = convert2Array(sb.toString());
        }

        return resultData;
    }

}
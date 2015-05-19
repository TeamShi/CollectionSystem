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

    public static String[] RIGEVENT_HEADER = new String[]{"班次/人数", "日期", "作业项目", "开始时间", "结束时间", "钻杆编号"};
    public static String[] SPTEVENT_HEADER = new String[]{"班次/人数", "日期", "作业项目", "开始时间", "结束时间", "钻杆编号"};
    public static String[] DSTEVENT_HEADER = new String[]{"班次/人数", "日期", "作业项目", "开始时间", "结束时间", "钻杆编号"};

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
        OutputStream stream = new FileOutputStream(file);
        // 写入数据
        wb.write(stream);
        // 关闭文件流
        stream.close();
        return true;
    }

    public static ArrayList<Hole> parse(String dirPath) throws Exception {
        ArrayList<Hole> holes = new ArrayList<Hole>();

        HashMap<String, ArrayList<String[]>> map = XlsParser.read(dirPath + "test.xls");
        ArrayList<String[]> rigRcords = map.get(RIGEVENT_NAME);
        ArrayList<String[]> sptRcords = map.get(SPTEVENT_NAME);
        ArrayList<String[]> dstRcords = map.get(DSTEVENT_NAME);

        ArrayList<RigEvent> rigEvents = convert2Rig(rigRcords); //TODO event Id not added
//      ArrayList<SPTRig> sptEvens =convert2Spt(sptRcords);

        return holes;
    }

    private static ArrayList<SPTRig> convert2Spt(ArrayList<String[]> sptRcords) {

        ArrayList<SPTRig> list = new ArrayList<>();

        for(int i =1,len = sptRcords.size() ;i<len;i++) {
            String[] values = sptRcords.get(i);
            SPTRig rigEvent = new SPTRig();
            rigEvent.setClassPeopleCount(values[0]);

            String date = values[1];
            rigEvent.setDate(Utility.getCalendarFromDateString(date, "yyyy年MM月dd日"));

            String startDate = date + values[2];
            rigEvent.setStartTime(Utility.getCalendarFromDateString(startDate, "yyyy年MM月dd日hh时mm分"));

            String endDate = date+ values[3];
            rigEvent.setEndTime(Utility.getCalendarFromDateString(endDate, "yyyy年MM月dd日hh时mm分"));

            rigEvent.setProjectName(values[4]);
            rigEvent.setDrillPipeId(Integer.parseInt(values[5]));
            rigEvent.setDrillPipeLength(Double.parseDouble(values[6]));
            rigEvent.setCumulativeLength(Double.parseDouble(values[7]));
            rigEvent.setCoreBarreliDiameter(Double.parseDouble(values[8]));
            rigEvent.setCoreBarreliLength(Double.parseDouble(values[9]));
            rigEvent.setDrillType(values[10]);
            rigEvent.setDrillDiameter(Double.parseDouble(values[11]));
            rigEvent.setDrillLength(Double.parseDouble(values[12]));
            rigEvent.setPenetrationDiameter(Double.parseDouble(values[13]));
            rigEvent.setPenetrationLength(Double.parseDouble(values[14]));
            if(!values [15].equals("")) {
                rigEvent.setDynamicSoundingType(Enum.valueOf(RigEvent.DynamicSoundingType.class, values[15]));
            }
            rigEvent.setSoundingDiameter(Double.parseDouble(values[16]));
            rigEvent.setSoundinglength(Double.parseDouble(values[17]));
            rigEvent.setDrillToolTotalLength(Double.parseDouble(values[18]));
            rigEvent.setDrillToolRemainLength(Double.parseDouble(values[19]));
            rigEvent.setRoundTripMeterage(Double.parseDouble(values[20]));
            rigEvent.setCumulativeMeterage(Double.parseDouble(values[21]));
            rigEvent.setRockCoreId(values[22]);
            rigEvent.setRockCoreLength(Double.parseDouble(values[23]));
            rigEvent.setRockCoreRecovery(Double.parseDouble(values[24]));
            rigEvent.setGroundName(values[25]);
            rigEvent.setStartDepth(Double.parseDouble(values[26]));
            rigEvent.setEndDepth(Double.parseDouble(values[27]));
            rigEvent.setGroundColor(values[28]);
            rigEvent.setGroundDensity(values[29]);
            rigEvent.setGroundSaturation(values[30]);
            rigEvent.setGroundWeathering(values[31]);
            rigEvent.setNote(values[32]);
            list.add(rigEvent);
        }

        return list;
    }

    private static ArrayList<RigEvent> convert2Rig(ArrayList<String[]> rigRcords) {
        ArrayList<RigEvent> list = new ArrayList<>();

        for(int i =1,len = rigRcords.size() ;i<len;i++) {
            String[] values = rigRcords.get(i);
            RigEvent rigEvent = new RigEvent();
            rigEvent.setClassPeopleCount(values[0]);

            String date = values[1];
            rigEvent.setDate(Utility.getCalendarFromDateString(date, "yyyy年MM月dd日"));

            String startDate = date + values[2];
            rigEvent.setStartTime(Utility.getCalendarFromDateString(startDate, "yyyy年MM月dd日hh时mm分"));

            String endDate = date+ values[3];
            rigEvent.setEndTime(Utility.getCalendarFromDateString(endDate, "yyyy年MM月dd日hh时mm分"));

            rigEvent.setProjectName(values[4]);
            rigEvent.setDrillPipeId(Integer.parseInt(values[5]));
            rigEvent.setDrillPipeLength(Double.parseDouble(values[6]));
            rigEvent.setCumulativeLength(Double.parseDouble(values[7]));
            rigEvent.setCoreBarreliDiameter(Double.parseDouble(values[8]));
            rigEvent.setCoreBarreliLength(Double.parseDouble(values[9]));
            rigEvent.setDrillType(values[10]);
            rigEvent.setDrillDiameter(Double.parseDouble(values[11]));
            rigEvent.setDrillLength(Double.parseDouble(values[12]));
            rigEvent.setPenetrationDiameter(Double.parseDouble(values[13]));
            rigEvent.setPenetrationLength(Double.parseDouble(values[14]));
            if(!values [15].equals("")) {
                rigEvent.setDynamicSoundingType(Enum.valueOf(RigEvent.DynamicSoundingType.class, values[15]));
            }
            rigEvent.setSoundingDiameter(Double.parseDouble(values[16]));
            rigEvent.setSoundinglength(Double.parseDouble(values[17]));
            rigEvent.setDrillToolTotalLength(Double.parseDouble(values[18]));
            rigEvent.setDrillToolRemainLength(Double.parseDouble(values[19]));
            rigEvent.setRoundTripMeterage(Double.parseDouble(values[20]));
            rigEvent.setCumulativeMeterage(Double.parseDouble(values[21]));
            rigEvent.setRockCoreId(values[22]);
            rigEvent.setRockCoreLength(Double.parseDouble(values[23]));
            rigEvent.setRockCoreRecovery(Double.parseDouble(values[24]));
            rigEvent.setGroundName(values[25]);
            rigEvent.setStartDepth(Double.parseDouble(values[26]));
            rigEvent.setEndDepth(Double.parseDouble(values[27]));
            rigEvent.setGroundColor(values[28]);
            rigEvent.setGroundDensity(values[29]);
            rigEvent.setGroundSaturation(values[30]);
            rigEvent.setGroundWeathering(values[31]);
            rigEvent.setNote(values[32]);
            list.add(rigEvent);
        }

        return list;
    }

    private static HashMap<String, ArrayList<String[]>> read(String filePath) throws IOException {
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
        Sheet rigEventSheet = wb.getSheet(RIGEVENT_NAME);
        ArrayList<String[]> rigRecords = convertSheet(rigEventSheet);

        Sheet sptEventSheet = wb.getSheet(SPTEVENT_NAME);
        ArrayList<String[]> sptRecords = convertSheet(sptEventSheet);

        Sheet dstEventSheet = wb.getSheet(DSTEVENT_NAME);
        ArrayList<String[]> dstRecords = convertSheet(dstEventSheet);

        HashMap<String, ArrayList<String[]>> map = new HashMap<>();
        map.put(RIGEVENT_NAME,rigRecords);
        map.put(SPTEVENT_NAME,sptRecords);
        map.put(DSTEVENT_NAME, dstRecords);
        stream.close();

        return  map;
    }

    private static ArrayList<String[]> convertSheet(Sheet rigEventSheet) {
        ArrayList<String[]> records = new ArrayList<String[]>();
        for (Row row : rigEventSheet) {
            StringBuffer sb = new StringBuffer();
            for (Cell cell : row) {
                sb.append(cell.getStringCellValue() + ",");
            }
            records.add(sb.toString().split(","));
        }
        return records;
    }


    public static void parse(String dirPath, ArrayList<Hole> holes) throws Exception {
        ArrayList<RigEvent> rigEvents = new ArrayList<RigEvent>();
        ArrayList<SPTRig> sptRigEvents = new ArrayList<SPTRig>();
        ArrayList<DSTRig> dstRigEvents = new ArrayList<DSTRig>();

        for (int i = 0, len = holes.size(); i < len; i++) {
            ArrayList<RigEvent> currRigEvents = holes.get(i).getRigLists();
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

        String[][] rigEventArray = convertRig(rigEvents);
        String[][] sptRigEventArray = convertSpt(sptRigEvents);
        String[][] dstRigEventArray = convertDst(dstRigEvents);

        XlsParser.write(dirPath + "test.xls", rigEventArray,RIGEVENT_NAME);
        XlsParser.write(dirPath + "test.xls", sptRigEventArray, SPTEVENT_NAME);
        XlsParser.write(dirPath + "test.xls", dstRigEventArray, DSTEVENT_NAME);

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
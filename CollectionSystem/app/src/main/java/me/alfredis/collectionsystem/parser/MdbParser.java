package me.alfredis.collectionsystem.parser;


import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;
import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import me.alfredis.collectionsystem.datastructure.DSTRig;
import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.datastructure.SPTRig;

/**
 * Created by jishshi on 2015/5/10.
 */
public class MdbParser {

    private static Database database = null;


    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
    }

    /**
     * 创建表并写入数据
     *
     * @throws IOException
     */
    public static Table createTable(String tableName, String[] columnNames, String[] types) throws IOException {
        Table newTable = null;
        if (!tableName.equals("") && columnNames.length == types.length) {
            ArrayList<ColumnBuilder> columnBuilders = new ArrayList<>();
            for (int i = 0, len = columnNames.length; i < len; i++) {
                DataType dataType = DataType.TEXT;
                switch (types[i]) {
                    case "int":
                        dataType = DataType.INT;
                        break;
                    case "double":
                        dataType = DataType.DOUBLE;
                        break;
                    case "string":
                        dataType = DataType.TEXT;
                        break;
                    default:
                        dataType = DataType.TEXT;
                }
                columnBuilders.add(new ColumnBuilder(columnNames[i], dataType));
            }
            newTable = new TableBuilder(tableName)
                    .addColumns(columnBuilders).toTable(database);
        }


        return newTable;
    }


    public static boolean parse(File dbFile, ArrayList<Hole> holes) throws IOException {
        try {
            database = DatabaseBuilder.open(dbFile);
            Table projectTable = database.getTable("项目表");
            Table prospectionTable = database.getTable("勘察点");
            Table holeTable = database.getTable("钻孔");
            Table sptTable = database.getTable("标贯表");
            Table groundTable = database.getTable("地层表");


            for (Hole hole : holes) {
                //导入项目表
                int projectID = 0;
                boolean isProjectExist = false;
                String projectName = hole.getProjectName();
                for (Row row : projectTable) {
                    if (row.get("项目名称").toString().equals(projectName)) {
                        isProjectExist = true;
                        projectID = (int) row.get("项目ID");
                    }
                }

                if (!isProjectExist) {
                    projectTable.addRow(projectID, hole.getProjectName(), hole.getHoleIdPart1(), hole.getArticle());//  项目ID自增
                    for (Row row : CursorBuilder.createCursor(projectTable.getPrimaryKeyIndex())) {
                        projectID = (int) row.get("项目ID");
                    }
                }

                boolean isHoleExist = false;
                String prospectionId = null;
                String holeId = hole.getHoleId();
                for (Row row : prospectionTable) {
                    if (row.get("名称").equals(holeId)) {
                        isHoleExist = true;
                        prospectionId = row.get("ID").toString();
                    }
                }

                if (isHoleExist) {
                    //更新勘察点表
//                    for (Row row : prospectionTable) {
//                        if (row.get("ID").equals(prospectionId)) {
//                            prospectionTable.deleteRow(row);
//                        }
//                    }
                    HashMap<String, Object> prospectionRowMap = new HashMap<>();
                    prospectionRowMap.put("项目ID", projectID);
                    prospectionRowMap.put("侧段", hole.getProjectStage());
                    prospectionRowMap.put("ID", prospectionId);
                    prospectionRowMap.put("代号", hole.getHoleIdPart1());
                    prospectionRowMap.put("名称", hole.getHoleId());
                    prospectionRowMap.put("冠词", hole.getArticle());
                    prospectionRowMap.put("里程", hole.getMileage());
                    prospectionRowMap.put("偏移量", hole.getOffset());
                    prospectionRowMap.put("高程", hole.getHoleElevation());
                    prospectionRowMap.put("经距X", hole.getLongitudeDistance());
                    prospectionRowMap.put("纬距Y", hole.getLatitudeDistance());
                    prospectionRowMap.put("位置描述", "");
                    prospectionRowMap.put("工程名称", hole.getProjectName());
                    prospectionRowMap.put("深度", hole.getActuralDepth());
                    prospectionRowMap.put("记录日期", hole.getRecordDate());
                    prospectionRowMap.put("复核日期", hole.getReviewDate());
                    prospectionRowMap.put("记录者", hole.getRecorderName());
                    prospectionRowMap.put("复核者", hole.getReviewerName());
                    prospectionRowMap.put("图片", hole.getPhoto());
                    prospectionRowMap.put("音像", "");
                    prospectionRowMap.put("附注", hole.getNote());
                    prospectionRowMap.put("锁定", 0);
                    prospectionTable.asUpdateRow(prospectionRowMap);
                    //Todo id not refer prospectionId
                    for (Row row : holeTable) {
                        if (row.get("ID").equals(prospectionId)) {
                            holeTable.deleteRow(row);
                        }
                    }
                    for (Row row : sptTable) {
                        if (row.get("ID").equals(prospectionId)) {
                            sptTable.deleteRow(row);
                        }
                    }
                    for (Row row : groundTable) {
                        if (row.get("ID").equals(prospectionId)) {
                            groundTable.deleteRow(row);
                        }
                    }
                }else {
                    Object[] objects = prospectionTable.addRow(projectID, hole.getProjectStage(), "", hole.getHoleIdPart1(), hole.getHoleId(), hole.getArticle(), hole.getMileage(), hole.getOffset(), hole.getHoleElevation(), hole.getLongitudeDistance(),
                            hole.getLatitudeDistance(), "位置描述", hole.getProjectName(), hole.getActuralDepth(), hole.getRecordDate(), hole.getRecordDate(), hole.getRecorderName(), hole.getReviewerName(),
                            "", "", hole.getNote(), 0);
                    prospectionId = String.valueOf(objects[2]);
                }



                holeTable.addRow(projectID, prospectionId, hole.getInitialLevel(), hole.getInitialLevelMeasuringDate(), hole.getStableLevel(), hole.getStableLevelMeasuringDate(), hole.getStartDate(), hole.getEndDate(), "0");

                ArrayList<RigEvent> rigEvents = hole.getRigList();
                for (RigEvent currRigEvent : rigEvents) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    //导入标贯表 0; -> 标贯,1 -> 动探
                    if (currRigEvent instanceof SPTRig) {
                        SPTRig rigEvent = (SPTRig) currRigEvent;
                        sptTable.addRow(projectID, "", prospectionId, 0, rigEvent.getPenetrationFrom(), rigEvent.getPenetrationTo(), rigEvent.getPenetration1Count(), rigEvent.getPenetrationLength());
                        //导入地层表
                        hashMap.put("项目ID", projectID);
                        hashMap.put("ID", prospectionId);
                        hashMap.put("稠度", rigEvent.getGroundDensity());
                        hashMap.put("层深", 0.0);//Todo
                        hashMap.put("岩性描述", rigEvent.getSpecialNote());
                        groundTable.addRowFromMap(hashMap);

                    } else if (currRigEvent instanceof DSTRig) {
                        DSTRig rigEvent = (DSTRig) currRigEvent;
                        for (DSTRig.DynamicSoundingEvent dynamicSoundingEvent : rigEvent.getDynamicSoundingEvents()) {
                            double startDepth = dynamicSoundingEvent.getDigDepth();
                            double endDepth = dynamicSoundingEvent.getDigDepth() + dynamicSoundingEvent.getPenetration() / 100;
                            sptTable.addRow(projectID, "", prospectionId, 1, startDepth, endDepth, dynamicSoundingEvent.getHammeringCount());

                            //导入地层表
                            hashMap.put("项目ID", projectID);
                            hashMap.put("ID", prospectionId);
                            hashMap.put("稠度", dynamicSoundingEvent.getCompactness());
                            hashMap.put("岩性描述", rigEvent.getSpecialNote());
                            hashMap.put("层深", 0.0);//Todo
                            groundTable.addRowFromMap(hashMap);
                        }
                    } else {
                        //导入地层表
                        hashMap.put("项目ID", projectID);
                        hashMap.put("ID", prospectionId);
                        hashMap.put("稠度", currRigEvent.getGroundDensity());
                        hashMap.put("岩性描述", currRigEvent.getSpecialNote());
                        hashMap.put("层深", 0.0);//Todo
                        groundTable.addRowFromMap(hashMap);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        database.close();

        return true;

    }

}


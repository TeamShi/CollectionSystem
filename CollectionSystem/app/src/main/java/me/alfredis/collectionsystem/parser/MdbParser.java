package me.alfredis.collectionsystem.parser;


import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.CursorBuilder;
import com.healthmarketscience.jackcess.DataType;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;
import com.healthmarketscience.jackcess.util.ImportUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

import me.alfredis.collectionsystem.Utility;
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


    public static boolean parse(String outPath, ArrayList<Hole> holes) throws IOException {
        try {
            File dbFile = new File("C:\\Users\\jishshi\\Desktop\\数据包\\Data\\DlcGeoInfo.mdb");
            database = DatabaseBuilder.open(dbFile);

            for (Hole hole : holes) {
                //导入项目表
                Table projectTable = database.getTable("项目表");
                int projectID = 0;
                projectTable.addRow(projectID, hole.getProjectName(), hole.getHoleIdPart1(), hole.getArticle());//  项目ID自增
                for (Row row : CursorBuilder.createCursor(projectTable.getPrimaryKeyIndex())) {
                    projectID = (int) row.get("项目ID");
                }

                //导入勘察点表（Jz）
                Table prospectionTable = database.getTable("勘察点");
                Object [] objects = prospectionTable.addRow(projectID, hole.getProjectStage(), "", hole.getHoleIdPart1(), hole.getHoleId(), hole.getArticle(), hole.getMileage(), hole.getOffset(), hole.getHoleElevation(), hole.getLongitudeDistance(),
                        hole.getLatitudeDistance(), "位置描述", hole.getProjectName(), hole.getActuralDepth(), hole.getRecordDate(), hole.getRecordDate(), hole.getRecorderName(), hole.getReviewerName(),
                        "", "", hole.getNote(), 0);
                String prospectionId = objects[2].toString();

                //导入钻孔表`
                Table holeTable = database.getTable("钻孔");
                holeTable.addRow(projectID,prospectionId,hole.getInitialLevel(),hole.getInitialLevelMeasuringDate(),hole.getStableLevel(),hole.getStableLevelMeasuringDate(),hole.getStartDate(),hole.getEndDate(),"0");

                //导入标贯表 0; -> 标贯,1 -> 动探
                Table sptTable = database.getTable("标贯表");
                ArrayList<RigEvent> rigEvents = hole.getRigLists();
                for (RigEvent currRigEvent :rigEvents) {
                    if (currRigEvent instanceof SPTRig) {
                        SPTRig rigEvent = (SPTRig) currRigEvent;
                        sptTable.addRow(projectID,"",prospectionId,0,rigEvent.getPenetrationFrom(),rigEvent.getPenetrationTo(),rigEvent.getPenetration1Count(),rigEvent.getPenetrationLength());
                    } else if (currRigEvent instanceof DSTRig) {
                         DSTRig rigEvent = (DSTRig) currRigEvent;
                         for(DSTRig.DynamicSoundingEvent dynamicSoundingEvent : rigEvent.getDynamicSoundingEvents()) {
                             double startDepth = dynamicSoundingEvent.getDigDepth();
                             double endDepth = dynamicSoundingEvent.getDigDepth() + dynamicSoundingEvent.getPenetration();
                             sptTable.addRow(projectID,"",prospectionId,1,startDepth,endDepth,dynamicSoundingEvent.getHammeringCount());
                         }
                    } else {
                        // do nothing
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

    private  static boolean convertProjectTable(ArrayList<Hole> holes) throws IOException {
        for (Hole hole : holes) {
            //导入项目表
            Table projectTable = database.getTable("项目表");
            int LargestKey = 0;
            for (Row row : CursorBuilder.createCursor(projectTable.getPrimaryKeyIndex())) {
                LargestKey = (int) row.get("项目ID");
            }
            try {
                projectTable.addRow(LargestKey + 1, hole.getProjectName(), hole.getHoleIdPart1(), hole.getArticle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private  static boolean convertProspectionTable(ArrayList<Hole> holes) throws IOException {
        for (Hole hole : holes) {
            //导入项目表
            Table table = database.getTable("勘察点");
            int LargestKey = 0;
            for (Row row : CursorBuilder.createCursor(table.getPrimaryKeyIndex())) {
                LargestKey = (int) row.get("项目ID");
            }
            try {
                table.addRow(LargestKey + 1, hole.getProjectName(), hole.getHoleIdPart1(), hole.getArticle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

}


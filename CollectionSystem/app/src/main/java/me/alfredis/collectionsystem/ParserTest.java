package me.alfredis.collectionsystem;

import java.io.File;
import java.util.ArrayList;

import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.parser.HtmlParser;


/**
 * Created by jishshi on 2015/5/9.
 */
public class ParserTest {
    public static String[] ROW_DRILL_DATA_HEADER = new String[]{"班次/人数", "日期", "作业项目", "开始时间", "结束时间", "钻杆编号"};

    public static String BASIC_RIG_EVENT_TEMPLATE = "." +File.separator+"template"+File.separator+"RigEventTable.html";

    private static String OutPath =  "." + File.separator + "out" + File.separator;

    public static void main(String[] args) throws Exception {
        //generate date to Test
        Hole hole = (new Hole(Hole.HoleIdPart1Type.JC, "2015", "1", "1", "projectName1", Hole.ProjectStageType.I, Hole.ArticleType.ACK, 55, 55, 13, 22, 23, "alfred", "alfred", "testnote", 11));
        RigEvent rigEvent = new RigEvent("1", "pn", 123, 123.45, 123.45, 123.45, 123.45, 123.45, 123.45, "test note");
        hole.setRigLists(new ArrayList<RigEvent>());
        ArrayList<RigEvent> rigEvents = hole.getRigLists();
        rigEvents.add(rigEvent);
        ArrayList<Hole> holes = new ArrayList<Hole>();
        holes.add(hole);

        //html output
        HtmlParser.parse(OutPath,holes);

        //excel output
//        String[][]  rowDrillData = ConvertUtil.convertList2Array(holes,ROW_DRILL_DATA_HEADER) ;
        //创建钻探原始数据表xls
//        XlsParser.write(OutPath + "test.xls", rowDrillData, "钻孔表");


    }

}

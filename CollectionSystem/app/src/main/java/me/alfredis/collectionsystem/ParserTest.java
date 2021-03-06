package me.alfredis.collectionsystem;

import java.io.File;
import java.util.ArrayList;

import me.alfredis.collectionsystem.ConfigurationManager;
import me.alfredis.collectionsystem.datastructure.CasedRig;
import me.alfredis.collectionsystem.datastructure.Configuration;
import me.alfredis.collectionsystem.datastructure.DSTRig;
import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.datastructure.SPTRig;
import me.alfredis.collectionsystem.parser.HtmlParser;
import me.alfredis.collectionsystem.parser.MdbParser;
import me.alfredis.collectionsystem.parser.XlsParser;


/**
 * Created by jishshi on 2015/5/9.
 */
public class ParserTest {

    public static String BASIC_RIG_EVENT_TEMPLATE = "" +File.separator+"template"+File.separator+ "RigEventTable.html";

    private static String OutPath =  "." + File.separator + "out" + File.separator;

    public static void main(String[] args) throws Exception {
        //generate date to Test
        Hole hole = (new Hole(Hole.HoleIdPart1Type.JZ, "2015", "1", "1", "projectName1", Hole.ProjectStageType.I, Hole.ArticleType.ACK, 55233, 505122, 13, 22, 23, "alfred", "alfred", "testnote", 11));
        RigEvent rigEvent = new RigEvent("1", "pn", 123, 123.45, 123.45, 123.45, 123.45, 123.45, 123.45, "test note");
        hole.setRigList(new ArrayList<RigEvent>());
        ArrayList<RigEvent> rigEvents = hole.getRigList();
        rigEvents.add(rigEvent);

        SPTRig sptRigEvent = new SPTRig();
        sptRigEvent.setPenetrationDiameter(0);
        sptRigEvent.setPenetrationLength(0);
        rigEvents.add(sptRigEvent);

        DSTRig dstRig = new DSTRig();
        dstRig.setDynamicSoundingType(RigEvent.DynamicSoundingType.HEAVY);
        dstRig.setSoundingDiameter(0);
        dstRig.setSoundinglength(0);
        DSTRig.DynamicSoundingEvent dynamicSoundingEvent = new DSTRig.DynamicSoundingEvent();
        dstRig.getDynamicSoundingEvents().add(dynamicSoundingEvent);
        rigEvents.add(dstRig);

        DSTRig dstRig1 = new DSTRig();
        dstRig1.setDynamicSoundingType(RigEvent.DynamicSoundingType.HEAVY);
        dstRig1.setSoundingDiameter(0);
        dstRig1.setSoundinglength(0);
        DSTRig.DynamicSoundingEvent dynamicSoundingEvent1 = new DSTRig.DynamicSoundingEvent();
        dstRig1.getDynamicSoundingEvents().add(dynamicSoundingEvent1);
        rigEvents.add(dstRig1);//add twice

        RigEvent restEvent = new RigEvent();
        restEvent.setProjectName("搬家移孔、下雨停工，其他");
        rigEvents.add(restEvent);

        CasedRig casedRig = new CasedRig();
        casedRig.setDadoType("type");
        casedRig.setCasedDiameter(0.2);
        casedRig.setCasedId(1);
        casedRig.setCasedLength(12);
        casedRig.setCasedTotalLength(23);
        casedRig.setCasedSituation("cased sit");
        casedRig.setSpecialNote("cased specital note");
        rigEvents.add(casedRig);

        ArrayList<Hole> holes = new ArrayList<Hole>();
        holes.add(hole);

        //html output
        HtmlParser.parseToLocal(OutPath, holes, "./template/");

        //excel output
//        XlsParser.parse(OutPath+"test.xls",holes);

        //excel input
//        ArrayList<Hole> list = XlsParser.parse(OutPath + "test.xls");

        //mdb output
//        MdbParser.parse(new File("C:\\Users\\jishshi\\Desktop\\数据包\\Data\\DlcGeoInfo.mdb"), holes);

        //export config
//        ConfigurationManager.exportConfig(new Configuration(),OutPath+"config.ser");

        //import config
        File file = new File(OutPath+"config.ser");
        ConfigurationManager.loadConfig(file);
    }

}

package me.alfredis.collectionsystem.datastructure;

import java.text.ParseException;
import java.util.Calendar;

import me.alfredis.collectionsystem.Utility;

import static me.alfredis.collectionsystem.Utility.formatCalendarDateString;

/**
 * Created by jishshi on 2015/6/16.
 */
public class RigView {

    private String holeId; //勘察点ID

    private String rigType;

    private String classPeopleCount;      //班次/人数

    private Calendar date;                  //日期
    private Calendar startTime;             //开始时间
    private Calendar endTime;               //结束时间
    private String timeInterval;          //计时

    private String projectName;             //作业项目


    //钻杆组成
    private String drillPipeId;                //钻杆编号
    private String drillPipeLength;         //钻杆长度
    private String cumulativeLength;        //累计长度

    //岩芯管
    private String coreBarreliDiameter;     //岩芯管直径
    private String coreBarreliLength;       //岩芯管长度

    //钻头组成
    private String drillType;               //钻头类型
    private String drillDiameter;           //钻头直径
    private String drillLength;             //钻头长度

    //进尺
    private String drillToolTotalLength;    //钻具总长
    private String drillToolRemainLength;   //钻杆余长
    private String roundTripMeterage;       //回次进尺
    private String cumulativeMeterage;      //累积进尺

    //护壁措施
    private String dadoType;                //护壁类型
    private String casedId;                    //编号
    private String casedDiameter;           //直径
    private String casedLength;             //长度
    private String casedTotalLength;        //总长

    //孔内情况
    private String casedSituation;

    //岩芯采取
    private String rockCoreId;                //岩芯编号
    private String rockCoreLength;          //岩芯长度
    private String rockCoreRecovery;        //岩芯采取旅

    //土样
    private String earthNo;
    private String earthDiameter;
    private String earthDepth;
    private String earthCount;

    //水样
    private String waterNo;
    private String waterDepth;
    private String waterCount;

    //地层
    private String groudNo;
    private String groundDepth;//底层深度
    private String groundDepthDiff;//层厚 本次累计进尺 -上次累计进尺
    private String groundNote;//名称及岩性
    private String groundClass;//岩层等级

    //地下水
    private String initialLevel;                //初见水位
    private String stableLevel;                 //稳定水位
    private String levelChange;
    private String measureDatesInterval;

    public RigView(Hole hole, RigEvent rig) {
        if (rig instanceof SPTRig) {
            this.rigType = "SPT";
        } else if (rig instanceof DSTRig) {
            this.rigType = "DST";
        } else if (rig instanceof CasedRig) {
            this.rigType = "CASED";
        } else {
            this.rigType = "Normal";
        }
        this.holeId = hole.getHoleId();

        this.classPeopleCount = rig.getClassPeopleCount();

        this.date = rig.getDate();
        this.startTime = rig.getStartTime();
        this.endTime = rig.getEndTime();
        this.timeInterval = Utility.calculateTimeSpan(rig.getStartTime(),rig.getEndTime());

        this.projectName = rig.getProjectName();

        this.drillPipeId = String.valueOf(rig.getDrillPipeId());
        this.drillPipeLength = String.valueOf(rig.getDrillPipeLength());
        this.cumulativeLength = String.valueOf(rig.getCumulativeLength());

        this.coreBarreliDiameter = String.valueOf(rig.getCoreBarreliDiameter());
        this.coreBarreliLength = String.valueOf(rig.getCoreBarreliLength());

        this.drillPipeId = String.valueOf(rig.getDrillPipeId());
        this.drillDiameter = String.valueOf(rig.getDrillDiameter());
        this.drillPipeLength = String.valueOf(rig.getDrillPipeLength());

        //进尺
        this.drillToolTotalLength = String.valueOf(rig.getDrillToolTotalLength());
        this.drillToolRemainLength = String.valueOf(rig.getDrillToolRemainLength());
        this.roundTripMeterage = String.valueOf(rig.getRoundTripMeterage());
        this.cumulativeMeterage = String.valueOf(rig.getCumulativeMeterage());

        //护壁措施
        if (rigType.equals("CASED")) {
            CasedRig casedRig = (CasedRig) rig;
            this.dadoType = casedRig.getDadoType();
            this.casedId = String.valueOf(casedRig.getCasedId());
            this.casedDiameter = String.valueOf(casedRig.getCasedDiameter());
            this.casedLength = String.valueOf(casedRig.getCasedLength());
            this.casedTotalLength = String.valueOf(casedRig.getCasedTotalLength());
            //孔内情况
            this.casedSituation = casedRig.getCasedSituation();
            this.groundNote = "";
            this.holeNote = casedRig.getSpecialNote();
        } else {
            this.dadoType = "";
            this.casedId = "";
            this.casedDiameter = "";
            this.casedLength = "";
            this.casedLength = "";
            //孔内情况
            this.casedSituation = "";
            this.groundNote = rig.getSpecialNote();
            this.holeNote = hole.getNote();
        }

        //岩心采取
        if (rigType.equals("Normal")) {
            this.rockCoreId = rig.getRockCoreId();
            this.rockCoreLength = String.valueOf(rig.getRockCoreLength());
            this.rockCoreRecovery = String.valueOf(rig.getRockCoreRecovery());
        } else {
            this.rockCoreId = "";
            this.rockCoreLength = "";
            this.rockCoreRecovery = "";
        }

        //土样 TODO
        this.earthNo = "";
        this.earthDepth = "";
        this.earthDiameter = "";
        this.earthCount = "";

        //水样
        this.waterNo = "";
        this.waterDepth = "";
        this.waterCount = "";

        //地层
        this.groudNo = "";//TODO 编号, 四类普通钻,编号加1
        this.groundDepth = String.valueOf(rig.getCumulativeMeterage());//底层深度 本次累计进尺
        this.groundDepthDiff = String.valueOf(rig.getRoundTripMeterage()); //TODO 回次进尺
//        this.groundNote = rig.getSpecialNote();
        this.groundClass = "";

        try {
            int intervalDays = Utility.getIntervalDays(hole.getInitialLevelMeasuringDate(), hole.getStableLevelMeasuringDate());
            this.measureDatesInterval = String.valueOf(intervalDays * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.initialLevel = String.valueOf(hole.getInitialLevel());
        this.stableLevel = String.valueOf(hole.getStableLevel());
        double levelDiff = hole.getStableLevel() - hole.getInitialLevel();
        String levelChange = null;
        if (levelDiff > 0) {
            levelChange = "上升";
        } else if (levelDiff < 0) {
            levelChange = "下降";
        } else {
            levelChange = "未变";
        }
        this.levelChange = levelChange;

    }


    public String getHoleId() {
        return holeId;
    }

    public void setHoleId(String holeId) {
        this.holeId = holeId;
    }

    //特殊情况记录
    private String holeNote;

    public String getDrillPipeLength() {
        return drillPipeLength;
    }

    public void setDrillPipeLength(String drillPipeLength) {
        this.drillPipeLength = drillPipeLength;
    }

    public String getRigType() {
        return rigType;
    }

    public void setRigType(String rigType) {
        this.rigType = rigType;
    }

    public String getClassPeopleCount() {
        return classPeopleCount;
    }

    public void setClassPeopleCount(String classPeopleCount) {
        this.classPeopleCount = classPeopleCount;
    }

    public String getDate() {
        return Utility.formatCalendarDateString(date, "MM/dd");
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getStartTime() {
        return Utility.formatCalendarDateString(startTime, "hh时mm分");
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return Utility.formatCalendarDateString(endTime, "hh时mm分");
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDrillPipeId() {
        return drillPipeId;
    }

    public void setDrillPipeId(String drillPipeId) {
        this.drillPipeId = drillPipeId;
    }

    public String getCumulativeLength() {
        return cumulativeLength;
    }

    public void setCumulativeLength(String cumulativeLength) {
        this.cumulativeLength = cumulativeLength;
    }

    public String getCoreBarreliDiameter() {
        return coreBarreliDiameter;
    }

    public void setCoreBarreliDiameter(String coreBarreliDiameter) {
        this.coreBarreliDiameter = coreBarreliDiameter;
    }

    public String getCoreBarreliLength() {
        return coreBarreliLength;
    }

    public void setCoreBarreliLength(String coreBarreliLength) {
        this.coreBarreliLength = coreBarreliLength;
    }

    public String getDrillType() {
        return drillType;
    }

    public void setDrillType(String drillType) {
        this.drillType = drillType;
    }

    public String getDrillDiameter() {
        return drillDiameter;
    }

    public void setDrillDiameter(String drillDiameter) {
        this.drillDiameter = drillDiameter;
    }

    public String getDrillLength() {
        return drillLength;
    }

    public void setDrillLength(String drillLength) {
        this.drillLength = drillLength;
    }

    public String getDrillToolTotalLength() {
        return drillToolTotalLength;
    }

    public void setDrillToolTotalLength(String drillToolTotalLength) {
        this.drillToolTotalLength = drillToolTotalLength;
    }

    public String getDrillToolRemainLength() {
        return drillToolRemainLength;
    }

    public void setDrillToolRemainLength(String drillToolRemainLength) {
        this.drillToolRemainLength = drillToolRemainLength;
    }

    public String getRoundTripMeterage() {
        return roundTripMeterage;
    }

    public void setRoundTripMeterage(String roundTripMeterage) {
        this.roundTripMeterage = roundTripMeterage;
    }

    public String getCumulativeMeterage() {
        return cumulativeMeterage;
    }

    public void setCumulativeMeterage(String cumulativeMeterage) {
        this.cumulativeMeterage = cumulativeMeterage;
    }

    public String getDadoType() {
        return dadoType;
    }

    public void setDadoType(String dadoType) {
        this.dadoType = dadoType;
    }

    public String getCasedId() {
        return casedId;
    }

    public void setCasedId(String casedId) {
        this.casedId = casedId;
    }

    public String getCasedDiameter() {
        return casedDiameter;
    }

    public void setCasedDiameter(String casedDiameter) {
        this.casedDiameter = casedDiameter;
    }

    public String getCasedLength() {
        return casedLength;
    }

    public void setCasedLength(String casedLength) {
        this.casedLength = casedLength;
    }

    public String getCasedTotalLength() {
        return casedTotalLength;
    }

    public void setCasedTotalLength(String casedTotalLength) {
        this.casedTotalLength = casedTotalLength;
    }

    public String getCasedSituation() {
        return casedSituation;
    }

    public void setCasedSituation(String casedSituation) {
        this.casedSituation = casedSituation;
    }

    public String getRockCoreId() {
        return rockCoreId;
    }

    public void setRockCoreId(String rockCoreId) {
        this.rockCoreId = rockCoreId;
    }

    public String getRockCoreLength() {
        return rockCoreLength;
    }

    public void setRockCoreLength(String rockCoreLength) {
        this.rockCoreLength = rockCoreLength;
    }

    public String getRockCoreRecovery() {
        return rockCoreRecovery;
    }

    public void setRockCoreRecovery(String rockCoreRecovery) {
        this.rockCoreRecovery = rockCoreRecovery;
    }

    public String getEarthNo() {
        return earthNo;
    }

    public void setEarthNo(String earthNo) {
        this.earthNo = earthNo;
    }

    public String getEarthDiameter() {
        return earthDiameter;
    }

    public void setEarthDiameter(String earthDiameter) {
        this.earthDiameter = earthDiameter;
    }

    public String getEarthDepth() {
        return earthDepth;
    }

    public void setEarthDepth(String earthDepth) {
        this.earthDepth = earthDepth;
    }

    public String getEarthCount() {
        return earthCount;
    }

    public void setEarthCount(String earthCount) {
        this.earthCount = earthCount;
    }

    public String getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(String waterNo) {
        this.waterNo = waterNo;
    }

    public String getWaterDepth() {
        return waterDepth;
    }

    public void setWaterDepth(String waterDepth) {
        this.waterDepth = waterDepth;
    }

    public String getWaterCount() {
        return waterCount;
    }

    public void setWaterCount(String waterCount) {
        this.waterCount = waterCount;
    }

    public String getGroudNo() {
        return groudNo;
    }

    public void setGroudNo(String groudNo) {
        this.groudNo = groudNo;
    }

    public String getGroundDepth() {
        return groundDepth;
    }

    public void setGroundDepth(String groundDepth) {
        this.groundDepth = groundDepth;
    }

    public String getGroundDepthDiff() {
        return groundDepthDiff;
    }

    public void setGroundDepthDiff(String groundDepthDiff) {
        this.groundDepthDiff = groundDepthDiff;
    }

    public String getGroundNote() {
        return groundNote;
    }

    public void setGroundNote(String groundNote) {
        this.groundNote = groundNote;
    }

    public String getGroundClass() {
        return groundClass;
    }

    public void setGroundClass(String groundClass) {
        this.groundClass = groundClass;
    }

    public String getInitialLevel() {
        return initialLevel;
    }

    public void setInitialLevel(String initialLevel) {
        this.initialLevel = initialLevel;
    }

    public String getStableLevel() {
        return stableLevel;
    }

    public void setStableLevel(String stableLevel) {
        this.stableLevel = stableLevel;
    }

    public String getLevelChange() {
        return levelChange;
    }

    public void setLevelChange(String levelChange) {
        this.levelChange = levelChange;
    }

    public String getMeasureDatesInterval() {
        return measureDatesInterval;
    }

    public void setMeasureDatesInterval(String measureDatesInterval) {
        this.measureDatesInterval = measureDatesInterval;
    }

    public String getHoleNote() {
        return holeNote;
    }

    public void setHoleNote(String holeNote) {
        this.holeNote = holeNote;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }
}

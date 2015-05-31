package me.alfredis.collectionsystem.datastructure;

import java.util.Calendar;

/**
 * Created by Alfred on 15/5/4.
 */
public class RigEvent {
    protected String holeId ; //勘察点ID
    protected  String eventId;  //作业ID
    protected String classPeopleCount;        //班次/人数
    protected Calendar date;                  //日期
    protected String projectName;             //作业项目

    //时间
    protected Calendar startTime;             //开始时间
    protected Calendar endTime;               //结束时间


    //钻杆组成
    protected int drillPipeId;                //钻杆编号
    protected double drillPipeLength;         //钻杆长度
    protected double cumulativeLength;        //累计长度


    //岩芯管
    protected double coreBarreliDiameter;     //岩芯管直径
    protected double coreBarreliLength;       //岩芯管长度

    //钻头组成
    protected String drillType;               //钻头类型
    protected double drillDiameter;           //钻头直径
    protected double drillLength;             //钻头长度

    //贯入器组成
    protected double penetrationDiameter; //贯入器直径
    protected double penetrationLength; //贯入器长度

    //动探类型
    protected DynamicSoundingType dynamicSoundingType;
    //探头组成
    protected double soundingDiameter; //探头直径
    protected double soundinglength; //探头长度

    //进尺
    protected double drillToolTotalLength;    //钻具总长
    protected double drillToolRemainLength;   //钻杆余长
    protected double roundTripMeterage;       //回次进尺
    protected double cumulativeMeterage;      //累积进尺

    //岩芯采取
    protected String rockCoreId;                //岩芯编号
    protected double rockCoreLength;          //岩芯长度
    protected double rockCoreRecovery;        //岩芯采取旅

    //地层
    protected double startDepth;              //本钻起深度 TODO 计算深度差?
    protected double endDepth;                //本钻止深度
    protected String groundName;              //岩土名称
    protected String groundColor;             //岩土颜色
    protected String groundDensity;           //岩土稠密度
    protected String groundSaturation;       //岩土饱和度
    protected String groundWeathering;       //岩石风化程度

    protected String Note;                     //TODO 岩土岩性?特殊情况记录

    public RigEvent() {
        this("1", "pn", 123, 123.45, 123.45, 123.45, 123.45, 123.45, 123.45, "test note");

        Calendar c = Calendar.getInstance();

        this.date = c;
        this.startTime = c;
        this.endTime = c;

        this.drillPipeId = 0;
        this.drillPipeLength = 0;
        this.cumulativeLength = 0;

        this.drillToolTotalLength = 0;
        this.drillToolRemainLength = 0;
        this.roundTripMeterage = 0;
        this.cumulativeMeterage = 0;

        this.classPeopleCount = "";
        this.drillType = "";
        this.rockCoreId = "";
    }

    public enum DynamicSoundingType {
        //动探类型： 轻型，重型，超重型
        LIGHT, HEAVY, SUPERHEAVY
    }

    public enum ProjectNameType {
        //作业类型： 合钻，标贯，动探
        NormalRig, StandardPenetration, DynamicSounding
    }
    public RigEvent(String classPeopleCount, String projectName, int drillPipeId, double drillPipeLength, double cumulativeLength, double drillToolTotalLength, double drillToolRemainLength, double roundTripMeterage, double cumulativeMeterage, String note) {
        Calendar c = Calendar.getInstance();
        this.classPeopleCount = classPeopleCount;
        this.date = c;
        this.startTime = c;
        this.endTime = c;
        this.projectName = projectName;
        this.drillPipeId = drillPipeId;
        this.drillPipeLength = drillPipeLength;
        this.cumulativeLength = cumulativeLength;
        this.drillToolTotalLength = drillToolTotalLength;
        this.drillToolRemainLength = drillToolRemainLength;
        this.roundTripMeterage = roundTripMeterage;
        this.cumulativeMeterage = cumulativeMeterage;
        Note = note;
    }

    public String getClassPeopleCount() {
        return classPeopleCount;
    }

    public void setClassPeopleCount(String classPeopleCount) {
        this.classPeopleCount = classPeopleCount;
    }

    public void setId(String classPeopleCount) {
        this.classPeopleCount = classPeopleCount;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public int getDrillPipeId() {
        return drillPipeId;
    }

    public void setDrillPipeId(int drillPipeId) {
        this.drillPipeId = drillPipeId;
    }

    public double getDrillPipeLength() {
        return drillPipeLength;
    }

    public void setDrillPipeLength(double drillPipeLength) {
        this.drillPipeLength = drillPipeLength;
    }

    public double getCumulativeLength() {
        return cumulativeLength;
    }

    public void setCumulativeLength(double cumulativeLength) {
        this.cumulativeLength = cumulativeLength;
    }

    public double getCoreBarreliDiameter() {
        return coreBarreliDiameter;
    }

    public void setCoreBarreliDiameter(double coreBarreliDiameter) {
        this.coreBarreliDiameter = coreBarreliDiameter;
    }

    public double getCoreBarreliLength() {
        return coreBarreliLength;
    }

    public void setCoreBarreliLength(double coreBarreliLength) {
        this.coreBarreliLength = coreBarreliLength;
    }

    public String getDrillType() {
        return drillType;
    }

    public void setDrillType(String drillType) {
        this.drillType = drillType;
    }

    public double getDrillDiameter() {
        return drillDiameter;
    }

    public void setDrillDiameter(double drillDiameter) {
        this.drillDiameter = drillDiameter;
    }

    public double getDrillLength() {
        return drillLength;
    }

    public void setDrillLength(double drillLength) {
        this.drillLength = drillLength;
    }

    public double getDrillToolTotalLength() {
        return drillToolTotalLength;
    }

    public void setDrillToolTotalLength(double drillToolTotalLength) {
        this.drillToolTotalLength = drillToolTotalLength;
    }

    public double getDrillToolRemainLength() {
        return drillToolRemainLength;
    }

    public void setDrillToolRemainLength(double drillToolRemainLength) {
        this.drillToolRemainLength = drillToolRemainLength;
    }

    public double getRoundTripMeterage() {
        return roundTripMeterage;
    }

    public void setRoundTripMeterage(double roundTripMeterage) {
        this.roundTripMeterage = roundTripMeterage;
    }

    public double getCumulativeMeterage() {
        return cumulativeMeterage;
    }

    public void setCumulativeMeterage(double cumulativeMeterage) {
        this.cumulativeMeterage = cumulativeMeterage;
    }

    public String getRockCoreId() {
        return rockCoreId;
    }

    public void setRockCoreId(String rockCoreId) {
        this.rockCoreId = rockCoreId;
    }

    public double getRockCoreLength() {
        return rockCoreLength;
    }

    public void setRockCoreLength(double rockCoreLength) {
        this.rockCoreLength = rockCoreLength;
    }

    public double getRockCoreRecovery() {
        return rockCoreRecovery;
    }

    public void setRockCoreRecovery(double rockCoreRecovery) {
        this.rockCoreRecovery = rockCoreRecovery;
    }

    public double getStartDepth() {
        return startDepth;
    }

    public void setStartDepth(double startDepth) {
        this.startDepth = startDepth;
    }

    public double getEndDepth() {
        return endDepth;
    }

    public void setEndDepth(double endDepth) {
        this.endDepth = endDepth;
    }

    public String getGroundName() {
        return groundName;
    }

    public void setGroundName(String groundName) {
        this.groundName = groundName;
    }

    public String getGroundColor() {
        return groundColor;
    }

    public void setGroundColor(String groundColor) {
        this.groundColor = groundColor;
    }

    public String getGroundDensity() {
        return groundDensity;
    }

    public void setGroundDensity(String groundDensity) {
        this.groundDensity = groundDensity;
    }

    public String getGroundSaturation() {
        return groundSaturation;
    }

    public void setGroundSaturation(String groundSaturation) {
        this.groundSaturation = groundSaturation;
    }

    public String getGroundWeathering() {
        return groundWeathering;
    }

    public void setGroundWeathering(String groundWeathering) {
        this.groundWeathering = groundWeathering;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        this.Note = note;
    }

    public double getPenetrationDiameter() {
        return penetrationDiameter;
    }

    public void setPenetrationDiameter(double penetrationDiameter) {
        this.penetrationDiameter = penetrationDiameter;
    }

    public double getPenetrationLength() {
        return penetrationLength;
    }

    public void setPenetrationLength(double penetrationLength) {
        this.penetrationLength = penetrationLength;
    }

    public DynamicSoundingType getDynamicSoundingType() {
        return dynamicSoundingType;
    }

    public void setDynamicSoundingType(DynamicSoundingType dynamicSoundingType) {
        this.dynamicSoundingType = dynamicSoundingType;
    }

    public double getSoundingDiameter() {
        return soundingDiameter;
    }

    public void setSoundingDiameter(double soundingDiameter) {
        this.soundingDiameter = soundingDiameter;
    }

    public double getSoundinglength() {
        return soundinglength;
    }

    public void setSoundinglength(double soundinglength) {
        this.soundinglength = soundinglength;
    }

    public String getHoleId() {
        return holeId;
    }

    public void setHoleId(String holeId) {
        this.holeId = holeId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}

package me.alfredis.collectionsystem.datastructure;

import java.util.Date;

/**
 * Created by Alfred on 15/5/4.
 */
public class RigEvent {
    protected String id;                      //班次/人数
    protected Date date;                      //日期
    protected String projectName;             //作业项目

    protected long startTime;                 //开始时间
    protected long endTime;                   //结束时间

    protected int drillPipeId;                //钻杆编号
    protected double drillPipeLength;         //钻杆长度
    protected double cumulativeLength;        //累计长度

    protected double coreBarreliDiameter;     //岩芯管直径
    protected double coreBarreliLength;       //岩芯管长度

    protected String drillType;               //钻头类型
    protected double drillDiameter;           //钻头直径
    protected double drillLength;             //钻头长度

    protected double drillToolTotalLength;    //钻具总长
    protected double drillToolRemainLength;   //钻杆余长
    protected double roundTripMeterage;       //回次进尺
    protected double cumulativeMeterage;      //累积进尺

    protected String rockCoreId;              //岩芯编号
    protected double rockCoreLength;          //岩芯长度
    protected double rockCoreRecovery;        //岩芯采取旅

    protected double startDepth;              //本钻起深度
    protected double endDepth;                //本钻止深度
    protected String groundName;              //岩土名称
    protected String groundColor;             //岩土颜色
    protected String groundDensity;           //岩土臭密度
    protected String groundSaturability;      //岩土饱和度
    protected String Note;                    //岩土及岩性

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
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

    public String getGroundSaturability() {
        return groundSaturability;
    }

    public void setGroundSaturability(String groundSaturability) {
        this.groundSaturability = groundSaturability;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}

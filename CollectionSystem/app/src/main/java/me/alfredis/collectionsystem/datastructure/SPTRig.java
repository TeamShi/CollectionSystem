package me.alfredis.collectionsystem.datastructure;

import java.io.Serializable;

import me.alfredis.collectionsystem.DataManager;

/**
 * Created by Alfred on 15/5/4.
 */
public class SPTRig extends RigEvent implements Serializable {
    private double penetrationFrom;             //贯入深度自
    private double penetrationTo;               //贯入深度至，一般为上值+0.45

    private double penetration1DepthFrom;       //第一击起深度
    private double penetration1DepthTo;         //第一击止深度
    private int penetration1Count;              //第一击贯入击数
    private double penetration2DepthFrom;       //第二击起深度
    private double penetration2DepthTo;         //第二击止深度
    private int penetration2Count;              //第二击贯入击数
    private double penetration3DepthFrom;       //第三击起深度
    private double penetration3DepthTo;         //第三击止深度
    private int penetration3Count;           //第三击中贯入击数

    private double rig1DepthFrom;               //钻进1深度起
    private double rig1DepthTo;                 //钻进1深度止
    private double rig2DepthFrom;               //钻进2深度起
    private double rig2DepthTo;                 //钻进2深度止
    private double rig3DepthFrom;               //钻进3深度起
    private double rig3DepthTo;                 //钻进3深度止

    private String groundName;                  //岩土名称
    private String groundColor;                 //颜色
    private String groundSaturability;         //饱和度
    private int cumulativeCount;                //累计击数

    private String specialNote;                        //其他特征

    public SPTRig(String id, String projectName, int drillPipeId, double drillPipeLength, double cumulativeLength, double drillToolTotalLength, double drillToolRemainLength, double roundTripMeterage, double cumulativeMeterage, String note) {
        super(id, projectName, drillPipeId, drillPipeLength, cumulativeLength, drillToolTotalLength, drillToolRemainLength, roundTripMeterage, cumulativeMeterage, note);
    }

    public SPTRig() {

    }

    public SPTRig(String holeId) {
        this.projectName = "标贯";

        if (DataManager.getLatestRoundTripDepth(holeId) != -1) {
            this.penetrationFrom = DataManager.getLatestRoundTripDepth(holeId);
            this.penetrationTo = DataManager.getLatestRoundTripDepth(holeId) + 0.45;

            this.penetration1DepthFrom = this.penetrationFrom + 0.15;
            this.penetration1DepthTo = this.penetration1DepthFrom + 0.1;
            this.penetration1Count = 0;
            this.rig1DepthFrom = this.penetrationFrom;
            this.rig1DepthTo = this.penetrationFrom + 0.25;

            this.penetration2DepthFrom = this.penetration1DepthTo;
            this.penetration2DepthTo = this.penetration2DepthFrom + 0.1;
            this.penetration2Count = 0;
            this.rig2DepthFrom = this.penetration2DepthFrom;
            this.rig2DepthTo = this.penetration2DepthTo;

            this.penetration3DepthFrom = this.penetration2DepthTo;
            this.penetration3DepthTo = this.penetration3DepthFrom + 0.1;
            this.penetration3Count = 0;
            this.rig3DepthFrom = this.penetration3DepthFrom;
            this.rig3DepthTo = this.penetration3DepthTo;

            this.groundName = "";
            this.groundColor ="";
            this.groundSaturability ="";
            this.cumulativeCount = 0;

            this.specialNote = "";
        } else {
            this.projectName = "标贯";
            this.penetrationFrom = 0;
            this.penetrationTo = 0;

            this.penetration1DepthFrom = 0;
            this.penetration1DepthTo = 0;
            this.penetration1Count =0;
            this.rig1DepthFrom = 0;
            this.rig1DepthTo =0;

            this.penetration2DepthFrom = 0;
            this.penetration2DepthTo = 0;
            this.penetration2Count = 0;
            this.rig2DepthFrom = 0;
            this.rig2DepthTo = 0;

            this.penetration3DepthFrom = 0;
            this.penetration3DepthTo = 0;
            this.penetration3Count = 0;
            this.rig3DepthFrom = 0;
            this.rig3DepthTo = 0;

            this.groundName = "";
            this.groundColor ="";
            this.groundSaturability ="";
            this.cumulativeCount = 0;

            this.specialNote = "";
        }

    }


    public double getPenetrationFrom() {
        return penetrationFrom;
    }

    public void setPenetrationFrom(double penetrationFrom) {
        this.penetrationFrom = penetrationFrom;
    }

    public double getPenetrationTo() {
        return penetrationTo;
    }

    public void setPenetrationTo(double penetrationTo) {
        this.penetrationTo = penetrationTo;
    }

    public double getPenetration1DepthFrom() {
        return penetration1DepthFrom;
    }

    public void setPenetration1DepthFrom(double penetration1DepthFrom) {
        this.penetration1DepthFrom = penetration1DepthFrom;
    }

    public double getPenetration1DepthTo() {
        return penetration1DepthTo;
    }

    public void setPenetration1DepthTo(double penetration1DepthTo) {
        this.penetration1DepthTo = penetration1DepthTo;
    }

    public int getPenetration1Count() {
        return penetration1Count;
    }

    public void setPenetration1Count(int penetration1Count) {
        this.penetration1Count = penetration1Count;
    }

    public double getPenetration2DepthFrom() {
        return penetration2DepthFrom;
    }

    public void setPenetration2DepthFrom(double penetration2DepthFrom) {
        this.penetration2DepthFrom = penetration2DepthFrom;
    }

    public double getPenetration2DepthTo() {
        return penetration2DepthTo;
    }

    public void setPenetration2DepthTo(double penetration2DepthTo) {
        this.penetration2DepthTo = penetration2DepthTo;
    }

    public int getPenetration2Count() {
        return penetration2Count;
    }

    public void setPenetration2Count(int penetration2Count) {
        this.penetration2Count = penetration2Count;
    }

    public double getPenetration3DepthFrom() {
        return penetration3DepthFrom;
    }

    public void setPenetration3DepthFrom(double penetration3DepthFrom) {
        this.penetration3DepthFrom = penetration3DepthFrom;
    }

    public double getPenetration3DepthTo() {
        return penetration3DepthTo;
    }

    public void setPenetration3DepthTo(double penetration3DepthTo) {
        this.penetration3DepthTo = penetration3DepthTo;
    }

    public int getPenetration3Count() {
        return penetration3Count;
    }

    public void setPenetration3Count(int penetration3Count) {
        this.penetration3Count = penetration3Count;
    }

    public double getRig1DepthFrom() {
        return rig1DepthFrom;
    }

    public void setRig1DepthFrom(double rig1DepthFrom) {
        this.rig1DepthFrom = rig1DepthFrom;
    }

    public double getRig1DepthTo() {
        return rig1DepthTo;
    }

    public void setRig1DepthTo(double rig1DepthTo) {
        this.rig1DepthTo = rig1DepthTo;
    }

    public double getRig2DepthFrom() {
        return rig2DepthFrom;
    }

    public void setRig2DepthFrom(double rig2DepthFrom) {
        this.rig2DepthFrom = rig2DepthFrom;
    }

    public double getRig2DepthTo() {
        return rig2DepthTo;
    }

    public void setRig2DepthTo(double rig2DepthTo) {
        this.rig2DepthTo = rig2DepthTo;
    }

    public double getRig3DepthFrom() {
        return rig3DepthFrom;
    }

    public void setRig3DepthFrom(double rig3DepthFrom) {
        this.rig3DepthFrom = rig3DepthFrom;
    }

    public double getRig3DepthTo() {
        return rig3DepthTo;
    }

    public void setRig3DepthTo(double rig3DepthTo) {
        this.rig3DepthTo = rig3DepthTo;
    }

    @Override
    public String getGroundName() {
        return groundName;
    }

    @Override
    public void setGroundName(String groundName) {
        this.groundName = groundName;
    }

    @Override
    public String getGroundColor() {
        return groundColor;
    }

    @Override
    public void setGroundColor(String groundColor) {
        this.groundColor = groundColor;
    }

    @Override
    public String getGroundSaturation() {
        return groundSaturability;
    }

    @Override
    public void setGroundSaturation(String groundSaturability) {
        this.groundSaturability = groundSaturability;
    }

    public int getCumulativeCount() {
        return cumulativeCount;
    }

    public void setCumulativeCount(int cumulativeCount) {
        this.cumulativeCount = cumulativeCount;
    }

    @Override
    public String getSpecialNote() {
        return note;
    }

    @Override
    public void setSpecialNote(String note) {
        this.note = note;
    }
}

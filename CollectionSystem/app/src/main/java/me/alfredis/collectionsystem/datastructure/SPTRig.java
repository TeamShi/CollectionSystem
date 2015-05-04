package me.alfredis.collectionsystem.datastructure;

/**
 * Created by Alfred on 15/5/4.
 */
public class SPTRig extends RigEvent {
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
    private double penetration3Count;           //第三击中贯入击数

    private double rig1DepthFrom;               //钻进1深度起
    private double rig1DepthTo;                 //钻进1深度止
    private double rig2DepthFrom;               //钻进2深度起
    private double rig2DepthTo;                 //钻进2深度止
    private double rig3DepthFrom;               //钻进3深度起
    private double rig3DepthTo;                 //钻进3深度止

    private String groundName;                  //岩土名称
    private String groundColor;                 //颜色
    private String groundSaturability;          //饱和度
    private int cumulativeCount;                //累计击数

    private String note;                        //其他特征

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

    public double getPenetration3Count() {
        return penetration3Count;
    }

    public void setPenetration3Count(double penetration3Count) {
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
    public String getGroundSaturability() {
        return groundSaturability;
    }

    @Override
    public void setGroundSaturability(String groundSaturability) {
        this.groundSaturability = groundSaturability;
    }

    public int getCumulativeCount() {
        return cumulativeCount;
    }

    public void setCumulativeCount(int cumulativeCount) {
        this.cumulativeCount = cumulativeCount;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public void setNote(String note) {
        this.note = note;
    }
}
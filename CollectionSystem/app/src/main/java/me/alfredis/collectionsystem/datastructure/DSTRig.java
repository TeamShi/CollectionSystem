package me.alfredis.collectionsystem.datastructure;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Alfred on 15/5/5.
 */
public class DSTRig extends RigEvent implements Serializable {
    public DSTRig(String id, String projectName, int drillPipeId, double drillPipeLength, double cumulativeLength, double drillToolTotalLength, double drillToolRemainLength, double roundTripMeterage, double cumulativeMeterage, String note) {
        super(id, projectName, drillPipeId, drillPipeLength, cumulativeLength, drillToolTotalLength, drillToolRemainLength, roundTripMeterage, cumulativeMeterage, note);
    }

    public static class DynamicSoundingEvent implements Serializable {
        public double totalLength;      //探杆总长
        public double digDepth;         //入土深度
        public double penetration;      //贯入度
        public int hammeringCount;      //锤击数
        public String compactness;      //密实度

        public DynamicSoundingEvent() {
            this.compactness = "密实";
        }

        public DynamicSoundingEvent(double totalLength, double digDepth, double penetration, int hammeringCount, String compactness) {
            this.totalLength = totalLength;
            this.digDepth = digDepth;
            this.penetration = penetration;
            this.hammeringCount = hammeringCount;
            this.compactness = compactness;
        }

        public double getTotalLength() {
            return totalLength;
        }

        public void setTotalLength(double totalLength) {
            this.totalLength = totalLength;
        }

        public double getDigDepth() {
            return digDepth;
        }

        public void setDigDepth(double digDepth) {
            this.digDepth = digDepth;
        }

        public double getPenetration() {
            return penetration;
        }

        public void setPenetration(double penetration) {
            this.penetration = penetration;
        }

        public int getHammeringCount() {
            return hammeringCount;
        }

        public void setHammeringCount(int hammeringCount) {
            this.hammeringCount = hammeringCount;
        }

        public String getCompactness() {
            return compactness;
        }

        public void setCompactness(String compactness) {
            this.compactness = compactness;
        }


    }

    private ArrayList<DynamicSoundingEvent> dynamicSoundingEvents;     //动力触探录入
    private String groundName;                                          //岩土名称

    public DSTRig() {
        this.projectName= "动探";
        this.dynamicSoundingEvents = new ArrayList<DynamicSoundingEvent>();
        this.groundName ="groundName";
    }

    public ArrayList<DynamicSoundingEvent> getDynamicSoundingEvents() {
        return dynamicSoundingEvents;
    }

    public void setDynamicSoundingEvents(ArrayList<DynamicSoundingEvent> dynamicSoundingEvents) {
        this.dynamicSoundingEvents = dynamicSoundingEvents;
    }

    @Override
    public String getGroundName() {
        return groundName;
    }

    @Override
    public void setGroundName(String groundName) {
        this.groundName = groundName;
    }
}

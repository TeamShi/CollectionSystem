package me.alfredis.collectionsystem.datastructure;

import java.util.ArrayList;

/**
 * Created by Alfred on 15/5/5.
 */
public class DSTRig extends RigEvent{
    public class DynamicSoundingEvent {
        public double totalLength;      //探杆总长
        public double digDepth;         //入土深度
        public double penetration;      //贯入度
        public int hammeringCount;      //锤击数
        public String compactness;      //密实度
    }

    private ArrayList<DynamicSoundingEvent> dynamicSoundingEvents;      //动力触探录入
    private String groundName;                                          //岩土名称

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

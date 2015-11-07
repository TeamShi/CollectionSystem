package me.alfredis.collectionsystem.datastructure;

import java.io.Serializable;

/**
 * Created by Alfred on 15/11/7.
 */
public class SamplingRig extends RigEvent implements Serializable {
    private String sampleStatus;
    private String samplerType;
    private String sampleId;
    private double sampleDiameter;
    private double sampleStartDepth;
    private double sampleEndDepth;
    private int sampleCount;

    public SamplingRig() {
        sampleStatus = "";
        samplerType = "";
        sampleId = "";
        sampleDiameter = 0;
        sampleStartDepth = 0;
        sampleEndDepth = 0;
        sampleCount = 0;

    }

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public String getSamplerType() {
        return samplerType;
    }

    public void setSamplerType(String samplerType) {
        this.samplerType = samplerType;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public double getSampleDiameter() {
        return sampleDiameter;
    }

    public void setSampleDiameter(double sampleDiameter) {
        this.sampleDiameter = sampleDiameter;
    }

    public double getSampleStartDepth() {
        return sampleStartDepth;
    }

    public void setSampleStartDepth(double sampleStartDepth) {
        this.sampleStartDepth = sampleStartDepth;
    }

    public double getSampleEndDepth() {
        return sampleEndDepth;
    }

    public void setSampleEndDepth(double sampleEndDepth) {
        this.sampleEndDepth = sampleEndDepth;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }
}

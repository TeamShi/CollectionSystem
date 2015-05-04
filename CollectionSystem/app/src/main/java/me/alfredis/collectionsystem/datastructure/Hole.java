package me.alfredis.collectionsystem.datastructure;

import java.util.Date;

/**
 * Created by Alfred on 15/5/4.
 */
public class Hole {
    private String projectName;

    private String holeId;
    private ProjectStageType projectStage;
    private MileageType mileage;
    private double offset;
    private double holeElevation;
    private double initialLevel;
    private double stableLevel;

    private Date startDate;
    private Date endDate;
    private String rigType;
    private String engineType;
    private String pumpType;
    private double designedDepth;
    private Date initialLevelMeasuringDate;
    private Date stableLevelMeasuringDate;
    private double longitudeDistance;
    private double latitudeDistance;

    private String explorationUnit;
    private String machineNumber;
    private double acturalDepth;

    private String photo; //TODO: what is photo

    private String recorderName;
    private Date recodeDate;
    private String squadName;
    private String captainName;

    private String reviewerName;
    private Date reviewDate;

    private String note;

    public enum ProjectStageType {
        I, II, III, IV
    }

    public enum MileageType {
        K, DK, AK, ACK, CDK
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getHoleId() {
        return holeId;
    }

    public void setHoleId(String holeId) {
        this.holeId = holeId;
    }

    public ProjectStageType getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(ProjectStageType projectStage) {
        this.projectStage = projectStage;
    }

    public MileageType getMileage() {
        return mileage;
    }

    public void setMileage(MileageType mileage) {
        this.mileage = mileage;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public double getHoleElevation() {
        return holeElevation;
    }

    public void setHoleElevation(double holeElevation) {
        this.holeElevation = holeElevation;
    }

    public double getInitialLevel() {
        return initialLevel;
    }

    public void setInitialLevel(double initialLevel) {
        this.initialLevel = initialLevel;
    }

    public double getStableLevel() {
        return stableLevel;
    }

    public void setStableLevel(double stableLevel) {
        this.stableLevel = stableLevel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRigType() {
        return rigType;
    }

    public void setRigType(String rigType) {
        this.rigType = rigType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getPumpType() {
        return pumpType;
    }

    public void setPumpType(String pumpType) {
        this.pumpType = pumpType;
    }

    public double getDesignedDepth() {
        return designedDepth;
    }

    public void setDesignedDepth(double designedDepth) {
        this.designedDepth = designedDepth;
    }

    public Date getInitialLevelMeasuringDate() {
        return initialLevelMeasuringDate;
    }

    public void setInitialLevelMeasuringDate(Date initialLevelMeasuringDate) {
        this.initialLevelMeasuringDate = initialLevelMeasuringDate;
    }

    public Date getStableLevelMeasuringDate() {
        return stableLevelMeasuringDate;
    }

    public void setStableLevelMeasuringDate(Date stableLevelMeasuringDate) {
        this.stableLevelMeasuringDate = stableLevelMeasuringDate;
    }

    public double getLongitudeDistance() {
        return longitudeDistance;
    }

    public void setLongitudeDistance(double longitudeDistance) {
        this.longitudeDistance = longitudeDistance;
    }

    public double getLatitudeDistance() {
        return latitudeDistance;
    }

    public void setLatitudeDistance(double latitudeDistance) {
        this.latitudeDistance = latitudeDistance;
    }

    public String getExplorationUnit() {
        return explorationUnit;
    }

    public void setExplorationUnit(String explorationUnit) {
        this.explorationUnit = explorationUnit;
    }

    public String getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(String machineNumber) {
        this.machineNumber = machineNumber;
    }

    public double getActuralDepth() {
        return acturalDepth;
    }

    public void setActuralDepth(double acturalDepth) {
        this.acturalDepth = acturalDepth;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRecorderName() {
        return recorderName;
    }

    public void setRecorderName(String recorderName) {
        this.recorderName = recorderName;
    }

    public Date getRecodeDate() {
        return recodeDate;
    }

    public void setRecodeDate(Date recodeDate) {
        this.recodeDate = recodeDate;
    }

    public String getSquadName() {
        return squadName;
    }

    public void setSquadName(String squadName) {
        this.squadName = squadName;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
}

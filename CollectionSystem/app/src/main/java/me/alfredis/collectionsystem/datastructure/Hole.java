package me.alfredis.collectionsystem.datastructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alfred on 15/5/4.
 */
public class Hole implements Serializable {
    private String projectName;                 //工程名称

    private String holeId;                      //钻孔编号
    private ProjectStageType projectStage;      //测段
    private ArticleType article;                //冠词
    private double mileage;                     //里程
    private double offset;                      //偏移量
    private double holeElevation;               //孔口标高
    private double initialLevel;                //初见水位
    private double stableLevel;                 //稳定水位

    private Date startDate;                     //开工日期
    private Date endDate;                       //完工日期
    private String rigType;                     //钻机类型
    private String engineType;                  //发动机类型
    private String pumpType;                    //水泵类型
    private double designedDepth;               //设计孔深
    private Date initialLevelMeasuringDate;     //？？
    private Date stableLevelMeasuringDate;      //?
    private double longitudeDistance;           //经距
    private double latitudeDistance;            //纬距

    private String explorationUnit;             //勘察单位
    private String machineNumber;               //机组编号
    private double acturalDepth;                //实际孔深

    private String photo; //TODO: what is photo

    private String recorderName;                //记录者
    private Date recodeDate;                    //记录日期
    private String squadName;                   //班长
    private String captainName;                 //机长

    private String reviewerName;                //复核者
    private Date reviewDate;                    //复核日期

    private String note;                        //附注

    private ArrayList<RigEvent> rigLists;       //钻孔作业列表

    public enum ProjectStageType {
        I, II, III, IV
    }

    public enum ArticleType {
        K, DK, AK, ACK, CDK
    }

    public Hole(String holeId,
                String projectName,
                String projectStage,
                String article,
                double mileage,
                double offset,

                double holeElevation,
                double longitudeDistance,
                double latitudeDistance,
                String recorderName,
                Date recordDate,
                String reviewerName,
                Date reviewDate,
                String note,
                double acturalDepth) {
        this.holeId = holeId;
        this.projectName = projectName;
        this.projectStage = ProjectStageType.I;
        this.article = ArticleType.ACK;
        this.mileage = mileage;
        this.offset = offset;
        this.holeElevation = holeElevation;
        this.longitudeDistance = longitudeDistance;
        this.latitudeDistance = latitudeDistance;
        this.recorderName = recorderName;
        this.recodeDate = new Date(43402340);
        this.reviewerName = reviewerName;
        this.reviewDate = new Date(12439014);
        this.note = note;
        this.acturalDepth = acturalDepth;
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

    public ArticleType getArticle() {
        return article;
    }

    public void setArticle(ArticleType article) {
        this.article = article;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
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

    public ArrayList<RigEvent> getRigLists() {
        return rigLists;
    }

    public void setRigLists(ArrayList<RigEvent> rigLists) {
        this.rigLists = rigLists;
    }
}

package me.alfredis.collectionsystem.datastructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Alfred on 15/5/4.
 */
public class Hole implements Serializable {
    private String projectName;                 //工程名称

    @Deprecated
    private String holeId;                      //钻孔编号

    //JC-III14-1-1
    private HoleIdPart1 holeIdPart1;
    private String holeIdPart2Year;
    private String holeIdPart3;
    private String holeIdPart4;
    private ProjectStageType projectStage;      //测段
    private ArticleType article;                //冠词
    private double mileage;                     //里程
    private double offset;                      //偏移量
    private double holeElevation;               //孔口标高
    private double initialLevel;                //初见水位
    private double stableLevel;                 //稳定水位

    private Calendar startDate;                 //开工日期
    private Calendar endDate;                   //完工日期
    private String rigType;                     //钻机类型
    private String engineType;                  //发动机类型
    private String pumpType;                    //水泵类型
    private double designedDepth;               //设计孔深
    private Calendar initialLevelMeasuringDate; //初见水位观测日期
    private Calendar stableLevelMeasuringDate;  //稳定水位观测日期
    private double longitudeDistance;           //经距
    private double latitudeDistance;            //纬距

    private String explorationUnit;             //勘察单位
    private String machineNumber;               //机组编号
    private double acturalDepth;                //实际孔深

    private String photo;                       //TODO: 钻孔示意图 ? 现场照片

    private String recorderName;                //记录者
    private Calendar recordDate;                //记录日期
    private String squadName;                   //班长
    private String captainName;                 //机长

    private String reviewerName;                //复核者
    private Calendar reviewDate;                //复核日期

    private String note;                        //附注

    private ArrayList<RigEvent> rigLists;       //钻孔作业列表

    public enum HoleIdPart1 {
        JC, JZ
    }

    public enum ProjectStageType {
        I, II, III, IV
    }

    public enum ArticleType {
        K, DK, AK, ACK, CDK
    }

    public Hole() {
        Calendar c = Calendar.getInstance();

        this.projectName = "";
        this.holeIdPart1 = HoleIdPart1.JC;
        this.holeIdPart2Year = String.valueOf(c.get(Calendar.YEAR));
        this.holeIdPart3 = "1";
        this.holeIdPart4 = "1";
        this.projectStage = ProjectStageType.I;
        this.article = ArticleType.K;
        this.mileage = 0;
        this.offset = 0;
        this.holeElevation = 0;
        this.initialLevel = 0;
        this.stableLevel = 0;
        this.startDate = c;
        this.endDate = c;
        this.rigType = "";
        this.engineType = "";
        this.pumpType = "";
        this.designedDepth = 0;
        this.initialLevelMeasuringDate = c;
        this.stableLevelMeasuringDate = c;
        this.longitudeDistance = 0;
        this.latitudeDistance = 0;
        this.explorationUnit = "";
        this.machineNumber = "";
        this.acturalDepth = 0;
        this.recorderName = "";
        this.recordDate = c;
        this.recorderName = "";
        this.reviewDate = c;
        this.note = "";
        this.rigLists = new ArrayList<RigEvent>();


    }

    public Hole(HoleIdPart1 holeIdPart1,
                String holeIdPart2Year,
                String holeIdPart3,
                String holeIdPart4,
                String projectName,
                ProjectStageType projectStage,
                ArticleType article,
                double mileage,
                double offset,
                double holeElevation,
                double longitudeDistance,
                double latitudeDistance,
                String recorderName,
                String reviewerName,
                String note,
                double acturalDepth) {
        Calendar c = Calendar.getInstance();

        this.holeIdPart1 = holeIdPart1;
        this.holeIdPart2Year = holeIdPart2Year;
        this.holeIdPart3 = holeIdPart3;
        this.holeIdPart4 = holeIdPart4;
        this.projectName = projectName;
        this.projectStage = projectStage;
        this.article = article;
        this.mileage = mileage;
        this.offset = offset;
        this.holeElevation = holeElevation;
        this.longitudeDistance = longitudeDistance;
        this.latitudeDistance = latitudeDistance;
        this.recorderName = recorderName;
        this.reviewerName = reviewerName;
        this.note = note;
        this.acturalDepth = acturalDepth;
        this.startDate = c;
        this.endDate = c;
        this.initialLevelMeasuringDate = c;
        this.stableLevelMeasuringDate = c;
        this.recordDate = c;
        this.reviewDate = c;
    }

    @Deprecated
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
                String reviewerName,
                String note,
                double acturalDepth) {
        Calendar c = Calendar.getInstance();

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
        this.reviewerName = reviewerName;
        this.note = note;
        this.acturalDepth = acturalDepth;
        this.startDate = c;
        this.endDate = c;
        this.initialLevelMeasuringDate = c;
        this.stableLevelMeasuringDate = c;
        this.recordDate = c;
        this.reviewDate = c;
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

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
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

    public Calendar getInitialLevelMeasuringDate() {
        return initialLevelMeasuringDate;
    }

    public void setInitialLevelMeasuringDate(Calendar initialLevelMeasuringDate) {
        this.initialLevelMeasuringDate = initialLevelMeasuringDate;
    }

    public Calendar getStableLevelMeasuringDate() {
        return stableLevelMeasuringDate;
    }

    public void setStableLevelMeasuringDate(Calendar stableLevelMeasuringDate) {
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

    public Calendar getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Calendar recordDate) {
        this.recordDate = recordDate;
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

    public Calendar getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Calendar reviewDate) {
        this.reviewDate = reviewDate;
    }

    public ArrayList<RigEvent> getRigLists() {
        return rigLists;
    }

    public void setRigLists(ArrayList<RigEvent> rigLists) {
        this.rigLists = rigLists;
    }

    public void setHoleIdPart1(HoleIdPart1 holeIdPart1) {
        this.holeIdPart1 = holeIdPart1;
    }

    public void setHoleIdPart2Year(String holeIdPart2Year) {
        this.holeIdPart2Year = holeIdPart2Year;
    }

    public void setHoleIdPart3(String holeIdPart3) {
        this.holeIdPart3 = holeIdPart3;
    }

    public void setHoleIdPart4(String holeIdPart4) {
        this.holeIdPart4 = holeIdPart4;
    }

    public String getHoleIdPart3() {
        return holeIdPart3;
    }

    public String getHoleIdPart4() {
        return holeIdPart4;
    }

    public String getHoleIdPart2() {
        return projectStage.toString() + holeIdPart2Year;
    }

    public String getHoleId() {
        return holeIdPart1.toString() + "-" + getHoleIdPart2() + "-" + getHoleIdPart3() + "-" + getHoleIdPart4();
    }
}

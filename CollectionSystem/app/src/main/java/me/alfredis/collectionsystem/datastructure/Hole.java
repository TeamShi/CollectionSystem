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
    private HoleIdPart1Type holeIdPart1;      //勘察点名称
    private String holeIdPart1ExtarString;
    private String holeIdPart2Year;
    private String holeIdPart3;
    private String holeIdPart4;
    private ProjectStageType projectStage;      //测段
    private ArticleType article;                //冠词
    private String articleExtraString;
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

    private String photo;                       //钻孔示意图

    private String recorderName;                //记录者
    private Calendar recordDate;                //记录日期
    private String squadName;                   //班长
    private String captainName;                 //机长

    private String reviewerName;                //复核者
    private Calendar reviewDate;                //复核日期

    private String note;                        //附注
    private String position;

    private ArrayList<RigEvent> rigList;       //钻孔作业列表

    private double currentDrillToolLength = 0;      //当前钻具总长
    private double currentRemainToolLength = 0;     //当前钻杆余长
    private double currentTotalInputLength = 0;     //当前累计进尺
    private int currentDrillToolCount = 0;          //当前钻杆个数
    private double lastDrillToolLength = 0;         //当前钻杆长度
    private double currentDrillPipeTotalLength = 0; //当前累计长度

    private Calendar rigInitStartDate;
    private String rigInitClass;

    public enum HoleIdPart1Type {
        JC, JZ, NULL
    }

    public enum ProjectStageType {
        I, II, III, IV
    }

    public enum ArticleType {
        K, DK, AK, ACK, CDK, NULL
    }

    public Hole() {
        Calendar c = Calendar.getInstance();

        this.projectName = "";
        this.holeIdPart1 = HoleIdPart1Type.JC;
        this.holeIdPart1ExtarString = "";
        this.holeIdPart2Year = String.valueOf(c.get(Calendar.YEAR) - 2000);
        this.holeIdPart3 = "1";
        this.holeIdPart4 = "1";
        this.projectStage = ProjectStageType.I;
        this.article = ArticleType.K;
        this.articleExtraString = "";
        this.mileage = 0;
        this.offset = 0;
        this.holeElevation = 0;
        this.initialLevel = 0;
        this.stableLevel = 0;
        this.startDate = c;
        this.endDate = c;
        this.rigType = "XJ-1";
        this.engineType = "S1105";
        this.pumpType = "BW-50";
        this.designedDepth = 0;
        this.initialLevelMeasuringDate = c;
        this.stableLevelMeasuringDate = c;
        this.longitudeDistance = 0;
        this.latitudeDistance = 0;
        this.explorationUnit = "铁四院工勘院地质-队";
        this.machineNumber = "4000";
        this.acturalDepth = 0;
        this.recorderName = "";
        this.recordDate = c;
        this.recorderName = "";
        this.reviewDate = c;
        this.note = "";
        this.rigList = new ArrayList<RigEvent>();
    }

    public Hole(HoleIdPart1Type holeIdPart1,
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

    public String getArticleExtraString() {
        return articleExtraString;
    }

    public void setArticleExtraString(String articleExtraString) {
        this.articleExtraString = articleExtraString;
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

    public ArrayList<RigEvent> getRigList() {
        return rigList;
    }

    public void setRigList(ArrayList<RigEvent> rigList) {
        this.rigList = rigList;
    }

    public void setHoleIdPart1(HoleIdPart1Type holeIdPart1) {
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

    public HoleIdPart1Type getHoleIdPart1() {return holeIdPart1;}

    public String getHoleIdPart1ExtarString() {
        return holeIdPart1ExtarString;
    }

    public void setHoleIdPart1ExtarString(String holeIdPart1ExtarString) {
        this.holeIdPart1ExtarString = holeIdPart1ExtarString;
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
        if (!holeIdPart1.equals(HoleIdPart1Type.NULL)) {
            return holeIdPart1.toString() + "-" + getHoleIdPart2() + "-" + getHoleIdPart3() + "-" + getHoleIdPart4();
        } else {
            return holeIdPart1ExtarString + "-" + getHoleIdPart2() + "-" + getHoleIdPart3() + "-" + getHoleIdPart4();
        }
    }

    public double getCurrentDrillToolLength() {
        return currentDrillToolLength;
    }

    public void setCurrentDrillToolLength(double currentDrillToolLength) {
        this.currentDrillToolLength = currentDrillToolLength;
    }

    public double getCurrentRemainToolLength() {
        return currentRemainToolLength;
    }

    public void setCurrentRemainToolLength(double currentRemainToolLength) {
        this.currentRemainToolLength = currentRemainToolLength;
    }

    public double getCurrentTotalInputLength() {
        return currentTotalInputLength;
    }

    public void setCurrentTotalInputLength(double currentTotalInputLength) {
        this.currentTotalInputLength = currentTotalInputLength;
    }

    public int getCurrentDrillToolCount() {
        return currentDrillToolCount;
    }

    public void setCurrentDrillToolCount(int currentDrillToolCount) {
        this.currentDrillToolCount = currentDrillToolCount;
    }

    public double getLastDrillToolLength() {
        return lastDrillToolLength;
    }

    public void setLastDrillToolLength(double lastDrillToolLength) {
        this.lastDrillToolLength = lastDrillToolLength;
    }

    public double getCurrentDrillPipeTotalLength() {
        return currentDrillPipeTotalLength;
    }

    public void setCurrentDrillPipeTotalLength(double currentDrillPipeTotalLength) {
        this.currentDrillPipeTotalLength = currentDrillPipeTotalLength;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Calendar getRigInitStartDate() {
        return rigInitStartDate;
    }

    public void setRigInitStartDate(Calendar rigInitStartDate) {
        this.rigInitStartDate = rigInitStartDate;
    }

    public String getRigInitClass() {
        return rigInitClass;
    }

    public void setRigInitClass(String rigInitClass) {
        this.rigInitClass = rigInitClass;
    }
}

package me.alfredis.collectionsystem.datastructure;

/**
 * Created by Alfred on 15/5/5.
 */
public class CasedRig extends RigEvent{
    private String dadoType;                //护壁类型
    private int casedId;                    //编号
    private double casedDiameter;           //直径
    private double casedLength;             //长度
    private double casedTotalLength;        //总场

    private String casedSituation;          //孔内情况
    private String specialNote;                    //特殊情况记录

    public CasedRig(String id, String projectName, int drillPipeId, double drillPipeLength, double cumulativeLength, double drillToolTotalLength, double drillToolRemainLength, double roundTripMeterage, double cumulativeMeterage, String specialNote) {
        super(id, projectName, drillPipeId, drillPipeLength, cumulativeLength, drillToolTotalLength, drillToolRemainLength, roundTripMeterage, cumulativeMeterage, specialNote);
    }

    public CasedRig() {};

    public String getDadoType() {
        return dadoType;
    }

    public void setDadoType(String dadoType) {
        this.dadoType = dadoType;
    }

    public int getCasedId() {
        return casedId;
    }

    public void setCasedId(int casedId) {
        this.casedId = casedId;
    }

    public double getCasedDiameter() {
        return casedDiameter;
    }

    public void setCasedDiameter(double casedDiameter) {
        this.casedDiameter = casedDiameter;
    }

    public double getCasedLength() {
        return casedLength;
    }

    public void setCasedLength(double casedLength) {
        this.casedLength = casedLength;
    }

    public double getCasedTotalLength() {
        return casedTotalLength;
    }

    public void setCasedTotalLength(double casedTotalLength) {
        this.casedTotalLength = casedTotalLength;
    }

    public String getCasedSituation() {
        return casedSituation;
    }

    public void setCasedSituation(String casedSituation) {
        this.casedSituation = casedSituation;
    }

    @Override
    public String getSpecialNote() {
        return specialNote;
    }

    @Override
    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }
}

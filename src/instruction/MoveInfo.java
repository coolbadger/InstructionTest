package instruction;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Badger on 16/1/7.
 */
public class MoveInfo {
    private String batchId;                 //作业批次号
    private int moveId;                     //作业编号
    private String moveKind;                //作业类型
    private String unitId;                  //箱编号

    private String unitLength;              //箱长

    private UnitPosition eFromPosition;      //计划提箱位置
    private UnitPosition eToPosition;       //计划放箱位置
    private UnitPosition aFromPosition;     //实际提箱位置
    private UnitPosition aToPosition;       //实际放箱位置

    private String fetchCHE;                //Fetch设备
    private String carryCHE;                //Carry设备
    private String putCHE;                  //Put设备

    private Date dispatchTime;              //指令派发时间
    private Date fetchTime;                 //Fetch确认时间
    private Date carryTime;                 //Carry确认时间
    private Date putTime;                   //Put确认时间

    private int state;                      //状态,未发送,作业中,已完成

    public String toString() {
        String outStr = "";
        outStr += this.batchId + "|";
        outStr += this.unitId + "|";
        if (this.eFromPosition != null)
            outStr += this.eFromPosition.toString() + "|";
        else
            outStr += "--|";
        if (this.eToPosition != null)
            outStr += this.eToPosition.toString() + "|";
        else
            outStr += "--|";
        if (this.aFromPosition != null)
            outStr += this.aFromPosition.toString() + "|";
        else
            outStr += "--|";
        if (this.aToPosition != null)
            outStr += this.aToPosition.toString() + "|";
        else
            outStr += "--|";


        return outStr;
    }

    //获取属性值

    public Object getFieldValueByName(String fieldName) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = this.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(this, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }
    //获取属性列表
    static public List getFiledsInfo(){
        Field[] fields= MoveInfo.class.getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        List list = new ArrayList();
        for(int i=0;i<fields.length;i++){
            list.add(fields[i].getName());
        }
        return list;
    }

    public String getUnitLength() {
        return unitLength;
    }

    public void setUnitLength(String unitLength) {
        this.unitLength = unitLength;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public int getMoveId() {
        return moveId;
    }

    public void setMoveId(int moveId) {
        this.moveId = moveId;
    }

    public String getMoveKind() {
        return moveKind;
    }

    public void setMoveKind(String moveKind) {
        this.moveKind = moveKind;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public UnitPosition geteFromPosition() {
        return eFromPosition;
    }

    public void seteFromPosition(UnitPosition eFromPosition) {
        this.eFromPosition = eFromPosition;
    }

    public UnitPosition geteToPosition() {
        return eToPosition;
    }

    public void seteToPosition(UnitPosition eToPosition) {
        this.eToPosition = eToPosition;
    }

    public UnitPosition getaFromPosition() {
        return aFromPosition;
    }

    public void setaFromPosition(UnitPosition aFromPosition) {
        this.aFromPosition = aFromPosition;
    }

    public UnitPosition getaToPosition() {
        return aToPosition;
    }

    public void setaToPosition(UnitPosition aToPosition) {
        this.aToPosition = aToPosition;
    }

    public String getFetchCHE() {
        return fetchCHE;
    }

    public void setFetchCHE(String fetchCHE) {
        this.fetchCHE = fetchCHE;
    }

    public String getCarryCHE() {
        return carryCHE;
    }

    public void setCarryCHE(String carryCHE) {
        this.carryCHE = carryCHE;
    }

    public String getPutCHE() {
        return putCHE;
    }

    public void setPutCHE(String putCHE) {
        this.putCHE = putCHE;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public Date getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(Date fetchTime) {
        this.fetchTime = fetchTime;
    }

    public Date getCarryTime() {
        return carryTime;
    }

    public void setCarryTime(Date carryTime) {
        this.carryTime = carryTime;
    }

    public Date getPutTime() {
        return putTime;
    }

    public void setPutTime(Date putTime) {
        this.putTime = putTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


}

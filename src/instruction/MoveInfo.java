package instruction;

import java.util.Date;

/**
 * Created by Badger on 16/1/7.
 */
public class MoveInfo {
    public String batchId;                 //作业批次号
    public int moveId;                     //作业编号
    public int movekind;                   //作业类型
    public String unitId;                  //箱编号

    public UnitPosition eFromPositon;      //计划提箱位置
    public UnitPosition eToPosition;       //计划放箱位置
    public UnitPosition aFromPosition;     //实际提箱位置
    public UnitPosition aToPosition;       //实际放箱位置

    public String fetchCHE;                //Fetch设备
    public String carryCHE;                //Carry设备
    public String putCHE;                  //Put设备

    public Date dispatchTime;              //指令派发时间
    public Date fetchTime;                 //Fetch确认时间
    public Date carryTime;                 //Carry确认时间
    public Date putTime;                   //Put确认时间

    public int state;                      //状态
}

package instruction;

import java.util.Date;

/**
 * Created by Badger on 16/1/7.
 */
public class MoveInfo {
    private String batchId;                 //作业批次号
    private int moveId;                     //作业编号
    private int movekind;                   //作业类型
    private String unitId;                  //箱编号

    private UnitPosition eFromPositon;      //计划提箱位置
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

    private int state;                      //状态
}

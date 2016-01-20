package main;

import importData.CwpResultInfo;
import importData.ImportData;
import instruction.MoveInfo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by leko on 2016/1/20.
 */
public class SWGenarateResultData extends SwingWorker{
    private static List<MoveInfo> result = new ArrayList<MoveInfo>();
    private static HashMap<String,Integer> crane = new HashMap<String, Integer>();
    //private static Integer[] hatch;
    private static HashMap<String,String> moverecords;          //根据舱和order定位位置
    private static List<Integer> movecounts;     //每个舱move数
    private static HashMap<String,String[]> autostowresult; //自动配载结果


    @Override
    protected Object doInBackground() throws Exception {
        System.out.println("开始执行：");
        result.clear();
        crane.clear();
//        for (int i=0;i<16;i++)
//            hatch[i]=0;
        for (int i=1;i<8;i++)
            crane.put("Q80"+String.valueOf(i),0);

        moverecords = ImportData.moveorderrecords;
        movecounts = ImportData.movecounts;
        autostowresult = ImportData.autostowresult;
        MoveInfo moveInfo;
        List<CwpResultInfo> cwpResultInfoList = ImportData.cwpResultInfoList;
        for (CwpResultInfo cwpResultInfo: cwpResultInfoList)
        {
            try {
                //System.out.println("开始：");
                String craneID = cwpResultInfo.getCRANEID();                //桥机号
                String hatchID = cwpResultInfo.getHATCHID();                //舱号
                Integer startmoveorder = cwpResultInfo.getStartMoveID();        //舱内开始的moveorder
                System.out.println("start"+startmoveorder.toString());
                Integer endmoveorder = cwpResultInfo.getEndMoveID()-1;          //舱内结束的moveorder
                Integer cnt = movecounts.get(Integer.valueOf(hatchID)-1);       //舱里共多少箱子
                System.out.println("end"+endmoveorder.toString());
                Integer starttime = cwpResultInfo.getWORKINGSTARTTIME();
                Integer endtime = cwpResultInfo.getWORKINGENDTIME();
                Integer singletime = (endtime-starttime)/cwpResultInfo.getMOVECOUNT();
                for (int i=startmoveorder;i<=endmoveorder && i<=cnt;i++)
                {
                    //System.out.println("新生成一条数据");
                    moveInfo = new MoveInfo();
                    moveInfo.setBatchId(craneID);               //批号为桥机号
                    //System.out.println("桥机号"+ moveInfo.getBatchId());
                    moveInfo.setMoveKind("Load");
                    Integer moveID = crane.get(craneID)+i-startmoveorder+1;     //桥机的move序列
                    moveInfo.setMoveId(moveID);
                    moveInfo.setGkey(craneID+"@"+moveID.toString());
                    String hatchmoveorder = hatchID+"."+String.valueOf(i);          //舱号连接编号
                    //System.out.println("moveorder:"+hatchmoveorder);
                    String vesselpositon = moverecords.get(hatchmoveorder);
                    moveInfo.setExToPosition(vesselpositon);
                    moveInfo.setWORKINGSTARTTIME(starttime+singletime*(i-startmoveorder));
                    String areaposition = autostowresult.get(vesselpositon)[0];
                    String unitID = autostowresult.get(vesselpositon)[1];
                    String size = autostowresult.get(vesselpositon)[2];
                    moveInfo.setExFromPosition(areaposition);
                    moveInfo.setUnitId(unitID);
                    moveInfo.setUnitLength(size);
                    result.add(moveInfo);

                }
                crane.put(craneID, crane.get(craneID) + cwpResultInfo.getMOVECOUNT());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
//        for (MoveInfo moveInfo1:result){
//            System.out.println(moveInfo1.getGkey()+" "+moveInfo1.getExToPosition());
//        }
        GlobalData.setGlobalMoveinfoList(result);
        return null;
    }
}

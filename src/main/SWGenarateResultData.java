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
        MoveInfo moveInfo;
        List<CwpResultInfo> cwpResultInfoList = ImportData.cwpResultInfoList;
        for (CwpResultInfo cwpResultInfo: cwpResultInfoList)
        {
            try {
                //System.out.println("开始：");
                String craneID = cwpResultInfo.getCRANEID();
                String hatchID = cwpResultInfo.getHATCHID();
                Integer startmoveorder = cwpResultInfo.getStartMoveID();
                System.out.println("start"+startmoveorder.toString());
                Integer endmoveorder = cwpResultInfo.getEndMoveID()-1;
                Integer cnt = movecounts.get(Integer.valueOf(hatchID)-1);
                System.out.println("end"+endmoveorder.toString());
                Integer starttime = cwpResultInfo.getWORKINGSTARTTIME();
                Integer endtime = cwpResultInfo.getWORKINGENDTIME();
                Integer singletime = (endtime-starttime)/cwpResultInfo.getMOVECOUNT();
                for (int i=startmoveorder;i<=endmoveorder && i<=cnt;i++)
                {
                    //System.out.println("新生成一条数据");
                    moveInfo = new MoveInfo();
                    moveInfo.setBatchId(craneID);
                    //System.out.println("桥机号"+ moveInfo.getBatchId());
                    moveInfo.setMoveKind("Load");
                    Integer moveID = crane.get(craneID)+i-startmoveorder+1;
                    moveInfo.setMoveId(moveID);
                    moveInfo.setGkey(craneID+"@"+moveID.toString());
                    String hatchmoveorder = hatchID+String.valueOf(i);
                    //System.out.println("moveorder:"+hatchmoveorder);
                    moveInfo.setExToPosition(moverecords.get(hatchmoveorder));
                    moveInfo.setWORKINGSTARTTIME(starttime+singletime*(i-startmoveorder));
                    result.add(moveInfo);

                }
                crane.put(craneID, crane.get(craneID) + cwpResultInfo.getMOVECOUNT());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        for (MoveInfo moveInfo1:result){
            System.out.println(moveInfo1.getGkey()+" "+moveInfo1.getExToPosition());
        }
        GlobalData.setGlobalMoveinfoList(result);
        return null;
    }
}

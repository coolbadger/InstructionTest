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
    private static List<MoveInfo> reault = new ArrayList<MoveInfo>();
    private static HashMap<String,Integer> crane = new HashMap<String, Integer>();
    //private static Integer[] hatch;
    private static HashMap<String,String> moverecords;          //根据舱和order定位位置

    @Override
    protected Object doInBackground() throws Exception {
        System.out.println("开始执行：");
        reault.clear();
        crane.clear();
//        for (int i=0;i<16;i++)
//            hatch[i]=0;
        for (int i=1;i<8;i++)
            crane.put("Q80"+String.valueOf(i),0);

        moverecords = ImportData.moveorderrecords;
        MoveInfo moveInfo;
        List<CwpResultInfo> cwpResultInfoList = ImportData.cwpResultInfoList;
        for (CwpResultInfo cwpResultInfo: cwpResultInfoList)
        {
            Integer startmoveorder = cwpResultInfo.getStartMoveID();
            System.out.println(startmoveorder);
            Integer endmoveorder = cwpResultInfo.getEndMoveID();
            System.out.println(endmoveorder);
            String craneID = cwpResultInfo.getCRANEID();
            for (int i=startmoveorder;i<=endmoveorder;i++)
            {
                try{
                    System.out.println("start");
                    moveInfo = new MoveInfo();
                    moveInfo.setBatchId(craneID);
                    System.out.println(craneID + " " + moveInfo.getBatchId());
                    moveInfo.setMoveKind("Load");
                    Integer moveID = crane.get(craneID)+i-startmoveorder+1;
                    moveInfo.setMoveId(moveID);
                    moveInfo.setGkey(craneID+moveID.toString());
                    String hatchmoveorder = cwpResultInfo.getHATCHID()+String.valueOf(i);
                    System.out.println(hatchmoveorder);
                    moveInfo.setExToPosition(moverecords.get(hatchmoveorder));
                    System.out.println(moveInfo.getGkey()+" "+moveInfo.getExToPosition());
                    reault.add(moveInfo);
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
            crane.put(craneID, crane.get(craneID) + cwpResultInfo.getMOVECOUNT());
        }
        System.out.println(reault.get(0).getGkey()+"----"+reault.get(0).getExFromPosition());
        GlobalData.setGlobalMoveinfoList(reault);
        return null;
    }
}

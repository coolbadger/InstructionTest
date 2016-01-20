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
        reault.clear();
        crane.clear();
//        for (int i=0;i<16;i++)
//            hatch[i]=0;
        moverecords = ImportData.moveorderrecords;
        MoveInfo moveInfo;
        List<CwpResultInfo> cwpResultInfoList = ImportData.cwpResultInfoList;
        for (CwpResultInfo cwpResultInfo: cwpResultInfoList)
        {
            Integer startmoveorder = cwpResultInfo.getStartMoveID();
            Integer endmoveorder = cwpResultInfo.getEndMoveID();
            String craneID = cwpResultInfo.getCRANEID();
            for (int i=startmoveorder;i<=endmoveorder;i++)
            {
                moveInfo = new MoveInfo();
                moveInfo.setBatchId(craneID);
                moveInfo.setMoveKind("Load");
                Integer moveID = crane.get(craneID)+i-startmoveorder+1;
                moveInfo.setMoveId(moveID);
                moveInfo.setGkey(craneID+moveID.toString());
                String hatchmoveorder = cwpResultInfo.getHATCHID()+String.valueOf(i);
                moveInfo.setExFromPosition(moverecords.get(hatchmoveorder));
                reault.add(moveInfo);
            }
            crane.put(craneID,crane.get(craneID)+cwpResultInfo.getMOVECOUNT());
        }
        GlobalData.setGlobalMoveinfoList(reault);
        return null;
    }
}

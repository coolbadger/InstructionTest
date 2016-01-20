package main;

import importData.BayPositionInfo;
import importData.ImportData;
import importData.PreStowageInfo;
import importData.WorkMoveInfo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by leko on 2016/1/18.
 */
public class SWGenerateWorkMoveData extends SwingWorker {

    private List<WorkMoveInfo> workMoveInfoList = new ArrayList<WorkMoveInfo>();
    private HashMap<String,Integer> baypos = new HashMap<String, Integer>();

    @Override
    protected Object doInBackground() throws Exception {
        workMoveInfoList.removeAll(workMoveInfoList);
        WorkMoveInfo newworkmoveInfo;
        for (BayPositionInfo bayPositionInfo:ImportData.bayPositionInfoList)
        {
            Integer BAYID = Integer.valueOf(bayPositionInfo.getVBY_BAYID());
            baypos.put(bayPositionInfo.getVBY_BAYID(),bayPositionInfo.getVBY_POSITION());
            //算平均值在这里省略了........因为默认一个舱长48.......
            if (BAYID%4==1)
            {
                BAYID++;
                baypos.put(BAYID.toString(),bayPositionInfo.getVBY_POSITION()+12);
            }
        }
        System.out.println("开始生成舱内作业关信息：");
        for (PreStowageInfo preStowageInfo: ImportData.preStowageInfoArrayList)
        {
            newworkmoveInfo = new WorkMoveInfo();
            newworkmoveInfo.setCWPWORKMOVENUM(preStowageInfo.getMOVE_ORDER());
            //甲板上/下
            Integer BAYID = preStowageInfo.getVBY_BAYID();
            if (BAYID>80) {
                newworkmoveInfo.setDECK("H");
            }
            else{
                newworkmoveInfo.setDECK("D");
            }
            newworkmoveInfo.setGLOBALPRIORITY(2);
            newworkmoveInfo.setHATCH(preStowageInfo.getVHT_ID().toString());
            //倍位中心的绝对位置
            newworkmoveInfo.setHORIZONTALPOSITION(baypos.get(BAYID.toString()));
            newworkmoveInfo.setLD("L");
            newworkmoveInfo.setMOVETYPE(preStowageInfo.getSIZE().toString());

            //调试信息
            System.out.println(newworkmoveInfo.getCWPWORKMOVENUM()+" "+preStowageInfo.getVBY_BAYID()+" "+newworkmoveInfo.getHORIZONTALPOSITION()+" "+newworkmoveInfo.getHATCH());
            workMoveInfoList.add(newworkmoveInfo);
        }
        ImportData.workMoveInfoList = workMoveInfoList;
        return null;
    }
}

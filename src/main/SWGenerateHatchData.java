package main;

import importData.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by leko on 2016/1/18.
 */
public class SWGenerateHatchData extends SwingWorker {

    private static List<HatchInfo> hatchInfoList = new ArrayList<HatchInfo>();

    @Override
    protected Object doInBackground() throws Exception {
        HatchInfo newhatchInfo;
        System.out.println("start");
        Date workingstarttime = ImportData.voyageInfoList.get(0).getVOTPWKSTTM();
        Date workingendtime = ImportData.voyageInfoList.get(0).getVOTPWKENTM();
        String vesselID = ImportData.voyageInfoList.get(0).getVESSELID();
        WorkingTimeRange workingTimeRange = new WorkingTimeRange();
        workingTimeRange.setID(null);
        workingTimeRange.setWORKSTARTTIME(workingstarttime);
        workingTimeRange.setWORKENDTIME(workingendtime);
        List<WorkingTimeRange> workingTimeRangeList = new ArrayList<WorkingTimeRange>();
        workingTimeRangeList.add(workingTimeRange);
        Integer i=0;
        for (HatchPositionInfo hatchPositionInfo: ImportData.hatchPositionInfoList){
            newhatchInfo = new HatchInfo();
            newhatchInfo.setHORIZONTALSTARTPOSITION(hatchPositionInfo.getPOSITION());
            newhatchInfo.setID(hatchPositionInfo.getVHT_ID());
            newhatchInfo.setLENGTH(hatchPositionInfo.getLENGTH());
            newhatchInfo.setVESSELID(vesselID);
            newhatchInfo.setMOVECOUNT(ImportData.movecounts.get(i++));
            newhatchInfo.setNO(hatchPositionInfo.getVHT_ID());
            newhatchInfo.setSEQ(hatchPositionInfo.getVHT_ID());
            newhatchInfo.setWORKINGTIMERANGES(workingTimeRangeList);//工作时间
            System.out.println(newhatchInfo.getHORIZONTALSTARTPOSITION()+" "+newhatchInfo.getID()+" "+newhatchInfo.getMOVECOUNT());
            hatchInfoList.add(newhatchInfo);
        }
        ImportData.hatchInfoList = hatchInfoList;
        return null;
    }
}

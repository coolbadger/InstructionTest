package main;

import importData.*;
import utils.FileUtil;

import javax.swing.*;
import java.util.List;

/**
 * Created by leko on 2016/1/19.
 */
public class SWGenerateAutostowData extends SwingWorker {
    public String containerarea;
    public String container;
    public String cwpoutput;
    public String prestowage;
    private static List<ContainerAreaInfo> containerAreaInfos;
    private static List<ContainerInfo> containerInfos;
    private static List<PreStowageInfo> preStowageInfos;
    private static List<CwpResultInfo> cwpResultInfos;

    @Override
    protected Object doInBackground() throws Exception {
        //生成箱区信息字符串
        containerarea="";
        containerAreaInfos = ImportData.containerAreaInfoList;
        for (ContainerAreaInfo containerAreaInfo: containerAreaInfos)
        {
            containerarea+=containerAreaInfo.getASCBOTTOMSPEED().toString()+",";
            containerarea+=containerAreaInfo.getASCTOPSPEED().toString()+",";
            containerarea+=containerAreaInfo.getID().toString()+",";
            containerarea+=containerAreaInfo.getLOCATIONLB()+containerAreaInfo.getLOCATIONLH()+",";
            containerarea+=containerAreaInfo.getLOCATIONRB()+containerAreaInfo.getLOCATIONRH()+",";
            containerarea+=containerAreaInfo.getVBYNUM().toString()+",";
            containerarea+=containerAreaInfo.getVTRNUM().toString()+",";
            containerarea+=containerAreaInfo.getVRWNUM().toString()+",";
            containerarea+=containerAreaInfo.getSCTYPE()+",";
            containerarea+=containerAreaInfo.getWORKEFFICIENCYB().toString()+",";
            containerarea+=containerAreaInfo.getWORKEFFICIENCYT().toString()+"#";
        }
        //生成在场箱信息字符串
        container="";
        containerInfos = ImportData.containerInfoList;
        for (ContainerInfo containerInfo: containerInfos)
        {
            container+=containerInfo.getIYC_CNTRNO()+",";
            container+=containerInfo.getIYC_CNTR_AREA_ID()+",";
            container+=containerInfo.getIYC_VOYID()+",";
            container+=containerInfo.getIYC_CTYPECD()+",";
            container+=containerInfo.getIYC_CSZ_CSIZECD()+",";
            container+=containerInfo.getIYC_PORTCD()+",";
            container+=containerInfo.getIYC_WEIGHT()+",";
            container+=containerInfo.getIYC_DNGFG()+",";
            container+=containerInfo.getIYC_REFFG()+",";
            container+=containerInfo.getIYC_YLOCATION()+",";
            container+=containerInfo.getIYC_PLANFG()+",";
            container+=containerInfo.getIYC_RETIME()+"#";
        }
        //生成预配箱信息
        preStowageInfos = ImportData.preStowageInfoArrayList;
        prestowage="";
        for (PreStowageInfo preStowageInfo:preStowageInfos)
        {
            prestowage+=preStowageInfo.getVHT_ID().toString()+",";
            prestowage+=preStowageInfo.getVBY_BAYID().toString()+",";
            prestowage+=preStowageInfo.getVTR_TIERNO().toString()+",";
            prestowage+=preStowageInfo.getVRW_ROWNO().toString()+",";
            String GroupID = preStowageInfo.getGROUP_ID();
            prestowage+=ImportData.groupmap.get(GroupID).get(2)+",";
            prestowage+=ImportData.groupmap.get(GroupID).get(0)+",";
            prestowage+=ImportData.groupmap.get(GroupID).get(1)+",";
            prestowage+=preStowageInfo.getWEIGHT().toString()+",";
            prestowage+=preStowageInfo.getMOVE_ORDER().toString()+"#";
        }
        //生成cwp输出结果
        cwpResultInfos = ImportData.cwpResultInfoList;
        cwpoutput="";
        for (CwpResultInfo cwpResultInfo:cwpResultInfos)
        {
            cwpoutput+=cwpResultInfo.getCRANEID().toString()+",";
            cwpoutput+=cwpResultInfo.getHATCHBWID().toString()+",";
            cwpoutput+=cwpResultInfo.getHATCHID().toString()+",";
            cwpoutput+=cwpResultInfo.getMOVECOUNT().toString()+",";
            cwpoutput+=cwpResultInfo.getQDC().toString()+",";
            cwpoutput+=cwpResultInfo.getVESSELID().toString()+",";
            cwpoutput+=cwpResultInfo.getWORKINGENDTIME().toString()+",";
            cwpoutput+=cwpResultInfo.getWORKINGSTARTTIME().toString()+"#";
        }
        try {
            FileUtil.writeToFile("E:/Containerarea.txt", containerarea);
            FileUtil.writeToFile("E:/Container.txt", container);
            FileUtil.writeToFile("E:/Prestowage.txt", prestowage);
            FileUtil.writeToFile("E:/Cwpoutput.txt", cwpoutput);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }
}

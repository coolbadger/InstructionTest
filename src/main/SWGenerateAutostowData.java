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
            String temp="";
            temp+=containerAreaInfo.getASCBOTTOMSPEED().toString()+",";
            temp+=containerAreaInfo.getASCTOPSPEED().toString()+",";
            temp+=containerAreaInfo.getID().toString()+",";
            temp+=containerAreaInfo.getLOCATIONLB()+containerAreaInfo.getLOCATIONLH()+",";
            temp+=containerAreaInfo.getLOCATIONRB()+containerAreaInfo.getLOCATIONRH()+",";
            temp+=containerAreaInfo.getVBYNUM().toString()+",";
            temp+=containerAreaInfo.getVTRNUM().toString()+",";
            temp+=containerAreaInfo.getVRWNUM().toString()+",";
            temp+=containerAreaInfo.getSCTYPE()+",";
            temp+=containerAreaInfo.getWORKEFFICIENCYB().toString()+",";
            temp+=containerAreaInfo.getWORKEFFICIENCYT().toString()+"#";
            containerarea+=temp;
        }
        //生成在场箱信息字符串
        container="";
        containerInfos = ImportData.containerInfoList;
        for (ContainerInfo containerInfo: containerInfos)
        {
            String temp="";
            temp+=containerInfo.getIYC_CNTRNO()+",";
            temp+=containerInfo.getIYC_CNTR_AREA_ID()+",";
            temp+=containerInfo.getIYC_VOYID()+",";
            temp+=containerInfo.getIYC_CTYPECD()+",";
            temp+=containerInfo.getIYC_CSZ_CSIZECD()+",";
            temp+=containerInfo.getIYC_PORTCD()+",";
            temp+=containerInfo.getIYC_WEIGHT()+",";
            temp+=containerInfo.getIYC_DNGFG()+",";
            temp+=containerInfo.getIYC_REFFG()+",";
            temp+=containerInfo.getIYC_YLOCATION()+",";
            temp+=containerInfo.getIYC_PLANFG()+",";
            temp+=containerInfo.getIYC_RETIME()+"#";
            container+=temp;
        }
        //生成预配箱信息
        preStowageInfos = ImportData.preStowageInfoArrayList;
        prestowage="";
        for (PreStowageInfo preStowageInfo:preStowageInfos)
        {
            String temp="";
            temp+=preStowageInfo.getVHT_ID().toString()+",";
            temp+=preStowageInfo.getVBY_BAYID().toString()+",";
            temp+=preStowageInfo.getVTR_TIERNO().toString()+",";
            temp+=preStowageInfo.getVRW_ROWNO().toString()+",";
            String GroupID = preStowageInfo.getGROUP_ID();
            temp+=ImportData.groupmap.get(GroupID).get(2)+",";
            temp+=ImportData.groupmap.get(GroupID).get(0)+",";
            temp+=ImportData.groupmap.get(GroupID).get(1)+",";
            temp+=preStowageInfo.getWEIGHT().toString()+",";
            temp+=preStowageInfo.getMOVE_ORDER().toString()+"#";
            prestowage+=temp;
        }
        //生成cwp输出结果
        cwpResultInfos = ImportData.cwpResultInfoList;
        cwpoutput="";
        for (CwpResultInfo cwpResultInfo:cwpResultInfos)
        {
            String temp="";
            temp+=cwpResultInfo.getCRANEID().toString()+",";
            temp+=cwpResultInfo.getHATCHBWID().toString()+",";
            temp+=cwpResultInfo.getHATCHID().toString()+",";
            temp+=cwpResultInfo.getMOVECOUNT().toString()+",";
            temp+=cwpResultInfo.getQDC().toString()+",";
            temp+=cwpResultInfo.getVESSELID().toString()+",";
            temp+=cwpResultInfo.getWORKINGENDTIME().toString()+",";
            temp+=cwpResultInfo.getWORKINGSTARTTIME().toString()+"#";
            cwpoutput+=temp;
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

package main;

import importData.ContainerInfo;
import importData.ContainerInfoProcess;
import importData.ImportData;

import javax.swing.*;
import java.util.*;

/**
 * Created by leko on 2016/1/15.
 */
public class SWGenerateGroupData extends SwingWorker {

    private static HashMap<String, ArrayList<String>> groupmap = new HashMap<String, ArrayList<String>>();
    private static Set<String> cportSet = new HashSet<String>();   //包含港口类型
    private static Set<String> ctypeSet = new HashSet<String>();  //包含箱型类型
    private static Set<String> csizeSet = new HashSet<String>();   //包含尺寸类型


    public List<ContainerInfo> containerInfoList = ContainerInfoProcess.containerInfoList;
    @Override
    protected Object doInBackground() throws Exception {
        //
        System.out.println("开始生成分组属性");
        for (ContainerInfo containerInfo : containerInfoList) {
            String port = containerInfo.getIYC_PORTCD();
            cportSet.add(port);                                      //统计港口类型
            String type = containerInfo.getIYC_CTYPECD();
            ctypeSet.add(type);                                      //统计箱型类型
            String size = containerInfo.getIYC_CSZ_CSIZECD();
            csizeSet.add(size);                                      //统计尺寸类型
        }

        System.out.println(cportSet);
        System.out.println(ctypeSet);
        System.out.println(csizeSet);

        Integer groupnum=1;
        for (String port: cportSet){
            for (String type:ctypeSet){
                for (String size: csizeSet) {
                    ArrayList<String> groupattri = new ArrayList<String>();
                    groupattri.add(port);
                    groupattri.add(type);
                    groupattri.add(size);
                    groupmap.put("G" + groupnum.toString(), groupattri);
                    groupnum++;
                }
            }
        }
        System.out.println(groupmap);

        ImportData.groupmap = groupmap;
        GroupData.setGroupMap(groupmap);
        System.out.println("结束生成分组属性");

        return null;
    }
}

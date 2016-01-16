package main;

import importData.ContainerInfo;
import importData.ContainerInfoProcess;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.StringBuffer;
import java.util.List;

/**
 * Created by leko on 2016/1/15.
 */
public class SWContainerData extends SwingWorker {
    public File inFile;
    public String inJson;
    public boolean noError = true;
    public String result;

    @Override
    protected Object doInBackground() throws Exception {
        //读取File
        StringBuffer fileContent = new StringBuffer(); // 文件很长的话建议使用StringBuffer
        try {
            FileInputStream fis = new FileInputStream(inFile);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                fileContent.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = e.toString();
            noError = false;
        }
        System.out.println(fileContent);
        //解析字符串
        if(noError){
            try {
                List<ContainerInfo> containerInfoList = new ContainerInfoProcess().getContainerInfo(fileContent);

                if (containerInfoList == null){
                    noError = false;
                    result = "解析失败！";

                }else{

                    for (ContainerInfo containerInfo:containerInfoList) {

                        System.out.print(containerInfo.getIYC_CNTR_AREA_ID()+" ");
                        System.out.print(containerInfo.getIYC_CNTRNO()+" ");
                        System.out.print(containerInfo.getIYC_CSZ_CSIZECD()+" ");
                        System.out.print(containerInfo.getIYC_CTYPECD()+" ");
                        System.out.print(containerInfo.getIYC_PORTCD()+" ");
                        System.out.print(containerInfo.getIYC_WEIGHT()+" ");
                        System.out.println();
                    }
                    System.out.print(containerInfoList.size());
                }

            } catch (Exception e) {
                e.printStackTrace();
                result = e.toString();
            }
        }
        return null;
    }
}

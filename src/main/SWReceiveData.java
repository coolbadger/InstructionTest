package main;

import instruction.JsonProcess;
import instruction.MoveInfo;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by lekoxnfx on 16/1/8.
 */
public class SWReceiveData extends SwingWorker {//接收返回的数据
    public File inFile;
    public String inJson;
    public boolean noError = true;
    public String result;
    @Override
    protected Object doInBackground() throws Exception {
        //读取File
        String fileContent = ""; // 文件很长的话建议使用StringBuffer
        try {
            FileInputStream fis = new FileInputStream(inFile);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                fileContent += line;
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
                List<MoveInfo> moveInfoList = new JsonProcess().getMoveInfos(fileContent);

                if (moveInfoList == null){
                    noError = false;
                    result = "解析失败！";
                }else{

                    //判断Gkey
                    for (MoveInfo moveInfo : moveInfoList){
                        MoveInfo moveInfo1 = GlobalData.getGlobalMoveinfoMap().get(moveInfo.getGkey());
                        if (moveInfo1==null){
                            noError = false;
                            result = "接收到的指令有无效Gkey:"+ moveInfo.getGkey();
                        }
                        else if(moveInfo1.getState() != 1){
                            noError = false;
                            result = "接收到的指令Gkey:"+ moveInfo.getGkey() + "状态不匹配！";
                        }
                        else {

                        }
                    }

                    //
                    if(noError){
                        for (MoveInfo moveInfo : moveInfoList) {
                            String gkey = moveInfo.getGkey();
                            GlobalData.addOrUpdateMoveInfo(moveInfo);
                        }
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                result = e.toString();
            }
        }


        return null;
    }
}

package main;

import instruction.JsonProcess;
import instruction.MoveInfo;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lekoxnfx on 16/1/8.
 */
public class SWSendData extends SwingWorker  {//发送数据
    public List<String> gkeyList;
    @Override
    protected Object doInBackground() throws Exception {
        try {
            List<MoveInfo> moveInstructions = new ArrayList<MoveInfo>();
            for (String key : gkeyList) {
                MoveInfo moveInfo = GlobalData.getGlobalMoveinfoMap().get(key);
                System.out.println(GlobalData.getGlobalMoveinfoMap().size());
                if (moveInfo != null) {
                    moveInstructions.add(moveInfo);
                    moveInfo.setState(1);
    //                GlobalData.addOrUpdateMoveInfo(moveInfo);

                }
            }
            String jsonStr = new JsonProcess().getJsonStr(moveInstructions);
            System.out.println(jsonStr);

            //写入文件
            String FileContent = jsonStr;
            try {
                FileOutputStream fos = new FileOutputStream("WorkInstructionSend.json");
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                osw.write(FileContent);
                osw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //指令状态重置

            if (jsonStr.length() > 10) {
                System.out.println("开始更改指令状态");
                for (String key : gkeyList) {
                    MoveInfo moveInfo = GlobalData.getGlobalMoveinfoMap().get(key);
                    System.out.println("key:" + key + "/" + moveInfo.getGkey());
                    if (moveInfo != null) {
                        System.out.println("更改指令状态" + key);
                        moveInfo.setState(1);
                        GlobalData.addOrUpdateMoveInfo(moveInfo);
                    }
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }
}

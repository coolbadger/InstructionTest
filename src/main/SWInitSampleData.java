package main;

import instruction.LoadConfig;
import instruction.MoveInfo;

import javax.swing.*;
import java.util.List;

/**
 * Created by lekoxnfx on 16/1/8.
 */
public class SWInitSampleData extends SwingWorker {//创建初始案例数据
    @Override
    protected Object doInBackground() throws Exception {

        try {
            System.out.println("创建案例数据");
            List<MoveInfo> moveInfoList = new LoadConfig().getInstructions();
            GlobalData.setGlobalMoveinfoList(moveInfoList);
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }


}

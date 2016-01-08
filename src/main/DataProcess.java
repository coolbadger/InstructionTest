package main;

import instruction.JsonProcess;
import instruction.LoadConfig;
import instruction.MoveInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Badger on 16/1/8.
 */
public class DataProcess {

    //初始化全局列表
    public synchronized void initGlobalList() {
        GlobalData.GLOBAL_MOVEINFO_LIST = new HashMap<String, MoveInfo>();

        List<MoveInfo> moveInfoList = new LoadConfig().getInstructions();
        this.setGlobalList(moveInfoList);
    }

    //根据返回Json,更新全局列表
    public synchronized void updateGlobalList(String jsonStr) {
        List<MoveInfo> moveInfoList = new JsonProcess().getMoveInfos(jsonStr);
        this.setGlobalList(moveInfoList);
    }

    //根据选中指令列表,生成指令Json字符串
    public synchronized String genInstructions(List<String> keyList) {
        List<MoveInfo> moveInstructions = new ArrayList<MoveInfo>();
        for (String key : keyList) {
            MoveInfo moveInfo = GlobalData.GLOBAL_MOVEINFO_LIST.get(key);
            if (moveInfo != null) {
                moveInstructions.add(moveInfo);
                moveInfo.setState(1);
                GlobalData.GLOBAL_MOVEINFO_LIST.put(key, moveInfo);
            }
        }
        String jsonStr = new JsonProcess().getJsonStr(moveInstructions);
        //指令状态重置
        if (jsonStr.length() > 10) {
            for (String key : keyList) {
                MoveInfo moveInfo = GlobalData.GLOBAL_MOVEINFO_LIST.get(key);
                if (moveInfo != null) {
                    moveInfo.setState(1);
                    GlobalData.GLOBAL_MOVEINFO_LIST.put(key, moveInfo);
                }
            }
        }
        return jsonStr;
    }

    private void setGlobalList(List<MoveInfo> moveInfoList) {
        for (MoveInfo moveInfo : moveInfoList) {
            String gkey = moveInfo.getBatchId() + "@" + moveInfo.getMoveId();
            GlobalData.GLOBAL_MOVEINFO_LIST.put(gkey, moveInfo);
        }
    }
}

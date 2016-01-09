package main;

import instruction.MoveInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lekoxnfx on 16/1/8.
 */
public class GlobalData {


    private static Map<String, MoveInfo> GLOBAL_MOVEINFO_MAP = new HashMap<String, MoveInfo>();

    public static synchronized void setGlobalMoveinfoMap(Map<String, MoveInfo> moveinfoMap){
        GLOBAL_MOVEINFO_MAP = moveinfoMap;
        notifyChange();
    }
    public static synchronized void setGlobalMoveinfoList(List<MoveInfo> moveinfoList){
        for (MoveInfo moveInfo : moveinfoList) {
            GLOBAL_MOVEINFO_MAP.put(moveInfo.getGkey(),moveInfo);
        }
        notifyChange();
    }

    public static synchronized void addOrUpdateMoveInfo(MoveInfo moveInfo){
        GLOBAL_MOVEINFO_MAP.put(moveInfo.getGkey(),moveInfo);
        notifyChange();
        System.out.println("全局数据更新，通知监听器");

    }
    public static synchronized void clearMoveInfo(){
        GLOBAL_MOVEINFO_MAP.clear();
        notifyChange();
    }

    public static Map<String, MoveInfo> getGlobalMoveinfoMap() {
        return GLOBAL_MOVEINFO_MAP;
    }

    //监听器及其设置
    private static List<IGlobalData> globalDataChangeListeners = new ArrayList<IGlobalData>();

    public static void addGlobalDataChangeListener(IGlobalData iGlobalData) {
        globalDataChangeListeners.add(iGlobalData);
    }

    private static void notifyChange() {//通知所有监听者
        for (IGlobalData iGlobalData : globalDataChangeListeners) {
            iGlobalData.globalDataChanged();
        }
    }
}

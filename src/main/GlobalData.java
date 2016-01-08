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
    public static Map<String, MoveInfo> GLOBAL_MOVEINFO_LIST = new HashMap<String, MoveInfo>();





    //监听器及其设置
    private static List<IGlobalData> globalDataChangeListeners = new ArrayList<IGlobalData>();

    public static void addGlobalDataChangeListener(IGlobalData iGlobalData){
        globalDataChangeListeners.add(iGlobalData);
    }

    private void notifyChange(){//通知所有监听者
        for(IGlobalData iGlobalData:globalDataChangeListeners){
            iGlobalData.globalDataChanged();
        }
    }


}

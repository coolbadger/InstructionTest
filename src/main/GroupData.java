package main;

import java.util.*;


public class GroupData {

    private static HashMap<String, ArrayList<String>> Group_MAP = new HashMap<String, ArrayList<String>>();

    public static synchronized void setGroupMap(HashMap<String, ArrayList<String>> groupmap){
        Group_MAP = groupmap;
        notifyChange();
    }


    public static synchronized void clearGroupInfo(){
        Group_MAP.clear();
        notifyChange();
    }

    public static HashMap<String, ArrayList<String>> getGroupMap() {
        return  Group_MAP;
    }

    //监听器及其设置
    private static List<IGroupData> groupDataChangeListeners = new ArrayList<IGroupData>();

    public static void addGroupDataChangeListener(IGroupData iGroupData) {
        groupDataChangeListeners.add(iGroupData);
    }

    private static void notifyChange() {//通知所有监听者
        System.out.println("notifychange");
        for (IGroupData iGroupData : groupDataChangeListeners) {
            iGroupData.groupDataChanged();
        }
    }

}

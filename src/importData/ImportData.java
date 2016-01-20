package importData;

import java.beans.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 导入数据全局变量类
 * Created by csw on 2016/1/16.
 */
public class ImportData {

    public static List<ContainerInfo> containerInfoList; //在场箱数据

    public static List<VesselStructureInfo> vesselStructureInfoList;//船舶结构数据
    public static List<HatchPositionInfo> hatchPositionInfoList;//船舱边缘绝对位置
    public static List<BayPositionInfo> bayPositionInfoList;//倍位中心绝对位置

    public static HashMap<String, ArrayList<String>> groupmap;//属性组信息
    public static ArrayList<PreStowageInfo> preStowageInfoArrayList; //预配图数据及moveorder数据
    public static HashMap<String,String> moveorderrecords;      //舱和moveorder确定具体位置
    public static List<Integer> movecounts;     //每个舱move数

    public static List<VoyageInfo> voyageInfoList;//航次信息
    public static List<CraneInfo> craneInfoList;//桥机信息

    public static List<HatchInfo> hatchInfoList; //船舱信息
    public static List<WorkMoveInfo> workMoveInfoList; //舱内作业关信息

    public static OthersInfo othersInfo;//其他信息

    public static List<ContainerAreaInfo> containerAreaInfoList;//场箱区信息

    public static String craneJsonStr,hatchJsonStr,movesJsonStr;//要传递给cwp算法的3个json数据字符串

    public static String cwpResult;//保存cwp算法结果
    public static List<CwpResultInfo> cwpResultInfoList;//解析后用List保存cwp算法结果
}

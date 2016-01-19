package importData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 导入数据全局变量类
 * Created by csw on 2016/1/16.
 */
public class ImportData {

    public static List<VesselStructureInfo> vesselStructureInfo;//船舶结构数据
    public static List<HatchPositionInfo> hatchPositionInfoList;//船舱边缘绝对位置
    public static List<BayPositionInfo> bayPositionInfoList;//倍位中心绝对位置

    public static ArrayList<PreStowageInfo> preStowageInfoArrayList; //预配图数据及moveorder数据
    public static List<Integer> movecounts;     //每个舱move数

    public static List<VoyageInfo> voyageInfoList;//航次信息
    public static List<CraneInfo> craneInfoList;//桥机信息

    public static List<HatchInfo> hatchInfoList; //船舱信息
    public static List<WorkMoveInfo> workMoveInfoList; //舱内作业关信息

    public static OthersInfo othersInfo;//其他信息

    public static List<ContainerAreaInfo> containerAreaInfoList;//场箱区信息
}

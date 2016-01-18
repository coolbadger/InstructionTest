package importData

import groovy.json.JsonSlurper
import utils.FileUtil

import java.text.SimpleDateFormat

/**
 * Created by csw on 2016/1/18.
 */
class OthersInfoProcess {

    public static OthersInfo getOthersInfo() {

        String agvPath = "E:/json/SHBTOS.CWPJUnitagv.txt"//将测试数据放在E盘的json文件夹即可
        String dcpvPath = "E:/json/SHBTOS.CWPJUnitdistanceCrossingPlatformVessel.txt"
        String mainCarPath = "E:/json/SHBTOS.CWPJUnitmainCar.txt"

        boolean isError = false
        OthersInfo othersInfo = new OthersInfo()

        try{
            String agvJsonStr = FileUtil.readFileToString(new File(agvPath)).toString();
            String dcpvJsonStr = FileUtil.readFileToString(new File(dcpvPath)).toString();
            String mainCarJsonStr = FileUtil.readFileToString(new File(mainCarPath)).toString();

            //AGV运行速度
            def root2 = new JsonSlurper().parseText(agvJsonStr)
            assert root2 instanceof Map
            AGV agv = new AGV()
            agv.SPEED = root2.SPEED
            othersInfo.agv = agv

            //道口-平台-船舶三点之间的距离信息
            def root3 = new JsonSlurper().parseText(dcpvJsonStr)
            assert root3 instanceof Map
            DistanceCrossingPlatformVessel dcpv = new DistanceCrossingPlatformVessel()
            dcpv.CP = root3.CP
            dcpv.CV = root3.CV
            dcpv.PV = root3.PV
            othersInfo.distanceCrossingPlatformVessel = dcpv

            //主小车在各个位置之间的运行速度
            def root4 = new JsonSlurper().parseText(mainCarJsonStr)
            assert root4 instanceof Map
            MainCar mainCar = new MainCar()
            mainCar.CPSPEED = root4.CPSPEED
            mainCar.CVSPEED = root4.CVSPEED
            mainCar.PVSPEED = root4.PVSPEED
            othersInfo.mainCar = mainCar

        }catch (Exception e){
            System.out.println("其他数据解析时，发现json数据异常！")
            isError = true;
            e.printStackTrace()
        }
        if(isError) {
            System.out.println("其他数据解析失败！")
            return null;
        }else {
            System.out.println("其他数据解析成功！")
            return othersInfo
        }
    }
}

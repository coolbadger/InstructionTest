package importData;

import java.util.Date;
import java.util.List;

/**
 * Created by csw on 2016/1/18.
 */
public class OthersInfo {

    private AGV agv;//AGV运行速度，单位m/s
    private DistanceCrossingPlatformVessel distanceCrossingPlatformVessel;//道口-平台-船舶三点之间的距离信息
    private MainCar mainCar;//主小车在各个位置之间的运行速度

    public AGV getAgv() {
        return agv;
    }

    public void setAgv(AGV agv) {
        this.agv = agv;
    }

    public DistanceCrossingPlatformVessel getDistanceCrossingPlatformVessel() {
        return distanceCrossingPlatformVessel;
    }

    public void setDistanceCrossingPlatformVessel(DistanceCrossingPlatformVessel distanceCrossingPlatformVessel) {
        this.distanceCrossingPlatformVessel = distanceCrossingPlatformVessel;
    }

    public MainCar getMainCar() {
        return mainCar;
    }

    public void setMainCar(MainCar mainCar) {
        this.mainCar = mainCar;
    }

}

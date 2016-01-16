package importData

import groovy.json.JsonSlurper

/**
 * Created by csw on 2016/1/15.
 */
class ContainerInfoProcess {

    private String jsonStr
    public static List<ContainerInfo> containerInfoList
    private String dateFormat = "yyyy-MM-dd HH:mm:ss"

    public ContainerInfoProcess() {
        jsonStr = ""
        containerInfoList = new ArrayList<ContainerInfo>()
    }

    //Json字符串解析编码
    public List<ContainerInfo> getContainerInfo(StringBuffer jsonStr) {

        def root = new JsonSlurper().parseText(jsonStr.toString())

        assert root instanceof List//根据读入数据的格式，可以直接把json转换成List
//        println root


        root.each {container->
            ContainerInfo containerInfo = new ContainerInfo()
            try{
                assert container instanceof Map
                containerInfo.IYC_CNTRNO = container.IYCCNTRNO
                containerInfo.IYC_CNTR_AREA_ID = container.IYCCNTRAREAID
                containerInfo.IYC_VOYID = container.IYCVOYID
                containerInfo.IYC_CTYPECD = container.IYCCTYPECD
                containerInfo.IYC_CSZ_CSIZECD = container.IYCCSZCSIZECD
                containerInfo.IYC_PORTCD = container.IYCPORTCD
                containerInfo.IYC_WEIGHT = container.IYCWEIGHT
                containerInfo.IYC_DNGFG = container.IYCDNGFG
                containerInfo.IYC_REFFG = container.IYCREFFG
                containerInfo.IYC_YLOCATION = container.IYCYLOCATION
                containerInfo.IYC_PLANFG = container.IYCPLANFG
                containerInfo.IYC_RETIME = container.IYCRETIME
            }catch (Exception e){
                e.printStackTrace()
            }
            this.containerInfoList.add(containerInfo)
        }

        return this.containerInfoList
    }

}

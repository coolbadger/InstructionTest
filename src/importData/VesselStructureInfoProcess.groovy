package importData

import groovy.json.JsonSlurper

/**
 * Created by csw on 2016/1/16.
 */
class VesselStructureInfoProcess {

    //Json字符串解析编码
    public static List<VesselStructureInfo> getVesselStructureInfo(String jsonStr) {

        boolean isError = false;
        List<VesselStructureInfo> vesselStructureInfoList = new ArrayList<VesselStructureInfo>();

        List<HatchPositionInfo> hatchPositionInfoList = new ArrayList<HatchPositionInfo>();
        List<BayPositionInfo> bayPositionInfoList = new ArrayList<BayPositionInfo>();

        Set<String> hatchs =new HashSet<String>();          //舱集合
        Set<String> bays = new HashSet<String>();           //倍集合

        try{
            def root = new JsonSlurper().parseText(jsonStr)

            assert root instanceof List//根据读入数据的格式，可以直接把json转换成List

            root.each { vesselStructure ->
                VesselStructureInfo vesselStructureInfo = new VesselStructureInfo()
                assert vesselStructure instanceof Map
                vesselStructureInfo.VHTID = vesselStructure.VHTID
                vesselStructureInfo.LENGTH = vesselStructure.LENGTH
                vesselStructureInfo.VHTPOISITION = vesselStructure.VHTPOSITION;
                vesselStructureInfo.VBYBAYID = vesselStructure.VBYBAYID
                vesselStructureInfo.VBYPOSITION = vesselStructure.VBYPOSITION
                vesselStructureInfo.VTRTIERNO = vesselStructure.VTRTIERNO
                vesselStructureInfo.VTRTIERSEQ = vesselStructure.VTRTIERSEQ
                vesselStructureInfo.VRWROWNO = vesselStructure.VRWROWNO
                vesselStructureInfo.VRWROWSEQ = vesselStructure.VRWROWSEQ
                vesselStructureInfo.VLCVWCID = vesselStructure.VLCVWCID
                vesselStructureInfoList.add(vesselStructureInfo)
            }
        }catch (Exception e){
            System.out.println("船舶结构数据解析时，发现json数据异常！")
            isError = true;
            e.printStackTrace()
        }
        if(isError) {
            System.out.println("船舶结构数据解析失败！")
            return null;
        }else {
            System.out.println("船舶结构数据解析成功！")
            HatchPositionInfo newhatchPositionInfo;
            BayPositionInfo newbayPositionInfo;
            Integer startposition = ImportData.voyageInfoList.get(0).getSTARTPOSITION();
            for (VesselStructureInfo vesselStructureInfo:vesselStructureInfoList) {
                String VHTID = vesselStructureInfo.getVHTID().toString();
                Integer Length = vesselStructureInfo.getLENGTH();
                Integer VHTPOSITION = vesselStructureInfo.getVHTPOISITION();
                String VBYBAYID = vesselStructureInfo.getVBYBAYID().toString();
                Integer VBYPOSIYION = vesselStructureInfo.getVBYPOSITION();
                if (!hatchs.contains(VHTID)){
                    newhatchPositionInfo = new HatchPositionInfo();
                    newhatchPositionInfo.setVHT_ID(VHTID);
                    newhatchPositionInfo.setLENGTH(Length);
                    Integer position = startposition+VHTPOSITION;
                    newhatchPositionInfo.setPOSITION(position);
                    hatchs.add(VHTID);
                    hatchPositionInfoList.add(newhatchPositionInfo);
                }
                if (!bays.contains(VBYBAYID))
                {
                    newbayPositionInfo = new BayPositionInfo();
                    newbayPositionInfo.setVHT_ID(VHTID);
                    newbayPositionInfo.setVBY_BAYID(VBYBAYID);
                    newbayPositionInfo.setVBY_POSITION(startposition+VBYPOSIYION);
                    bays.add(VBYBAYID);
                    bayPositionInfoList.add(newbayPositionInfo);
                }
            }
            ImportData.hatchPositionInfoList = hatchPositionInfoList;
            ImportData.bayPositionInfoList = bayPositionInfoList;

            //调试信息
            for (HatchPositionInfo hatchPositionInfo1:ImportData.hatchPositionInfoList)
            {
                System.out.println(hatchPositionInfo1.getVHT_ID()+" "+hatchPositionInfo1.getPOSITION());
            }
            System.out.println("倍位信息");
            for (BayPositionInfo bayPositionInfo1:ImportData.bayPositionInfoList)
            {
                System.out.println(bayPositionInfo1.getVBY_BAYID()+" "+bayPositionInfo1.getVBY_POSITION());
            }
            //System.out.println(vesselStructureInfoList.size());
            return vesselStructureInfoList
        }
    }
}

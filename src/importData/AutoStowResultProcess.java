package importData;

import utils.FileUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by csw on 2016/1/20.
 */
public class AutoStowResultProcess {

    public static HashMap<String,String[]> getAutoStowResult() {

        HashMap<String,String[]> stringMap = new HashMap<String, String[]>();
        String filePath = "E:/StowageResult.txt";
        try{
            String stowResult = FileUtil.readFileToString(new File(filePath)).toString();
            System.out.println(stowResult);
            String[] str = stowResult.split("#");
            System.out.println(str.length);
            for(int i=0; i<str.length; i++) {
                String[] weizi = str[i].split(",")[0].split("%");
                String xianghao = str[i].split(",")[1];
                String[] cangxiangwei = str[i].split(",")[2].split("%");
                String wz = weizi[0] + "." + weizi[1] + "." +weizi[2] + "." +weizi[3];//key,船上的位置
                String[] value = new String[3];//value,0放箱区位置，1放箱号，2放尺寸
                value[0] = cangxiangwei[0] + "." + cangxiangwei[1] + "." +cangxiangwei[2] + "." +cangxiangwei[3];
                value[1] = xianghao;
                if(Integer.valueOf(weizi[1]) % 2 != 0) {//倍为基数
                    value[2] = "20";
                } else {
                    value[2] = "40";
                }
                System.out.println(wz+"----"+value[0]+"-"+value[1]+"-"+value[2]);
                stringMap.put(wz, value);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return stringMap;
    }
}

package instruction;

import java.util.List;

/**
 * Created by Badger on 16/1/7.
 */
public class UnitPosition {
    private String area;        //场区
    private String bay;         //贝
    private String lay;         //位
    private String tie;         //层

    /*
    箱位置字符串格式:场区.贝.位.层
    或者:场区-贝位层(各两位数字)
     */
    public String toString() {
        String unionStr = area + "." + bay + "." + lay + "." + tie;
        return unionStr;
    }

    public UnitPosition() {
    }

    //根据船上和场地两种位置信息格式,处理字符串
    public UnitPosition(String positionStr) {
        int pointIndex = positionStr.indexOf(".", positionStr.length() - 3);

        //统一长度格式,lay插入"0",层替换"."
        if (pointIndex > 3) {
            String newStr = positionStr.substring(0, pointIndex - 1) + "0" + positionStr.substring(pointIndex - 1);
            positionStr = newStr.replace(".", "0");
        }

        if (positionStr.length() > 7) {
            //如果是船上位置,删掉多余的"-"
            if (positionStr.charAt(positionStr.length() - 7) == '-')
                this.area = positionStr.substring(0, positionStr.length() - 7);
            else
                this.area = positionStr.substring(0, positionStr.length() - 6);
            this.bay = positionStr.substring(positionStr.length() - 6, positionStr.length() - 4);
            this.lay = positionStr.substring(positionStr.length() - 4, positionStr.length() - 2);
            this.tie = positionStr.substring(positionStr.length() - 2, positionStr.length() - 0);
        } else {
            String[] positionArr = positionStr.split(".");
            if (positionArr.length == 4) {
                this.area = positionArr[0];
                this.bay = positionArr[1];
                this.lay = positionArr[2];
                this.tie = positionArr[3];
            }
        }
    }

    public UnitPosition(String area, String bay, String lay, String tie) {
        this.area = area;
        this.bay = bay;
        this.lay = lay;
        this.tie = tie;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBay() {
        return bay;
    }

    public void setBay(String bay) {
        this.bay = bay;
    }

    public String getLay() {
        return lay;
    }

    public void setLay(String lay) {
        this.lay = lay;
    }

    public String getTie() {
        return tie;
    }

    public void setTie(String tie) {
        this.tie = tie;
    }

}

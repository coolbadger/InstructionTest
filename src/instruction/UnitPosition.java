package instruction;

/**
 * Created by Badger on 16/1/7.
 */
public class UnitPosition {
    private String area;        //场区
    private String bay;         //贝
    private String lay;         //位
    private String tie;         //层

    public String toString() {
        String unionStr = area + bay + lay + tie;
        return unionStr;
    }

    public UnitPosition() {
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

package importData;

import java.util.Map;

/**
 * 船舶结构
 * Created by csw on 2016/1/16.
 */
public class VesselStructureInfo {

    private Integer VHTID;//舱位ID
    private Integer LENGTH;//舱位长度
    private Integer VHTPOSITION; //舱开始相对于船头位置
    private Integer VBYBAYID;//倍位ID
    private Integer VBYPOSITION;//倍位中心相对于船头位置
    private Integer VTRTIERNO;//层号
    private Integer VTRTIERSEQ;//层序号
    private Integer VRWROWNO;//排号
    private Integer VRWROWSEQ;//排序号
    private Integer VLCVWCID;//重量等级ID

    private String groupId;//属性组

    public Integer getVHTID() {
        return VHTID;
    }

    public void setVHTID(Integer VHTID) {
        this.VHTID = VHTID;
    }

    public Integer getLENGTH() {
        return LENGTH;
    }

    public void setLENGTH(Integer LENGTH) {
        this.LENGTH = LENGTH;
    }

    public Integer getVHTPOISITION() {
        return VHTPOSITION;
    }

    public void setVHTPOISITION(Integer VHTPOISITION) {
        this.VHTPOSITION = VHTPOISITION;
    }

    public Integer getVBYBAYID() {
        return VBYBAYID;
    }

    public void setVBYBAYID(Integer VBYBAYID) {
        this.VBYBAYID = VBYBAYID;
    }

    public Integer getVBYPOSITION() {
        return VBYPOSITION;
    }

    public void setVBYPOSITION(Integer VBYPOSITION) {
        this.VBYPOSITION = VBYPOSITION;
    }

    public Integer getVTRTIERNO() {
        return VTRTIERNO;
    }

    public void setVTRTIERNO(Integer VTRTIERNO) {
        this.VTRTIERNO = VTRTIERNO;
    }

    public Integer getVTRTIERSEQ() {
        return VTRTIERSEQ;
    }

    public void setVTRTIERSEQ(Integer VTRTIERSEQ) {
        this.VTRTIERSEQ = VTRTIERSEQ;
    }

    public Integer getVRWROWNO() {
        return VRWROWNO;
    }

    public void setVRWROWNO(Integer VRWROWNO) {
        this.VRWROWNO = VRWROWNO;
    }

    public Integer getVRWROWSEQ() {
        return VRWROWSEQ;
    }

    public void setVRWROWSEQ(Integer VRWROWSEQ) {
        this.VRWROWSEQ = VRWROWSEQ;
    }

    public Integer getVLCVWCID() {
        return VLCVWCID;
    }

    public void setVLCVWCID(Integer VLCVWCID) {
        this.VLCVWCID = VLCVWCID;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

package instruction;

/**
 * Created by Badger on 16/1/7.
 */
public class JsonProcess {
    private String jsonStr;
    private MoveInfo moveInfo;

    public JsonProcess() {
        jsonStr = "";
        moveInfo = new MoveInfo();
    }

    //Json字符串解析编码
    public MoveInfo getMoveInfo(String jsonStr) {

        return this.moveInfo;
    }

    //Json字符串编码
    public String getJsonStr(MoveInfo moveInfo) {

        return this.jsonStr;
    }
}

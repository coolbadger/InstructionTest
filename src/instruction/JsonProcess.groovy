package instruction

import demo.Instruction
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

/**
 * Created by Badger on 16/1/7.
 */
class JsonProcess {
    private String jsonStr
    private List<MoveInfo> moveInfoList
    private String dateFormat = "YYYY-MM-DD HH:mm:ss"

    public JsonProcess() {
        jsonStr = ""
        moveInfoList = new ArrayList<MoveInfo>()
    }

    //Json字符串解析编码
    public List<MoveInfo> getMoveInfos(String jsonStr) {
        //用Demo测试
        jsonStr = Instruction.receiveJsonDemo

        def root = new JsonSlurper().parseText(jsonStr)

        assert root instanceof Map
        assert root.Instructions instanceof List

        List moveInstructionList = root.Instructions
        for (def movInsItemArr in moveInstructionList) {
            def movInsItem = movInsItemArr.content

            assert movInsItem instanceof Map

            MoveInfo moveInfo = new MoveInfo()
            //尝试解析Json
            try {
                moveInfo.batchId = movInsItem.batchId
                moveInfo.moveId = movInsItem.moveId
                moveInfo.moveKind = movInsItem.moveKind
                moveInfo.unitId = movInsItem.unitId

                if (movInsItem.eFromPosition)
                    moveInfo.eFromPosition = new UnitPosition(
                            movInsItem.eFromPosition.area,
                            movInsItem.eFromPosition.bay,
                            movInsItem.eFromPosition.lay,
                            movInsItem.eFromPosition.tie
                    )
                if (movInsItem.eToPosition)
                    moveInfo.eToPosition = new UnitPosition(
                            movInsItem.eToPosition.area,
                            movInsItem.eToPosition.bay,
                            movInsItem.eToPosition.lay,
                            movInsItem.eToPosition.tie
                    )
                if (movInsItem.aFromPosition)
                    moveInfo.aFromPosition = new UnitPosition(
                            movInsItem.aFromPosition.area,
                            movInsItem.aFromPosition.bay,
                            movInsItem.aFromPosition.lay,
                            movInsItem.aFromPosition.tie
                    )
                if (movInsItem.aToPosition)
                    moveInfo.aToPosition = new UnitPosition(
                            movInsItem.aToPosition.area,
                            movInsItem.aToPosition.bay,
                            movInsItem.aToPosition.lay,
                            movInsItem.aToPosition.tie
                    )
                moveInfo.fetchCHE = movInsItem.fetchCHE
                moveInfo.carryCHE = movInsItem.carryCHE
                moveInfo.putCHE = movInsItem.putCHE

                moveInfo.dispatchTime = Date.parse(dateFormat, movInsItem.dispatchTime)
                moveInfo.fetchTime = Date.parse(dateFormat, movInsItem.fetchTime)
                moveInfo.carryTime = Date.parse(dateFormat, movInsItem.carryTime)
                moveInfo.putTime = Date.parse(dateFormat, movInsItem.putTime)

            } catch (Exception e) {
                e.printStackTrace()
            }

            this.moveInfoList.add(moveInfo)
        }

        return this.moveInfoList
    }

    //Json字符串编码
    public String getJsonStr(List<MoveInfo> moveInfoList) {
        def builder = new JsonBuilder()
        def instruction = new JsonBuilder()
        List InstructionList = new ArrayList()
        //生成所有指令的Json
        for (MoveInfo moveInfo in moveInfoList) {
            instruction {
                batchId moveInfo.batchId
                moveId moveInfo.moveId
                moveKind moveInfo.moveKind
                state moveInfo.state
                unitId moveInfo.unitId

                //预计提箱位置
                eFromPosition {
                    area moveInfo.eFromPosition.area
                    bay moveInfo.eFromPosition.bay
                    lay moveInfo.eFromPosition.lay
                    tie moveInfo.eFromPosition.tie
                }
                //预计放箱位置
                eToPosition {
                    area moveInfo.eToPosition.area
                    bay moveInfo.eToPosition.bay
                    lay moveInfo.eToPosition.lay
                    tie moveInfo.eToPosition.tie
                }

//                dispatchTime moveInfo.dispatchTime
            }
            InstructionList.add(instruction)
        }
        //打包Json指令
        builder {
            Instructions(InstructionList)
            dispatchTime new Date().format(dateFormat)
        }
        this.jsonStr = JsonOutput.prettyPrint(builder.toString())
        return this.jsonStr;
    }
}

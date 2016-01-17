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
    private String dateFormat = "yyyy-MM-dd HH:mm:ss"

    public JsonProcess() {
        jsonStr = ""
        moveInfoList = new ArrayList<MoveInfo>()
    }

    //Json字符串解析编码
    public List<MoveInfo> getMoveInfos(String jsonStr) {
        //用Demo测试
//        jsonStr = Instruction.receiveJsonDemo

        def root = new JsonSlurper().parseText(jsonStr)

        assert root instanceof Map
        assert root.Instructions instanceof List

        List moveInstructionList = root.Instructions
        Date instructionDate = Date.parse(dateFormat, root.dispatchTime)
        for (def movInsItemArr in moveInstructionList) {
            def movInsItem = movInsItemArr.content

            assert movInsItem instanceof Map

            MoveInfo moveInfo = new MoveInfo()
            //尝试解析Json
            try {
                moveInfo.gkey = movInsItem.gkey
                moveInfo.batchId = movInsItem.batchId
                moveInfo.moveId = movInsItem.moveId
                moveInfo.moveKind = movInsItem.moveKind
                moveInfo.unitId = movInsItem.unitId
                moveInfo.unitLength = movInsItem.unitLength
                moveInfo.state = movInsItem.state

                if (movInsItem.exFromPosition)
                    moveInfo.exFromPosition = new UnitPosition(
                            movInsItem.exFromPosition.area,
                            movInsItem.exFromPosition.bay,
                            movInsItem.exFromPosition.lay,
                            movInsItem.exFromPosition.tie
                    )
                if (movInsItem.exToPosition)
                    moveInfo.exToPosition = new UnitPosition(
                            movInsItem.exToPosition.area,
                            movInsItem.exToPosition.bay,
                            movInsItem.exToPosition.lay,
                            movInsItem.exToPosition.tie
                    )
                if (movInsItem.acFromPosition)
                    moveInfo.acFromPosition = new UnitPosition(
                            movInsItem.acFromPosition.area,
                            movInsItem.acFromPosition.bay,
                            movInsItem.acFromPosition.lay,
                            movInsItem.acFromPosition.tie
                    )
                if (movInsItem.acToPosition)
                    moveInfo.acToPosition = new UnitPosition(
                            movInsItem.acToPosition.area,
                            movInsItem.acToPosition.bay,
                            movInsItem.acToPosition.lay,
                            movInsItem.acToPosition.tie
                    )
                moveInfo.fetchCHE = movInsItem.fetchCHE
                moveInfo.carryCHE = movInsItem.carryCHE
                moveInfo.putCHE = movInsItem.putCHE

                moveInfo.dispatchTime = instructionDate
                if (movInsItem.fetchTime != null)
                    moveInfo.fetchTime = Date.parse(dateFormat, movInsItem.fetchTime)
                if (movInsItem.carryTime != null)
                    moveInfo.carryTime = Date.parse(dateFormat, movInsItem.carryTime)
                if (movInsItem.putTime)
                    moveInfo.putTime = Date.parse(dateFormat, movInsItem.putTime)

                if (moveInfo.toString())
                    println(moveInfo.toString())
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
        def instruction = null
        List InstructionList = new ArrayList()
        //生成所有指令的Json
        moveInfoList.each { moveInfo ->
            instruction = new JsonBuilder()
            instruction {
                gkey moveInfo.gkey
                batchId moveInfo.batchId
                moveId moveInfo.moveId
                moveKind moveInfo.moveKind
                state moveInfo.state
                unitId moveInfo.unitId
                unitLength moveInfo.unitLength

                //预计提箱位置
                exFromPosition {
                    area moveInfo.exFromPosition.area
                    bay moveInfo.exFromPosition.bay
                    lay moveInfo.exFromPosition.lay
                    tie moveInfo.exFromPosition.tie
                }
                //预计放箱位置
                exToPosition {
                    area moveInfo.exToPosition.area
                    bay moveInfo.exToPosition.bay
                    lay moveInfo.exToPosition.lay
                    tie moveInfo.exToPosition.tie
                }
                /*
                本段代码用于生成测试的返回指令,在发送指令时无需生成

                //实际提箱位置
                acFromPosition {
                    area moveInfo.exFromPosition.area
                    bay moveInfo.exFromPosition.bay
                    lay moveInfo.exFromPosition.lay
                    tie moveInfo.exFromPosition.tie
                }
                //实际放箱位置
                acToPosition {
                    area moveInfo.exToPosition.area
                    bay moveInfo.exToPosition.bay
                    lay moveInfo.exToPosition.lay
                    tie moveInfo.exToPosition.tie
                }
                fetchCHE ''
                carryCHE ''
                putCHE ''

                fetchTime new Date().format(dateFormat)
                carryTime new Date().format(dateFormat)
                putTime new Date().format(dateFormat)

                 */
            }
            InstructionList.add(instruction)

        }
        //打包Json指令
        builder {
            Instructions(InstructionList)
            dispatchTime new Date().format(dateFormat)
        }
        this.jsonStr = builder.toString()
        //为直观调试方便的格式化输出
//        this.jsonStr = JsonOutput.prettyPrint(builder.toString())
        return this.jsonStr;
    }
}

package instruction

import main.Logger
import main.UiGlobal
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFWorkbook

/**
 * Created by Badger on 16/1/8.
 */
class LoadConfig {
    private File file
    private String filePath
    private HSSFWorkbook workbook
    private List<MoveInfo> moveInfoList
    private Logger logger

    public LoadConfig() {
        filePath = "MoveEvent_Load.xls"
        file = new File(filePath)
        workbook = new HSSFWorkbook(new FileInputStream(file))
        logger = UiGlobal.logger
        moveInfoList = new ArrayList<MoveInfo>()
    }

    public List<MoveInfo> getInstructions() {
        //暂时取固定批次号
        String batchStr = ""
        int sheetCount = workbook.size()
        int currentSheet = 1
        int itemIndex = 0
        workbook.each { sheet ->
            logger.info("读取Sheet(" + currentSheet + "/" + sheetCount + "):" + sheet.sheetName)
//            logger.info("一共有" + sheet.lastRowNum + "行数据")

            batchStr = sheet.sheetName
            //处理每一行数据
            sheet.eachWithIndex { row, index ->
                boolean read_correct = true
                MoveInfo newMoveInfo = new MoveInfo()

                if (index == 0) {//检查标题行
                    logger.info("检查标题行......")
                    if (stripe.call(getCellVal(row.getCell(0))) != 'Move Kind') read_correct = false
                    if (stripe.call(getCellVal(row.getCell(1))) != 'Unit Category') read_correct = false
                    if (stripe.call(getCellVal(row.getCell(2))) != 'Unit Nbr') read_correct = false
                    if (stripe.call(getCellVal(row.getCell(3))) != 'CHE') read_correct = false
                    if (stripe.call(getCellVal(row.getCell(4))) != 'Unit Length') read_correct = false
                    if (stripe.call(getCellVal(row.getCell(5))) != 'From Position') read_correct = false
                    if (stripe.call(getCellVal(row.getCell(6))) != 'To Position') read_correct = false

                    if (read_correct == false) {
                        logger.info("Excel文件列名错误！")
                    } else {
                        logger.info("标题行列名正确,开始处理数据……")
                    }
                } else if (stripe.call(getCellVal(row.getCell(0)))) {
                    itemIndex++
                    newMoveInfo.setBatchId(batchStr)
                    newMoveInfo.setMoveId(itemIndex)
                    newMoveInfo.setMoveKind(stripe.call(getCellVal(row.getCell(0))))
                    newMoveInfo.setUnitId(stripe.call(getCellVal(row.getCell(2))))
                    newMoveInfo.setUnitLength(stripe.call(getCellVal(row.getCell(4))))
                    newMoveInfo.setExFromPosition(new UnitPosition(stripe.call(getCellVal(row.getCell(5)))))
                    newMoveInfo.setExToPosition(new UnitPosition(stripe.call(getCellVal(row.getCell(6)))))
                    newMoveInfo.setState(0)
                    moveInfoList.add(newMoveInfo)
                }
            }
        }
        logger.info("一共有" + itemIndex + "条有效数据")

        return this.moveInfoList
    }

    def getCellVal(HSSFCell cell) {
        if (cell == null) return ""
        else {
            def value
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    value = cell.getBooleanCellValue();
                    break
                case HSSFCell.CELL_TYPE_ERROR:
                    value = cell.getErrorCellValue()
                    break
                case HSSFCell.CELL_TYPE_NUMERIC:
                    value = "" + cell.getNumericCellValue()
                    for (int i = 0; i < value.length(); i++) {
                        if (".".equals(value.charAt(i))) {
                            //有小数点
                        } else {
                            //无小数点，转换为int后转换为String
                            value = "" + ((int) Double.parseDouble(value))
                        }
                    }
                    break
                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue()
                    break
                default:
                    value = ""
            }
            return value
        }

    }

    def stripe = {
        it.toString().reverse().stripIndent().reverse().stripIndent()
    }
}

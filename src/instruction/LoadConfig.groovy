package instruction

import main.LogListener
import main.Logger
import main.UiGlobal
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem

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
        filePath = ""
        file = new File(filePath)
        workbook = new HSSFWorkbook(new FileInputStream(new POIFSFileSystem(file)))
        logger = UiGlobal.logger
    }

    public List<MoveInfo> getInstructions() {
        int sheetCount = workbook.size()
        int currentSheet = 1
        workbook.each { sheet ->
            logger.info("读取Sheet(" + currentSheet + "/" + sheetCount + "):" + sheet.sheetName)
            logger.info("一共有" + sheet.lastRowNum + "行数据")

            //处理每一行数据
            sheet.eachWithIndex { row, index ->
                boolean read_correct = true
                MoveInfo newMoveInfo = new MoveInfo()

                if (index == 0) {//检查标题行
                    logger.info("检查标题行......")
                    if (stripe.call(getCellVal(row.getCell(0))) != '提单号') read_correct = false
                    if (stripe.call(getCellVal(row.getCell(1))) != '箱号') read_correct = false
                    if (stripe.call(getCellVal(row.getCell(2))) != '箱长') read_correct = false
                    if (stripe.call(getCellVal(row.getCell(3))) != '箱型') read_correct = false

                    if (read_correct == false) {
                        logger.info("Excel文件列名错误！应为：提单号 箱号 箱长 箱型")
                    }
                }

                newMoveInfo.setBatchId(stripe.call(getCellVal(row.getCell(0))))
                newMoveInfo.setMoveId(stripe.call(getCellVal(row.getCell(0))))
                newMoveInfo.setMoveKind(stripe.call(getCellVal(row.getCell(0))))
                newMoveInfo.setFetchCHE(stripe.call(getCellVal(row.getCell(0))))
                newMoveInfo.setFetchTime(stripe.call(getCellVal(row.getCell(0))))
                newMoveInfo.setCarryCHE(stripe.call(getCellVal(row.getCell(0))))
                newMoveInfo.setCarryTime(stripe.call(getCellVal(row.getCell(0))))
                newMoveInfo.setPutCHE(stripe.call(getCellVal(row.getCell(0))))
                newMoveInfo.setPutTime(stripe.call(getCellVal(row.getCell(0))))

                moveInfoList.add(newMoveInfo)
            }
        }

        return null
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

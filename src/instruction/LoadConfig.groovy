package instruction

import main.LogListener
import main.Logger
import main.UiGlobal
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

        }

        return null
    }
}

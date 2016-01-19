package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

/**
 * Created by csw on 2016/1/16.
 */
public class FileUtil {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static StringBuffer readFileToString(File file) {
        //读取File
        StringBuffer fileContent = new StringBuffer(); // 文件很长的话建议使用StringBuffer
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                fileContent.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}

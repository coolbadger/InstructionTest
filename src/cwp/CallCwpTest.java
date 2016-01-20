package cwp;

import java.io.File;


public class CallCwpTest {

	static {  
        System.loadLibrary("cwp_to_java");  
    }  
      
    public static native String callCwp(String str1, String str2, String str3);
    
    public static String cwp() {
    	String cranes = FileReader.readToString(new File("E:/cwpToJavaData/cwpWorkCranes.txt"));
        String hatches = FileReader.readToString(new File("E:/cwpToJavaData/cwpWorkHatches.txt"));
        String moves = FileReader.readToString(new File("E:/cwpToJavaData/cwpWorkMoves.txt"));
    	String str = callCwp(cranes, hatches, moves);  
//        System.out.println(str);
        return str;
    }

}

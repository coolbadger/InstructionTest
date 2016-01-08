package main;

import javax.swing.*;

/**
 * Created by lekoxnfx on 16/1/8.
 */
public class SWInitSampleData extends SwingWorker {//创建初始案例数据
    @Override
    protected Object doInBackground() throws Exception {
        System.out.println("正在忙...");
        Thread.sleep(5000);
        System.out.println("忙完了");

        return null;
    }


}

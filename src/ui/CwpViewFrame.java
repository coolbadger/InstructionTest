package ui;

import javax.swing.*;

/**
 * Created by csw on 2016/1/18.
 */
public class CwpViewFrame extends JFrame{

    public CwpViewFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("cwp计划结果图");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);//居中显示

    }
}

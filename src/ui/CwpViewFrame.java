package ui;

import javax.swing.*;

/**
 * Created by csw on 2016/1/18.
 */
public class CwpViewFrame extends JFrame{

    private JLabel label;
    private JFileChooser chooser;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 400;

    public CwpViewFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("cwp计划结果图");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);//居中显示

        label = new JLabel();
        label.setIcon(new ImageIcon("E:/cwp_data/file.png"));
        add(label);
    }

//    public static void main(String[] args) {
//        new CwpViewFrame().setVisible(true);
//    }
}

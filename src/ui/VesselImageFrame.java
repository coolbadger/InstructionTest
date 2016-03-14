package ui;

import importData.ImportData;
import importData.VesselStructureInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.*;

/**
 * Created by ding on 2016/3/11 0011.
 */
public class VesselImageFrame extends JFrame {
    private JPanel topPanel;
    private JPanel contentPanel;
    private JLabel label;
    private JTextField textField;
    private JButton button;
    //船体四个点的坐标
    private  int[] SHIPX={0,150,850,1000};
    private  int[] SHIPY={250,500,500,250};

    VesselImageFrame() {initComponents();}

    private void initComponents(){
        Container c = getContentPane();
        c.setLayout(new BorderLayout(10,10));
        this.setTitle("侧视图");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1200,800);
        this.setLocationRelativeTo(null);

        //顶部Panel
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        label = new JLabel("请输入要查询的倍数");
        label.setFont(new Font("",0,20));

        textField = new JTextField(10);
        textField.setPreferredSize(new Dimension(10,28));
        //单击按钮
        button = new JButton("确定");
        {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!textField.getText().matches("[0-9]+") || Integer.valueOf(textField.getText()) % 2 == 0){
                        JFrame dialogBackground = new JFrame();
                        dialogBackground.setSize(300,300);
                        dialogBackground.setTitle("输入错误");
                        dialogBackground.add(new JLabel("         输入错误"));
                        dialogBackground.setLocationRelativeTo(null);
                        dialogBackground.setVisible(true);
                    }else {
                        int bayID = Integer.valueOf(textField.getText());
                        java.util.List<VesselStructureInfo> vesselStructureInfos = ImportData.vesselStructureInfoList;
                        Integer[] rowData = new Integer[3];
                        int maxTierUnder = 0;
                        int maxTierAbove = 0;
                        int maxCol = 0;
                        for (VesselStructureInfo vesselStructureInfo : vesselStructureInfos) {
                            //                rowData[0] = vesselStructureInfo.getVHTID();      //舱次
                            rowData[0] = vesselStructureInfo.getVBYBAYID();     //倍
                            rowData[1] = vesselStructureInfo.getVTRTIERNO();    //层
                            rowData[2] = vesselStructureInfo.getVRWROWNO();     //排
                            if( rowData[0] == bayID){
                                if(maxCol < rowData[2])maxCol = rowData[2];
                                if( rowData[1] < 80 &&  rowData[1] >= 0 && maxTierUnder < rowData[1]/2){
                                    maxTierUnder = rowData[1]/2;
                                }else if(rowData[1] > 80 && maxTierAbove < (rowData[1]-80)/2){
                                    maxTierAbove = (rowData[1]-80)/2;
                                }
                            }
                        }
                        ContainerImageFrame containerImageFrame = new ContainerImageFrame();
                        containerImageFrame.initComponents(bayID,maxTierUnder,maxCol,maxTierAbove,maxCol);
                        containerImageFrame.setVisible(true);
                    }
                }
            });
        }
        //添加顶部panel
        topPanel.add(label);
        topPanel.add(textField);
        topPanel.add(button);
        c.add("North",topPanel);

        //概图panel
        contentPanel = new JPanel();
        DrawShip drawShip = new DrawShip();  //画船
        DrawContainers drawContainers = new DrawContainers();   //画集装箱
        contentPanel.add(drawShip);
        contentPanel.add(drawContainers);
        c.add("Center",contentPanel);
    }

    //画船体
     class DrawShip extends JPanel{
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D drawShip;
            drawShip=(Graphics2D)g;
            drawShip.setColor(Color.BLACK); //设置弧形的颜色为黑色
            drawShip.setStroke(new BasicStroke(5)); //船的粗细
            drawShip.drawPolygon(SHIPX,SHIPY,4);    //画船四边形
            this.setOpaque(false);  //透明
            this.setBounds(100,100,1000,1000);
        }
    }

    //画集装箱
    class DrawContainers extends JPanel{
        protected  void  paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D drawboxes;
            drawboxes = (Graphics2D)g;
            drawboxes.setColor(Color.BLACK);
            drawboxes.setBackground(null);
            this.setBounds(100,100,1000,1000);
            this.setOpaque(false);

            java.util.List<VesselStructureInfo> vesselStructureInfos = ImportData.vesselStructureInfoList;
            int BAYCount;   //倍数
            HashSet<Integer> Bay = new HashSet<Integer>();  //统计倍数
            Map<Integer,Integer> MaxTierUnder = new HashMap<Integer, Integer>(); //记录甲板下最高层《倍号，层数》
            Map<Integer,Integer> MaxTierAbove = new HashMap<Integer, Integer>(); //记录甲板上最高层
            Integer[] rowData = new Integer[3];
                for (VesselStructureInfo vesselStructureInfo : vesselStructureInfos) {
    //                rowData[0] = vesselStructureInfo.getVHTID();      //舱次
                    rowData[0] = vesselStructureInfo.getVBYBAYID();     //倍
                    rowData[1] = vesselStructureInfo.getVTRTIERNO();    //层
    //                rowData[2] = vesselStructureInfo.getVRWROWNO();     //排
                    Bay.add(rowData[0]); //统计倍数
                    if( rowData[1] < 80 &&  rowData[1] >= 0){
                        if(!MaxTierUnder.containsKey(rowData[0]) || MaxTierUnder.get(rowData[0]) < rowData[1]){
                            MaxTierUnder.put(rowData[0], rowData[1]/2);
                        }
                    }else if(rowData[1] > 80){
                        if(!MaxTierAbove.containsKey(rowData[0]) ||MaxTierAbove.get(rowData[0]) < rowData[1]){
                            MaxTierAbove.put(rowData[0], (rowData[1]-80)/2);
                        }
                    }
                }
            BAYCount = Bay.size();
            int boxWidth = (SHIPX[2] - SHIPX[1]) / (BAYCount + 4); //计算每个集装箱多宽比较合适，850和150船底坐标，+4为了留有一定空隙
            int boxHeight = (SHIPY[1] - SHIPY[0]) / 15;   //假设甲板下最大就是15个集装箱
            for(int i = 0 ; i < BAYCount;i++) {
                //添加倍的文字
                if(i%2==0){
                    drawboxes.drawString(i*2+1+"B",SHIPX[1]+ boxWidth * (i+1) + (i/2)*5,SHIPY[1] + 15);
                    drawboxes.drawString(i*2+1+"A",SHIPX[1]+ boxWidth * (i+1) + (i/2)*5,MaxTierAbove.get(2+1)*boxHeight-50);//写成了固定值
                }
                //画集装箱
                for(int j=0;j<MaxTierUnder.get(i*2+1);j++){
                    Rectangle2D rectangle2D = new Rectangle2D.Double(SHIPX[1]+ boxWidth * (i+1) + (i/2)*5,SHIPY[1] - boxHeight*(j+1)-5,boxWidth,boxHeight);
                    drawboxes.draw(rectangle2D);
                }
                for(int j=0;j<MaxTierAbove.get(i*2+1);j++){
                    Rectangle2D rectangle2D = new Rectangle2D.Double(SHIPX[1]+ boxWidth * (i+1) + (i/2)*5,SHIPY[0] - boxHeight*(j+1)-5,boxWidth,boxHeight);
                    drawboxes.draw(rectangle2D);
                }
            }
        }
    }
}

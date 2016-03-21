package ui;

import importData.ImportData;
import importData.VesselStructureInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

/**
 * Created by ding on 2016/3/11 0011.
 */
public class VesselImageFrame extends JFrame {
    private JPanel topPanel;
    private JPanel contentPanel;
    private JPanel forScrollPanel;
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private int MaxTierUnderForDrawBoat = 0;//用来统计甲板下集装箱的最大层数以便于画船体
    private int MacTierAboveForDrawString = 0;//用来统计甲板下集装箱的最大层数以便于写集装箱上方的文字
    private int bayCount;   //倍数
    int boxWidth = 30; //集装箱宽度固定值40
    int boxHeight =  15;   //集装箱高度就是15
    int gap = 15; //倍之间的间隔
    int scrollwidth;
    //船体四个点的坐标(程序中对四个点进行过更新)
     int[] SHIPX={20,150,1150,1280};
     int[] SHIPY={200,450,450,200};

    HashSet<Integer> Bay = new HashSet<Integer>();  //统计倍数总数
    Map<Integer,Integer> MaxTierUnder = new HashMap<Integer, Integer>(); //记录甲板下每个倍最高层《倍号，层数》
    Map<Integer,Integer> MaxTierAbove = new HashMap<Integer, Integer>(); //记录甲板上最高层



    VesselImageFrame() {initComponents();}

    private void initComponents(){
        Container c = getContentPane();
//        c.setLayout(new BorderLayout(10,10));
        this.setTitle("侧视图");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1400,800);
        this.setLocationRelativeTo(null);

        java.util.List<VesselStructureInfo> vesselStructureInfos = ImportData.vesselStructureInfoList;
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
                if(MaxTierUnderForDrawBoat < rowData[1]/2) MaxTierUnderForDrawBoat = rowData[1]/2;

            }else if(rowData[1] > 80){
                if(!MaxTierAbove.containsKey(rowData[0]) ||MaxTierAbove.get(rowData[0]) < rowData[1]){
                    MaxTierAbove.put(rowData[0], (rowData[1]-80)/2);
                }
                if(MacTierAboveForDrawString < (rowData[1]-80)/2) MacTierAboveForDrawString = (rowData[1]-80)/2;
            }
        }
        bayCount = Bay.size();
        SHIPY[0] = SHIPY[1] - (MaxTierUnderForDrawBoat + 3) * 15;
        SHIPY[3] = SHIPY[2] - (MaxTierUnderForDrawBoat + 3) * 15;
        SHIPX[2] = SHIPX[1] + (bayCount+2)*boxWidth + gap*(bayCount/2);//+2为了有空隙空间
        SHIPX[3] = SHIPX[2] +130;
        scrollwidth = (int)((SHIPX[2]-SHIPX[1])*1.5+gap*(bayCount/2));


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

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
                    if(!textField.getText().matches("[0-9]+") || Integer.valueOf(textField.getText()) % 2 == 0){//判断输入是否合法。为奇数整数
                        JFrame dialogBackground = new JFrame();
                        dialogBackground.setSize(300,300);
                        dialogBackground.setTitle("输入错误");
                        dialogBackground.add(new JLabel("         输入错误"));
                        dialogBackground.setLocationRelativeTo(null);
                        dialogBackground.setVisible(true);
                    }else {
                        int bayID = Integer.valueOf(textField.getText());//获取倍数
                        java.util.List<VesselStructureInfo> vesselStructureInfos = ImportData.vesselStructureInfoList;
                        Integer[] rowData = new Integer[3];
                        int maxTierUnder = 0;
                        int maxTierAbove = 0;
                        int maxColUnder = 0;
                        int maxColAbove = 0;
                        for (VesselStructureInfo vesselStructureInfo : vesselStructureInfos) {
                            //                rowData[0] = vesselStructureInfo.getVHTID();      //舱次
                            rowData[0] = vesselStructureInfo.getVBYBAYID();     //倍
                            rowData[1] = vesselStructureInfo.getVTRTIERNO();    //层
                            rowData[2] = vesselStructureInfo.getVRWROWNO();     //排
                            if( rowData[0] == bayID){
                                //记录甲板上下的最高排数
                                if( rowData[1] < 80 &&  rowData[1] >= 0 && maxColUnder < rowData[2]){
                                    maxColUnder = rowData[2];
                                }else if(rowData[1] > 80 && maxColAbove <rowData[2]){
                                    maxColAbove = rowData[2];
                                }
                                //记录甲板上下的最高层数
                                if( rowData[1] < 80 &&  rowData[1] >= 0 && maxTierUnder < rowData[1]/2){
                                    maxTierUnder = rowData[1]/2;
                                }else if(rowData[1] > 80 && maxTierAbove < (rowData[1]-80)/2){
                                    maxTierAbove = (rowData[1]-80)/2;
                                }
                            }
                        }
                        ContainerImageFrame containerImageFrame = new ContainerImageFrame(); //集装箱倍位概图界面
                        containerImageFrame.initComponents(bayID,maxTierAbove,maxColAbove,maxTierUnder,maxColUnder);
                        containerImageFrame.setVisible(true);
                    }
                }
            });
        }


        //添加顶部panel
        topPanel.add(label);
        topPanel.add(textField);
        topPanel.add(button);

        contentPanel = new JPanel();
        DrawContainers drawContainers = new DrawContainers();   //画集装箱
        DrawShip drawShip = new DrawShip();  //画船

        drawContainers.setVisible(true);
        contentPanel.add(drawShip);
        contentPanel.add(drawContainers);
//        SHIPX[2] = SHIPX[1] + (drawContainers.getbayCount()+2)*boxWidth + gap*(bayCount/2);//+2为了有空隙空间
//        SHIPX[3] = SHIPX[2] +130;
//        contentPanel.setPreferredSize(new Dimension(scrollwidth,700));
        contentPanel.setPreferredSize(new Dimension((int)((SHIPX[2] - SHIPX[1])*1.5),700));
//        contentPanel.setPreferredSize(new Dimension(1800,600));
        scrollPane.setViewportView(contentPanel);

        forScrollPanel = new JPanel();
        forScrollPanel.setLayout(new BorderLayout(10,10));
        forScrollPanel.add("North",topPanel);
        forScrollPanel.add("Center",scrollPane);

        c.add(forScrollPanel);
    }

    //画船体
     class DrawShip extends JPanel{
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D drawShip;
            drawShip=(Graphics2D)g;
            drawShip.setColor(Color.BLUE); //设置弧形的颜色为黑色
            drawShip.setStroke(new BasicStroke(5)); //船的粗细

//            drawShip.drawPolygon(SHIPX,SHIPY,4);    //画船四边形
            drawShip.drawLine(SHIPX[0],SHIPY[0],SHIPX[3],SHIPY[3]);//画甲板线
            drawShip.drawLine(SHIPX[1],SHIPY[1],SHIPX[2],SHIPY[2]);//画船底线
            QuadCurve2D curveLeft = new QuadCurve2D.Double(SHIPX[0],SHIPY[0],SHIPX[1]+20,SHIPY[1]-100,SHIPX[1],SHIPY[1]);//画船的左侧线条
            QuadCurve2D curveRight = new QuadCurve2D.Double(SHIPX[2],SHIPY[2],SHIPX[2]-20,SHIPY[2]-100,SHIPX[3],SHIPY[3]);//画船的右侧线条
            drawShip.draw(curveLeft);
            drawShip.draw(curveRight);

            drawShip.setFont(new Font("",Font.BOLD,30));
            drawShip.drawString("船头",SHIPX[0],SHIPY[1]);
            this.setOpaque(false);  //透明
            this.setBounds(100,100,1500,1500);
//            this.setPreferredSize(new Dimension((int)((SHIPX[3] - SHIPX[0])*1.5),600));
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
            drawboxes.setStroke(new BasicStroke(2));//集装箱粗细
            drawboxes.setFont(new Font("",Font.BOLD,15));
            this.setBounds(100,100,1500,1500);
            this.setOpaque(false);



//            int boxWidth = (SHIPX[2] - SHIPX[1]) / (BAYCount + 10); //计算每个集装箱多宽比较合适，+10为了留有一定空隙
//            int gap = ((SHIPX[2] - SHIPX[1])-boxWidth*BAYCount)/15;
            for(int i = 0 ; i < bayCount;i++) {
                //添加倍的文字
                if(i%2==0){
                    drawboxes.drawString(i*2+1+"B",SHIPX[1]+ boxWidth * (i+1) + (i/2)*gap,SHIPY[1] + 20);
                    drawboxes.drawString(i*2+1+"A",SHIPX[1]+ boxWidth * (i+1) + (i/2)*gap,SHIPY[1]-(MaxTierUnderForDrawBoat + 3)*boxHeight - (MacTierAboveForDrawString + 3) * boxHeight);
                }
                //画集装箱
                for(int j=0;j<MaxTierUnder.get(i*2+1);j++){
                    Rectangle2D rectangle2D = new Rectangle2D.Double(SHIPX[1]+ boxWidth * (i+1) + (i/2)*gap,SHIPY[1] - boxHeight*(j+1)-5,boxWidth,boxHeight);
                    drawboxes.draw(rectangle2D);
                }
                for(int j=0;j<MaxTierAbove.get(i*2+1);j++){
                    Rectangle2D rectangle2D = new Rectangle2D.Double(SHIPX[1]+ boxWidth * (i+1) + (i/2)*gap,SHIPY[0] - boxHeight*(j+1)-5,boxWidth,boxHeight);
                    drawboxes.draw(rectangle2D);
                }
            }
        }

    }
}

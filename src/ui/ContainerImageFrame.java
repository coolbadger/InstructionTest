package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ding on 2016/3/13 0013.
 */
public class ContainerImageFrame extends JFrame{
    private JPanel contentPanel;
    private JLabel boardLabel;
    private JScrollPane scrollPane;
    private JPanel forScrolPanel;

//    ContainerImageFrame(){initComponents();}

    public void initComponents(int bayID, int rowAbove , int colAbove,int rowUnder,int colUnder){
        Container c = getContentPane();
//        c.add(contentPanel);
        this.setTitle("第"+bayID+"倍倍位概图");
        this.setLayout(new BorderLayout());
        this.setSize(1200,800);
        //甲板字样
        boardLabel = new JLabel();
        boardLabel.setFont(new Font("宋体",Font.BOLD,30));
        boardLabel.setHorizontalAlignment(JLabel.RIGHT);
        boardLabel.setText("    甲板");

        //集装箱panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2,1,1,10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        contentPanel.setSize(new Dimension(900,900));
        //画集装箱
        DrawContainers drawContainersAbove = new DrawContainers(rowAbove,colAbove,true);
        DrawContainers drawContainersUnder = new DrawContainers(rowUnder,colUnder,false);
        contentPanel.add(drawContainersAbove);
        contentPanel.add(drawContainersUnder);

        //滚动条panel
        forScrolPanel = new JPanel();
        forScrolPanel.setLayout(new BorderLayout(0,0));
        forScrolPanel.add(boardLabel,BorderLayout.WEST);
        forScrolPanel.add(contentPanel,BorderLayout.CENTER);

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(forScrolPanel);
        this.add(scrollPane);
        this.setLocationRelativeTo(null);

    }
    //画集装箱类
    class DrawContainers extends JPanel{
        public  DrawContainers(int row,int col,boolean underOrAbove){//underOrAbove用来判断是甲板上下以便于draw排号和层号,true代表甲板上
            this.setLayout(new GridLayout(row+1,col+1,10,5));

            //生成排数的字符串.偶数放在前面技术放在后面
            StringBuilder stringRes = new StringBuilder("");
            StringBuilder tempStr;
            for(int i = 1;i <= col; i++){
                tempStr = new StringBuilder(String.format("%02d",i));
                if(i % 2 == 0){
                    stringRes = tempStr.append(stringRes);
                }else{
                    stringRes = stringRes.append(tempStr);
                }
            }

            //画集装箱
            JButton[][] btn = new JButton[row+1][col+1];
            for(int i=0;i<=row;i++){
                for(int j=0;j<=col;j++){
                    btn[i][j] = new JButton();
                    btn[i][j].setPreferredSize(new Dimension(80,40));
                    btn[i][j].setFont(new Font("",Font.BOLD,10));

                    if(i== row && j == 0){
                        btn[i][j] = new JButton(new String(""));
                        btn[i][j].setContentAreaFilled(false);//jbutton透明
                        btn[i][j].setBorderPainted(false);//jbutton透明
                    }else if(i == row){
                        btn[i][j] = new JButton(stringRes.substring(2*(j-1),2*(j-1)+2));
                        btn[i][j].setContentAreaFilled(false);//jbutton透明
                        btn[i][j].setBorderPainted(false);//jbutton透明
                    }else if(j == 0){
                        if(underOrAbove == true){
                            btn[i][j] = new JButton(new String((row - i)*2+80 + ""));//甲板上的层数
                        }else {
                            btn[i][j] = new JButton(String.format("%02d",(row - i)*2));//甲板下的层数
                        }
                        btn[i][j].setContentAreaFilled(false);//jbutton透明
                        btn[i][j].setBorderPainted(false);//jbutton透明
                    }else{
                        btn[i][j].setText("<html>"+"属性1"+"<br>属性2</html>");
                    }
                    add(btn[i][j]);
                }
            }
        }
    }

}

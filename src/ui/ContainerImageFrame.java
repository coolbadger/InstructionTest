package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ding on 2016/3/13 0013.
 */
public class ContainerImageFrame extends JFrame{
    private JPanel contentPanel;
    private JLabel boardLabel;
    private JPanel eastPanel;

//    ContainerImageFrame(){initComponents();}

    public void initComponents(int bayID, int rowAbove , int colAbove,int rowUnder,int colUnder){
        Container c = getContentPane();
//        c.add(contentPanel);
        this.setTitle("第"+bayID+"倍倍位概图");
        this.setLayout(new BorderLayout());
        this.setSize(1200,800);
        //甲板字样
        boardLabel = new JLabel("甲板");
        boardLabel.setFont(new Font("宋体",Font.BOLD,20));
        boardLabel.setHorizontalAlignment(JLabel.RIGHT);

        eastPanel= new JPanel();
        eastPanel.setLayout(new BorderLayout());
        eastPanel.setPreferredSize(new Dimension(100,0));
        eastPanel.add(boardLabel,BorderLayout.WEST);
        //集装箱panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2,1,1,20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
//        contentPanel.setBackground(Color.GREEN);
        contentPanel.setSize(new Dimension(900,900));
        //画集装箱
        DrawContainers drawContainersAbove = new DrawContainers(rowAbove,colAbove);
        DrawContainers drawContainersUnder = new DrawContainers(rowUnder,colUnder);
        contentPanel.add(drawContainersAbove);
        contentPanel.add(drawContainersUnder);

        this.add(eastPanel,BorderLayout.EAST);
        this.add(contentPanel,BorderLayout.CENTER);
        this.setLocationRelativeTo(null);

    }
    //画集装箱类
    class DrawContainers extends JPanel{
        public  DrawContainers(int row,int col){
            this.setLayout(new GridLayout(row,col,1,1));
            JButton[][] btn = new JButton[row][col];
            for(int i=0;i<row;i++){
                for(int j=0;j<col;j++){
                    btn[i][j]= new JButton(new String("<html>"+"属性1"+"<br>属性2</html>"));
                    btn[i][j].setFont(new Font("",Font.BOLD,8));
                    add(btn[i][j]);
                }
            }
        }
    }

}

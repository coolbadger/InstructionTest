package ui;

import main.GroupData;
import main.IGroupData;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

/**
 * Created by leko on 2016/1/12.
 */
public class GroupFrame extends JFrame {
    private JPanel panelCenter;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable tableWQL;


    GroupFrame() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);//居中显示
        {
            this.panelCenter = new JPanel();
            this.panelCenter.setBorder(new TitledBorder(null, "属性组", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            this.contentPane = new JPanel();
            this.contentPane.setLayout(new BorderLayout(0, 0));
            this.contentPane.add(this.panelCenter, BorderLayout.CENTER);
            setContentPane(this.contentPane);
            this.panelCenter.setLayout(new BorderLayout(0, 0));
            {
                this.scrollPane = new JScrollPane();
                this.panelCenter.add(this.scrollPane,BorderLayout.CENTER);
                {
                    this.tableWQL = new JTable();
                    this.scrollPane.setViewportView(this.tableWQL);
                    DefaultTableModel tableModel = new DefaultTableModel();

                    //增加列名
                    ArrayList<String> colList = new ArrayList<String>(Arrays.asList("属性组","港口", "箱型", "尺寸"));
                    for (String col : colList) {
                        System.out.println(col);
                        tableModel.addColumn(col);
                    }

                    //增加内容
                    HashMap<String, ArrayList<String>> Group_MAP = GroupData.getGroupMap();
                    System.out.print("生成内容");
                    Iterator iter = Group_MAP.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        String groupno = entry.getKey().toString();
                        ArrayList<String> groupattri = Group_MAP.get(groupno);
                        Object[] rowData = new Object[groupattri.size()+1];
                        rowData[0]=groupno;
                        for (int i = 1; i <= groupattri.size(); i++) {
                            String attri = groupattri.get(i-1);
                            rowData[i] = attri;
                        }
                        tableModel.addRow(rowData);
                    }

                    this.tableWQL.setModel(tableModel);
                }
            }

        }
        //监听全局变量
        GroupData.addGroupDataChangeListener(new IGroupData() {
            @Override
            public void groupDataChanged() {
                System.out.println("检测到集装箱变化，更新属性组");
                DefaultTableModel tableModel = (DefaultTableModel) tableWQL.getModel();
                while (tableModel.getRowCount() > 0) {//清除表格中已有数据
                    tableModel.removeRow(tableModel.getRowCount() - 1);
                }

                HashMap<String, ArrayList<String>> Group_MAP = GroupData.getGroupMap();
                System.out.print(Group_MAP);
                Iterator iter = Group_MAP.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String groupno = entry.getKey().toString();
                    ArrayList<String> groupattri = Group_MAP.get(groupno);
                    Object[] rowData = new Object[groupattri.size()+1];
                    rowData[0]=groupno;
                    for (int i = 1; i <= groupattri.size(); i++) {
                        String attri = groupattri.get(i-1);
                        rowData[i] = attri;
                    }
                    tableModel.addRow(rowData);
                }
            }
        });
    }
}

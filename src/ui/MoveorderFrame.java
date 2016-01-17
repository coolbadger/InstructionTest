package ui;

import importData.ImportData;
import importData.PreStowageInfo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by leko on 2016/1/17.
 */
public class MoveorderFrame extends JFrame{
    private JPanel panelCenter;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable tableWQL;

    MoveorderFrame() {initComponents();}

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        {
            this.panelCenter = new JPanel();
            this.panelCenter.setBorder(new TitledBorder(null, "作业序列信息", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
                    ArrayList<String> colList = new ArrayList<String>(Arrays.asList("舱位ID","倍位ID", "层号", "排号","作业序列"));
                    for (String col : colList) {
                        System.out.println(col);
                        tableModel.addColumn(col);
                    }

                    //增加内容
                    ArrayList<PreStowageInfo> preStowageInfoArrayList = ImportData.preStowageInfoArrayList;
                    Object[] rowData = new Object[5];
                    for (PreStowageInfo preStowageInfo:preStowageInfoArrayList)
                    {
                        rowData[0] = preStowageInfo.getVHT_ID();
                        rowData[1] = preStowageInfo.getVBY_BAYID();
                        rowData[2] = preStowageInfo.getVTR_TIERNO();
                        rowData[3] = preStowageInfo.getVRW_ROWNO();
                        rowData[4] = preStowageInfo.getMOVE_ORDER();
                        tableModel.addRow(rowData);
                    }
                    this.tableWQL.setModel(tableModel);
                }
            }
        }
    }
}

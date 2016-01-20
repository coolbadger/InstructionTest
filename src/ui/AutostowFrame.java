package ui;

import importData.AutostowInfo;
import importData.ImportData;
import utils.FileUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

/**
 * Created by leko on 2016/1/20.
 */
public class AutostowFrame extends JFrame {
    private JPanel panelCenter;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable tableWQL;


    AutostowFrame() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 100);
        setLocationRelativeTo(null);//居中显示
        {
            this.panelCenter = new JPanel();
            this.panelCenter.setBorder(new TitledBorder(null, "自动配载结果", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            this.contentPane = new JPanel();
            this.contentPane.setLayout(new BorderLayout(0, 0));
            this.contentPane.add(this.panelCenter, BorderLayout.CENTER);
            setContentPane(this.contentPane);
            this.panelCenter.setLayout(new BorderLayout(0, 0));
            {
                {
                    this.scrollPane = new JScrollPane();
                    this.panelCenter.add(this.scrollPane, BorderLayout.CENTER);
                    {
                        this.tableWQL = new JTable();
                        this.scrollPane.setViewportView(this.tableWQL);
                        DefaultTableModel tableModel = new DefaultTableModel();

                        //增加列名
                        ArrayList<String> colList = new ArrayList<String>(Arrays.asList("箱号", "尺寸", "箱区位置", "船上位置"));
                        for (String col : colList) {
                            System.out.println(col);
                            tableModel.addColumn(col);
                        }

                        //增加内容
                        java.util.List<AutostowInfo> autostowInfoList = ImportData.autostowInfoList;
                        System.out.print("生成内容");
                        if (autostowInfoList!=null) {
                            for (AutostowInfo autostowInfo : autostowInfoList) {
                                Object[] rowData = new Object[4];
                                rowData[0] = autostowInfo.getUnitID();
                                rowData[1] = autostowInfo.getCsize();
                                rowData[2] = autostowInfo.getAreaposition();
                                rowData[3] = autostowInfo.getVesselposition();
                                tableModel.addRow(rowData);
                            }
                        }
                        this.tableWQL.setModel(tableModel);
                    }
                }
            }
        }
    }
}

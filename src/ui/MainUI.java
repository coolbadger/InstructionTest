package ui;

import instruction.LoadConfig;
import instruction.MoveInfo;
import instruction.UnitPosition;
import main.GlobalData;
import main.IGlobalData;
import main.SWInitSampleData;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class MainUI extends JFrame {

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItemExit;
	private JMenuItem menuItemImportSampleData;
	private JPanel panelNorth;
	private JPanel panelCenter;
	private JTable tableWQL;
	private JScrollPane scrollPane;
	private JButton buttonSendSelectedIns;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainUI() {
		initComponents();
	}
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		{
			this.menuBar = new JMenuBar();
			this.menuBar.setBackground(Color.LIGHT_GRAY);
			setJMenuBar(this.menuBar);
			{
				this.menu = new JMenu("文件");
				this.menuBar.add(this.menu);
				{
					this.menuItemExit = new JMenuItem("退出");
					this.menuItemExit.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							System.exit(NORMAL);
						}
					});
					this.menu.add(this.menuItemExit);

					this.menuItemImportSampleData = new JMenuItem("导入案例数据");
					this.menuItemImportSampleData.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							setCursor(new Cursor(Cursor.WAIT_CURSOR));

							new SWInitSampleData(){
								@Override
								protected void done() {
									super.done();
									setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
								}
							}.run();

						}
					});
					this.menu.add(this.menuItemImportSampleData);
				}
			}
		}
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(this.contentPane);
		{
			this.panelNorth = new JPanel();
			this.contentPane.add(this.panelNorth, BorderLayout.NORTH);
			{
				this.buttonSendSelectedIns = new JButton("生成发送指令");
				this.buttonSendSelectedIns.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(tableWQL.getSelectedColumnCount()<1) {
							JOptionPane.showMessageDialog(MainUI.this, "未选中任何指令！","错误", JOptionPane.ERROR_MESSAGE);
						}
						else {

						}
					}
				});
				this.panelNorth.add(this.buttonSendSelectedIns);


			}

		}
		{
			this.panelCenter = new JPanel();
			this.panelCenter.setBorder(new TitledBorder(null, "指令队列", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			this.contentPane.add(this.panelCenter, BorderLayout.CENTER);
			this.panelCenter.setLayout(new BorderLayout(0, 0));
			{
				this.scrollPane = new JScrollPane();
				this.panelCenter.add(this.scrollPane);
				{
					this.tableWQL = new JTable();
					this.scrollPane.setViewportView(this.tableWQL);
					DefaultTableModel tableModel = new DefaultTableModel();

					List<String> colList = MoveInfo.getFiledsInfo();
					for(String col:colList){
						System.out.println(col);
						tableModel.addColumn(col);
					}



					this.tableWQL.setModel(tableModel);
					this.tableWQL.setRowSorter(new TableRowSorter(tableModel));


					
				}
			}
		}
		//监听全局变量
		GlobalData.addGlobalDataChangeListener(new IGlobalData() {
			@Override
			public void globalDataChanged() {
				DefaultTableModel tableModel = (DefaultTableModel) tableWQL.getModel();
				while (tableModel.getRowCount() > 0) {//清除表格中已有数据
					tableModel.removeRow(tableModel.getRowCount());
				}
				List<MoveInfo> sampleList = new LoadConfig().getInstructions();
				for (MoveInfo moveInfo : sampleList) {
					List<String> propertyList = MoveInfo.getFiledsInfo();//取出属性列表
					Object[] rowData = new Object[propertyList.size()];
					for (int i = 0; i < propertyList.size(); i++) {
						String property = propertyList.get(i);
						Object value = moveInfo.getFieldValueByName(property);//取出每个属性的值，判断
						if (value instanceof UnitPosition) {
							rowData[i] = value.toString();
						} else {
							rowData[i] = value;
						}
					}
					tableModel.addRow(rowData);
				}
			}
		});

	}

}

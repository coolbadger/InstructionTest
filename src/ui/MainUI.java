package ui;

import importData.*;
import instruction.MoveInfo;
import instruction.UnitPosition;
import main.*;
import utils.FileUtil;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.filechooser.FileFilter;

public class MainUI extends JFrame {

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu menu1,menu2,menu3,menu4;
	private JMenuItem menuItemExit;
	private JMenuItem voyage,shipstructure,crane,container,containerarea,others;
	private JMenuItem groups,prestowage,moveorder;
	private JMenuItem cwp,stowage;
	private JMenuItem menuItemImportSampleData;
	private JPanel panelNorth;
	private JPanel panelCenter;
	private JTable tableWQL;
	private JScrollPane scrollPane;
	private JButton buttonSendSelectedIns;
	private JButton buttonReceiveIns;

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

    private File choosefile(){
		File file=new File(".");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("请选择文件（UTF-8编码）");
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setAcceptAllFileFilterUsed(false);
        final String[][] fileENames = {{".json", "json数据"}};
        fileChooser.addChoosableFileFilter(new FileFilter() {
            public boolean accept(File file) {
                return true;
            }

            public String getDescription() {
                return "所有文件(*.*)";
            }
        });
        for (final String[] fileEName : fileENames) {
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File file) {
                    if (file.getName().endsWith(fileEName[0]) || file.isDirectory()) {
                        return true;
                    }
                    return false;
                }
                public String getDescription() {
                    return fileEName[1];
                }
            });
        }
        //fileChooser.showDialog(null, null);
		int sel = fileChooser.showOpenDialog(MainUI.this);
		if(sel == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		return file;
    }

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 600);
		setLocationRelativeTo(null);//居中显示
		{
			this.menuBar = new JMenuBar();
			this.menuBar.setBackground(Color.LIGHT_GRAY);
			setJMenuBar(this.menuBar);
			{
				this.menu1 = new JMenu("导入");
				this.menuBar.add(this.menu1);
				{
					this.voyage = new JMenuItem("航次");
					this.voyage.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							File file= choosefile();
							if(!".".equals(file.getName())) {
								System.out.println("打开航次数据文件："+file.getName());
								setCursor(new Cursor(Cursor.WAIT_CURSOR));//设置鼠标忙
								StringBuffer str = FileUtil.readFileToString(file);//得到文件的字符串
								//将字符串解析,将航次数据保存到全局变量里面
								ImportData.voyageInfoList = VoyageInfoProcess.getVoyageInfo(str.toString());
								setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//结束后设置鼠标为正常状态
							}
						}
					});
					this.shipstructure = new JMenuItem("船舶结构");
					this.shipstructure.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							File file= choosefile();
							//System.out.println(file.getName());
							if(!".".equals(file.getName())) {
								System.out.println("打开船舶结构文件："+file.getName());
								setCursor(new Cursor(Cursor.WAIT_CURSOR));//设置鼠标忙
								StringBuffer str = FileUtil.readFileToString(file);//得到文件的字符串
								//将字符串解析,将船舶结构数据保存到全局变量里面
								ImportData.vesselStructureInfo = VesselStructureInfoProcess.getVesselStructureInfo(str.toString());
								setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//结束后设置鼠标为正常状态
							}
						}
					});
					this.crane = new JMenuItem("桥机");
					this.crane.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							File file= choosefile();
							if(!".".equals(file.getName())) {
								System.out.println("打开桥机数据文件："+file.getName());
								setCursor(new Cursor(Cursor.WAIT_CURSOR));//设置鼠标忙
								StringBuffer str = FileUtil.readFileToString(file);//得到文件的字符串
								//将字符串解析,将桥机数据保存到全局变量里面
								ImportData.craneInfoList = CraneInfoProcess.getCraneInfo(str.toString());
//								System.out.println(ImportData.craneInfoList.get(0).getNAME());
								setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//结束后设置鼠标为正常状态
							}
						}
					});
					this.container = new JMenuItem("在场箱");
					this.container.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							File file= choosefile();
							System.out.println(file.getName());
							final SWContainerData containerData = new SWContainerData(){
								@Override
								protected void done() {
									super.done();
									setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//结束后设置鼠标为正常状态
								}
							};
							containerData.inFile = file;
							containerData.run();

						}
					});
					this.containerarea = new JMenuItem("箱区信息");
					this.containerarea.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							File file= choosefile();
							if(!".".equals(file.getName())) {
								System.out.println("打开在场箱区信息文件："+file.getName());
								setCursor(new Cursor(Cursor.WAIT_CURSOR));//设置鼠标忙
								StringBuffer str = FileUtil.readFileToString(file);//得到文件的字符串
								//将字符串解析,将在场箱区信息保存到全局变量里面
								ImportData.containerAreaInfoList = ContainerAreaInfoProcess.getContainerAreaInfo(str.toString());
								setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//结束后设置鼠标为正常状态
							}
						}
					});
					this.others = new JMenuItem("其他信息");
					this.others.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							setCursor(new Cursor(Cursor.WAIT_CURSOR));//设置鼠标忙
							ImportData.othersInfo = OthersInfoProcess.getOthersInfo();
							if(ImportData.othersInfo != null) {
								JOptionPane.showMessageDialog(MainUI.this, "其他信息导入成功!",
										"提示", JOptionPane.INFORMATION_MESSAGE);
							}else {
								JOptionPane.showMessageDialog(MainUI.this, "其他信息导入失败!",
										"提示", JOptionPane.INFORMATION_MESSAGE);
							}
							setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//结束后设置鼠标为正常状态

						}
					});
					this.menu1.add(this.voyage);
					this.menu1.add(this.shipstructure);
					this.menu1.add(crane);
					this.menu1.add(container);
					this.menu1.add(containerarea);
					this.menu1.add(others);
				}

				this.menu2 = new JMenu("生成");
				this.menuBar.add(this.menu2);
				{
					this.groups = new JMenuItem("属性组");
					this.groups.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							//跑后台线程
							setCursor(new Cursor(Cursor.WAIT_CURSOR));//设置鼠标忙
							//后台线程结束后,打开窗口
							SWGenerateGroupData swGenerateGroupData = new SWGenerateGroupData(){
								@Override
								protected void done() {
									super.done();
									GroupFrame groupFrame = new GroupFrame();
									groupFrame.setVisible(true);
									setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//结束后设置鼠标为正常状态
								}
							};
							swGenerateGroupData.run();
						}
					});

					this.prestowage = new JMenuItem("预配图");
					this.prestowage.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							setCursor(new Cursor(Cursor.WAIT_CURSOR));//设置鼠标忙
							SWGeneratePrestowageData swGeneratePrestowageData = new SWGeneratePrestowageData(){
								@Override
								protected void done() {
									super.done();
									PrestowageFrame prestowageFrame = new PrestowageFrame();
									prestowageFrame.setVisible(true);
									setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//结束后设置鼠标为正常状态
								}
							};
							swGeneratePrestowageData.run();
						}
					});
					this.moveorder = new JMenuItem("作业序列");
					this.moveorder.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							MoveorderFrame moveorderFrame = new MoveorderFrame();
							moveorderFrame.setVisible(true);
						}
					});
					this.menu2.add(this.groups);
					this.menu2.add(this.prestowage);
					this.menu2.add(this.moveorder);
				}

				this.menu3 = new JMenu("预览");
				this.menuBar.add(this.menu3);
				{
					this.cwp = new JMenuItem("cwp计划");
//					this.cwp.addActionListener(new ActionListener() {
//						@Override
//						public void actionPerformed(ActionEvent e) {
//							DialogFrame d = new DialogFrame("系统正在调用cwp算法，请耐心等待...");
//						}
//					});
					this.cwp.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							System.out.println("生成船舱信息");
							final SWGenerateHatchData swGenerateHatchData = new SWGenerateHatchData(){
								@Override
								protected void done() {
									super.done();
								}
							};
							swGenerateHatchData.run();
						}
					});

					this.stowage = new JMenuItem("自动配载");
					this.menu3.add(this.cwp);
					this.menu3.add(this.stowage);
				}

				this.menu4 = new JMenu("操作");
				this.menuBar.add(this.menu4);
				{
//					this.menuItemExit = new JMenuItem("退出");
//					this.menuItemExit.addActionListener(new ActionListener() {
//						public void actionPerformed(ActionEvent e) {
//							System.exit(NORMAL);
//						}
//					});
//					this.menu.add(this.menuItemExit);

					this.menuItemImportSampleData = new JMenuItem("装船");
					this.menuItemImportSampleData.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							System.out.println("菜单触发导入案例数据");

							setCursor(new Cursor(Cursor.WAIT_CURSOR));//设置鼠标正忙

							new SWInitSampleData(){//执行SwingWorker
								@Override
								protected void done() {
									super.done();
									setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//结束后设置鼠标为正常状态
								}
							}.run();

						}
					});
					this.menu4.add(this.menuItemImportSampleData);
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
						if (tableWQL.getSelectedRowCount() < 1) {
							JOptionPane.showMessageDialog(MainUI.this, "未选中任何指令！", "错误", JOptionPane.ERROR_MESSAGE);
						} else {
							boolean hasNoSendable = false;//是否有不能发送的指令
							List<String> gkeyList = new ArrayList<String>();//被选中的指令的Gkey
							for (int i : tableWQL.getSelectedRows()) {//遍历选中的行
								String gkey = (String) tableWQL.getValueAt(i, tableWQL.getColumn("gkey").getModelIndex());
								int currentState = (Integer) tableWQL.getValueAt(i, tableWQL.getColumn("state").getModelIndex());
								System.out.println("选中:" + gkey + ":" + currentState);
								if (currentState != 0) {
									hasNoSendable = true;
								}
								gkeyList.add(gkey);
							}
							if (hasNoSendable) {
								JOptionPane.showMessageDialog(MainUI.this, "含有已发送的指令!", "错误", JOptionPane.ERROR_MESSAGE);
							} else {
								//执行发送线程
								setCursor(new Cursor(Cursor.WAIT_CURSOR));//设置鼠标正忙

								SWSendData swSendData = new SWSendData() {
									@Override
									protected void done() {
										super.done();
										setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//结束后设置鼠标为正常状态

										int willOpen = JOptionPane.showConfirmDialog(MainUI.this, "已写入在程序根目录下WorkInstructionSend.json，是否打开？", "已写入", JOptionPane.YES_NO_OPTION);
										if (willOpen == JOptionPane.YES_OPTION) {
											try {
												Desktop.getDesktop().open(new File("WorkInstructionSend.json"));
											} catch (IOException e1) {
												e1.printStackTrace();
											}
										}

									}
								};
								swSendData.gkeyList = gkeyList;//设置传入参数
								swSendData.run();
							}

						}
					}
				});
				this.panelNorth.add(this.buttonSendSelectedIns);

				this.buttonReceiveIns = new JButton("接受返回指令");
				this.buttonReceiveIns.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setDialogTitle("请选择文件（UTF-8编码）");
						fileChooser.setDialogType(JFileChooser.FILES_ONLY);
						fileChooser.setCurrentDirectory(new File("."));
						int sel = fileChooser.showOpenDialog(MainUI.this);
						if(sel == JFileChooser.APPROVE_OPTION){
							setCursor(new Cursor(Cursor.WAIT_CURSOR));//设置鼠标正忙
							//执行接收线程
							File file = fileChooser.getSelectedFile();
							System.out.println(file.getName());
							final SWReceiveData swReceiveData = new SWReceiveData(){
								@Override
								protected void done() {
									super.done();
									setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//结束后设置鼠标为正常状态
								}
							};
							swReceiveData.inFile = file;
							swReceiveData.run();
							//判断执行结果
							if(!swReceiveData.noError){
								JOptionPane.showMessageDialog(MainUI.this, "接受失败！\n"+swReceiveData.result, "错误", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
				this.panelNorth.add(this.buttonReceiveIns);


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
//					this.tableWQL.setRowSorter(new TableRowSorter(tableModel));
					//针对特殊的列设置排序器
					TableRowSorter tableRowSorter = new TableRowSorter(tableModel);
					tableRowSorter.setComparator(tableWQL.getColumn("gkey").getModelIndex(), new Comparator<String>() {
						@Override
						public int compare(String o1, String o2) {
							//先以@为界分裂
							String o1s = o1.split("@")[0];
							String o2s = o2.split("@")[0];
							String o1d = o1.split("@")[1];
							String o2d = o2.split("@")[1];
							System.out.println(o1s + " " + o1d);
							System.out.println(o2s + " " + o2d);
							if (o1s.equals(o2s)) {//前半截相同,后半截处理为Integer，比对
								System.out.println("前半截相同,后半截处理为Integer，比对");
								int o1i = Integer.valueOf(o1d);
								int o2i = Integer.valueOf(o2d);
								return Integer.compare(o1i, o2i);//对比后半截
							} else {
								return Collator.getInstance().compare(o1s, o2s);//对比前半截
							}
						}
					});
					//tableRowSorter.setComparator();
					tableWQL.setRowSorter(tableRowSorter);
					//设置默认排序
					RowSorter.SortKey sortKey = new RowSorter.SortKey(tableWQL.getColumn("gkey").getModelIndex(),SortOrder.ASCENDING);
					List<RowSorter.SortKey> sortKeyList = new ArrayList<RowSorter.SortKey>();
					sortKeyList.add(sortKey);
					tableRowSorter.setSortKeys(sortKeyList);
					tableRowSorter.setSortsOnUpdates(true);
				}
			}
		}
		//监听全局变量
		GlobalData.addGlobalDataChangeListener(new IGlobalData() {
			@Override
			public void globalDataChanged() {
				System.out.println("检测到全局数据变化，更新表格");
				DefaultTableModel tableModel = (DefaultTableModel) tableWQL.getModel();
				while (tableModel.getRowCount() > 0) {//清除表格中已有数据
					tableModel.removeRow(tableModel.getRowCount() - 1);
				}
				for (MoveInfo moveInfo : GlobalData.getGlobalMoveinfoMap().values()) {
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

package ui;

import cwp.CallCwpTest;
import importData.CwpResultInfoProcess;
import importData.ImportData;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class CwpProgressFrame {

	private JDialog dialog;
	private JProgressBar progressBar;
	private JLabel lbStatus;
	private Window parent;
	private Thread thread; // 处理业务的线程
	private String statusInfo;//进度条状态
	private String wrightInfo;//算法返回正确结果
	private String wrongInfo;//算法未返回结果
	
//	public static String result;//保存算法结果

	public CwpProgressFrame() {
		Thread thread = new Thread() {
			public void run() {
//				int index = 0;
//				while (index < 5) {
//					try {
//						sleep(1000);
//						++index;
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
				ImportData.cwpResult = CallCwpTest.cwp();
//				result = "cwp测试中...";
				System.out.println(ImportData.cwpResult);
			}
		};
		CwpProgressFrame.show((Frame) null, thread, "系统正在调用cwp算法，请耐心等待...", "cwp算法执行完毕，请打开文件查看结果!",
				"cwp算法执行过程中，发现错误，未返回结果！");
	}
	
	public static void show(Window parent, Thread thread, String statusInfo,
			String resultInfo, String cancelInfo) {
		new CwpProgressFrame(parent, thread, statusInfo, resultInfo, cancelInfo);
	}

	private CwpProgressFrame(Window parent, Thread thread, String statusInfo,
							 String resultInfo, String cancelInfo) {
		this.parent = parent;
		this.thread = thread;
		this.statusInfo = statusInfo;
		this.wrightInfo = resultInfo;
		this.wrongInfo = cancelInfo;
		initUI();
		startThread();
		dialog.setVisible(true);
	}

	private void initUI() {
		if (parent instanceof Dialog) {
			dialog = new JDialog((Dialog) parent, true);
		} else if (parent instanceof Frame) {
			dialog = new JDialog((Frame) parent, true);
		} else {
			dialog = new JDialog((Frame) null, true);
		}
		final JPanel mainPane = new JPanel(null);
		progressBar = new JProgressBar();
		lbStatus = new JLabel("" + statusInfo);
		progressBar.setIndeterminate(true);
		mainPane.add(progressBar);
		mainPane.add(lbStatus);
		dialog.getContentPane().add(mainPane);
//		dialog.setUndecorated(true);// 除去title
		dialog.setResizable(true);
		dialog.setSize(405, 180);
		dialog.setLocationRelativeTo(parent); // 设置此窗口相对于指定组件的位置
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // 不允许关闭

		mainPane.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				layout(400, 60);
			}
		});
	}

	private void layout(int width, int height) {
		progressBar.setBounds(20, 20, 350, 15);
		lbStatus.setBounds(85, 50, 350, 25);
	}

	private void startThread() {
		new Thread() {
			public void run() {
				try {
					thread.start(); // 处理耗时任务
					thread.join();// 等待事务处理线程结束
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					// 关闭进度提示框
					dialog.dispose();
					String title = "消息提示";
					if (ImportData.cwpResult != null) {
//						JOptionPane.showMessageDialog(parent, wrightInfo,
//								title, JOptionPane.INFORMATION_MESSAGE);
						ImportData.cwpResultInfoList = CwpResultInfoProcess.getCwpResultInfo(ImportData.cwpResult);
						int isOpen = JOptionPane.showConfirmDialog(dialog, wrightInfo, title, JOptionPane.YES_NO_OPTION);
						if (isOpen == JOptionPane.YES_OPTION) {
							try {
								CwpViewFrame cwpViewFrame = new CwpViewFrame();
								cwpViewFrame.setVisible(true);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}else {
						JOptionPane.showMessageDialog(parent, wrongInfo,
								title, JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		}.start();
	}
	
}
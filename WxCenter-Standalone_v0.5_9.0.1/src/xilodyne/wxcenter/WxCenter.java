package xilodyne.wxcenter;

import xilodyne.wxcenter.devices.server.Stack;
import xilodyne.wxcenter.devices.server.UDPServer;
import xilodyne.wxcenter.gui.Model;
import xilodyne.wxcenter.gui.View;
import xilodyne.wxcenter.gui.Controller;
import xilodyne.wxcenter.logging.WxLogging;
import xilodyne.wxcenter.server.ProcessMessage;

import java.awt.EventQueue;

/**
 * @author aholiday
 * 
 */
public class WxCenter {

	static UDPServer udpserver = new UDPServer();
	static ProcessMessage procmessage;
	static WxLogging myLogger = new WxLogging(WxLogging.sourceGUI);
	static Stack stack = new Stack();


	 //static test2 test2;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		 * // creating and showing this application's GUI.
		 * javax.swing.SwingUtilities.invokeLater(new Runnable() { public void
		 * run() { test.createAndShowGUI();
		 * 
		 * }
		 * 
		 * });
		 */

		Model model = new Model();
		View view = new View(model);
		Controller  controller = new Controller (model, view);
		controller.mainloop();
		
		 procmessage = new ProcessMessage(controller);

		 startThreads();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	test2 frame = new test2();
				//	frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	private static void startThreads() {
		new Thread(udpserver).start();
		new Thread(procmessage).start();
	}

}

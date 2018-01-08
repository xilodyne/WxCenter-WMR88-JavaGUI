package com.xilosilo.wxcenter.data;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBException;

import xilodyne.wxcenter.devices.xml.jaxb.WXAnemometer;
import xilodyne.wxcenter.devices.xml.jaxb.WXBarometer;
import xilodyne.wxcenter.devices.xml.jaxb.WXClock;
import xilodyne.wxcenter.devices.xml.jaxb.WXRainGauge;
import xilodyne.wxcenter.devices.xml.jaxb.WXThermohygrometer;
import xilodyne.wxcenter.globals.Definitions;
import xilodyne.wxcenter.gui.Controller;
import xilodyne.wxcenter.gui.Model;
import xilodyne.wxcenter.gui.View;
import xilodyne.wxcenter.logging.WxLogging;

public class UpdateManager implements Runnable {

	public static WXAnemometer wind = new WXAnemometer();
	public static WXBarometer barm = new WXBarometer();
	public static WXThermohygrometer tempIndoor = new WXThermohygrometer();
	public static WXThermohygrometer tempOutdoor = new WXThermohygrometer();
	public static WXRainGauge rain = new WXRainGauge();
	public static WXClock clock = new WXClock();

	DeviceFromJAXB getData;
	Controller controller;



	public UpdateManager(Controller control, String url) {
		this.controller = control;
		this.getData = new DeviceFromJAXB(url);
		Definitions.checkServerAvailable = false;

	}

	public void run() {
		WxLogging.toConsole(WxLogging.callEmpty , 
				"Starting Update Manager thread.");


	//	controller.mainloop();
		try {
			while (true) {
				try {
					if (Definitions.checkServerAvailable ) {

					getData.updateDevices(barm, wind, tempIndoor, tempOutdoor, rain, clock);
					controller.printWind(wind);
					controller.printBarm(barm);
					controller.printTempIndoor(tempIndoor);
					controller.printTempOutdoor(tempOutdoor);


					WxLogging.toConsole("UpdtMgr" , 
							"Update Message wind: " + wind.printValues());

					WxLogging.toConsole("UpdtMgr" , 
							"Update Message barm: " + barm.printValues());

					WxLogging.toConsole("UpdtMgr", 
							"Update Message indoor: " + tempIndoor.printValues());
					
					WxLogging.toConsole("UpdtMgr", 
							"Update Message outdoor: " + tempOutdoor.printValues());
					
					WxLogging.toConsole("UpdtMgr" , 
							"Update Message rain: " + rain.printValues());
					
					WxLogging.toConsole("UpdtMgr" , 
							"Update Message clock: " + clock.printValues());

					} else {
						WxLogging.toConsole(WxLogging.callEmpty , 
								"Establshing connection...");
						getData.connectServer();
					}

				} catch (ConnectException e) {
					WxLogging.toConsole(WxLogging.callEmpty , 
							"No HTTP connection.");
					Definitions.updateCheckServerAvailable(false);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JAXBException e) {
					e.printStackTrace();
				}
				

				Thread.sleep(10000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

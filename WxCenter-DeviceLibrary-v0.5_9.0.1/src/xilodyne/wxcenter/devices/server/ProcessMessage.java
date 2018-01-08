package xilodyne.wxcenter.devices.server;


import xilodyne.wxcenter.globals.Definitions;

import xilodyne.wxcenter.devices.server.Stack;
import xilodyne.wxcenter.devices.xml.jaxb.WXAnemometer;
import xilodyne.wxcenter.devices.xml.jaxb.WXBarometer;
import xilodyne.wxcenter.devices.xml.jaxb.WXCenterDevice;
import xilodyne.wxcenter.devices.xml.jaxb.WXClock;
import xilodyne.wxcenter.devices.xml.jaxb.WXRainGauge;
import xilodyne.wxcenter.devices.xml.jaxb.WXThermohygrometer;
import xilodyne.wxcenter.logging.WxLogging;

public class ProcessMessage implements Runnable {
	// http://systembash.com/content/a-simple-java-udp-server-and-udp-client/

	/*
	 * Take each message: If begin with "Frame", then get next message If not
	 * "Frame" then process message Based upon Frame message, format object and
	 * send to gui
	 */
	String message;
	short deviceID;
	public static WXAnemometer wind;
	public static WXBarometer barm;
	public static WXThermohygrometer tempIndoor;
	public static WXThermohygrometer tempOutdoor;
	public static WXRainGauge rain;
	public static WXClock clock;


	public ProcessMessage() {

	}

	public ProcessMessage(WXAnemometer wind, WXBarometer barm,
			WXThermohygrometer tempIndoor, WXThermohygrometer tempOutdoor,
			WXRainGauge rain, WXClock clock) {
		ProcessMessage.wind = wind;
		ProcessMessage.barm = barm;
		ProcessMessage.tempIndoor = tempIndoor;
		ProcessMessage.tempOutdoor = tempOutdoor;
		ProcessMessage.rain = rain;
		ProcessMessage.clock = clock;

	}

	public void run() {
		WxLogging.toConsole(WxLogging.callEmpty,
				"Starting Process Message thread.");

		while (true) {

			message = (String) Stack.pop();

			// message =
			// "[INF 17:49:18 22/11/13] Frame 00 48 02 0C 0F F0 00 04 00 59 01";
			// Anemometer: Direction 45° (NE), Average 1.5m/s, Gust 1.5m/s,
			// Chill 4.0°, Battery OK
			// "[INF 23:32:21 25/10/13] Frame 00 46 E5 03 E5 03 16 02";
			if (message == null) {
				// System.out.println("message is NULL");
			} else {
				deviceID = WXCenterDevice.getWXDevice(message);
				WxLogging.toConsole(WxLogging.callEmpty,
						"Device ID: " + deviceID);

				switch (deviceID) { // check sensor code
				case Definitions.FRAME_ANEMOMETER:
					this.setAnemometer(message);

					// controller.printWind(new WXAnemometer(message));

					break;
				case Definitions.FRAME_BAROMETER:
					this.setBarometer(message);
					// controller.printBarm(new WXBarometer(message));

					break;
				case Definitions.FRAME_THERMOHYGROMETER:
					this.setTemperature(message);
					// controller.printThermohygrometer(new WXThermohygrometer(
					// message));
					// ShowBarometer.printThermohygrometer(new
					// WXThermohygrometer(
					// message));
					break;
				case Definitions.FRAME_RAINFALL:
					this.setRain(message);
					break;
				case Definitions.FRAME_CLOCK:
					this.setClock(message);
					break;
				}
				/*
				 * if (device == Definitions.FRAME_BAROMETER) {
				 * ShowBarometer.printDevice(new WXBarometer (message)); //
				 * ShowBarometer.printBarom(new WXBarometer (message)); } if
				 * (device == Definitions.FRAME_ANEMOMETER ){ //
				 * ShowBarometer.printAnemometer(new WXAnemometer (message)); }
				 */// System.out.println(message);
				/*
				 * if ((message.indexOf("Frame")) >= 0) { // find a frame //
				 * message frame = this.getFrame(message); // frame = message;
				 * System.out.println(); System.out.println("Frame[1]: " + "0x"
				 * + Integer.toHexString(frame[1]) + " : " + " hex: " +
				 * Integer.toHexString(Definitions.FRAME_BAROMETER)); if
				 * (frame[1] == Definitions.FRAME_BAROMETER) { //
				 * WeatherDataObject barom = new // WeatherDataObject(message);
				 * // WXBarometer barom = new WXBarometer(frame); //
				 * ShowBarometer.printBarom(barom); ShowBarometer.printBarom(new
				 * WXBarometer (frame)); } else if (frame[1] ==
				 * Definitions.FRAME_ANEMOMETER) {
				 * ShowBarometer.printAnemometer(new WXAnemometer(frame)); }
				 * frame = null; }
				 */

			}

		}

	}

	//public WXAnemometer getAnemometer() {
	//	return this.wind;
	//}

	private void setAnemometer(String message) {
		wind.updateValues(message);
	}

	private void setBarometer(String message) {
		barm.updateValues(message);
	}

	//public WXBarometer getBarometer() {
	//	return this.barm;
	//}

	private void setTemperature(String message) {
		// first get the sensor by creating a temp Wx object
		WXThermohygrometer tempTemp = new WXThermohygrometer();
		tempTemp.updateValues(message);
		WxLogging.toConsole(WxLogging.callEmpty , 
				"Temp sensor: " + tempTemp.getSensor());
		WxLogging.toConsole(WxLogging.callEmpty , 
				"Temp values: " + tempTemp.printValues());

		
		if (tempTemp.getSensor() == 0) {
			tempIndoor.updateValues(message);
		} else {
			tempOutdoor.updateValues(message);
		}
	}
	private void setRain(String message) {
		rain.updateValues(message);
	}
	
	private void setClock(String message) {
		clock.updateValues(message);
	}

	

}

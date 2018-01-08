package xilodyne.wxcenter.server;

import java.util.StringTokenizer;

import xilodyne.wxcenter.devices.server.Stack;
import xilodyne.wxcenter.devices.xml.jaxb.WXAnemometer;
import xilodyne.wxcenter.devices.xml.jaxb.WXBarometer;
import xilodyne.wxcenter.devices.xml.jaxb.WXCenterDevice;
import xilodyne.wxcenter.devices.xml.jaxb.WXThermohygrometer;
import xilodyne.wxcenter.globals.*;
import xilodyne.wxcenter.gui.Controller;


public class ProcessMessage implements Runnable {
	// http://systembash.com/content/a-simple-java-udp-server-and-udp-client/

	/*
	 * Take each message: If begin with "Frame", then get next message If not
	 * "Frame" then process message Based upon Frame message, format object and
	 * send to gui
	 */
	String message;
	short device;
	Controller controller;

	public ProcessMessage(Controller controller) {
		this.controller = controller;
	}

	public void run() {

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
				device = WXCenterDevice.getWXDevice(message);
				switch (device) { // check sensor code
				case Definitions.FRAME_ANEMOMETER:
					controller.printWind(new WXAnemometer(message));

					break;
				case Definitions.FRAME_BAROMETER:
					controller.printBarm(new WXBarometer(message));

					break;
				case Definitions.FRAME_THERMOHYGROMETER:
					controller.printThermohygrometer(new WXThermohygrometer(
							message));
					// ShowBarometer.printThermohygrometer(new
					// WXThermohygrometer(
					// message));
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

	private short[] getFrame(String message) {
		// if frame is in the string
		// add each frame byte to the array
		int iByteStart = message.indexOf("Frame") + "Frame".length();
		String frameBytes = message.substring(iByteStart).trim();
	//	System.out.println(frameBytes);

		StringTokenizer loop = new StringTokenizer(frameBytes);
		// Vector bytes = new Vector();
		int length = frameBytes.length() / 3;
//		System.out.println("length: " + length);
		short[] bytes = new short[frameBytes.length() / 3];
		int count = 0;
		try {
			while (loop.hasMoreElements()) {
				String val = loop.nextElement().toString().trim();
		//		System.out.print(count + " " + val + ":   ");
				bytes[count] = Short.parseShort(val, 16);
				count++;
			}
		} catch (ArrayIndexOutOfBoundsException e) { // fix later for wrongly
														// formated messages
		}

		// System.out.println();
		// for (int i = 0; i < bytes.length; i++) {
		// System.out.print(" " + Integer.toHexString(bytes[i]));
		// }
		// System.out.println();
		// Enumeration eLoop = bytes.elements();
		// while (eLoop.hasMoreElements()) {
		// System.out.print((Byte)eLoop.nextElement());
		// }
		return bytes;

	}

}

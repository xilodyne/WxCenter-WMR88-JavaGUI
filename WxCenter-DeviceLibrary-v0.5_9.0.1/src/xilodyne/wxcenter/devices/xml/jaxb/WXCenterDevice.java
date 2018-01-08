package xilodyne.wxcenter.devices.xml.jaxb;

import uk.ac.stir.cs.wx.WXLoggerDefinitions;
import xilodyne.wxcenter.logging.WxLogging;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.StringTokenizer;

@XmlRootElement
public class WXCenterDevice {

	private short[] frame;
	private short device;
	private String message;
	private String batteryDescription;
	private int batteryValue;
	private long dateTimeStamp; //date in millisecs since 1970

	private boolean checksum;

	public WXCenterDevice(short[] frame) {
		this.frame = frame;
	}

	public WXCenterDevice() {
		this.clearValues();
	}

	public void clearValues() {
		this.frame = null;
		this.device = 0;
		this.setMessage("");
		this.batteryDescription = "";
		this.batteryValue = 0;
		this.checksum = false;
	//	this.dateTimeStamp = "";
		this.dateTimeStamp = 0;
	}

	public WXCenterDevice(String message) {
		this.setMessage(message);
		this.frame = getFrame(message);
		this.setValues();
	}

	public void updateValues(String message) {
		this.setMessage(message);
		this.frame = getFrame(message);
		this.setValues();
	}

	public short[] frame() {
		return this.frame;
	}

	public boolean checksum() {
		return this.checksum;
	}

	public short device() {
		return this.device;
	}

	private void setValues() {
		this.checksum = (frame[0] == 0);
		this.device = frame[1];
		this.setBatteryDescription(this.getBattery(this.getFrameAtLoc(0) / 64));
		this.setBatteryValue(this.getFrameAtLoc(0) / 64);
		this.setDateTimeStampNew();
	}

	public static short getWXDevice(String message) {
		short[] frame;
		short device = 0;
		if (message == null) {
			return device;
		}

		if ((message.indexOf("Frame")) < 0) { // find a frame
			return device;
		}
		// message
		frame = getFrame(message);
		return frame[1];
	}

	public static short[] getFrame(String message) {
		// if frame is in the string
		// add each frame byte to the array
		int iByteStart = message.indexOf("Frame") + "Frame".length();
		String frameBytes = message.substring(iByteStart).trim();
		StringTokenizer loop = new StringTokenizer(frameBytes);
		int length = frameBytes.length() / 3; // 3 = space + digit + digit
		WxLogging.toConsole(WxLogging.callUDP, "Length: " + length + " Frame: "
				+ frameBytes);

		short[] bytes = new short[length];

		int count = 0;
		try {
			while (loop.hasMoreElements()) { // loop until hit exception
				String val = loop.nextElement().toString().trim();
				bytes[count] = Short.parseShort(val, 16);
				count++;
			}
		} catch (ArrayIndexOutOfBoundsException e) { // fix later for wrongly
			// System.out.println(e.getLocalizedMessage()); // formated messages
		}
		return bytes;
	}

	public short getFrameAtLoc(int index) {
		short value = 0;
		try {
		//return this.frame[index];
			value = this.frame[index];
		} catch (ArrayIndexOutOfBoundsException e) {
			WxLogging.toConsole("FrmAtLo" , 
					e.getLocalizedMessage());

		}
		return value;
	}

	/**
	 * Return description corresponding to battery code.
	 * 
	 * @param batteryCode
	 *            battery code
	 * @return battery description
	 */

	private String getBattery(int batteryCode) {
		int batteryIndex = batteryCode == 0 ? 0 : 1;// get battery description
													// index
		return (WXLoggerDefinitions.BATTERY_DESCRIPTION[batteryIndex]); // return
																		// battery
																		// description
	}

	public void setBatteryDescription(String value) {
		this.batteryDescription = value;
	}

	@XmlElement
	public String getBatteryDescription() {
		return this.batteryDescription;

	}

	public void setBatteryValue(int value) {
		this.batteryValue = value;
	}

	@XmlElement
	public int getBatteryValue() {
		return this.batteryValue;
	}

	public void setDateTimeStampNew() {
		this.dateTimeStamp = WxLogging.getCurrentTime();
	}

	@XmlElement
	public long getDateTimeStamp(){
		return this.dateTimeStamp;
	}
//	public String getDateTimeStamp() {
//		return this.dateTimeStamp;
//	}

	public void setDateTimeStamp(long value) {
		this.dateTimeStamp = value;
	}
//	public void setDateTimeStamp(String value) {
//		this.dateTimeStamp = value;
//	}

	// public char setBatteryStatus(int sensorCode, String batteryDescription) {
	// char batterySymbol = // get battery status symbol
	// batteryDescription.equals("OK") ? WXLoggerDefinitions.STATUS_OK
	// : WXLoggerDefinitions.STATUS_BATTERY;
	/*
	 * if (batterySymbol == WXLoggerDefinitions.STATUS_BATTERY) { // battery //
	 * low? if (sensorCode == WXLoggerDefinitions.CODE_ANEMOMETER) //
	 * anemometer? statusAnemometer = batterySymbol; // set anemometer status
	 * else if (sensorCode == WXLoggerDefinitions.CODE_RAINFALL) // rain //
	 * gauge? statusRain = batterySymbol; // set rain gauge status else if
	 * (sensorCode == WXLoggerDefinitions.CODE_THERMOHYGROMETER) // outdoor //
	 * thermohygrometer? statusThermohygrometer = batterySymbol; // set
	 * thermohygrometer // status else if (sensorCode ==
	 * WXLoggerDefinitions.CODE_UV) // UV sensor? statusUV = batterySymbol; //
	 * set UV sensor status }
	 */
	// return (batterySymbol); // return battery status symbol
	// }

	/**
	 * Return sign value corresponding to sign code (0 positive, non-0
	 * negative).
	 * 
	 * @param signCode
	 *            sign code
	 * @return sign (+1 or -1)
	 */

	public int getSign(int signCode) {
		return (signCode == 0 ? +1 : -1); // return sign code
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

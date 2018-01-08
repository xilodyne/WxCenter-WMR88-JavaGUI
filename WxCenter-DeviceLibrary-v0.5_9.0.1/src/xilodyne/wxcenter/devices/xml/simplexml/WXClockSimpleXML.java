package xilodyne.wxcenter.devices.xml.simplexml;


import org.simpleframework.xml.*;

import xilodyne.wxcenter.globals.Definitions;
import xilodyne.wxcenter.logging.WxLogging;

@Root
public class WXClockSimpleXML extends WXCenterDeviceSimpleXML{
	
	@Element(name = "dateStamp", required = false)
	private String dateStamp = "";
	private int minute, hour, day, month, year;
							
	@Element(name = "zoneSign")
	private int zoneSign;
	
	@Element(name = "zone")
	private int zone;
	
	@Element(name = "radioLevel")
	private int radioLevel; 
	//private String radioDescription; 
	//private String time, date;
	
	public WXClockSimpleXML() {
		super();
	}

	public WXClockSimpleXML(String message) {
		super(message);
		this.setValues();
		WxLogging.toConsole("CL init" , 
				this.printValues());
	}
	
	public void clearValues() {
		super.clearValues();
		this.minute = 0;
		this.hour = 0;
		this.day = 0;
		this.month = 0;
		this.year = 0;
		this.zoneSign = 0;
		this.zone = 0;
		this.radioLevel = 0;
		
		this.dateStamp = "";	
	}
	
	public void updateValues(String message) {
		WxLogging.toConsole(WxLogging.callEmpty , 
				"Update Clock...");
		this.clearValues();
		super.updateValues(message);
		this.setValues();
		WxLogging.toConsole(WxLogging.callUpdate, 
				this.printValues());
	}
	
	//used by JAXB
	public void loadClock(WXClockSimpleXML temp) {
		this.clearValues();
		super.setBatteryDescription(temp.getBatteryDescription());
		super.setBatteryValue(temp.getBatteryValue());
		
		this.setDateStamp(temp.getDateStamp());	
		this.setZone(temp.getZone());
		this.setZoneSign(temp.getZoneSign());
		this.setRadioLevel(temp.getRadioLevel());
	}
	
	public void setDateStamp(String value) {
		this.dateStamp = value;
	}
	

	public String getDateStamp() {
		return this.dateStamp;
	}

	public int getZoneSign() {
		return this.zoneSign;
	}
	
	public void setZoneSign(int value) {
		this.zoneSign = value;
	}

	public int getZone() {
		return this.zone;
	}
	public void setZone(int value) {
		this.zone = value;
	}
	
	public int getRadioLevel() {
		return this.radioLevel;
	}
	public void setRadioLevel(int value) {
		this.radioLevel = value;
	}
	private void setValues(){
		this.minute = super.getFrameAtLoc(4) % 60;
		this.hour = super.getFrameAtLoc(5) % 24;
		this.day = super.getFrameAtLoc(6) % 32;
		this.month = super.getFrameAtLoc(7) % 13;
		this.year = Definitions.CENTURY + (super.getFrameAtLoc(8) % 100);
		
		this.setDateStamp(this.year + "-" + this.month + "-" + this.day + " " +
				this.hour + ":" + this.minute );
		this.zoneSign = super.getSign(super.getFrameAtLoc(9) / 128);
		this.zone = super.getFrameAtLoc(9) %128;
		this.radioLevel = super.getFrameAtLoc(0) / 16 % 4;
		
	}
	
	public String printValues() {
		StringBuffer sb = new StringBuffer();
		sb.append("Date / Time stamp: " + this.dateStamp);
		sb.append("Zone sign: " + this.zoneSign);
		sb.append("Zone: " + this.zone);
		sb.append("Radio Level: " + this.radioLevel);
		
		return sb.toString();
	}
	/*
	 * 	private static void analyseClock(byte[] frame) throws IOException {
		int minute = getInt(frame[4]) % 60; // get minute, limited to 59
		int hour = getInt(frame[5]) % 24; // get hour, limited to 23
		int day = getInt(frame[6]) % 32; // get day, limited to 31
		int month = getInt(frame[7]) % 13; // get month, limited to 12
		int year = CENTURY + (getInt(frame[8]) % 100); // get year, limited to
														// 99
		int zoneSign = // get time zone sign
		getSign(getInt(frame[9]) / 128);
		int zone = getInt(frame[9]) % 128; // get time zone
		int radioLevel = (getInt(frame[0]) / 16) % 4; // get radio level
		String radioDescription = getRadio(radioLevel); // get radio description
		String time = getTime(hour, minute); // get current time
		String date = getDate(year, month, day); // get current date
		if ((logFlags & LOG_SENSOR) != 0) // sensor data to be output?
			logInfo( // log sensor data
			"Clock: " + "Time " + time + ", " + "Date " + date + ", " + "UTC "
					+ String.format("%+2d", zoneSign * zone) + "h, " + "Radio "
					+ radioLevel + " (" + radioDescription + ")");
	}

	 */
	
	/**
	 * Return sign value corresponding to sign code (0 positive, non-0
	 * negative).
	 * 
	 * @param signCode
	 *            sign code
	 * @return sign (+1 or -1)
	 */

}


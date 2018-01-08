package xilodyne.wxcenter.bsgui;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import xilodyne.wxcenter.devices.xml.jaxb.WXAnemometer;
import xilodyne.wxcenter.devices.xml.jaxb.WXBarometer;
//import xilodyne.wxcenter.devices.xml.jaxb.WXClock;
import xilodyne.wxcenter.devices.xml.jaxb.WXThermohygrometer;
import xilodyne.wxcenter.devices.xml.simplexml.WXAnemometerSimpleXML;
import xilodyne.wxcenter.devices.xml.simplexml.WXBarometerSimpleXML;
//import xilodyne.wxcenter.devices.xml.simplexml.WXClockSimpleXML;
import xilodyne.wxcenter.devices.xml.simplexml.WXThermohygrometerSimpleXML;
import xilodyne.wxcenter.logging.WxLogging;

public class Controller {
	private static View view;

	private long holdOutdoorTemp_dateTime = 0;
	private long holdIndoorTemp_dateTime = 0;
	private long holdHumidity_dateTime = 0;

	private Date dateHoldOutdoorTemp = new Date();
	private Date dateHoldIndoorTemp = new Date();
	private Date dateHoldOutdoorHumidity = new Date();

	final int waitTime = 3; // in minutes

	public Controller(final Model model, final View view) {
		// listeners here
		Controller.view = view;
		this.dateHoldOutdoorTemp = WxLogging.getTimeStamp();
		this.dateHoldIndoorTemp = WxLogging.getTimeStamp();
		this.dateHoldOutdoorHumidity = WxLogging.getTimeStamp();
		WxLogging.toConsole(WxLogging.callEmpty, "new date: "
				+ this.dateHoldOutdoorTemp.getTime());

	}

	public void mainloop() {
		view.frame.setVisible(true);
	}

	// public updateTemp()
	public void printThermohygrometer(WXThermohygrometer thermo) {
		if (thermo.getSensor() == 0) {
			this.printTempIndoor(thermo);
		//	view.mainPanel.updateIndoorTemp(thermo.getTemperature());
		//	view.mainPanel.updateIndoorBattery(thermo.getBatteryValue());
		}
		if (thermo.getSensor() == 1) {
			this.printTempOutdoor(thermo);
		//	view.mainPanel.updateOutdoorTemp(thermo.getTemperature());
		//	view.mainPanel.updateOutdoorHumidity(thermo.getHumidity());
		//	view.mainPanel.updateOutdoorBattery(thermo.getBatteryValue());
		
		}
	}

	// public updateTemp()
	public void printTempIndoor(WXThermohygrometer thermo) {
		view.mainPanel.updateIndoorTemp(thermo.getTemperature());
		view.mainPanel.updateIndoorBattery(thermo.getBatteryValue());
		this.checkUpdateIndoorTemp(thermo.getDateTimeStamp());
	}
	// public updateTemp()
	public void printTempIndoor(WXThermohygrometerSimpleXML thermo) {
		view.mainPanel.updateIndoorTemp(thermo.getTemperature());
		view.mainPanel.updateIndoorBattery(thermo.getBatteryValue());
		this.checkUpdateIndoorTemp(thermo.getDateTimeStamp());
	}

	public void printTempOutdoor(WXThermohygrometer thermo) {
		view.mainPanel.updateOutdoorTemp(thermo.getTemperature());
		view.mainPanel.updateOutdoorHumidity(thermo.getHumidity());
		view.mainPanel.updateOutdoorBattery(thermo.getBatteryValue());
		this.checkUpdateOutdoorTemp(thermo.getDateTimeStamp());
	}
	public void printTempOutdoor(WXThermohygrometerSimpleXML thermo) {
		view.mainPanel.updateOutdoorTemp(thermo.getTemperature());
		view.mainPanel.updateOutdoorHumidity(thermo.getHumidity());
		view.mainPanel.updateOutdoorBattery(thermo.getBatteryValue());
		this.checkUpdateOutdoorTemp(thermo.getDateTimeStamp());
	}

	public void printBarm(WXBarometer bar) {
		view.mainPanel.updateBarometer(bar.getBarABS());
		view.mainPanel.updateForecast(bar.getWeatherForecastValue());
	}
	public void printBarm(WXBarometerSimpleXML bar) {
		view.mainPanel.updateBarometer(bar.getBarABS());
		view.mainPanel.updateForecast(bar.getWeatherForecastValue());
	}

	public void printWind(WXAnemometer wind) {
		view.mainPanel.updateWindSpeed(wind.getWindSpeed());
		// view.updateWindDirection(wind.getWindDirection());
		view.mainPanel.updateWindDirection(wind.getWindDirDescription());
		view.mainPanel.updateWindBattery(wind.getBatteryValue());
	}
	public void printWind(WXAnemometerSimpleXML wind) {
		view.mainPanel.updateWindSpeed(wind.getWindSpeed());
		// view.updateWindDirection(wind.getWindDirection());
		view.mainPanel.updateWindDirection(wind.getWindDirDescription());
		view.mainPanel.updateWindBattery(wind.getBatteryValue());
	}

	/*
	 * If over five minutes, gray out text Check if object timestamps match, if
	 * so check if current app timestamp is five minutes past last app timestamp
	 * for object
	 * 
	 * http://stackoverflow.com/questions/4142313/java-convert-milliseconds-to-time
	 * -format long millis = millis % 1000; long second = (millis / 1000) % 60;
	 * long minute = (millis / (1000 * 60)) % 60; long hour = (millis / (1000 *
	 * 60 * 60)) % 24; String time = String.format("%02d:%02d:%02d:%d", hour,
	 * minute, second, millis);
	 */

	private void checkUpdateOutdoorTemp(long dateTime) {
		if ((dateTime == this.holdOutdoorTemp_dateTime)
				&& this.hasWaitTimeBeenReach(this.holdOutdoorTemp_dateTime)) {
			view.mainPanel.setOutdoorTempOutOfDate();
			view.mainPanel.setOutdoorHumidityOutOfDate();

		} else {
			view.mainPanel.setOutdoorTempCurrent();
			view.mainPanel.setOutdoorHumidityCurrent();
			this.dateHoldOutdoorTemp = WxLogging.getTimeStamp();
			this.holdOutdoorTemp_dateTime = dateTime;
		}
	}
	private void checkUpdateIndoorTemp(long dateTime) {
		if ((dateTime == this.holdIndoorTemp_dateTime)
				&& this.hasWaitTimeBeenReach(this.holdIndoorTemp_dateTime)) {
			view.mainPanel.setIndoorTempOutOfDate();
		} else {
			view.mainPanel.setIndoorTempCurrent();
			this.dateHoldIndoorTemp = WxLogging.getTimeStamp();
			this.holdIndoorTemp_dateTime = dateTime;
		}
	}


	private boolean hasWaitTimeBeenReach(long holdTime) {
		int newMinute = (int) TimeUnit.MILLISECONDS.toMinutes(WxLogging
				.getCurrentTime());
		int oldMinute = (int) TimeUnit.MILLISECONDS
				.toMinutes(holdTime);
		int diffMinute = newMinute - oldMinute;
		return (diffMinute > this.waitTime);
	}

	public long getHoldHumidity_dateTime() {
		return holdHumidity_dateTime;
	}

	public void setHoldHumidity_dateTime(long holdHumidity_dateTime) {
		this.holdHumidity_dateTime = holdHumidity_dateTime;
	}
}

package xilodyne.wxcenter.devices.xml.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import xilodyne.wxcenter.logging.WxLogging;

@XmlRootElement
public class WXRainGauge extends WXCenterDevice {

	private float rainRate;
	private float rainRecent;
	private float rainDay;
	private float rainReset;

	// private float rainMidnight;

	public WXRainGauge() {
		super();
	}

	// new creation from application
	public WXRainGauge(String message) {
		super(message);
		this.setValues();
		WxLogging.toConsole("BA init", this.printValues());
	}

	public void clearValues() {
		super.clearValues();
		this.rainRate = 0;
		this.rainRecent = 0;
		this.rainDay = 0;
		this.rainReset = 0;
		// this.rainMidnight = 0;
	}

	// load to JAXB
	public void updateValues(String message) {
		WxLogging.toConsole(WxLogging.callEmpty, "Update Rain...");
		this.clearValues();
		super.updateValues(message);
		this.setValues();
		WxLogging.toConsole(WxLogging.callUpdate, this.printValues());

	}

	// load from JAXB
	public void loadRain(WXRainGauge temp) {
		this.clearValues();
		super.setBatteryDescription(temp.getBatteryDescription());
		super.setBatteryValue(temp.getBatteryValue());

		// this.setBarABS(temp.getBarABS());
		// this.setWeatherForecastValue(temp.getWeatherForecastValue());
		// super.setDateTimeStamp(temp.getDateTimeStamp());
	}

	private void setValues() {
		this.rainRate = getRain(256.0f * super.getFrameAtLoc(3)
				+ super.getFrameAtLoc(2));
		this.rainRecent = getRain(256.0f * super.getFrameAtLoc(5)
				+ super.getFrameAtLoc(4));
		this.rainDay = getRain(256.0f * super.getFrameAtLoc(7)
				+ super.getFrameAtLoc(6));
		this.rainReset = getRain(256.0f * super.getFrameAtLoc(9)
				+ super.getFrameAtLoc(8));

		// this.rainMidnight = 0;
	}

	public String printValues() {
		StringBuffer sb = new StringBuffer();

		sb.append("Rain rate: " + this.rainRate);
		sb.append(", Rain recent: " + this.rainRecent);
		sb.append(", Rain day: " + this.rainDay);
		sb.append(", Rain reset: " + this.rainReset);

		return sb.toString();
	}

	@XmlElement
	public float getRainRate() {
		return this.rainRate;
	}

	public void setRainRate(float value) {
		this.rainRate = value;
	}

	@XmlElement
	public float getRainRecent() {
		return this.rainRecent;
	}

	public void setRainRecent(float value) {
		this.rainRecent = value;
	}

	@XmlElement
	public float getRainDay() {
		return this.rainDay;
	}

	public void setRainDay(float value) {
		this.rainDay = value;
	}

	@XmlElement
	public float getRainReset() {
		return this.rainReset;
	}

	public void setRainReset(float value) {
		this.rainReset = value;
	}

	/*
	 * private static void analyseRainfall(byte[] frame) { String
	 * batteryDescription = // get battery level description
	 * getBattery(getInt(frame[0]) / 64); statusRain = // set rain gauge battery
	 * status setBatteryStatus(CODE_RAINFALL, batteryDescription); float
	 * rainRate = // get rainfall rate (mm/hr) getRain(256.0f * getInt(frame[3])
	 * + getInt(frame[2])); float rainRecent = // get recent (mm) getRain(256.0f
	 * * getInt(frame[5]) + getInt(frame[4])); float rainDay = // get rainfall
	 * for day (mm) getRain(256.0f * getInt(frame[7]) + getInt(frame[6])); float
	 * rainReset = // get rainfall since reset (mm) getRain(256.0f *
	 * getInt(frame[9]) + getInt(frame[8])); if (rainInitial == DUMMY_VALUE) //
	 * initial rain offset unknown? rainInitial = rainReset; // use rain since
	 * last reset else if (rainReset < rainInitial) // rain memory has been
	 * reset? rainInitial = -rainInitial; // adjust initial rain offset float
	 * rainMidnight = // set rain total since midnight Math.round(10.0f *
	 * (rainReset - rainInitial)) / 10.0f; // to 1 dec. // place int minute =
	 * getInt(frame[10]) % 60; // get minute, limited to 59 int hour =
	 * getInt(frame[11]) % 24; // get hour, limited to 23 int day =
	 * getInt(frame[12]) % 32; // get day, limited to 31 int month =
	 * getInt(frame[13]) % 13; // get month, limited to 12 int year = CENTURY +
	 * (getInt(frame[14]) % 100); // get year, limited to // 99 String resetTime
	 * = getTime(hour, minute); // get last reset time String resetDate =
	 * getDate(year, month, day); // get last reset date if ((logFlags &
	 * LOG_SENSOR) != 0) // sensor data to be output? logInfo( // log sensor
	 * data "Rain Gauge: " + "Rate " + rainRate + "mm/h, " + "Recent " +
	 * rainRecent + "mm, " + "24 Hour " + rainDay + "mm, " + "From Midnight " +
	 * rainMidnight + "mm, " + "From Reset " + rainReset + "mm, " + "Reset " +
	 * resetTime + " " + resetDate + ", " + "Battery " + batteryDescription);
	 * addMeasure(INDEX_RAIN_TOTAL, rainMidnight); // add daily rain //
	 * measurement }
	 */

	/**
	 * Return rainfall in inches.
	 * 
	 * @param rain
	 *            rainfall in 100ths of inches
	 * @return rainfall in mm
	 */
	private float getRain(float rain) {
		rain = rain / 100.0f * 25.39f; // get rainfall in mm
		rain = Math.round(rain * 10.0f) / 10.0f; // round to one decimal place
		return (rain); // return rainfall in mm
	}
}

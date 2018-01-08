package xilodyne.wxcenter.devices.xml.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import uk.ac.stir.cs.wx.WXLoggerDefinitions;
import xilodyne.wxcenter.logging.WxLogging;

@XmlRootElement
public class WXBarometer extends WXCenterDevice {

	private int pressureAbsolute;
	private int pressureRelative;
	private int weatherForecastValue;
	private String weatherForecast;
	private String weatherPrevious;
	
	public WXBarometer() {
		super();
			}

	//new creation from application
	public WXBarometer(String message) {
		super(message);
		this.setValues();
		WxLogging.toConsole("BA init" , 
				this.printValues());
	}
	
	public void clearValues() {
		super.clearValues();
		this.pressureAbsolute = 0;
		this.pressureRelative = 0;
		this.weatherForecastValue = 0;
		this.weatherForecast = "";
		this.weatherPrevious ="";	
	}
	
	//load to JAXB
	public void updateValues(String message) {
		WxLogging.toConsole(WxLogging.callEmpty , 
				"Update Barm...");
		this.clearValues();
		super.updateValues(message);
		this.setValues();
		WxLogging.toConsole(WxLogging.callUpdate, 
				this.printValues());

	}
	
	//load from JAXB
	public void loadBarm(WXBarometer temp) {
		this.clearValues();
		super.setBatteryDescription(temp.getBatteryDescription());
		super.setBatteryValue(temp.getBatteryValue());
		
		this.setBarABS(temp.getBarABS());
		this.setWeatherForecastValue(temp.getWeatherForecastValue());
		super.setDateTimeStamp(temp.getDateTimeStamp());
	}

	private void setValues() {
		this.pressureAbsolute = // get absolute pressure (mb)
		256 * (super.getFrameAtLoc(3) % 16) + super.getFrameAtLoc(2);
		this.pressureRelative = // get relative pressure (mb)
		256 * (super.getFrameAtLoc(5) % 16) + super.getFrameAtLoc(4);
		this.weatherForecast = // get forecast weather descrip.
		getWeather(super.getFrameAtLoc(3) / 16);
		this.weatherForecastValue = super.getFrameAtLoc(3) / 16;

		this.weatherPrevious = // get previous weather descrip.
		getWeather(super.getFrameAtLoc(5) / 16);

	}

	public String printValues() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Bar Absolute: " + pressureAbsolute);
		sb.append(", Relative: " + pressureRelative);
		sb.append(", Forcast val: " + weatherForecastValue);
		sb.append(", Forecast: " + weatherForecast);
		sb.append(", Previous: " + weatherPrevious);

		return sb.toString();
	}

	/**
	 * Return description corresponding to weather code.
	 * 
	 * @param weatherCode
	 *            weather code
	 * @return weather description
	 */
	private static String getWeather(int weatherCode) {
		// return weather description// weather
		// code
		// in
		// range?
		return (weatherCode < WXLoggerDefinitions.WEATHER_DESCRIPTION.length ? WXLoggerDefinitions.WEATHER_DESCRIPTION[weatherCode]
				: "Unknown");
	}

	// public double getBarABS() {
	// Integer temp = this.pressureAbsolute;
	// return temp.doubleValue();
	// }

	public void setBarABS(int value) {
		this.pressureAbsolute = value;
	}

	@XmlElement
	public int getBarABS() {
		return this.pressureAbsolute;
	}

	public void setWeatherForecastValue(int value) {
		this.weatherForecastValue = value;
	}

	@XmlElement
	public int getWeatherForecastValue() {
		return this.weatherForecastValue;
	}
}

package xilodyne.wxcenter.devices.xml.simplexml;

import org.simpleframework.xml.*;

import uk.ac.stir.cs.wx.WXLoggerDefinitions;
import xilodyne.wxcenter.logging.WxLogging;

//Temperature and humidity gauge

@Root
public class WXThermohygrometerSimpleXML extends WXCenterDeviceSimpleXML {


	@Element(name = "temperature")
	private float temperature;
	
	@Element(name = "sensor")
	private int sensor;

	private int temperatureSign;

	private String temperatureTrend; // get temperature trend
	
	@Element(name = "humidity")
	private int humidity; // get humidity (%)
	private String humidityTrend; // get humidity trend
	private int dewpointSign; // get dewpoint sign
	private float dewpoint; // get dewpoint (deg C)

	private boolean heatValid; // get heat index validity
	private float heatIndex; // get heat index (deg C)
	private String heatDescription; // get heat index description

	public WXThermohygrometerSimpleXML(){
		super();
	}
	
	public WXThermohygrometerSimpleXML(String message) {
		super(message);
		this.setValues();
		WxLogging.toConsole("TE init" , 
				this.printValues());

		this.printValues();
		
	}
	public void clearValues() {
		super.clearValues();	
		this.temperature = 0;
		this.sensor = 0;
		this.temperatureSign = 0;
		this.temperatureTrend = ""; 
		this.humidity = 0; 
		this.humidityTrend = ""; 
		this.dewpointSign = 0; 
		this.dewpoint = 0; 
		this.heatValid = false; 
		this.heatIndex = 0; 
		this.heatDescription = ""; 
	}
	
	public void updateValues(String message) {
		WxLogging.toConsole(WxLogging.callEmpty , 
				"Update Temp...");
		this.clearValues();
		super.updateValues(message);
		this.setValues();
		WxLogging.toConsole(WxLogging.callUpdate, 
				this.printValues());
	}

	public void loadTemp(WXThermohygrometerSimpleXML temp) {
		this.clearValues();
		super.setBatteryDescription(temp.getBatteryDescription());
		super.setBatteryValue(temp.getBatteryValue());
		super.setDateTimeStamp(temp.getDateTimeStamp());
		
		this.setSensor(temp.getSensor());
		this.setHumidity(temp.getHumidity());
		this.setTemperature(temp.getTemperature());
	
	}
	
	
	public float getTemperature() {
		return this.temperature;
	}
	
	public void setTemperature(float value) {
		this.temperature = value;
	}
	
	
	public int getHumidity() {
		return this.humidity;
	}
	
	public void setHumidity(int value) {
		this.humidity = value;
	}
	
	
	public int getSensor() {
		return this.sensor;
	}
	
	
	public void setSensor(int value) {
		this.sensor = value;
	}
	


	private void setValues() {

		this.sensor = super.getFrameAtLoc(2)% 16;

		this.temperatureSign = this.getSign(super.getFrameAtLoc(4));

		
		this.temperature = temperatureSign // get temperature (deg C)
				* (256.0f * (super.getFrameAtLoc(4)% 16) + super.getFrameAtLoc(3))
				/ 10.0f;

		this.temperatureTrend = getTrend(super.getFrameAtLoc(0) % 4);
		this.humidity = super.getFrameAtLoc(5) % 100; // get humidity (%)
		this.humidityTrend = getTrend(super.getFrameAtLoc(2) % 4);
		this.dewpointSign = getSign(super.getFrameAtLoc(7));
		this.dewpoint = dewpointSign
				* // get dewpoint (deg C)
				(256.0f * (super.getFrameAtLoc(7) + super.getFrameAtLoc(6)))
				/ 10.0f;
		this.heatValid = super.getFrameAtLoc(9) == 0;

		this.heatIndex = dewpointSign * // get heat index (deg C)
				fahrenheitCelsius((256.0f * (super.getFrameAtLoc(9) + super
						.getFrameAtLoc(8))) / 10.0f);
		this.heatDescription = // get heat index description
		heatValid ? heatIndex + WXLoggerDefinitions.DEGREE + "C" : "N/A";
		/*
		 * if ((logFlags & LOG_SENSOR) != 0) // sensor data to be output?
		 * logInfo( // log sensor data "Thermohygrometer: " + "Sensor " + sensor
		 * + ", " + "Temperature " + temperature + DEGREE + "C (" +
		 * temperatureTrend + "), " + "Humidity " + humidity + "% (" +
		 * humidityTrend + "), " + "Dewpoint " + dewpoint + DEGREE + "C, " +
		 * "Index " + heatDescription + ", " + "Battery " + batteryDescription);
		 */
		/*
		 * if (sensor == 0) { // indoor sensor?
		 * addMeasure(INDEX_INDOOR_TEMPERATURE, // add indoor temperature
		 * temperature); addMeasure(INDEX_INDOOR_HUMIDITY, // add indoor
		 * humidity humidity); addMeasure(INDEX_INDOOR_DEWPOINT, // add indoor
		 * dewpoint dewpoint); } else if (sensor == OUTDOOR_SENSOR) { // outdoor
		 * sensor? statusThermohygrometer = // set thermo. battery status
		 * setBatteryStatus(CODE_THERMOHYGROMETER, batteryDescription);
		 * outdoorTemperature = temperature; // set outdoor temperature
		 * addMeasure(INDEX_OUTDOOR_TEMPERATURE, // add outdoor temperature
		 * temperature); addMeasure(INDEX_OUTDOOR_HUMIDITY, // add outdoor
		 * humidity humidity); addMeasure(INDEX_OUTDOOR_DEWPOINT, // add outdoor
		 * dewpoint dewpoint); }
		 */
	}



	/**
	 * Return description corresponding to trend code.
	 * 
	 * @param trendCode
	 *            trend code
	 * @return trend description
	 */
	private String getTrend(int trendCode) {
		return ( // return trend description
		trendCode < WXLoggerDefinitions.TREND_DESCRIPTION.length // trend code
																	// in range?
		? WXLoggerDefinitions.TREND_DESCRIPTION[trendCode]
				: "Unknown");
	}

	/**
	 * Return Celsius equivalent of Fahrenheit temperature.
	 * 
	 * @param fahrenheit
	 *            Fahrenheit temperature
	 * @return Celsius temperature
	 */
	private  float fahrenheitCelsius(float fahrenheit) {
		return (0.5555f * (fahrenheit - 32.0f)); // return Celsius equivalent
	}
	
	public String printValues() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("Bat Desc: " + super.getBatteryDescription() ) ;
		sb.append(", Temp: " + this.temperature );
		sb.append(", Sensor: " + this.sensor);
		sb.append(", Temp Sign: " + this.temperatureSign);
		sb.append(", Temp Trend: " + this.temperatureTrend);
		sb.append(", Humdity: " + this.humidity);
		sb.append(", Hum Trend: " + this.humidityTrend);
		sb.append(", Dewpoint: " + this.dewpoint);
		sb.append(", Dewp Sign: " + this.dewpointSign);
		sb.append(", Heat Valid: " + this.heatValid);
		sb.append(", Heat Index: " + this.heatIndex);
		sb.append(", Head Desc: " + this.heatDescription);
		
		return sb.toString();
	}
}


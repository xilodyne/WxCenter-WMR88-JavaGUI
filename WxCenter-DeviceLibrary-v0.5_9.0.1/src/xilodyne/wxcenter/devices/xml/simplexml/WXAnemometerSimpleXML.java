package xilodyne.wxcenter.devices.xml.simplexml;

import org.simpleframework.xml.*;

import uk.ac.stir.cs.wx.WXLoggerDefinitions;
import xilodyne.wxcenter.logging.WxLogging;


@Root
public class WXAnemometerSimpleXML extends WXCenterDeviceSimpleXML {

	@Element(name = "windGust")
	private float windGust;
	

	private float windAverage;
	
	@Element(name = "windSpeed")
	private float windSpeed;
	
	@Element(name = "windDegrees")
	private int windDirectionDegrees;
	
	@Element(name ="windCompass")
	private int windDirectionCompass;
	
	@Element(name = "windDirDescription", required = false)
	private String windDirDescription;
	private int chillSign;

	private boolean chillValid;
	private float windChill;
	private String chillDescription;

	public WXAnemometerSimpleXML() {
		super();
	}
	


	public WXAnemometerSimpleXML(String message) {
		super(message);
		this.setValues();
		WxLogging.toConsole("WI init" , 
				this.printValues());

	}

	public void clearValues() {
		super.clearValues();

		// this.statusAnemometer = 0;
		this.windGust = 0;
		this.windAverage = 0;
		this.windSpeed = 0;
		this.windDirectionDegrees = 0;
		this.windDirectionCompass = 0;
		this.windDirDescription = "";
		this.chillSign = 0;

		this.chillValid = false;
		this.windChill = 0;
		this.chillDescription = "";
	}

	public void updateValues(String message) {
		WxLogging.toConsole(WxLogging.callEmpty , 
				"Update wind...");
		this.clearValues();
		super.updateValues(message);
		this.setValues();
		
		WxLogging.toConsole(WxLogging.callUpdate, 
				this.printValues());


	}
	
	public void loadWind(WXAnemometerSimpleXML temp) {
		this.clearValues();
		super.setBatteryDescription(temp.getBatteryDescription());
		super.setBatteryValue(temp.getBatteryValue());
		
		this.setWindSpeed(temp.getWindSpeed() );
		this.setWindDirDescription(temp.getWindDirDescription());
		
	}

	private void setValues() {
		this.setWindGust((256.0f * (super.getFrameAtLoc(5) % 16) + super
				.getFrameAtLoc(4)) / 10.0f);

		windAverage = (16.0f * super.getFrameAtLoc(6) + super.getFrameAtLoc(5) / 16) / 10.0f;
		this.setWindDegrees(Math.round((super.getFrameAtLoc(2) % 16) * 22.5f));
		this.setWindCompass((super.getFrameAtLoc(2) % 16)); // get wind
												// direction //
															// (16ths)
		this.setWindDirDescriptionByInt(this.getWindCompass());

		chillSign = super.getFrameAtLoc(8) / 16; // get wind chill sign quartet
		chillValid = (chillSign & 0x2) == 0;// get wind chill validity
		chillSign = getSign(chillSign / 8); // get wind chill sign
		windChill = chillSign * // get wind chill (deg C)
				super.getFrameAtLoc(7);
		chillDescription = // set wind chill description
		chillValid ? Float.toString(windChill) + WXLoggerDefinitions.DEGREE
				: "N/A";

		// statusAnemometer = super.setBatteryStatus(
		// WXLoggerDefinitions.CODE_ANEMOMETER,
		// super.getBatteryDescription());

		// wind speed/gust in m/s
		// windGust = (256.0f * (super.getFrameAtLoc(5) % 16) + super
		// .getFrameAtLoc(4)) / 10.0f;

		// windAverage = (16.0f * super.getFrameAtLoc(6) +
		// super.getFrameAtLoc(5)) / 10.0f;
		// windDirection = super.getFrameAtLoc(2) % 16; // get wind direction
		// this.setWindDirection(super.getFrameAtLoc(2) % 16); // get wind
		// direction // (16ths)

		// directionDescription = // get wind direction descr.
		// getDirection(windDirection);
		// this.setWindDescription(this.getWindDirDescription(this.windDirection));
		// this.setWindDescription(this.getWindDirDescription());
		// windDirection = // get wind direction (deg)

		// Math.round((super.getFrameAtLoc(2) % 16) * 22.5f);
		/*
		 * if (outdoorTemperature != WXLoggerDefinitions.DUMMY_VALUE) { //
		 * outdoor temperature known? if (windGust > 1.3) { // wind speed high
		 * enough? windPower = // get (gust speed)^0.16 (float)
		 * Math.pow(windGust, 0.16); windChill = // calculate wind chill (deg C)
		 * 13.12f + (0.6215f * outdoorTemperature) - (13.956f * windPower) +
		 * (0.487f * outdoorTemperature * windPower); if (windChill >
		 * outdoorTemperature) // invalid chill calculation? windChill =
		 * outdoorTemperature; // reset chill to outdoor temp. } else // wind
		 * speed not high enough windChill = outdoorTemperature; // set chill to
		 * outdoor temp. } else if (!chillValid) // no outdoor temp. or chill?
		 * windChill = 0.0f; // set dummy chill of 0 (deg C)
		 */
	}

	/**
	 * Return description corresponding to wind direction code.
	 * 
	 * @param directionCode
	 *            wind direction code
	 * @return wind direction description
	 */

	// public String getDirection(int directionCode) {
	// return ( // return wind direction descr.
	// directionCode < WXLoggerDefinitions.DIRECTION_DESCRIPTION.length //
	// weather
	// ? WXLoggerDefinitions.DIRECTION_DESCRIPTION[directionCode]
	// : "Unknown");
	// }
	/*
	 * public String getWindDirDescription(double directionCode) { // int
	 * iDirCode = (int) dircccectionCode; return ( // return wind direction descr.
	 * iDirCode < WXLoggerDefinitions.DIRECTION_DESCRIPTION.length // weather ?
	 * WXLoggerDefinitions.DIRECTION_DESCRIPTION[iDirCode] : "Unknown"); }
	 */

	public void setWindSpeed(float value) {
		this.windAverage = value;
	}

	public void setWindSpeed(double value) {
		this.windAverage = (float) value;
	}


	public double getWindSpeed() {
		Float temp = this.windAverage;
		return temp.doubleValue();
	}

	public void setWindDegrees(int value) {
		this.windDirectionDegrees = value;
	}

	public int getWindDegrees() {
		return this.windDirectionDegrees;
	}

	public void setWindCompass(int value) {
		this.windDirectionCompass = value;
	}


	public int getWindCompass() {
		// Integer temp = this.windDirection;
		// return temp.doubleValue();
		return this.windDirectionCompass;
	}

	// public void setWindDirDescriptionion(String value) {
	// this.windDirDescription = value;
	// }

	public void setWindDirDescriptionByInt(int windDir) {
		this.windDirDescription = windDir < WXLoggerDefinitions.DIRECTION_DESCRIPTION.length // weather
		? WXLoggerDefinitions.DIRECTION_DESCRIPTION[windDir]
				: "Unknown";
	}

	public void setWindDirDescription(String value) {
		this.windDirDescription = value;
	}

	public String getWindDirDescription() {
		return this.windDirDescription;
	}

	public void setWindGust(float value) {
		this.windGust = value;
	}

	public float getWindGust() {
		return this.windGust;
	}

	public String printValues() {
		StringBuffer sb = new StringBuffer();
		sb.append("Bat Desc: " + super.getBatteryDescription());

		// (", Status: " + statusAnemometer);
		sb.append(", Gust: " + windGust);
		sb.append(", avg: " + windAverage);
		sb.append(", Direction: " + windDirectionDegrees);
		sb.append(", dir desc: " + windDirDescription);
		sb.append(", Chill sign: " + chillSign);

		sb.append(", chill valid: " + chillValid);
		sb.append(", wind chill: " + windChill);
		sb.append(", chill desc: " + chillDescription);

		return sb.toString();
	}

}

package uk.ac.stir.cs.wx;

import java.text.SimpleDateFormat;

/**

<p>
  This program logs data to file from an Oregon Scientific WMR180 Professional
  Weather Station. This is a simple logging program without any graphing or
  upload capabilities. The log format is compatible with other software
  created by the author. For built-in graphs or weather site upload, look at
  alternatives such as:
</p>

<ul>

  <li>
    <a href="http://sandaysoft.com/" target="_blank">Cumulus</a>
  </li>

  <li>
    <a href="http://wmrx00.sourceforge.net/" target="_blank">Weather Station
    Data Logger</a>
  </li>

  <li>
    <a href="http://code.google.com/p/wfrog/" target="_blank">wfrog</a>
  </li>

  <li>
    <a href="http://www.wviewweather.com/" target="_blank">wview</a>
  </li>

</ul>



<p>
  In the code, entries are in alphabetical order by category. The code is
  open-source under the GNU General Public License version 2 or (at your
  discretion) any later version. The code embodies information made public in
  a number of sources that include:
</p>

<ul>

  <li>
    <a
    href="http://wiki.sandaysoft.com/files/memorymaps/Decode_WMR100-200_v5.xlsm"
    target="_blank">WMR100 and WMR200 Protocol Decoder</a>
  </li>

  <li>
    <a href="http://www.dg1sfj.de/hardware/hw_wmr100_protokoll.html"
    target="_blank">WMR100 Protocol</a>
  </li>

</ul>

<p>
  A file for each day is created in the current directory with the name
  'YYYYMMDD.DAT' (e.g. '20130131.DAT'). It contains the following fields
  separated by one or more spaces. Values are calculated for each logging
  interval, except rainfall which is totalled since the previous midnight.
</p>

<pre>
  HH:MM:SS               time of log
  NN.N NN.N NN.N         wind speed average/minimum/maximum (m/s)
  NNN NNN NNN            wind direction average/minimum/maximum (deg from N)
  NN.N NN.N NN.N         outdoor temperature average/minimum/maximum (C)
  NN NN NN               outdoor humidity average/minimum/maximum (%)
  NN.N NN.N NN.N         outdoor dewpoint average/minimum/maximum (C)
  NNNN.N NNNN.N NNNN.N   indoor pressure average/minimum/maximum (mb)
  NN.N NN.N NN.N         indoor temperature average/minimum/maximum (C)
  NN NN NN               indoor humidity average/minimum/maximum (%)
  NN                     rainfall total since midnight (mm)
  NN.N NN.N NN.N         wind chill temperature average/minimum/maximum (C)
  NN.N NN.N NN.N         indoor dewpoint average/minimum/maximum (C)
  NN NN NN               UV index average/minimum/maximum (0 low, 6 high, ...)
</pre>

<p>
  This format is compatible with that used by the original WeatherView
  software supplied by Oregon Scientific, except that measurements finish with
  an indoor dewpoint and a UV index. Another difference is that rainfall is
  cumulative for each day since midnight. The log format could be customised
  by changing method <var>logMeasures</var>.
</p>

<p>
  The wind chill is normally calculated when an anemometer reading is
  received. If an outdoor temperature has been received and the wind speed is
  high enough, the wind chill is calculated from the North American/UK
  formula. If an outdoor temperature has been received but the wind speed is
  not high enough, the wind chill is set to the outdoor temperature. If an
  outdoor temperature has not been received and the weather station wind chill
  is invalid, the wind chill is set to zero (which may result in the first
  such value being incorrect).
</p>

<p>
  Likely program customisations involve changing the following constants
  in the customisation section:
</p>

<dl>

  <dt>ARCHIVE_PATH</dt>
  <dd>
    directory path for archive files (default '/tmp', must not be '.', or end
    with '/' or '\'); if empty, no archiving takes place (i.e. log files
    remain in their original place)
  </dd>

  <dt>BACKUP_LOG</dt>
  <dd>
    when archiving, preserve the current day's log as "LAST_DAY.DAT" (default
    true, i.e. do backup); the last day's log file will be replaced if it
    exists
  </dd>

  <dt>DEGREE</dt>
  <dd>
    degree symbol (default Unicode degree); this may need to be set to empty
    if the console does not support Unicode
  </dd>

  <dt>LOG_DEFAULT</dt>
  <dd>
    default flags for log output as logical OR of individual log flags
    (default hourly data, for 0 nothing, 1 USB data, 2 frame data, 4 sensor
    data, 8 hourly data)
  </dd>

  <dt>LOG_FILE</dt>
  <dd>
    name of a log file to use, or empty to mean output to the console (default
    empty)
  </dd>

  <dt>LOG_INTERVAL</dt>
  <dd>
    the number of minutes between log entries (default 15, i.e. 00/15/30/45
    minutes past the hour); the value must be a submultiple of 60 (e.g. 10,
    15, 20, 30)
  </dd>

  <dt>OUTDOOR_SENSOR</dt>
  <dd>
    outdoor temperature sensor to log (default 1)
  </dd>

</dl>

<p>
  Error messages and the following hourly summaries can appear in the log
  output. Hourly figures are averages for the past hour, except for rainfall
  which is cumulative. Indoor or outdoor sensor data is followed by '?' if
  there is missing data for the corresponding sensor. Outdoor sensor data is
  followed by '!' if the corresponding sensor battery is low.
</p>

<pre>
  HH:MM  time
  NN.N   wind speed
  NNN    wind direction
  NN.N   outdoor temperature
  NN     outdoor humidity
  NNNN   outdoor pressure
  NN.N   outdoor rainfall total since midnight
  NN     outdoor UV index
</pre>

@author	Kenneth J. Turner (http://www.cs.stir.ac.uk/~kjt)
@version	1.0 (18th February 2013)
*/

public class WXLoggerDefinitions {
	

	
	  // ******************************** Constants ********************************

	  // ---------------------------- general constants ----------------------------

	  /** Battery description (indexed by battery code) */
	  public static final String[] BATTERY_DESCRIPTION = { "OK", "Low" };

	  /** Current century */
	  public static final int CENTURY = 2000;

	  /** Trend description (indexed by trend code) */
	  public static final String[] DIRECTION_DESCRIPTION = {
	    "N", "NNE","NE" ,"ENE", "E", "ESE", "SE", "SSE",
	    "S" ,"SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"
	  };

	  /** Format for log dates */
	  public static final SimpleDateFormat DATE_FORMAT =
	    new SimpleDateFormat("HH:mm:ss dd/MM/yy");

	  /** Dummy value for initialisation */
	  public static final float DUMMY_VALUE = -999.0f;

	  /** Weather station data frame delimiter */
	  public static final int FRAME_BYTE = (byte) 0xFF;

	  /** Human Interface Device native library name */
	  public static final String HID_LIBRARY = "hidapi";

	  /** File name for last day's data */
	  public static final String LAST_DAY = "LAST_DAY.DAT";

	  /** Console log output has frames and timestamped messages as received */
	  public static final int LOG_FRAME = (byte) 0x02;

	  /** Console log output has hourly data */
	  public static final int LOG_HOUR = (byte) 0x08;

	  /** Console log output has sensor data as received */
	  public static final int LOG_SENSOR = (byte) 0x04;

	  /** Console log output has raw USB data as received */
	  public static final int LOG_USB = (byte) 0x01;

	  /** Radio description (indexed by radio code) */
	  public static final String[] RADIO_DESCRIPTION = {
	    "None", "Searching/Weak", "Average", "Strong"
	  };

	  /** Seconds past the hour for logging (for a margin of error in timing) */
	  public static final int SECOND_OFFSET = 2;

	  /** Trend description (indexed by trend code) */
	  public static final String[] TREND_DESCRIPTION = {
	    "Steady", "Rising", "Falling"
	  };

	  /** UV description (indexed by UV code) */
	  public static final String[] UV_DESCRIPTION = {
	    "Low", "Medium", "High", "Very High", "Extremely High"
	  };

	  /** Weather description (indexed by weather code) */
	  public static final String[] WEATHER_DESCRIPTION = {
	    "Partly Cloudy", "Rainy", "Cloudy", "Sunny", "?", "Snowy"
	  };

	  // ------------------------ customisation constants --------------------------

	  /**
	    Directory path for archive files (default '/tmp', must not be '.', or end
	    with '/' or '\'); if empty, no archiving takes place
	  */
	  public static final String ARCHIVE_PATH = "/tmp";

	  /** Backup last day's data midnight if true */
	  public static final boolean BACKUP_LOG = true;

	  /** Degree character */
	  public static final String DEGREE =
	    "\u00B0";					// with Unicode console
	    // "";					// without Unicode console

	  /** Default log flags (logical OR of individual log flags) */
	  public static final int LOG_DEFAULT = LOG_HOUR;

	  /** Log destination (non-empty means a file, empty means the console) */
	  public static final String LOG_FILE = "";

	  /** Logging interval (minutes, must be sub-multiple of 60) */
	  public static final int LOG_INTERVAL = 15;

	  /** Channel for outdoor sensor (value 1/2/4? = channel 1/2/3) */
	  public static final int OUTDOOR_SENSOR = 1;

	  // -------------------------- measurement constants --------------------------

	  /** Index of indoor dewpoint measurements (Celsius) */
	  public final static int INDEX_INDOOR_DEWPOINT = 10;

	  /** Index of indoor humidity measurements (%) */
	  public final static int INDEX_INDOOR_HUMIDITY = 7;

	  /** Index of indoor pressure measurements (mbar) */
	  public final static int INDEX_INDOOR_PRESSURE = 5;

	  /** Index of indoor temperature measurements (Celsius) */
	  public final static int INDEX_INDOOR_TEMPERATURE = 6;

	  /** Index of outdoor dewpoint measurements (Celsius) */
	  public final static int INDEX_OUTDOOR_DEWPOINT = 4;

	  /** Index of outdoor humidity measurements (%) */
	  public final static int INDEX_OUTDOOR_HUMIDITY = 3;

	  /** Index of outdoor temperature measurements (Celsius) */
	  public final static int INDEX_OUTDOOR_TEMPERATURE = 2;

	  /** Index of UV index measurements (0..) */
	  public final static int INDEX_UV_INDEX = 11;

	  /** Index of rainfall measurements (mm) */
	  public final static int INDEX_RAIN_TOTAL = 8;

	  /** Index of wind chill measurements (Celsius) */
	  public final static int INDEX_WIND_CHILL = 9;

	  /** Index of wind direction measurements (degrees) */
	  public final static int INDEX_WIND_DIRECTION = 1;

	  /** Index of wind speed measurements (metres/sec) */
	  public final static int INDEX_WIND_SPEED = 0;

	  /** Number of measurement types */
	  public static final int MEASURE_SIZE = 12;

	  /** Number of periods per hour */
	  public static final int PERIOD_SIZE = 60 / LOG_INTERVAL;

	  /** Low battery status symbol */
	  public static final char STATUS_BATTERY = '!';

	  /** Missing data status symbol */
	  public static final char STATUS_MISSING = '?';

	  /** Sensor status OK symbol */
	  public static final char STATUS_OK = ' ';

	  // ------------------------------ USB constants ------------------------------

	  /** Anemometer code */
	  public final static byte CODE_ANEMOMETER = (byte) 0x48;

	  /** Barometer code */
	  public final static byte CODE_BAROMETER = (byte) 0x46;

	  /** Clock code */
	  public final static byte CODE_CLOCK = (byte) 0x60;

	  /** Rainfall bucket code */
	  public final static byte CODE_RAINFALL = (byte) 0x41;

	  /** Thermohygrometer code */
	  public final static byte CODE_THERMOHYGROMETER = (byte) 0x42;

	  /** UV code */
	  public final static byte CODE_UV = (byte) 0x47;

}

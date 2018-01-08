package xilodyne.wxcenter.gui;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import eu.hansolo.steelseries.gauges.*;
// eu.hansolo.steelseries.gauges.
import eu.hansolo.steelseries.tools.*;
import java.awt.Image;
import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.JSeparator;

public class JPanelMainPage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Radial2Top barometer;
	public RadialBargraph windSpeed;
	//private double windSpeedMax = 10.0;
	BufferedImage imageSun, imageCloud, imageRain;
	JLabel outdoorTemp, outdoorDegree, outdoorTempScale, outdoorTempDecimal,
			outdoorTempPoint, lblOutdoorData, outdoorTemplblBatIndicator,
			outdoorTemplbl;
	JLabel outdoorHumiditylbl,outdoorHumidity, outdoorHumidityScale, lblSun, lblCloud, lblRain;
	JLabel windDirection, lblWindBatIndicator, lblWindSpeed;

	JLabel indoorTemp, indoorDegree, indoorTempScale, indoorTempDecimal,
			indoorTempPoint, indoorlbl, lblIndoorData, indoorlblBatIndicator;
	private JLabel lblWindData;
	
	RainGauge rainGaugeDaily;
	private JLabel rain;

	public JPanelMainPage(final Model model) {
		setBackground(Color.DARK_GRAY);
		setBorder(new CompoundBorder());
		setLayout(null);

		try {
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();

			imageSun = ImageIO.read(classLoader
					.getResourceAsStream("Resources/sun_woodcut.png"));
			imageCloud = ImageIO.read(classLoader
					.getResourceAsStream("Resources/2_clouds.png"));
			imageRain = ImageIO.read(classLoader
					.getResourceAsStream("Resources/rain_cloud_glossy.png"));

		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}

		lblSun = new JLabel(new ImageIcon(imageSun.getScaledInstance(200, 200,
				Image.SCALE_SMOOTH)));
		lblSun.setBounds(203, 293, 244, 228);
		add(lblSun);

		lblCloud = new JLabel(new ImageIcon(imageCloud.getScaledInstance(200,
				200, Image.SCALE_SMOOTH)));
		lblCloud.setBounds(203, 293, 244, 228);
		add(lblCloud);

		lblRain = new JLabel(new ImageIcon(imageRain.getScaledInstance(200,
				200, Image.SCALE_SMOOTH)));
		lblRain.setBounds(203, 293, 244, 228);
		add(lblRain);

		barometer = new Radial2Top();
		barometer.setMinValue(900.0);
		barometer.setUnitString("mb");
		barometer.setForegroundType(ForegroundType.FG_TYPE4);
		barometer.setTitle("Barometer");
		barometer.setBounds(31, 569, 267, 244);
		add(barometer);
		barometer.setMaxValue(1001.0);
		//eu.hansolo.steelseries.gauges.Radial1SquareBeanInfo test3 = new Radial1SquareBeanInfo();
		windSpeed = new RadialBargraph();
		windSpeed.setMinorTickmarkType(TickmarkType.CIRCLE);
		windSpeed.setMajorTickSpacing(1.0);
		windSpeed.setLcdVisible(false);
		windSpeed.setLcdDecimals(1);
		windSpeed.setUnitString("m/s");
		windSpeed.setTitle("Wind speed");
		windSpeed.setTrackSection(1.0);
		windSpeed.setTrackStop(1.0);
		windSpeed.setMaxNoOfMinorTicks(5);
		windSpeed.setMaxValue(4.0);
		windSpeed.setBounds(751, 43, 249, 242);
		add(windSpeed);

		outdoorTemp = new JLabel("\u00B7\u00B7\u00B7");
		outdoorTemp.setForeground(Color.WHITE);
		outdoorTemp.setHorizontalAlignment(SwingConstants.RIGHT);
		outdoorTemp.setFont(new Font("Tahoma", Font.PLAIN, 150));
		outdoorTemp.setBounds(10, 59, 224, 134);
		add(outdoorTemp);

		indoorTemp = new JLabel("\u00B7\u00B7\u00B7");
		indoorTemp.setHorizontalAlignment(SwingConstants.RIGHT);
		indoorTemp.setForeground(Color.WHITE);
		indoorTemp.setFont(new Font("Tahoma", Font.PLAIN, 90));
		indoorTemp.setBounds(477, 112, 131, 109);
		add(indoorTemp);

		outdoorDegree = new JLabel("\u00B0");
		outdoorDegree.setForeground(Color.WHITE);
		outdoorDegree.setFont(new Font("Tahoma", Font.PLAIN, 60));
		outdoorDegree.setBounds(263, 63, 50, 63);
		add(outdoorDegree);

		indoorDegree = new JLabel("\u00B0");
		indoorDegree.setForeground(Color.WHITE);
		indoorDegree.setFont(new Font("Tahoma", Font.PLAIN, 40));
		indoorDegree.setBounds(648, 96, 36, 73);
		add(indoorDegree);

		outdoorTempScale = new JLabel("C");
		outdoorTempScale.setForeground(Color.WHITE);
		outdoorTempScale.setFont(new Font("Tahoma", Font.PLAIN, 60));
		outdoorTempScale.setBounds(292, 46, 36, 97);
		add(outdoorTempScale);

		indoorTempScale = new JLabel("F");
		indoorTempScale.setForeground(Color.WHITE);
		indoorTempScale.setFont(new Font("Tahoma", Font.PLAIN, 40));
		indoorTempScale.setBounds(678, 101, 41, 63);
		add(indoorTempScale);

		outdoorTempDecimal = new JLabel("\u00B7");
		outdoorTempDecimal.setForeground(Color.WHITE);
		outdoorTempDecimal.setFont(new Font("Tahoma", Font.PLAIN, 60));
		outdoorTempDecimal.setBounds(251, 116, 62, 97);
		add(outdoorTempDecimal);

		outdoorTempPoint = new JLabel(".");
		outdoorTempPoint.setForeground(Color.WHITE);
		outdoorTempPoint.setFont(new Font("Tahoma", Font.PLAIN, 60));
		outdoorTempPoint.setBounds(228, 122, 24, 85);
		add(outdoorTempPoint);

		indoorTempPoint = new JLabel(".");
		indoorTempPoint.setForeground(Color.WHITE);
		indoorTempPoint.setFont(new Font("Tahoma", Font.PLAIN, 60));
		indoorTempPoint.setBounds(614, 137, 24, 85);
		add(indoorTempPoint);

		indoorTempDecimal = new JLabel("\u00B7");
		indoorTempDecimal.setForeground(Color.WHITE);
		indoorTempDecimal.setFont(new Font("Tahoma", Font.PLAIN, 60));
		indoorTempDecimal.setBounds(634, 147, 36, 63);
		add(indoorTempDecimal);

		indoorlbl = new JLabel("Indoor");
		indoorlbl.setHorizontalAlignment(SwingConstants.CENTER);
		indoorlbl.setForeground(Color.WHITE);
		indoorlbl.setFont(new Font("Tahoma", Font.PLAIN, 60));
		indoorlbl.setBounds(488, 11, 193, 97);
		add(indoorlbl);

		outdoorTemplbl = new JLabel("Temperature");
		outdoorTemplbl.setForeground(Color.WHITE);
		outdoorTemplbl.setFont(new Font("Tahoma", Font.PLAIN, 30));
		outdoorTemplbl.setBounds(106, 194, 181, 50);
		add(outdoorTemplbl);

		outdoorHumiditylbl = new JLabel("Humidity");
		outdoorHumiditylbl.setForeground(Color.WHITE);
		outdoorHumiditylbl.setFont(new Font("Tahoma", Font.PLAIN, 30));
		outdoorHumiditylbl.setBounds(352, 696, 131, 50);
		add(outdoorHumiditylbl);

		outdoorHumidity = new JLabel("\u00B7\u00B7\u00B7");
		outdoorHumidity.setHorizontalAlignment(SwingConstants.RIGHT);
		outdoorHumidity.setForeground(Color.WHITE);
		outdoorHumidity.setFont(new Font("Tahoma", Font.PLAIN, 90));
		outdoorHumidity.setBounds(319, 618, 147, 103);
		add(outdoorHumidity);

		outdoorHumidityScale = new JLabel("%");
		outdoorHumidityScale.setHorizontalAlignment(SwingConstants.RIGHT);
		outdoorHumidityScale.setForeground(Color.WHITE);
		outdoorHumidityScale.setFont(new Font("Tahoma", Font.PLAIN, 50));
		outdoorHumidityScale.setBounds(476, 653, 50, 73);
		add(outdoorHumidityScale);

		JLabel windDirectionlbl = new JLabel("Direction");
		windDirectionlbl.setHorizontalAlignment(SwingConstants.CENTER);
		windDirectionlbl.setForeground(Color.WHITE);
		windDirectionlbl.setFont(new Font("Tahoma", Font.PLAIN, 30));
		windDirectionlbl.setBounds(769, 474, 131, 50);
		add(windDirectionlbl);

		JSeparator separator = new JSeparator();
		separator.setBackground(Color.LIGHT_GRAY);
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(279, 344, -237, -16);
		add(separator);

		outdoorTemplblBatIndicator = new JLabel("\u25CF");
		outdoorTemplblBatIndicator.setFont(new Font("Arial", Font.PLAIN, 60));
		outdoorTemplblBatIndicator.setBounds(20, 204, 36, 43);
		add(outdoorTemplblBatIndicator);

		indoorlblBatIndicator = new JLabel("\u25CF");
		indoorlblBatIndicator.setFont(new Font("Arial", Font.PLAIN, 50));
		indoorlblBatIndicator.setBounds(658, 163, 36, 43);
		add(indoorlblBatIndicator);

		lblWindBatIndicator = new JLabel("\u25CF");
		lblWindBatIndicator.setFont(new Font("Arial", Font.PLAIN, 50));
		lblWindBatIndicator.setBounds(905, 481, 36, 43);
		add(lblWindBatIndicator);

		lblOutdoorData = new JLabel("\u25B2");
		lblOutdoorData.setForeground(Color.GRAY);
		lblOutdoorData.setFont(new Font("Arial", Font.PLAIN, 50));
		lblOutdoorData.setBounds(52, 204, 59, 50);
		add(lblOutdoorData);

		lblIndoorData = new JLabel("\u25B2");
		lblIndoorData.setForeground(Color.GRAY);
		lblIndoorData.setFont(new Font("Arial", Font.PLAIN, 50));
		lblIndoorData.setBounds(699, 137, 81, 85);
		add(lblIndoorData);
		
		rainGaugeDaily  = new RainGauge();
		

	//	rainGauge.setForeground(Color.GRAY);
		rainGaugeDaily.setBounds(574, 339, 121, 320);
		rainGaugeDaily.setTitle("Daily");
		rainGaugeDaily.setScale("mm");
		rainGaugeDaily.setBase("0");
		rainGaugeDaily.setTop("7");
		add(rainGaugeDaily);

		// lblWindData.setBounds(81, 291, 70, 21);

		lblWindData = new JLabel("\u25B2");
		lblWindData.setForeground(Color.GRAY);
		lblWindData.setFont(new Font("Arial", Font.PLAIN, 50));
		lblWindData.setBounds(932, 475, 50, 55);
		add(lblWindData);
		this.lblOutdoorData.setVisible(false);
		this.outdoorTemplblBatIndicator.setForeground(Color.DARK_GRAY);
		this.lblIndoorData.setVisible(false);
		this.indoorlblBatIndicator.setForeground(Color.DARK_GRAY);
		this.lblWindData.setVisible(false);
		this.lblWindBatIndicator.setForeground(Color.DARK_GRAY);
		
		windDirection = new JLabel("\u00B7\u00B7\u00B7");
		windDirection.setBounds(718, 384, 299, 103);
		add(windDirection);
		windDirection.setHorizontalAlignment(SwingConstants.CENTER);
		windDirection.setForeground(Color.WHITE);
		windDirection.setFont(new Font("Tahoma", Font.PLAIN, 60));
		
		rain = new JLabel("Rain");
		rain.setHorizontalAlignment(SwingConstants.CENTER);
		rain.setForeground(Color.WHITE);
		rain.setFont(new Font("Tahoma", Font.PLAIN, 30));
		rain.setBounds(564, 696, 131, 50);
		add(rain);

		this.setVisible(true);
	}

	public void updateWindSpeed(double speedValue) {
		double speed = 0;
		speed = speedValue;
		this.windSpeed.setValue(speed);
		//DataTimer dataTimer = new DataTimer(this.lblWindData);
		new DataTimer(this.lblWindData);
	}

	public void updateWindDirection(String direction) {
		this.windDirection.setText(direction);
	}

	public void updateIndoorTemp(float temp) {
		this.indoorTemp.setText(String.valueOf(this.getIntFromFloat(temp)));
		this.indoorTempDecimal.setText(String.valueOf(this.getIntFromDecPlace(
				1, temp)));
		//DataTimer dataTimer = new DataTimer(this.lblIndoorData);
		new DataTimer(this.lblIndoorData);
	}

	public void updateOutdoorTemp(float temp) {
		this.outdoorTemp.setText(String.valueOf(this.getIntFromFloat(temp)));
		this.outdoorTempDecimal.setText(String.valueOf(this.getIntFromDecPlace(
				1, temp)));
		new DataTimer(this.lblOutdoorData);
		// DataTimer dataTimer = new DataTimer(this.lblOutdoorData);
	}

	public void updateIndoorBattery(int battery) {
		switch (battery) {
		case 0:
			this.indoorlblBatIndicator.setForeground(Color.GREEN);
			break;
		case 1:
			this.indoorlblBatIndicator.setForeground(Color.YELLOW);
			break;
		}
	}

	public void updateOutdoorBattery(int battery) {
		switch (battery) {
		case 0:
			this.outdoorTemplblBatIndicator.setForeground(Color.GREEN);
			break;
		case 1:
			this.outdoorTemplblBatIndicator.setForeground(Color.YELLOW);
			break;
		}
	}

	public void updateWindBattery(int battery) {
		switch (battery) {
		case 0:
			this.lblWindBatIndicator.setForeground(Color.GREEN);
			break;
		case 1:
			this.lblWindBatIndicator.setForeground(Color.YELLOW);
			break;
		}
	}

	public void updateOutdoorHumidity(int humidity) {
		this.outdoorHumidity.setText(String.valueOf(humidity));
	}

	public void updateBarometer(double value) {
		barometer.setValue(value);
	}

	public void updateForecast(int value) {
		// reference from WXLoggerDefinitions:
		// public static final String[] WEATHER_DESCRIPTION = {
		// "Partly Cloudy", "Rainy", "Cloudy", "Sunny", "?", "Snowy"
		// };
		// weatherCode < WXLoggerDefinitions.WEATHER_DESCRIPTION.length
		// ? WXLoggerDefinitions.WEATHER_DESCRIPTION[weatherCode]
		// : "Unknown");
		this.lblCloud.setVisible(false);
		this.lblRain.setVisible(false);
		this.lblSun.setVisible(false);

		switch (value) { // check sensor code
		case 0:
			System.out.println("add image partly cloudy");
			break;
		case 1:
			this.lblRain.setVisible(true);
			break;
		case 2:
			this.lblCloud.setVisible(true);
		case 3:
			this.lblSun.setVisible(true);

			break;
		case 4:
			System.out.println("unknown");
			break;
		case 5:
			System.out.println("add image snowy");
			break;
		}

	}

	

	public void setOutdoorTempCurrent() {
		this.outdoorDegree.setForeground(Color.WHITE);
		this.outdoorTemp.setForeground(Color.WHITE);
		this.outdoorTempDecimal.setForeground(Color.WHITE);
		this.outdoorTempPoint.setForeground(Color.WHITE);
		this.outdoorTempScale.setForeground(Color.WHITE);
		this.outdoorTemplbl.setForeground(Color.WHITE);	
	//	this.outdoorTemplblBatIndicator.setVisible(true);

	}

	public void setOutdoorTempOutOfDate() {
		this.outdoorDegree.setForeground(Color.GRAY);
		this.outdoorTemp.setForeground(Color.GRAY);
		this.outdoorTempDecimal.setForeground(Color.GRAY);
		this.outdoorTempPoint.setForeground(Color.GRAY);
		this.outdoorTempScale.setForeground(Color.GRAY);
		this.outdoorTemplbl.setForeground(Color.GRAY);
//		this.outdoorTemplblBatIndicator.setVisible(false);
	}
	
	public void setIndoorTempCurrent() {
		this.indoorDegree.setForeground(Color.WHITE);
		this.indoorlbl.setForeground(Color.WHITE);
		this.indoorTemp.setForeground(Color.WHITE);
		this.indoorTempDecimal.setForeground(Color.WHITE);
		this.indoorTempPoint.setForeground(Color.WHITE);
		this.indoorTempScale.setForeground(Color.WHITE);
	}
	
	public void setIndoorTempOutOfDate() {
		this.indoorDegree.setForeground(Color.GRAY);
		this.indoorlbl.setForeground(Color.GRAY);
		this.indoorTemp.setForeground(Color.GRAY);
		this.indoorTempDecimal.setForeground(Color.GRAY);
		this.indoorTempPoint.setForeground(Color.GRAY);
		this.indoorTempScale.setForeground(Color.GRAY);
	}

	public void setOutdoorHumidityCurrent() {
		this.outdoorHumidity.setForeground(Color.WHITE);
		this.outdoorHumidityScale.setForeground(Color.WHITE);
		this.outdoorHumiditylbl.setForeground(Color.WHITE);
	}
	public void setOutdoorHumidityOutOfDate() {
		this.outdoorHumidity.setForeground(Color.GRAY);
		this.outdoorHumidityScale.setForeground(Color.GRAY);
		this.outdoorHumiditylbl.setForeground(Color.GRAY);
	}


	private int getIntFromFloat(float value) {
		return (int) Math.floor(value);
	}

	private int getIntFromDecPlace(int decPlace, float value) {
		return ((int) (Math.floor((value) * (10 * decPlace))) % 10);
	}

	/*
	 * class xDataTimer implements Runnable { JLabel label;
	 * 
	 * public xDataTimer(JLabel label) { this.label = label; }
	 * 
	 * public void run() { this.label.setVisible(true);
	 * System.out.println("set true: " + this.label.getName());
	 * 
	 * try { this. sleep(1); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * this.label.setVisible(false);
	 * 
	 * 
	 * } }
	 */

	class DataTimer {
		public DataTimer(JLabel outlabel) {
			String name = "test";
			setInner(new Inner(outlabel, name));
		}

		public Inner getInner() {
			return inner;
		}

		public void setInner(Inner inner) {
			this.inner = inner;
		}

		private int countDown = 5;

		private Inner inner;

		private class Inner implements Runnable {
			Thread t;
			JLabel finalLabel;

			Inner(JLabel inLabel, String name) {
				t = new Thread(this, name);
				finalLabel = inLabel;
				t.start();
			}

			public void run() {
				this.finalLabel.setVisible(true);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				this.finalLabel.setVisible(false);
			}

			public String toString() {
				return t.getName() + ": " + countDown;
			}
		}

	}
}

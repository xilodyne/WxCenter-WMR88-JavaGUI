package com.xilosilo.wxcenter.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import xilodyne.wxcenter.devices.xml.jaxb.WXAnemometer;
import xilodyne.wxcenter.devices.xml.jaxb.WXBarometer;
import xilodyne.wxcenter.devices.xml.jaxb.WXClock;
import xilodyne.wxcenter.devices.xml.jaxb.WXRainGauge;
import xilodyne.wxcenter.devices.xml.jaxb.WXThermohygrometer;
import xilodyne.wxcenter.globals.Definitions;
import xilodyne.wxcenter.logging.WxLogging;

public class DeviceFromJAXB {

	String urlDataServlet;
	String file;
	MyHTTPConnection webConnection;

	public DeviceFromJAXB(String url) {
		this.urlDataServlet = url;
	}

	public void connectServer() {
		String resource, host, port, hostport;
		int slashPos, colonPos;

		boolean https = this.urlDataServlet.startsWith("https");

		if (https) {
			resource = this.urlDataServlet.substring(8);

		} else {
			resource = this.urlDataServlet.substring(7);

		}
		// resource = "http://10.232.49.202/WxCenterServer/WxCenterGetData"
		// .substring(7); // skip HTTP://
		slashPos = resource.indexOf('/');
		if (slashPos < 0) {
			resource = resource + "/";
			slashPos = resource.indexOf('/');
		}
		file = resource.substring(slashPos); // isolate host and file parts
		hostport = resource.substring(0, slashPos);

		colonPos = hostport.indexOf(':');
		if (colonPos > -1) {
			port = hostport.substring(colonPos + 1);
			host = hostport.substring(0, colonPos);

		} else { // no port found, default
			host = hostport;
			// check https
			if (https) {
				port = "443";
			} else {
				port = "80";
			}
		}
		Definitions.updateCheckServerAvailable(true);
		WxLogging.toConsole(WxLogging.callEmpty, "Host to contact: '" + host
				+ "'");
		WxLogging.toConsole(WxLogging.callEmpty, "  File to fetch: '" + file
				+ "'");
		WxLogging.toConsole(WxLogging.callEmpty, "      HTTP Port: '" + port
				+ "'");
		try {
			webConnection = new MyHTTPConnection(host, port);
		} catch (UnknownHostException e) {
			WxLogging.toConsole(WxLogging.callEmpty, "Connection failed.");
			WxLogging.toConsole(WxLogging.callEmpty, e.getLocalizedMessage());
			Definitions.checkServerAvailable = false;
		}

	}

	public void updateDevices(WXBarometer barm, WXAnemometer wind,
			WXThermohygrometer tempIndoor, WXThermohygrometer tempOutdoor,
			WXRainGauge rain, WXClock clock) throws IOException, UnknownHostException,
			JAXBException, ConnectException {
		StringBuffer result = new StringBuffer();
		if (webConnection != null) {
			BufferedReader in = webConnection.get(file);
			// result = webConnection.get(file);
			String line;

			WxLogging.toConsole(WxLogging.callJAXBget,
					"Buffer in: " + in.toString());

			// System.out.println(in.toString());
			// extractObject(in);

			while ((line = in.readLine()) != null) { // read until EOF //
				result.append(line); //
			}
			WxLogging.toConsole(WxLogging.callJAXBget,
					"Result: " + result.toString());

	//		System.setSecurityManager(null);
			//http://web.securityinnovation.com/appsec-weekly/blog/bid/72451/Define-a-Security-Policy
	//		if (System.getSecurityManager() == null)
	//		{
	//		      System.setSecurityManager(new SecurityManager());
	//		}
			JAXBContext jc = JAXBContext.newInstance(WXAnemometer.class,
					WXBarometer.class, WXThermohygrometer.class,
					WXThermohygrometer.class, 
					WXRainGauge.class, WXClock.class);
			Unmarshaller u = jc.createUnmarshaller();
			u.setEventHandler(new ValidationEventHandler() {
				public boolean handleEvent(ValidationEvent event) {
					throw new RuntimeException(event.getMessage(), event
							.getLinkedException());
				}
			});
			String xml = result.toString();
			String[] xmlArray = xml.split("<\\?xml");

			WxLogging.toConsole(WxLogging.callEmpty, "Array 0: " + xmlArray[0]);
			WxLogging.toConsole(WxLogging.callEmpty, "Array 1: " + xmlArray[1]);
			WxLogging.toConsole(WxLogging.callEmpty, "Array 2: " + xmlArray[2]);
			WxLogging.toConsole(WxLogging.callEmpty, "Array 3: " + xmlArray[3]);
			WxLogging.toConsole(WxLogging.callEmpty, "Array 4: " + xmlArray[4]);
			WxLogging.toConsole(WxLogging.callEmpty, "Array 5: " + xmlArray[5]);
			WxLogging.toConsole(WxLogging.callEmpty, "Array 5: " + xmlArray[6]);

			WXAnemometer windlocal = (WXAnemometer) u
					.unmarshal(new StringReader("<?xml" + xmlArray[1]));
			windlocal.printValues();
			wind.loadWind(windlocal);

			WXBarometer barmlocal = (WXBarometer) u.unmarshal(new StringReader(
					"<?xml" + xmlArray[2]));
			barmlocal.printValues();
			barm.loadBarm(barmlocal);

			WXThermohygrometer templocal = (WXThermohygrometer) u
					.unmarshal(new StringReader("<?xml" + xmlArray[3]));
			templocal.printValues();
			tempIndoor.loadTemp(templocal);

			WXThermohygrometer templocal2 = (WXThermohygrometer) u
					.unmarshal(new StringReader("<?xml" + xmlArray[4]));
			templocal2.printValues();
			tempOutdoor.loadTemp(templocal2);

			WXRainGauge raintemp = (WXRainGauge) u.unmarshal(new StringReader("<?xml"
					+ xmlArray[5]));
			raintemp.printValues();
			rain.loadRain(raintemp);

			WXClock clocktemp = (WXClock) u.unmarshal(new StringReader("<?xml"
					+ xmlArray[6]));
			clocktemp.printValues();
			clock.loadClock(clocktemp);

			Definitions.checkServerAvailable = true;

		}
		// System.out.println("\nDone.");
	}

}

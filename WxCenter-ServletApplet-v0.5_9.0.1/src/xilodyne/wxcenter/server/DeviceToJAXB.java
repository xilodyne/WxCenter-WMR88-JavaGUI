package xilodyne.wxcenter.server;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import xilodyne.wxcenter.logging.WxLogging;
import xilodyne.wxcenter.servlet.WxCenterServlet;
import xilodyne.wxcenter.devices.xml.jaxb.WXAnemometer;
import xilodyne.wxcenter.devices.xml.jaxb.WXBarometer;
import xilodyne.wxcenter.devices.xml.jaxb.WXClock;
import xilodyne.wxcenter.devices.xml.jaxb.WXRainGauge;
import xilodyne.wxcenter.devices.xml.jaxb.WXThermohygrometer;

public class DeviceToJAXB {

	public static String createMultiObject(PrintWriter out) {
		StringBuffer buffer = new StringBuffer();

		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			JAXBContext jaxbContext = JAXBContext.newInstance(
					WXAnemometer.class, WXBarometer.class, 
					WXThermohygrometer.class, 
					WXThermohygrometer.class,
					WXRainGauge.class,
					WXClock.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(WxCenterServlet.wind, os);
			jaxbMarshaller.marshal(WxCenterServlet.barm, os);
			jaxbMarshaller.marshal(WxCenterServlet.tempIndoor, os);
			jaxbMarshaller.marshal(WxCenterServlet.tempOutdoor, os);
			jaxbMarshaller.marshal(WxCenterServlet.rain, os);
			jaxbMarshaller.marshal(WxCenterServlet.clock, os);
					
			buffer.append(os.toString());
			
			WxLogging.toConsole(WxLogging.callEmpty , 
					"Assembed: " + os.toString() );

		} catch (JAXBException e) {
			// out.println(e.printStackTrace());
			e.printStackTrace();
		}
		return buffer.toString();
	}
}

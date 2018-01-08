package xilodyne.wxcenter.servlet;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xilodyne.wxcenter.devices.server.Stack;
import xilodyne.wxcenter.logging.WxLogging;
import xilodyne.wxcenter.server.DeviceToJAXB;
import xilodyne.wxcenter.devices.server.ProcessMessage;
import xilodyne.wxcenter.devices.server.UDPServer;
import xilodyne.wxcenter.devices.xml.jaxb.WXAnemometer;
import xilodyne.wxcenter.devices.xml.jaxb.WXBarometer;
import xilodyne.wxcenter.devices.xml.jaxb.WXClock;
import xilodyne.wxcenter.devices.xml.jaxb.WXRainGauge;
import xilodyne.wxcenter.devices.xml.jaxb.WXThermohygrometer;


/**
 * Servlet implementation class WxCenter
 */
@WebServlet(description = "Entry point for downloading wxcenter applet", urlPatterns = { "/WxCenter" })

public class WxCenterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static UDPServer udpserver = new UDPServer();
	static WxLogging myLogger = new WxLogging(WxLogging.sourceServer);
	public static WXAnemometer wind = new WXAnemometer();
	public static WXBarometer barm = new WXBarometer();
	public static WXThermohygrometer tempIndoor = new WXThermohygrometer();
	public static WXThermohygrometer tempOutdoor = new WXThermohygrometer();
	public static WXRainGauge rain = new WXRainGauge();
	public static WXClock clock = new WXClock();
	


	static Stack stack = new Stack();
	static DeviceToJAXB devices = new DeviceToJAXB();
	static ProcessMessage procmessage = new ProcessMessage(wind, barm, tempIndoor, tempOutdoor, rain, clock);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WxCenterServlet() {
		super();
		WxLogging.toConsole(WxLogging.callEmpty , 
				"Starting Servlet WxCenterServlet");
		WxCenterServlet.startThreads();
	}

	private static void startThreads() {
		new Thread(udpserver).start();
		new Thread(procmessage).start();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();

		WxLogging.toConsole(WxLogging.callEmpty , 
				"Starting doGet");



		out.println("<html><head><title>WxCenter</title></head>");

		out.println("<BODY>");
		/*
		 * out.println("Started XXXX..................");
		 * out.println("<CENTER><B>Applet JAR Example</B>");
		 * out.println("<BR>"); out.println(
		 * "<APPLET CODE=\"WxCenterApplet.class\" ARCHIVE=\"WxCenterApplet.jar\" WIDTH=400 HEIGHT=300>"
		 * ); out.println("Your browser is not Java enabled.");
		 * out.println("</APPLET>"); out.println("</CENTER>");
		 */
		// out.println("<APPLET archive=\"WxCenterApp.jar\" code=\"xilodyne.wxcenter.gui.WxCenterApp.class\" width=288 height=288></APPLET>")
/*
		out.println("<script src=\"http://java.com/js/deployJava.js\"></script>");
		out.println("<script>");
		out.println("  var attributes = {codebase:'http://java.sun.com/products/plugin/1.5.0/demos/jfc/Java2D',");
		out.println("                   code:'java2d.Java2DemoApplet.class',");
		out.println("                   archive:'Java2Demo.jar',");
		out.println("                   width:710, height:540} ;");
		out.println(" var parameters = {fontSize:16} ;");
		out.println(" var version = '1.6' ;");
		out.println(" deployJava.runApplet(attributes, parameters, version);");
		out.println("</script>");
	*/	
		
/*		out.println("<script src=\"http://java.com/js/deployJava.js\"></script>");
		out.println("<script>");
		out.println("  var attributes = {codebase:'http://java.sun.com/products/plugin/1.5.0/demos/jfc/Java2D',");
		out.println("                   code:'java2d.Java2DemoApplet.class',");
		out.println("                   archive:'Java2Demo.jar',");
		out.println("                   width:710, height:540} ;");
		out.println(" var parameters = {fontSize:16} ;");
		out.println(" var version = '1.6' ;");
		out.println(" deployJava.runApplet(attributes, parameters, version);");
		out.println("</script>");

		out.println("</BODY>");
		out.println("</html>");
*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


}

package xilodyne.wxcenter.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xilodyne.wxcenter.server.DeviceToJAXB;

/**
 * Servlet implementation class WxCenterGetData
 */
@WebServlet(description = "Sends out JAXB data", urlPatterns = { "/WxCenterGetData" })
public class WxCenterGetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//static DeviceToJAXB devices = new DeviceToJAXB(); 

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WxCenterGetData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    PrintWriter out = response.getWriter();
	    System.out.println("doGet..................");
	    
	    
//	    out.println(devices.createObject(out));
//	    out.println(devices.createMultiObject(out));
	    out.println(DeviceToJAXB.createMultiObject(out));
//	    devices.createMultiObjectFile(out);
	    
		    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    PrintWriter out = response.getWriter();
	    System.out.println("doPost..................");
	    out.println(DeviceToJAXB.createMultiObject(out));

	}

}

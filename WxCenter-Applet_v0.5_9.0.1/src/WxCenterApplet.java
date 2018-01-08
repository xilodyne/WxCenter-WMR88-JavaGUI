import java.net.MalformedURLException;
import java.net.URL;
import java.security.Permission;

import javax.swing.JApplet;

import com.xilosilo.wxcenter.data.UpdateManager;
import xilodyne.wxcenter.gui.Controller;
import xilodyne.wxcenter.gui.Model;
import xilodyne.wxcenter.gui.View;
import xilodyne.wxcenter.logging.WxLogging;

public class WxCenterApplet extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static WxLogging myLogger = new WxLogging(WxLogging.sourceApplet);

	final int radius = 25;
	public static UpdateManager updateManager;
	Model model = new Model();
	View view = new View(model, this.rootPane);
	Controller controller = new Controller(model, view);
	URL url;
	String urlDataServlet = "WxCenterGetData";

	public void init() {
		
//		SecurityManager sm = new MySecurityManager();
//		System.setSecurityManager(sm);
//		System.setSecurityManager(null);
		try {
			url = new URL(getCodeBase().toString());
			// URL: http://10.232.49.202:8080/WxCenterServer/
			// URL: file:/C:/Backup/Projects/Web/WxCenterApplet/bin/

			// if URL = code base then give it dev tomcat server
			if (url.toString().startsWith("file")) {
	//			url = new URL("http://10.232.49.202:8080/WxCenterServer/");
				url = new URL("http://localhost:8080/WxCenterServer/");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		WxLogging.toConsole(WxLogging.callEmpty , 
				"URL: " + url.toString());
		updateManager = new UpdateManager(controller, url.toString()
				+ this.urlDataServlet);

		startThreads();

	}

	public void start() {
		// SecurityManager sm = new MySecurityManager();
		// System.setSecurityManager(sm);
	}

	public void stop() {

	}

	public void destory() {

	}

	private static void startThreads() {
		new Thread(updateManager).start();
	}

	/*
	 * //http://stackoverflow.com/questions/11307206/java-security-
	 * accesscontrolexception-access-denied-executing-a-signed-java-ap private
	 * class MySecurityManager extends SecurityManager {
	 * 
	 * @Override public void checkPermission(Permission perm) { return; } }
	 */
}

//class MySecurityManager extends SecurityManager {
//    @Override
//    public void checkPermission(Permission perm) {
//        return;
//    }
//}

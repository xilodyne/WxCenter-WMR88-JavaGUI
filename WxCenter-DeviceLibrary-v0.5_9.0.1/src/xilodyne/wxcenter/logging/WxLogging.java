package xilodyne.wxcenter.logging;



import java.text.SimpleDateFormat;
import java.util.Date;

public class WxLogging {
	
	public static String source = "";
	public final static String sourceServer = "SVR";	//tomcat
	public final static String sourceApplet = "APL";	//applet
	public final static String sourceGUI = "GUI";	//stand-alone GUI application
	
	public final static String callUDP =  "UDP_rec";
	public final static String callREQ =   "svr_req";
	public final static String callUpdate = "update ";
	public final static String callEmpty = "       ";
	public final static String callJAXBget = "JAX_get";
	public final static String callJAXBput = "JAX_put";
	
	private static Date myDate;
	private static SimpleDateFormat sdf, sdfsecond;
	private static String dateString;

	
	public WxLogging(String mySource) {
		WxLogging.source = mySource;
		WxLogging.updateDate();
	}
	
	public static void toConsole(String call, String message) {
		dateString = sdf.format(myDate);
		
		System.out.print("[" +dateString + "]");
		System.out.print("[" + WxLogging.source +" " + call +"] ");
		System.out.print(message + " xxx");
		System.out.print(ClassLocator.getCallerClass().getName() + " ");
		System.out.println();
	}
	public static Date getTimeStamp() {
		updateDate();
		return myDate;
	}
	
	public static long getCurrentTime(){
	//	updateDate();
	//	return myDate.getTime();
		return System.currentTimeMillis();
	//	return sdf.format(myDate);
	}
	
	public static String getSecond(){
		return sdfsecond.format(myDate);
	}

	private static void updateDate() {
		myDate = new Date();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdfsecond = new SimpleDateFormat("ss");

	}
}
class ClassLocator extends SecurityManager {
	public static Class<?> getCallerClass() {
		return new ClassLocator().getClassContext()[2];
	}

	public static String getCallerInfo() {
		String callerInfo = "DefaultName-callerUnknown";
		// use class name instead of filename since filename is null for
		// Sun/Java
		// classes when running in a JRE-only context
		String thisClassName = "org.stirling.stackinfo.CallerInfo";
		Thread thread = Thread.currentThread();
		StackTraceElement[] framesArray = thread.getStackTrace();
		// look for the last stack frame from this class and then whatever is
		// next
		// is the caller we want to know about
		for (StackTraceElement stackFrame : framesArray) {
			// filter out Thread because we just created a couple frames using
			// Thread
			if (!stackFrame.getClassName().equals("java.lang.Thread")
					&& !stackFrame.getClassName().equals(thisClassName)) {
				// handle case for file name when debug info is missing from
				// classfile
				String fileName = stackFrame.getFileName() != null ? stackFrame.getFileName() : "Unknown";
				StringBuilder sb = new StringBuilder(stackFrame.getMethodName());
				sb.append('(');
				sb.append(fileName);
				sb.append(':');
				// if no debug info, returns a negative number
				sb.append(stackFrame.getLineNumber());
				sb.append(')');
				callerInfo = sb.toString();
				break;
			}
		}
		return callerInfo;
	}

}

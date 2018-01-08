package xilodyne.wxcenter.globals;

import java.util.Random;

public class Definitions {

	// public final static String BAROMETER_FRAME_HEX="0x46";
	public final static int FRAME_ANEMOMETER = 0x48;
	public final static int FRAME_BAROMETER = 0x46;
	public final static int FRAME_THERMOHYGROMETER = 0x42;
	public final static int FRAME_CLOCK = 0x60;
	public final static int FRAME_RAINFALL = 0x41;

	public static Random random = new Random();

	public static boolean checkServerAvailable = false;

	/** Current century */
	public static final int CENTURY = 2000;

	public static void updateCheckServerAvailable(boolean value) {
		checkServerAvailable = value;
	}

}

package com.dabinci.os;

import net.rim.device.api.system.DeviceInfo;

import com.dabinci.utils.DLogger;

public class DabinciOSUtil {
	private static final String TAG = "DabinciOSUtil";
	private static DabinciOS instance;

	static {
		if (isUpperOsVersion(7))
			try {
				DLogger.log(TAG, "com.dabinci.os.DabinciOS7");
				Class c = Class.forName("com.dabinci.os.DabinciOS7");
				instance = (DabinciOS) c.newInstance();
			} catch (ClassNotFoundException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			} catch (NoClassDefFoundError e) {
			}
		
		if (isUpperOsVersion(6) && instance == null)
			try {
				DLogger.log(TAG, "com.dabinci.os.DabinciOS6");
				Class c = Class.forName("com.dabinci.os.DabinciOS6");
				instance = (DabinciOS) c.newInstance();
			} catch (ClassNotFoundException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			} catch (NoClassDefFoundError e) {
			}
		
		DLogger.log(TAG, "com.dabinci.os.DabinciOS5");
		if (instance == null)
			instance = new DabinciOS5();
	}
	
	public static DabinciOS getInstance() {
		return instance;
	}
	
	public static boolean isUpperOsVersion(int version) {
		String mainVersion = DeviceInfo.getSoftwareVersion().substring(0, 1);
		int mainV = Integer.parseInt(mainVersion);
		
		if (mainV >= version) {
			return true;
		}
		
		return false;
	}
}

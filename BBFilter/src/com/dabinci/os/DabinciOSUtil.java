package com.dabinci.os;

public class DabinciOSUtil {
	private static DabinciOS instance;

	static {
		try {
			Class c = Class.forName("com.dabinci.os.DabinciOS7");
			instance = (DabinciOS) c.newInstance();
		} catch (Exception e) {
		}
		
		if (instance == null)
			try {
				Class c = Class.forName("com.dabinci.os.DabinciOS6");
				instance = (DabinciOS) c.newInstance();
			} catch (Exception e) {
			}
		
		if (instance == null)
			instance = new DabinciOS5();
	}
	
	public static DabinciOS getInstance() {
		return instance;
	}
}

package com.dabinci.ui;

import java.util.Hashtable;

public class DColor {
	public static Hashtable colors = new Hashtable();

	public static final String COLOR_BG = "COLOR_BG";
	public static final String COLOR_SELECTED_BG = "COLOR_SELECTED_BG";
	
	static {
		colors.put(COLOR_BG, new Integer(0x2c435e));
		colors.put(COLOR_SELECTED_BG, new Integer(0x426082));
	}
	
	public static int getColor(final String key) {
		Integer i = (Integer) colors.get(key);
		return i.intValue();
	}
	
}

package com.dabinci.ui;

import java.util.Hashtable;

public class DColor {
	public static Hashtable colors = new Hashtable();

	public static final String COLOR_BG = "COLOR_BG";
	public static final String COLOR_SELECTED_BG = "COLOR_SELECTED_BG";
	public static final String COLOR_BG_GREY = "COLOR_BG_GREY";
	public static final String COLOR_LINE_GREY = "COLOR_LINE_GREY";
	
	static {
		colors.put(COLOR_BG, new Integer(0x2c435e));
		colors.put(COLOR_SELECTED_BG, new Integer(0x426082));
		colors.put(COLOR_BG_GREY, new Integer(0xf2f2f2));
		colors.put(COLOR_LINE_GREY, new Integer(0xe1e1e1));
	}
	
	public static int getColor(final String key) {
		Integer i = (Integer) colors.get(key);
		return i.intValue();
	}
	
}

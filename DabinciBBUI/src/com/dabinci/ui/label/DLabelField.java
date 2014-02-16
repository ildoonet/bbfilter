package com.dabinci.ui.label;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.LabelField;

import com.dabinci.ui.DResolution;

public class DLabelField extends LabelField {
	
	public final static int COLOR_WHITE = 0xffffff;
	public final static int COLOR_BLACK = 0x000000;
	public final static int COLOR_BROWN = 0x543F3E;
	public final static int COLOR_YELLOW = 0xffe400;
	public final static int COLOR_BUTTON = 0x222222;
	public final static int COLOR_DESC = 0x3abcb9;
	public final static int COLOR_DESC2 = 0x737147;
	public final static int COLOR_DESC_SETTING = 0x999999;
	public final static int COLOR_DESC_SETTING2 = 0x493334;
	
	public final static int FONT_SIZE_TITLE = DResolution.getFontHeight(11);
	public final static int FONT_SIZE_BUTTON = DResolution.getFontHeight(14);
	public final static int FONT_SIZE_DESC = DResolution.getFontHeight(13);
	public final static int FONT_SIZE_DESC_SETTING = DResolution.getFontHeight(12);
	public final static int FONT_SIZE_DESC_SMALL = DResolution.getFontHeight(11);
	public final static int FONT_SIZE_TAB = DResolution.getFontHeight(15);
	public final static int FONT_SIZE_REG_TITLE = DResolution.getFontHeight(16);
	
	private final static int MARGIN_DEFAULT = DResolution.getPixel(8);
	
	private int color;
	private int fixedWidth;
	
	public static DLabelField newInstance(String text, long style) {
		return new DLabelField(text, style);
	}
	
	public static DLabelField newInstance(String text) {
		return new DLabelField(text, Field.FOCUSABLE);
	}
	
	public DLabelField(String text, long style) {
		super(text, style);
	}
	
	public DLabelField setColor(int color) {
		this.color = color;
		return this;
	}
	
	public DLabelField setFontSize(int fontSize) {
		setFont(getFont().derive(getFont().getStyle(), fontSize));
		return this;
	}
	
	public DLabelField setFixedWidth(int width) {
		fixedWidth = width;
		return this;
	}
	
	public int getFixedWidth() {
		return fixedWidth;
	}
	
	protected void layout(int width, int height) {
		if (fixedWidth > 0) {
			super.layout(fixedWidth, height);
			setExtent(fixedWidth, getHeight());
		} else {
			super.layout(width, height);
		}
	}
	
	protected void paint(Graphics graphics) {
		if (isFocusable() == true) {
			if (getManager().isFocus() == false) {
				graphics.setColor(color);
			} else { 
				graphics.setColor(COLOR_WHITE);
			}
		} else {
			graphics.setColor(color);
		}
		super.paint(graphics);
	}
	
	public DLabelField setDefaultMargin() {
		setMargin(MARGIN_DEFAULT, MARGIN_DEFAULT, MARGIN_DEFAULT, MARGIN_DEFAULT);
		return this;
	}
	
}

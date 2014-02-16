package com.dabinci.ui.screen;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.VirtualKeyboard;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

import com.dabinci.ui.DColor;

public class DMainScreen extends MainScreen {
	public final static int BG_GREY = DColor.getColor(DColor.COLOR_BG_GREY);
	private static Border defaultBorder;
	
	private DTitleBar titleBar;
	
	public DMainScreen() {
		this(BG_GREY);
	}
	
	public DMainScreen(long style) {
		this(BG_GREY, style);
	}
	
	public DMainScreen(int bgColor) {
		this(bgColor, 0l);
	}
	
	public DMainScreen(int bgColor, long style) {
		super(style);
		setBackground(bgColor);		
	}
	
	public void setTitle(String title) {
		setTitle(title, null, null);
	}
	
	public void setTitle(String title, String desc, Field rightField) {
		setBanner(titleBar = new DTitleBar(title, desc, rightField));
	}
	
	public void setTitle(String title, String desc, Field rightField, int type) {
		setBanner(titleBar = new DTitleBar(title, desc, rightField, type));
	}
	
	public void setTitle(Bitmap icon, String title, String desc, Field rightField) {
		setBanner(titleBar = new DTitleBar(icon, title, desc, rightField));
	}
	
	public void setTitle(Bitmap icon, String title, String desc, Field rightField, int type) {
		setBanner(titleBar = new DTitleBar(icon, title, desc, rightField, type));
	}

	
	public DTitleBar getTitle() {
		return titleBar;
	}
	
	public void setBackground(final int bgColor) {
		Background b = BackgroundFactory.createSolidBackground(bgColor);
		setBackground(b);
		getMainManager().setBackground(b);
	}
	
	public Border getDefaultFieldBorder() {
		if (defaultBorder == null) {
			final int BORDER_COLOR = 0xe6db2b;
			defaultBorder = BorderFactory.createSimpleBorder(new XYEdges(1,1,1,1), 
					new XYEdges(BORDER_COLOR,BORDER_COLOR,BORDER_COLOR,BORDER_COLOR), Border.STYLE_SOLID);
		}
		
		return defaultBorder;
	}
	
	protected void setVirtualKeyboardVisible(boolean isShow) {
		if (!VirtualKeyboard.isSupported()) {
			return;
		}
		
		VirtualKeyboard keyboard = getVirtualKeyboard();
		if (keyboard == null) {
			return;
		}
		
		if (isShow) {
			keyboard.setVisibility(VirtualKeyboard.SHOW);
		} else {
			keyboard.setVisibility(VirtualKeyboard.HIDE);
		}
	}
	
	protected boolean onSavePrompt() {
		return true;
	}
	
	protected boolean onSave() {
		return true;
	}
}

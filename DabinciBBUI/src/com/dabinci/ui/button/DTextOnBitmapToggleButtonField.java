package com.dabinci.ui.button;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.dabinci.ui.DColor;
import com.dabinci.ui.DResolution;

public class DTextOnBitmapToggleButtonField extends DTextButtonField {

	private Bitmap bg;
	private volatile boolean toggled;
	private onChangeToggleStatus listener;
	
	private int prevTextColor;
	
	public DTextOnBitmapToggleButtonField(String text, Bitmap bg) {
		super(text, DTextButtonField.TYPE_GREY);
		
		this.bg = bg;
		
		setBackground(BackgroundFactory.createBitmapBackground(bg,
						Background.POSITION_X_CENTER,
						Background.POSITION_Y_CENTER, 
						Background.REPEAT_NONE));
		
		setBackground(Field.VISUAL_STATE_FOCUS, BackgroundFactory.createSolidBackground(DColor.getColor(DColor.COLOR_SELECTED_BG)));
		setHeight(bg.getHeight());
		setFontSize(DResolution.getFontHeight(16));
	}

	protected void layout(int maxWidth, int maxHeight) {
		super.layout(bg.getWidth(), bg.getHeight());
	}
	
	protected void paint(Graphics graphics) {
		if (toggled == false)
			super.paint(graphics);
		else {
			paintBackground(graphics);
		}
	}
	
	protected boolean navigationClick(int status, int time) {
		toggleStatus();
		if (listener != null) {
			listener.onChangeToggleStatus(toggled);
		}
		
		return super.navigationClick(status, time);
	}
	
	public void setToggleListener(onChangeToggleStatus listener) {
		this.listener = listener;
	}
	
	private void toggleStatus() {
		toggled = !toggled;
		
		if (toggled) {
			// toggle off for others
			Manager m = getManager();
			for (int i = 0; m != null && i < m.getFieldCount(); i ++) {
				if (m.getField(i) instanceof DTextOnBitmapToggleButtonField && m.getField(i) != this) {
					((DTextOnBitmapToggleButtonField) m.getField(i)).toggleOff();
				}
			}
			
			// toggle on
			prevTextColor = this.textColor;
			this.textColor = Color.WHITE;
			setBackground(BackgroundFactory.createSolidBackground(Color.BLACK));
		} else {
			this.textColor = prevTextColor;
			setBackground(BackgroundFactory.createBitmapBackground(bg,
					Background.POSITION_X_CENTER,
					Background.POSITION_Y_CENTER, 
					Background.REPEAT_NONE));
			setBackground(Field.VISUAL_STATE_FOCUS, BackgroundFactory.createSolidBackground(DColor.getColor(DColor.COLOR_SELECTED_BG)));
		}
	}
	
	public void toggleOff() {
		System.out.println("toggleoff");
		if (toggled == true)
			toggleStatus();
	}
	
	public interface onChangeToggleStatus {
		public void onChangeToggleStatus(boolean toggled);
	}
}

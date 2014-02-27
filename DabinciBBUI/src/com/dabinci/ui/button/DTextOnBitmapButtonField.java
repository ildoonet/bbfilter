package com.dabinci.ui.button;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.dabinci.ui.DColor;
import com.dabinci.ui.DRes;

public class DTextOnBitmapButtonField extends DTextButtonField {

	public DTextOnBitmapButtonField(String text, Bitmap bg) {
		super(text, DTextButtonField.TYPE_GREY);
		
		setBackground(BackgroundFactory.createBitmapBackground(bg,
						Background.POSITION_X_CENTER,
						Background.POSITION_Y_CENTER, 
						Background.REPEAT_NONE));
		
		setBackground(Field.VISUAL_STATE_FOCUS, BackgroundFactory.createSolidBackground(DColor.getColor(DColor.COLOR_SELECTED_BG)));
		setHeight(DRes.getPixel(60));
	}
}

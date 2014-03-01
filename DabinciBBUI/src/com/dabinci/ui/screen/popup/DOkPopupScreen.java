package com.dabinci.ui.screen.popup;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;

import com.dabinci.ui.DBitmapTools;
import com.dabinci.ui.DRes;
import com.dabinci.ui.button.DBitmapButtonField;

public class DOkPopupScreen extends DBasePopupScreen {
	public DOkPopupScreen(int type, String message, final Runnable runnableOk) {
		super(type);
		super.lb.setText(message);
		
		Bitmap okButtonBitmap = Bitmap.getBitmapResource("ico_ok.png");
		Bitmap okButtonBitmapResized = DBitmapTools.ResizeTransparentBitmap(okButtonBitmap, okButtonBitmap.getWidth() / 2, okButtonBitmap.getHeight() / 2, Bitmap.FILTER_BOX, Bitmap.SCALE_TO_FIT);
		DBitmapButtonField btnOk = new DBitmapButtonField(DRes.getBitmap(okButtonBitmapResized), null, Field.USE_ALL_WIDTH);
		btnOk.setAction(new Runnable() {
			public void run() {
				if (runnableOk != null)
					runnableOk.run();
				close();
			}
		});
		super.btnWrapper.add(btnOk);
	}
}
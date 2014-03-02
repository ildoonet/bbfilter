package com.dabinci.ui.screen.popup;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;

import com.dabinci.ui.DBitmapTools;
import com.dabinci.ui.DRes;
import com.dabinci.ui.button.DBitmapButtonField;

public class DOkCancelPopupScreen extends DOkPopupScreen {

	public DOkCancelPopupScreen(int type, String message, Runnable runnableOk, final Runnable runnableCancel) {
		super(type, message, runnableOk);
		
		Bitmap cancelButtonBitmap = Bitmap.getBitmapResource("ico_cancel.png");
		Bitmap cancelButtonBitmapResized = DBitmapTools.ResizeTransparentBitmap(cancelButtonBitmap, 
				(int) (cancelButtonBitmap.getWidth() * 0.7), (int) (cancelButtonBitmap.getHeight() * 0.7), Bitmap.FILTER_BOX, Bitmap.SCALE_TO_FIT);
		DBitmapButtonField btnCancel = new DBitmapButtonField(DRes.getBitmap(cancelButtonBitmapResized), null, Field.USE_ALL_WIDTH);
		btnCancel.setAction(new Runnable() {
			public void run() {
				if (runnableCancel != null)
					runnableCancel.run();
				close();
			}
		});
		super.btnWrapper.add(btnCancel);
	}

}

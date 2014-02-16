package net.ildoo.app.maintab;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.picker.FilePicker;

import com.dabinci.os.DabinciOSUtil;
import com.dabinci.ui.DResolution;
import com.dabinci.ui.button.DBitmapButtonField;
import com.dabinci.ui.tab.DTabContent;
import com.dabinci.utils.DFileUtils;
import com.dabinci.utils.DLogger;

public class TabGallery extends DTabContent {
	protected static final String TAG = "TabGallery";

	public TabGallery() {
		
		DBitmapButtonField btnGallery = new DBitmapButtonField(
				DResolution.getBitmap(Bitmap.getBitmapResource("ico_gallery_off.png")),
				DResolution.getBitmap(Bitmap.getBitmapResource("ico_gallery.png")), 
				Field.USE_ALL_WIDTH);
		btnGallery.setBackground(Field.VISUAL_STATE_NORMAL, BackgroundFactory.createSolidBackground(Color.WHITE));
		btnGallery.setMargin(getBtnMargin());
		btnGallery.setBorder(getBtnBorder());
		btnGallery.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				FilePicker fp = FilePicker.getInstance();
				DabinciOSUtil.getInstance().setPicturePicker(fp);
				String path = fp.show();
				
				DLogger.tlog(TAG, "load image " + path);
				if (path == null)
					return;
				
				byte[] bmpbyte = DFileUtils.getBytesFromFile(path);
				
				if (bmpbyte == null)
					return;
				
				Bitmap bmp = Bitmap.createBitmapFromBytes(bmpbyte, 0, bmpbyte.length, 1);

				DLogger.tlog(TAG, "done-");
			}
		});
		add(btnGallery);
	}
	
	public void requestRefresh() {

	}
}

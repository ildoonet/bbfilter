package net.ildoo.app.maintab;

import net.ildoo.app.FilterConfig;
import net.ildoo.app.filterlist.FilterListScreen;
import net.ildoo.bbfilter.FilterCache;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.picker.FilePicker;

import com.dabinci.os.DabinciOSUtil;
import com.dabinci.ui.DRes;
import com.dabinci.ui.button.DBitmapButtonField;
import com.dabinci.ui.tab.DTabContent;
import com.dabinci.utils.DFileUtils;
import com.dabinci.utils.DLogger;

public class TabGallery extends DTabContent {
	protected static final String TAG = "TabGallery";

	public TabGallery() {
		
		DBitmapButtonField btnGallery = new DBitmapButtonField(
				DRes.getBitmap(Bitmap.getBitmapResource("ico_gallery_off.png")),
				DRes.getBitmap(Bitmap.getBitmapResource("ico_gallery.png")), 
				Field.USE_ALL_WIDTH);
		btnGallery.setBackground(Field.VISUAL_STATE_NORMAL, BackgroundFactory.createSolidBackground(Color.WHITE));
		btnGallery.setMargin(getBtnMargin());
		btnGallery.setBorder(getBtnBorder());
		btnGallery.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				startImagePicker();
			}
		});
		add(btnGallery);
	}
	
	public void requestRefresh() {

	}
	
	private void startImagePicker() {
		FilePicker fp = FilePicker.getInstance();
		DabinciOSUtil.getInstance().setPicturePicker(fp);
		String path = fp.show();
		
		DLogger.tlog(TAG, "load image " + path);
		if (path == null)
			return;
		
		byte[] bmpbyte = DFileUtils.getBytesFromFile(path);
		
		if (bmpbyte == null || bmpbyte.length == 0)
			return;
		
		Bitmap bitmap = Bitmap.createBitmapFromBytes(bmpbyte, 0, bmpbyte.length, 1);

		int width = FilterConfig.getInstance().getInt(FilterConfig.KEY_RESOLUTION_WIDTH, Display.getWidth());
		int height = FilterConfig.getInstance().getInt(FilterConfig.KEY_RESOLUTION_HEIGHT, Display.getHeight());
		
		Bitmap resized = bitmap;
		if (bitmap.getWidth() * bitmap.getHeight() > width * height) {
			resized = new Bitmap(width, height);
			bitmap.scaleInto(resized, Bitmap.FILTER_LANCZOS);
		}
		
		FilterListScreen s = new FilterListScreen(resized);
		UiApplication.getUiApplication().pushScreen(s);
		
		FilterCache.getInstance().clear();
	}
}

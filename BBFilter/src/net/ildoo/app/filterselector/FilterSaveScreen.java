package net.ildoo.app.filterselector;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.NullField;

import com.dabinci.ui.DRes;
import com.dabinci.ui.button.DBitmapButtonField;

public class FilterSaveScreen extends FilterBaseScreen {

	public FilterSaveScreen(String title, final Bitmap bitmap, Bitmap blurred) {
		super(title, bitmap, blurred);
		
		manager.getThumbnailManager().add(new NullField(Field.FOCUSABLE));
		
		// add button in status
		DBitmapButtonField btnSave = new DBitmapButtonField(
				DRes.getBitmap(Bitmap.getBitmapResource("ico_camera_off.png")),
				DRes.getBitmap(Bitmap.getBitmapResource("ico_camera.png")),
				Field.USE_ALL_WIDTH
			);
		btnSave.setMargin(new XYEdges(DRes.getPixel(5), DRes.getPixel(5), DRes.getPixel(5), DRes.getPixel(5)));
		setStatus(btnSave);
		
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
				manager.setBitmap(bitmap);
			}
		});
	}
}

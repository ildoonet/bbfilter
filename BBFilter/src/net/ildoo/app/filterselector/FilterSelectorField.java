package net.ildoo.app.filterselector;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;

import com.dabinci.ui.manager.DCenterManager;
import com.dabinci.ui.manager.DHorizontalEvenManager;
import com.dabinci.utils.DLogger;

public class FilterSelectorField extends VerticalFieldManager {
	private final static String TAG = "FilterSelectorField";
	
	private final DCenterManager centerManager;
	private final BitmapField bitmapField;
	private final HorizontalFieldManager thumbs;
	private final DHorizontalEvenManager buttonWrapper;
	
	public FilterSelectorField() {
		super(USE_ALL_WIDTH | NO_VERTICAL_SCROLL | USE_ALL_HEIGHT);
		
		add(centerManager = new DCenterManager());
		centerManager.add(bitmapField = new BitmapField());

		thumbs = new HorizontalFieldManager(Field.FIELD_HCENTER);
		add(thumbs);
		
		buttonWrapper = new DHorizontalEvenManager(0);
		add(buttonWrapper);
	}
	
	protected void sublayout(int maxWidth, int maxHeight) {
		super.sublayout(maxWidth, maxHeight);

		// button wrapper
		setPositionChild(buttonWrapper, 0, getHeight() - buttonWrapper.getHeight());
		maxHeight -= buttonWrapper.getHeight();
		
		// thumbs
		layoutChild(thumbs, maxWidth, maxHeight);
		
		centerManager.setWidth(maxWidth);
		centerManager.setHeight(maxHeight - thumbs.getHeight());
		layoutChild(centerManager, maxWidth, maxHeight - thumbs.getHeight());
		setPositionChild(centerManager, 0, 0);
		
		setPositionChild(thumbs, thumbs.getLeft(), maxHeight - thumbs.getHeight());
	}
	

	protected void addBottomButton(Field field) {
		buttonWrapper.add(field);
	}
	
	public Manager getThumbnailManager() {
		return thumbs;
	}
	
	public void setBitmap(Bitmap bitmap) {
		DLogger.log(TAG, "setBitmap() +");
		final int width = (int) (centerManager.getWidth() * 0.9);
		final int height = (int) (centerManager.getHeight() * 0.8);
		
		Bitmap resized = bitmap;
		if (width < bitmap.getWidth() || height < bitmap.getHeight()) {
			final float ratio = Math.min((float) width / bitmap.getWidth(), (float) height / bitmap.getHeight());
			resized = new Bitmap(
					(int) (bitmap.getWidth() * ratio), 
					(int) (bitmap.getHeight() * ratio));
			bitmap.scaleInto(resized, Bitmap.FILTER_BOX, Bitmap.SCALE_TO_FIT);
		}
		
		bitmapField.setBitmap(resized);
		DLogger.log(TAG, "setBitmap() -");
	}
}

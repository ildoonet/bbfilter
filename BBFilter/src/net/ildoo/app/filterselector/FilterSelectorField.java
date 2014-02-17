package net.ildoo.app.filterselector;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;

import com.dabinci.ui.manager.DCenterManager;

public class FilterSelectorField extends VerticalFieldManager {
	
	private final DCenterManager centerManager;
	private final BitmapField bitmapField;
	private final HorizontalFieldManager thumbs;
	
	public FilterSelectorField() {
		super(USE_ALL_WIDTH | NO_VERTICAL_SCROLL);
		
		add(centerManager = new DCenterManager());
		centerManager.add(bitmapField = new BitmapField());

		thumbs = new HorizontalFieldManager(Field.FIELD_HCENTER);
		add(thumbs);
	}
	
	protected void sublayout(int maxWidth, int maxHeight) {
		super.sublayout(maxWidth, maxHeight);

		layoutChild(thumbs, maxWidth, maxHeight);
		
		centerManager.setWidth(maxWidth);
		centerManager.setHeight(maxHeight - thumbs.getHeight());
		layoutChild(centerManager, maxWidth, maxHeight - thumbs.getHeight());
		setPositionChild(centerManager, 0, 0);
		
		setPositionChild(thumbs, thumbs.getLeft(), maxHeight - thumbs.getHeight());
	}
	
	public Manager getThumbnailManager() {
		return thumbs;
	}
	
	public void setBitmap(Bitmap bitmap) {
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
	}
}

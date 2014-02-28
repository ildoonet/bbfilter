package net.ildoo.app.filterselector;

import net.ildoo.bbfilter.FilterListener;
import net.ildoo.bbfilter.FilterManager;
import net.ildoo.bbfilter.FilteredBitmap;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.dabinci.ui.screen.DMainScreen;
import com.dabinci.ui.tab.DTabToolTipManager;
import com.dabinci.utils.DLogger;

abstract class FilterBaseScreen extends DMainScreen implements FilterListener {
	private final static String TAG = "PreviewScreen";
	protected final FilterManager filterManager;

	protected final DTabToolTipManager tooltipManager;
	protected final FilterSelectorField manager;
	
	public FilterBaseScreen(String title, final Bitmap bitmap, Bitmap blurred) {
		super(Manager.NO_HORIZONTAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
		setTitle(title);
		setBackground(Color.BLACK);
//		getMainManager().setBackground(BackgroundFactory.createSolidBackground(DColor.getColor(DColor.COLOR_BG_GREY)));
	
		filterManager = new FilterManager(this);
		if (blurred == null)
			filterManager.requestBlurredBacgkround(bitmap, getMainManager());
		else
			onBlurred(blurred, getMainManager());
		
		add(tooltipManager = new DTabToolTipManager(manager = new FilterSelectorField()));
	}
	
	// FilterListener Implementation
	
	public void onThumbnailed(Bitmap originalThumb, FilteredBitmap[] thumbs) {}
	
	public void onProgressChanged(float percentage) {
	}
	
	public void onFiltered(Bitmap bitmap) {}
	
	public void onFilterFail() {
	}
	
	public void onBlurred(Bitmap bitmap, Field field) {
		DLogger.log(TAG, "onBlurred()+");
		if (field != null)
			field.setBackground(BackgroundFactory.createBitmapBackground(bitmap, Background.POSITION_X_CENTER, Background.POSITION_Y_CENTER, Background.REPEAT_NONE));
		DLogger.log(TAG, "onBlurred()-");
	}
}

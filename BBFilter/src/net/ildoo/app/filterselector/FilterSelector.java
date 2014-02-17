package net.ildoo.app.filterselector;

import net.ildoo.bbfilter.FilterListener;
import net.ildoo.bbfilter.FilterManager;
import net.ildoo.bbfilter.FilteredBitmap;
import net.ildoo.bbfilter.filter.FilterGroup;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.dabinci.ui.screen.DMainScreen;
import com.dabinci.ui.tab.DTabToolTipManager;
import com.dabinci.utils.DLogger;

public class FilterSelector extends DMainScreen {
	private static final String TAG = "FilterSelector";

	private final DTabToolTipManager tooltipManager;
	private final FilterSelectorField manager;
	
	private final FilterManager filterManager;
	private final FilterGroup filterGroup;
	
	public FilterSelector(final String title, final Bitmap bitmap, final FilterGroup filterGroup) {
		super(Manager.NO_HORIZONTAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
		setTitle(title);
		setBackground(Color.BLACK);
		
		this.filterGroup = filterGroup;

		filterManager = new FilterManager(filterListener);
		filterManager.requestBlurredBacgkround(bitmap, getMainManager());
		filterManager.requestThumbs(bitmap, filterGroup);
		
		add(tooltipManager = new DTabToolTipManager(manager = new FilterSelectorField()));
		tooltipManager.startWaitingDialog(2);
	}

	public void close() {
		super.close();
	};
	
	FilterListener filterListener = new FilterListener() {
		public void onFiltered(Bitmap bitmap) {
			
		}

		public void onThumbnailed(FilteredBitmap[] thumbs) {
			DLogger.log(TAG, "onThumbnailed()+");
			tooltipManager.stopWaitingDialog();
			
			manager.getThumbnailManager().deleteAll();
			for (int i = 0; i < thumbs.length; i ++) {
				BitmapField bf = new BitmapField(thumbs[i].getFilterBitmap());
				manager.getThumbnailManager().add(bf);
			}
			
			DLogger.log(TAG, "onThumbnailed()-");
		}

		public void onBlurred(Bitmap bitmap, Field field) {
			DLogger.log(TAG, "onBlurred()+");
			tooltipManager.stopWaitingDialog();
			if (field != null)
				field.setBackground(BackgroundFactory.createBitmapBackground(bitmap, Background.POSITION_X_CENTER, Background.POSITION_Y_CENTER, Background.REPEAT_NONE));
			DLogger.log(TAG, "onBlurred()-");
		}

		public void onProgressChanged(float percentage) {
		}

		public void onFilterFail() {
		}
	};
}

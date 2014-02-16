package net.ildoo.app.filterselector;

import net.ildoo.bbfilter.FilterListener;
import net.ildoo.bbfilter.FilterManager;
import net.ildoo.bbfilter.FilteredBitmap;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;

import com.dabinci.ui.screen.DMainScreen;
import com.dabinci.ui.tab.DTabToolTipManager;
import com.dabinci.utils.DLogger;

public class FilterSelector extends DMainScreen {
	private static final String TAG = "FilterSelector";
	
	private final DTabToolTipManager tooltipManager;
	private final VerticalFieldManager manager;
	
	public FilterSelector(final String title, final Bitmap bitmap) {
		super(Manager.NO_HORIZONTAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
		setTitle(title);
		setBackground(Color.BLACK);

		FilterManager filterManager = new FilterManager(filterListener);
		filterManager.requestBlurredBacgkround(bitmap, getMainManager());
		
		add(tooltipManager = new DTabToolTipManager(manager = new VerticalFieldManager()));
		
		tooltipManager.startWaitingDialog();
	}
	
	FilterListener filterListener = new FilterListener() {
		public void onFiltered(Bitmap bitmap) {
			
		}

		public void onThumbnailed(FilteredBitmap[] thumbs) {
			
		}

		public void onBlurred(Bitmap bitmap, Field field) {
			DLogger.log(TAG, "onBlurred()+");
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

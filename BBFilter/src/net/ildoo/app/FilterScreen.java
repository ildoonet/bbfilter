package net.ildoo.app;

import net.ildoo.bbfilter.FilterListener;
import net.ildoo.bbfilter.FilterManager;
import net.ildoo.bbfilter.FilteredBitmap;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.FlowFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class FilterScreen extends MainScreen
{
	private static final String TAG = "FilterScreen";

	private FilterManager filterManager;
	
    /**
     * Creates a new FilterScreen object
     */
    public FilterScreen()
    {        
        // Set the displayed title of the screen       
        setTitle("MyTitle");

        filterManager = new FilterManager(listener);

        add(ffm = new FlowFieldManager());
        
        UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
		        Bitmap bitmap = Bitmap.getBitmapResource("testpic.jpg");
		        
		        TimerLogger.tlog(TAG, "start");
		        ffm.add(new BitmapField(bitmap));
		        TimerLogger.tlog(TAG, "original end");

		        filterManager.requestThumbs(bitmap, filterManager.getFilterGroup(0));
		        filterManager.requestBlurredBacgkround(bitmap, getMainManager());
			}
		});
    }
    
    FilterListener listener = new FilterListener() {
		public void onThumbnailed(FilteredBitmap[] thumbs) {
			TimerLogger.log(TAG, "onThumbnailed()+");
			for (int i = 0; i < thumbs.length; i++) {
				ffm.add(new BitmapField((
						thumbs[i].getFilterBitmap()
					)));
			}
			TimerLogger.log(TAG, "onThumbnailed()-");
		}
		
		public void onFiltered(Bitmap bitmap) {
			
		}
		
		public void onBlurred(Bitmap bitmap, Field field) {
			TimerLogger.log(TAG, "onBlurred()+");
			if (field != null)
				field.setBackground(BackgroundFactory.createBitmapBackground(bitmap, Background.POSITION_X_LEFT, Background.POSITION_Y_TOP, Background.REPEAT_SCALE_TO_FIT));
			TimerLogger.log(TAG, "onBlurred()-");
		}
		
		public void onProgressChanged(float percentage) {
			TimerLogger.log(TAG, "onProgressChanged()+ " + percentage);
		}
		
		public void onFilterFail() {
			TimerLogger.log(TAG, "onFilterFail()+");
		}
	};

	private FlowFieldManager ffm;
}

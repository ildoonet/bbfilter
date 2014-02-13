package net.ildoo.app;

import net.ildoo.bbfilter.FilterListener;
import net.ildoo.bbfilter.FilterManager;
import net.ildoo.bbfilter.FilteredBitmap;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.FlowFieldManager;
import net.rim.device.api.ui.container.MainScreen;

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
        
        
        Bitmap bitmap = Bitmap.getBitmapResource("testpic.jpg");

        ffm = new FlowFieldManager();
        
        TimerLogger.tlog(TAG, "start");
        ffm.add(new BitmapField(bitmap));
        TimerLogger.tlog(TAG, "original end");

        filterManager.requestThumbs(bitmap, filterManager.getFilterGroup(0));
//        ffm.add(new BitmapField((new FilterSunrise().filtering(bitmap))));
//        TimerLogger.log(TAG, "FilterSunrise end");
//        ffm.add(new BitmapField((new FilterCalm().filtering(bitmap))));
//        TimerLogger.log(TAG, "FilterCalm end");
//        ffm.add(new BitmapField((new FilterCool().filtering(bitmap))));
//        TimerLogger.log(TAG, "FilterCool end");
//        ffm.add(new BitmapField((new FilterWhiteCat().filtering(bitmap))));
//        TimerLogger.log(TAG, "FilterWhiteCat end");
//        ffm.add(new BitmapField((new FilterAntique().filtering(bitmap))));
//        TimerLogger.log(TAG, "FilterAntique end");
//        ffm.add(new BitmapField((new FilterBW().filtering(bitmap))));
//        TimerLogger.log(TAG, "FilterBW end");
//        ffm.add(new BitmapField((new FilterSepia().filtering(bitmap))));
//        TimerLogger.log(TAG, "FilterSepia end");
//        ffm.add(new BitmapField((new FilterToy().filtering(bitmap))));
//        TimerLogger.log(TAG, "FilterToy end");
//        ffm.add(new BitmapField((new FilterToyVignette().filtering(bitmap))));
//        TimerLogger.log(TAG, "FilterToyVignette end");
//        ffm.add(new BitmapField((new FilterSepia2().filtering(bitmap))));
//        TimerLogger.log(TAG, "FilterSepia2 end");
//        ffm.add(new BitmapField((new FilterRomance().filtering(bitmap))));
//        TimerLogger.log(TAG, "FilterRomance end");
//        ffm.add(new BitmapField((new FilterClear().filtering(bitmap))));
//        TimerLogger.log(TAG, "FilterClear end");
//        
//        ffm.add(new BitmapField((new FilterStark().filtering(bitmap))));
//        TimerLogger.log(TAG, "stark end");
//        ffm.add(new BitmapField((new FilterSunnySide().filtering(bitmap))));
//        TimerLogger.log(TAG, "sunnyside end");
//        ffm.add(new BitmapField((new FilterVintage().filtering(bitmap))));
//        TimerLogger.log(TAG, "vintage end");
        
//        ffm.add(new BitmapField((new FilterWorn().filtering(bitmap))));
//        TimerLogger.log(TAG, "worn end");
//        ffm.add(new BitmapField((new FilterWornBottomGradient().filtering(bitmap))));
//        TimerLogger.log(TAG, "wormbottomgradient end");
//        ffm.add(new BitmapField((new FilterStark2().filtering(bitmap))));
//        TimerLogger.log(TAG, "stark2 end");
        
        add(ffm);
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
		
		public void onProgressChanged(float percentage) {
			
		}
		
		public void onFilterFail() {
			TimerLogger.log(TAG, "onFilterFail()+");
		}
	};

	private FlowFieldManager ffm;
}

package net.ildoo.app;

import net.ildoo.bbfilter.filter.FilterStark;
import net.ildoo.bbfilter.filter.FilterStark2;
import net.ildoo.bbfilter.filter.FilterSunnySide;
import net.ildoo.bbfilter.filter.FilterVintage;
import net.ildoo.bbfilter.filter.FilterVintageVinette;
import net.ildoo.bbfilter.filter.FilterWorn;
import net.ildoo.bbfilter.filter.FilterWornBottomGradient;
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
	
    /**
     * Creates a new FilterScreen object
     */
    public FilterScreen()
    {        
        // Set the displayed title of the screen       
        setTitle("MyTitle");
        
        Bitmap bitmap = Bitmap.getBitmapResource("testpic.jpg");
        
        FlowFieldManager ffm = new FlowFieldManager();
        
        TimerLogger.log(TAG, "start");
        ffm.add(new BitmapField(bitmap));
        TimerLogger.log(TAG, "original end");
        ffm.add(new BitmapField((new FilterStark().filtering(bitmap))));
        TimerLogger.log(TAG, "stark end");
        ffm.add(new BitmapField((new FilterSunnySide().filtering(bitmap))));
        TimerLogger.log(TAG, "sunnyside end");
        ffm.add(new BitmapField((new FilterVintage().filtering(bitmap))));
        TimerLogger.log(TAG, "vintage end");
        ffm.add(new BitmapField((new FilterVintageVinette().filtering(bitmap))));
        TimerLogger.log(TAG, "vintagevinette end");
        ffm.add(new BitmapField((new FilterWorn().filtering(bitmap))));
        TimerLogger.log(TAG, "worn end");
        ffm.add(new BitmapField((new FilterWornBottomGradient().filtering(bitmap))));
        TimerLogger.log(TAG, "wormbottomgradient end");
        ffm.add(new BitmapField((new FilterStark2().filtering(bitmap))));
        TimerLogger.log(TAG, "stark2 end");
        
        add(ffm);
    }
}

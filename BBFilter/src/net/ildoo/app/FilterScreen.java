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
    /**
     * Creates a new FilterScreen object
     */
    public FilterScreen()
    {        
        // Set the displayed title of the screen       
        setTitle("MyTitle");
        
        Bitmap bitmap = Bitmap.getBitmapResource("testpic.jpg");
        
        FlowFieldManager ffm = new FlowFieldManager();
        
        ffm.add(new BitmapField(bitmap));
        ffm.add(new BitmapField((new FilterStark().filtering(bitmap))));
        ffm.add(new BitmapField((new FilterSunnySide().filtering(bitmap))));
        ffm.add(new BitmapField((new FilterVintage().filtering(bitmap))));
        ffm.add(new BitmapField((new FilterVintageVinette().filtering(bitmap))));
        ffm.add(new BitmapField((new FilterWorn().filtering(bitmap))));
        ffm.add(new BitmapField((new FilterWornBottomGradient().filtering(bitmap))));
        ffm.add(new BitmapField((new FilterStark2().filtering(bitmap))));
        
        add(ffm);
    }
}

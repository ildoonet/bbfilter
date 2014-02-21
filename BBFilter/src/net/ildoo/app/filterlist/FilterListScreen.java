package net.ildoo.app.filterlist;

import net.ildoo.bbfilter.FilterManager;
import net.rim.device.api.system.Bitmap;

import com.dabinci.ui.screen.DMainScreen;

public class FilterListScreen extends DMainScreen {
	public FilterListScreen(final Bitmap original) {
		super();
		
		// TODO 
		
		FilterListManager fList = new FilterListManager(FilterManager.getPurchasedFilterGroups(), original);
		add(fList);
	}
}

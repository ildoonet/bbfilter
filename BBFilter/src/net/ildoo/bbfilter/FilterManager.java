package net.ildoo.bbfilter;

import java.util.Vector;

import net.ildoo.app.TimerLogger;
import net.ildoo.bbfilter.filter.Filter;
import net.ildoo.bbfilter.filter.FilterGroup;
import net.ildoo.bbfilter.filter.toy.FilterGroupToy;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.UiApplication;

public class FilterManager {
	private static final String TAG = "FilterManager";
	private static final int THUMBW = 100;
	private static final int THUMBH = 100;
	private static FilterGroup[] filterGroups;
	
	{
		filterGroups = new FilterGroup[] {
				new FilterGroupToy()
		};
	}
	
	private final FilterListener listener;
	
	public FilterManager(final FilterListener listener) {
		this.listener = listener;
	}
	
	public FilterGroup[] getAllFilterGroup() {
		return filterGroups;
	}
	
	public FilterGroup getFilterGroup(final int idx) {
		return filterGroups[idx];
	}
	
	public void requestThumbs(final Bitmap bitmap, final FilterGroup filterGroup) {
		TimerLogger.log(TAG, "requestThumbs()+ " + filterGroup.getGroupName());
		
		Thread worker = new FilterThread() {
			public void run() {
				TimerLogger.tlog(TAG, "requestThumbs Thread +");
				
				final Vector vector = new Vector();
				final Class[] filters = filterGroup.getFilters();
				
				if (filters == null) {
					TimerLogger.tlog(TAG, "filters is null.");
					return;
				}
				
				Bitmap smallBitmap = new Bitmap(THUMBW, THUMBH);
				bitmap.scaleInto(smallBitmap, Bitmap.FILTER_LANCZOS, Bitmap.SCALE_TO_FILL);
				
				for (int i = 0; i < filters.length; i++) {
					try {
						Filter f = (Filter) filters[i].newInstance();
						
						FilteredBitmap fb = new FilteredBitmap();
						fb.setFilterClass(filters[i]);
						fb.setFilterBitmap(f.filtering(smallBitmap));
						
						vector.addElement(fb);
					} catch (Exception e) {
						TimerLogger.log(TAG, "getThumbs() e = " + e.toString());
					}
				}
				
				if (vector.size() == 0) {
					UiApplication.getUiApplication().invokeLater(new Runnable() {
						public void run() {
							listener.onFilterFail();
						}
					});
					return;
				}
				
				final FilteredBitmap[] filteredBitmaps = new FilteredBitmap[vector.size()];
				vector.copyInto(filteredBitmaps);
				
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						listener.onThumbnailed(filteredBitmaps);
					}
				});
				
				TimerLogger.tlog(TAG, "requestThumbs Thread -");
			}
		};
		
		worker.start();
	}
	
	public void requestFilter(final Bitmap bitmap, final Filter filter) {
		
	}
}

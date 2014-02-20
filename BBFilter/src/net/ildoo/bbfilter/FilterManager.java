package net.ildoo.bbfilter;

import java.util.Vector;

import net.ildoo.bbfilter.blur.Blur;
import net.ildoo.bbfilter.filter.Filter;
import net.ildoo.bbfilter.filter.FilterGroup;
import net.ildoo.bbfilter.filter.calm.FilterGroupCalm;
import net.ildoo.bbfilter.filter.classic.FilterGroupClassic;
import net.ildoo.bbfilter.filter.romance.FilterGroupRomance;
import net.ildoo.bbfilter.filter.sepia.FilterGroupSepia;
import net.ildoo.bbfilter.filter.stark.FilterGroupStark;
import net.ildoo.bbfilter.filter.sunny.FilterGroupSunnySide;
import net.ildoo.bbfilter.filter.toy.FilterGroupToy;
import net.ildoo.bbfilter.filter.vintage.FilterGroupVintage;
import net.ildoo.bbfilter.filter.whitecat.FilterGroupWhitecat;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.UiApplication;

import com.dabinci.ui.DResolution;
import com.dabinci.utils.DLogger;

public class FilterManager {
	private static final String TAG = "FilterManager";
	private static final int THUMBW = DResolution.getPixel(75);
	private static final int THUMBH = DResolution.getPixel(75);
	private static FilterGroup[] filterGroups;
	
	static {
		filterGroups = new FilterGroup[] {
				new FilterGroupCalm(),
				new FilterGroupClassic(),
				new FilterGroupRomance(),
				new FilterGroupSepia(),
				new FilterGroupStark(),
				new FilterGroupSunnySide(),
				new FilterGroupToy(),
				new FilterGroupVintage(),
				new FilterGroupWhitecat(),
		};
	}

	public static FilterGroup[] getAllFilterGroups() {
		return filterGroups;
	}
	
	public static FilterGroup getFilterGroup(final int idx) {
		return filterGroups[idx];
	}
	
	public static FilterGroup[] getPurchasedFilterGroups() {
		Vector purchased = new Vector();
		for (int i = 0; i < filterGroups.length; i++) {
			if (filterGroups[i].isPurchased())
				purchased.addElement(filterGroups[i]);
		}
		
		FilterGroup[] purchasedArr = new FilterGroup[purchased.size()];
		purchased.copyInto(purchasedArr);
		
		return purchasedArr;
	}
	
	private final FilterListener listener;
	
	public FilterManager(final FilterListener listener) {
		this.listener = listener;
	}
	
	public void requestThumbs(final Bitmap bitmap, final FilterGroup filterGroup) {
		DLogger.log(TAG, "requestThumbs()+ " + filterGroup.getGroupName());
		
		Thread worker = new FilterThread() {
			public void run() {
				DLogger.tlog(TAG, "requestThumbs Thread +");
				
				final Vector vector = new Vector();
				final Class[] filters = filterGroup.getFilters();
				
				if (filters == null) {
					DLogger.tlog(TAG, "filters is null.");
					return;
				}
				
				final Bitmap smallBitmap = new Bitmap(THUMBW, THUMBH);
				bitmap.scaleInto(smallBitmap, Bitmap.FILTER_LANCZOS, Bitmap.SCALE_TO_FILL);
				
				for (int i = 0; i < filters.length; i++) {
					try {
						if (isCanceled() == true) {
							return;
						}
						
						Filter f = (Filter) filters[i].newInstance();
						
						FilteredBitmap fb = new FilteredBitmap();
						fb.setFilterName(f.getName());
						fb.setFilterClass(filters[i]);
						fb.setFilterBitmap(f.filtering(smallBitmap, false));
						
						vector.addElement(fb);
						
						listener.onProgressChanged((float)i / filters.length);
					} catch (Exception e) {
						DLogger.log(TAG, "getThumbs() e = " + e.toString());
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
						listener.onThumbnailed(smallBitmap, filteredBitmaps);
					}
				});
				
				DLogger.tlog(TAG, "requestThumbs Thread -");
			}
		};
		
		worker.start();
	}
	
	public void requestBlurredBacgkround(final Bitmap bitmap, final Field field) {
		DLogger.log(TAG, "requestThumbs()+");
		
		Thread worker = new FilterThread(false) {
			public void run() {
				final int width = field.getWidth();
				final int height = field.getHeight();
				
				Bitmap resized = new Bitmap(width, height);
				bitmap.scaleInto(resized, Bitmap.FILTER_BOX, Bitmap.SCALE_TO_FILL);
				
				final Bitmap blurred = Blur.fastblur(resized, 20);
				
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						listener.onBlurred(blurred, field);
					}
				});
			}
		};
		
		worker.start();
	}
	
	public void requestFilter(final Bitmap bitmap, final Filter filter) {
		Thread worker = new FilterThread() {
			public void run() {
				final Bitmap filtered = filter.filtering(bitmap, true);
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						listener.onFiltered(filtered);
					}
				});
			}
		};
		worker.start();
	}
	
	public void cancelAll() {
		FilterThread.getCurrentWorker().cancel();
		FilterThread.getCurrentWorker().interrupt();
	}
}

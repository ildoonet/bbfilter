package net.ildoo.bbfilter.filter;

import net.rim.device.api.system.Bitmap;

public abstract class FilterGroup {
	protected Class[] filterList;
	
	public FilterGroup() {
		initGroup();
	}
	
	public abstract void initGroup();
	public abstract String getGroupName();
	
	public Bitmap getSampleBitmap() {
		return Bitmap.getBitmapResource("sample1.jpg");
	}
	
	public Class[] getFilters() {
		return filterList;
	}
	
	public boolean isPurchased() {
		return true;
	}
}

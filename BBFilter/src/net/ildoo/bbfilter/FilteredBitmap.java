package net.ildoo.bbfilter;

import net.rim.device.api.system.Bitmap;

public class FilteredBitmap {
	private Class filterClass;
	private Bitmap filterBitmap;
	
	public Class getFilterClass() {
		return filterClass;
	}
	public void setFilterClass(Class filterClass) {
		this.filterClass = filterClass;
	}
	public Bitmap getFilterBitmap() {
		return filterBitmap;
	}
	public void setFilterBitmap(Bitmap filterBitmap) {
		this.filterBitmap = filterBitmap;
	}
}

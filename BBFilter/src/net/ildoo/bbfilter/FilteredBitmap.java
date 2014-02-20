package net.ildoo.bbfilter;

import net.rim.device.api.system.Bitmap;

public class FilteredBitmap {
	private String filterName;
	private Class filterClass;
	private Bitmap filterBitmap;
	
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
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

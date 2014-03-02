package net.ildoo.bbfilter.filter.whitecat;

import net.ildoo.bbfilter.filter.FilterGroup;
import net.rim.device.api.system.Bitmap;

public class FilterGroupWhitecat extends FilterGroup {
	public void initGroup() {
		filterList = new Class[] {
			FilterBW.class,
			FilterWhiteCat.class
		};
	}

	public String getGroupName() {
		return "white cat".toUpperCase();
	}

	public Bitmap getTitleBitmap() {
		return Bitmap.getBitmapResource("sample3.jpg");
	}
}

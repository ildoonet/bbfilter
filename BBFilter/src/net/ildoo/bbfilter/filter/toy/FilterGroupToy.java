package net.ildoo.bbfilter.filter.toy;

import net.ildoo.bbfilter.filter.FilterGroup;
import net.rim.device.api.system.Bitmap;

public class FilterGroupToy extends FilterGroup {

	public void initGroup() {
		filterList = new Class[] {
			FilterToy.class,
			FilterToyVignette.class,
		};
	}

	public String getGroupName() {
		return "Toy".toUpperCase();
	}
	
	public Bitmap getTitleBitmap() {
		return Bitmap.getBitmapResource("sample5.jpg");
	}
}

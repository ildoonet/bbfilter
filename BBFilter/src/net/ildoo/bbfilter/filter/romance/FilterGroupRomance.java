package net.ildoo.bbfilter.filter.romance;

import net.ildoo.bbfilter.filter.FilterGroup;
import net.rim.device.api.system.Bitmap;

public class FilterGroupRomance extends FilterGroup {

	public void initGroup() {
		filterList = new Class[] {
			FilterRomance.class,
			FilterRomanceVignette.class,
			FilterClear.class,
			FilterClearVignette.class
		};
	}

	public String getGroupName() {
		return "romance".toUpperCase();
	}

	public Bitmap getTitleBitmap() {
		return Bitmap.getBitmapResource("sample7.jpg");
	}
}

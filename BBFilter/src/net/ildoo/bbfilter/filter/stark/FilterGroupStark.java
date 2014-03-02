package net.ildoo.bbfilter.filter.stark;

import net.ildoo.bbfilter.filter.FilterGroup;
import net.rim.device.api.system.Bitmap;

public class FilterGroupStark extends FilterGroup {

	public void initGroup() {
		filterList = new Class[] {
			FilterStark.class,
			FilterStark2.class
		};
	}

	public String getGroupName() {
		return "stark".toUpperCase();
	}

	public Bitmap getTitleBitmap() {
		return Bitmap.getBitmapResource("sample5.jpg");
	}
}

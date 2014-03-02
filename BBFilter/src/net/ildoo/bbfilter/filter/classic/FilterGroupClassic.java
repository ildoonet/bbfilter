package net.ildoo.bbfilter.filter.classic;

import net.ildoo.bbfilter.filter.FilterGroup;
import net.rim.device.api.system.Bitmap;

public class FilterGroupClassic extends FilterGroup {

	public void initGroup() {
		filterList = new Class[] {
			FilterAntique.class,
			FilterCool.class,
			FilterToy.class
		};
	}

	public String getGroupName() {
		return "classic".toUpperCase();
	}

	public Bitmap getTitleBitmap() {
		return Bitmap.getBitmapResource("sample6.jpg");
	}
}

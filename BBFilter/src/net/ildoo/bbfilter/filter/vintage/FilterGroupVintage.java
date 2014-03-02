package net.ildoo.bbfilter.filter.vintage;

import net.ildoo.bbfilter.filter.FilterGroup;
import net.rim.device.api.system.Bitmap;

public class FilterGroupVintage extends FilterGroup {

	public void initGroup() {
		filterList = new Class[] {
			FilterVintage.class,
			FilterVintageVinette.class,
			FilterWorn.class,
			FilterWornBottomGradient.class
		};
	}

	public String getGroupName() {
		return "vintage".toUpperCase();
	}

	public Bitmap getTitleBitmap() {
		return Bitmap.getBitmapResource("sample2.jpg");
	}
}

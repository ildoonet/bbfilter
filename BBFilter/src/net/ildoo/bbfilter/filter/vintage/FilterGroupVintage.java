package net.ildoo.bbfilter.filter.vintage;

import net.ildoo.bbfilter.filter.FilterGroup;

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

}
